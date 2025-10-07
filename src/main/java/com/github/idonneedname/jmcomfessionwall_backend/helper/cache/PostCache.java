package com.github.idonneedname.jmcomfessionwall_backend.helper.cache;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;
import com.github.idonneedname.jmcomfessionwall_backend.helper.StringHelper;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.PostMapper;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//对post的操作尽量用这个，这样我好处理缓存
@Service
public class PostCache //帖子缓存，包含了删改读写，但直接读列表的还没做，mdJAVA没委托做你妈
{
    public PostMapper postMapper;
    public CacheHelper<Post> posts;
    public Set<Integer> allId;
    public void init()
    {
        allId=new HashSet<>();
        posts=new CacheHelper<>();
        detectAll();
    }
    //从数据库中读取所有post
    public void detectAll()
    {
        posts.map=new HashMap<>();
        List<Post> post = null;
        post = postMapper.selectList(null);
        if(post==null) return ;
        for(int i=0;i<post.size();i++)
        {
            posts.map.put(post.get(i).id, post.get(i));
           // if(post.get(i).ispublic) StringHelper.log("true "+post.get(i).id);
           // else StringHelper.log("false "+post.get(i).id);
            allId.add(post.get(i).id);
        }
    }
    //用id获取post
    public Post tryFindById(int id)
    {
        Post post=posts.map.get(id);
        if(post==null)
        {
                QueryWrapper<Post> wrapper=new QueryWrapper<>();
                wrapper.eq("id",id);
                post=postMapper.selectOne(wrapper);
                posts.map.put(id,post);
        }
        return post;
    }
    //更新
    @Transactional
    public void tryUpdate(Post post) {
        postMapper.updateById(post);
        // 事务提交后执行缓存清除
//        if (TransactionSynchronizationManager.isSynchronizationActive()) {
//            TransactionSynchronizationManager.registerSynchronization(
//                    new TransactionSynchronization() {
//                        @Override
//                        public void afterCommit() {
//                            posts.map.put(post.id, post);
//                        }
//                    }
//            );
//        }
        //待改进
    }
    //插入
   // @Transactional
    public void tryInsert(Post post) {
        postMapper.insert(post);

                            posts.map.put(post.id, post);
                            allId.add(post.id);
                       //     StringHelper.log("enter");
    }
    //删除
    //@Transactional
    public void tryDelete(Post post) {
        postMapper.deleteById(post);
                            posts.map.remove(post.id);
                            allId.remove(post.id);
                        }


}
