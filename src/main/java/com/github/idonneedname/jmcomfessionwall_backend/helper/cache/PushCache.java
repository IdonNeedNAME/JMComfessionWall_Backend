package com.github.idonneedname.jmcomfessionwall_backend.helper.cache;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.idonneedname.jmcomfessionwall_backend.constant.Constant;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;
import com.github.idonneedname.jmcomfessionwall_backend.entity.User;
import com.github.idonneedname.jmcomfessionwall_backend.helper.ArrayNodeHelper;
import com.github.idonneedname.jmcomfessionwall_backend.helper.StringHelper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public class PushCache {
    @Resource
    public UserMapper userMapper;
    public InfoPool<Integer> pool1,pool2;
    public int read=1;
    public int maxOfPush=5;
    public float baseProbability=80;
    public boolean reversible,shouldSort=false;
    public PushCache(){
        pool1=new InfoPool<>();
        pool2=new InfoPool<>();
        //resolvedPool();
    }
    public boolean guess(float probability)
    {
        float random=(float)Math.random()*100;
        return random<probability;
    }
    //获取可读数据池
    public InfoPool<Integer> getReadPool()
    {
        if(read==1) return pool1;
        else return pool2;
    }
    //获取可写数据池
    public InfoPool<Integer> getWritePool()
    {
        if(read==1) return pool2;
        else return pool1;
    }
    //数据池轮换
    public void reverse()
    {
        if(read==1) read=2;
        else read=1;
    }
    public Post getPost(int id)
    {
        return Constant.postCache.tryFindById(id);
    }
    public boolean isValidPost(int postId, int userId)
    {
        Post post=Constant.postCache.tryFindById(postId);
        if(post==null) return false;
        return isValidPost(post,userId);
    }
    public boolean isValidPost(Post post, int userId)
    {
        if(post.hidden) return false;
        if(!post.ispublic&&userId!=post.host)
            return false;
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("id",post.host);
        User user=userMapper.selectOne(wrapper);
        if(user==null) return true;
        if(ArrayNodeHelper.idInArray(user.blacklist,userId)!=-1)
            return false;
        return true;
    }
    //获取推送
    public List<Post> getRecommended(int id)
    {
        StringHelper.log(pool1.pool.size());
        StringHelper.log(pool2.pool.size());
        InfoPool<Integer> pool = getReadPool();
        List<Post> availableTemp=new ArrayList<>();
        List<Post> selectedTemp=new ArrayList<>();
        pool.notifyReadingStart();
        Session session;
        session=Constant.sessionCache.userSession(id);
        try{
            int p=0;
            while(selectedTemp.size()<maxOfPush&&p<pool.pool.size())
            {
                log.info("P: "+p);
                Post post=getPost(pool.pool.get(p));
                if(!isValidPost(post,id))
                {
                    p++;
                    continue;
                }

                if(session.userHasVisited(pool.pool.get(p)))
                {
                        availableTemp.add(post);
                }
                else
                {
                    if(guess(baseProbability))
                    {
                        selectedTemp.add(post);
                    }
                    else
                    {
                        availableTemp.add(post);
                    }
                }
                p++;
            }
            for(int i=selectedTemp.size();i<maxOfPush&&i<availableTemp.size();i++)
            {
                selectedTemp.add(availableTemp.get(i));
            }
                   }
        catch(Exception e)
        {
            log.error("recommendError");
            selectedTemp=null;
        }
        pool.notifyReadingEnd();
        if(pool.writable()&&shouldSort)
        {
            shouldSort=false;
            resolvedPool();
        }
           for(int i=0;i<selectedTemp.size();i++)
        {
            session.hadVisited.add(selectedTemp.get(i).id);
        }
        return selectedTemp;
    }
    //获取推荐值
    public static double getRecRate(int id)
    {
        Post post=Constant.postCache.tryFindById(id);
        if(post==null) return -9999999;
        return getRecRate(post);
    }
    public static double getRecRate(Post post)
    {
        return (Math.log10(post.likes+3)+Math.log10(post.comments+3))/((Instant.now().toEpochMilli()-post.date)/(36000000.0f)+0.1f);
    }
    //处理数据池
    public void resolvedPool()
    {
        reversible=false;
        InfoPool<Integer> tar=getWritePool();
        Set<Integer> keys = Constant.postCache.allId;
       // StringHelper.log("keys "+keys.size());
        tar.pool.clear();
        tar.pool.addAll(keys);
        tar.pool.sort((p1, p2) -> {
        double weight1 = getRecRate(p1);
        double weight2 = getRecRate(p2);
        return Double.compare(weight2, weight1);
    });
        reversible=true;
    }
}
