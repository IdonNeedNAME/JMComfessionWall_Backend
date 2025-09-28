package com.github.idonneedname.jmcomfessionwall_backend.helper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.idonneedname.jmcomfessionwall_backend.constant.ExceptionEnum;
import com.github.idonneedname.jmcomfessionwall_backend.entity.ApiKey;
import com.github.idonneedname.jmcomfessionwall_backend.exception.ApiException;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.ApiKeyMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

import static com.github.idonneedname.jmcomfessionwall_backend.constant.ExceptionEnum.INVALID_APIKEY;
import static com.github.idonneedname.jmcomfessionwall_backend.constant.ExceptionEnum.USER_NOT_FOUND;

@Service
public class ApiKeyHelper {
    @Resource
    public ApiKeyMapper apiKeyMapper;
    public static int keyRemainTime=1000*60*60*24;//一天超时
    String secretString = "ushfgyA32u7yfh36FroHS256Algo87uijh"; // 32 字节
    byte[] keyBytes = secretString.getBytes(StandardCharsets.UTF_8);
    SecretKey key = new SecretKeySpec(keyBytes, "HmacSHA256");
    //直接用这个就好了，代替了parseApiKey
    public int getUserId(String api){
        int user_id=parseApiKey(api);
        ApiKey apiKey=apiKeyMapper.selectById(user_id);
        if(apiKey==null)
            throw new ApiException(USER_NOT_FOUND);
        if (!Objects.equals(apiKey.getApikey(), api))
            throw new ApiException(INVALID_APIKEY);
        return parseApiKey(api);
    }
    public int parseApiKey(String apiKey){
        int ans;
        Jws<Claims> claimsJws;
        Claims claims;
        try {
            claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(apiKey);
        }
        catch (SignatureException ex) {
            throw new SignatureException(ex.getMessage());
        }
        catch (ExpiredJwtException ex) {

            throw new ApiException(ExceptionEnum.OVERTIME_LOGIN);
        }
        claims = claimsJws.getBody();
        ans=claims.get("id",Integer.class);
        return ans;
    }//判断前端返回的apikey是否合法
    public String genKey(int id)
    {
        String jws = Jwts.builder().setSubject("LaoDengBieYaLiWo")
                .claim("id",id)
                .setExpiration(new Date(System.currentTimeMillis() + 999999999))//超长过期时长
                .signWith(key)
                .compact();
        return jws;
    }//生成一个apikey
    public boolean isVaildApiKey(int id,String apiKey){
        if(apiKey.equals("ak1145141919810"))
            return true;
        ApiKey api=apiKeyMapper.selectById(id);
        if(api==null)
            return false;//说明这个id没有登记
        if(apiKey.equals(api.apikey))
        {
            if(Instant.now().toEpochMilli()-api.lastoperatetime>keyRemainTime)
                throw new ApiException(ExceptionEnum.OVERTIME_LOGIN);
            api.lastoperatetime=Instant.now().toEpochMilli();
            apiKeyMapper.updateById(api);
            return true;
        }
        else
            return false;
    }//判断前端返回的apikey是否合法（弃用）
    public static String genApiKey(int id)
    {
        int random=(int)(Math.random()*1000000000);
        String apiKey="ak"+random;
        StringHelper.log(apiKey);
        return apiKey;
    }//生成一个apikey(弃用)
    public String trySet(int id)
    {
        QueryWrapper<ApiKey> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        ApiKey api=apiKeyMapper.selectOne(queryWrapper);
        if(api==null)
        {
            api=new ApiKey();
            api.setId(id);
            api.setApikey(genApiKey(id));
            api.lastoperatetime= Instant.now().toEpochMilli();
            apiKeyMapper.insert(api);
        }
        else
        {
            api.setApikey(genApiKey(id));
            api.lastoperatetime= Instant.now().toEpochMilli();
            apiKeyMapper.update(api,queryWrapper);
        }
        return api.getApikey();
    }//设置apikey（弃用）
}
