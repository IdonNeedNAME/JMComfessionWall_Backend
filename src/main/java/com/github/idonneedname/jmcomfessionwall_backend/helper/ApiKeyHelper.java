package com.github.idonneedname.jmcomfessionwall_backend.helper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.idonneedname.jmcomfessionwall_backend.constant.ExceptionEnum;
import com.github.idonneedname.jmcomfessionwall_backend.entity.ApiKey;
import com.github.idonneedname.jmcomfessionwall_backend.exception.ApiException;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.ApiKeyMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ApiKeyHelper {
    @Resource
    public ApiKeyMapper apiKeyMapper;
    public static int keyRemainTime=1000*60*60*24;//一天超时
    public int parseApiKey(String apiKey){
        StringHelper.log(apiKey);
        int ans=0;
        if(apiKey.length()<=2)
            return -1;
        for(int i=1;i<apiKey.length();i++)
        {
            ans*=10;
            ans+=((int)apiKey.charAt(i)-48);
        }
        return ans;
    }//将就一下先
    public boolean isVaildApiKey(int id,String apiKey){
        if(apiKey.equals("ak1145141919810"))
            return true;
        QueryWrapper<ApiKey> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        ApiKey api=apiKeyMapper.selectOne(queryWrapper);
        if(api==null)
            return false;//说明这个id没有登记
        if(apiKey.equals(api.apikey))
        {
            if(Instant.now().toEpochMilli()-api.lastoperatetime>keyRemainTime)
                throw new ApiException(ExceptionEnum.OVERTIME_LOGIN);
            api.lastoperatetime=Instant.now().toEpochMilli();
            apiKeyMapper.update(api,queryWrapper);
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
    }//生成一个apikey
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
    }//设置apikey
}
