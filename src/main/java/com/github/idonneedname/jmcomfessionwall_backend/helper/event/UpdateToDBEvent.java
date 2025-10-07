package com.github.idonneedname.jmcomfessionwall_backend.helper.event;

import com.github.idonneedname.jmcomfessionwall_backend.constant.Constant;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;
import com.github.idonneedname.jmcomfessionwall_backend.helper.StringHelper;

import java.time.Instant;

public class UpdateToDBEvent implements IResolvable {
    public long lastUpdateTime,maxOfTolerant=1000;
    //public PostMapper postMapper;
    public EventHandler handler;
    public UpdateToDBEvent(EventHandler handler) {
        lastUpdateTime= Instant.now().toEpochMilli();
        this.handler=handler;
        //this.postMapper=postMapper;
    }
    @Override
    public void Resolve()
    {
        StringHelper.log(handler.eventQue.size()+" events left");

           if(shouldUpdateNow())
           {
               for(Post update: handler.idUpdated)
               {
                   Constant.postCache.tryUpdate(update);
               }
               handler.idUpdated.clear();
               handler.haveUpdateEvent=false;
           }
           else
           {
               addIntoHandler();
               return ;
           }
    }
    public boolean shouldUpdateNow()
    {
        if(Instant.now().toEpochMilli()-lastUpdateTime>1000)
            return true;
           return handler.eventQue.size()==1||handler.eventQue.size()>maxOfTolerant;
    }
    public void addIntoHandler()
    {
          if(!handler.haveUpdateEvent)
          {
              handler.haveUpdateEvent=true;
              handler.eventQue.add(this);
          }

    }
}
