package com.github.idonneedname.jmcomfessionwall_backend.helper.event;

import com.github.idonneedname.jmcomfessionwall_backend.entity.Post;

import java.util.*;
import java.util.concurrent.CompletableFuture;

//事件处理器，用于把并发事件排队解决来解决并发冲突，性能问题待改进
public class EventHandler {
    public EventHandler() {
        eventQue=new LinkedList<>();
        idUpdated =new HashSet<>();
    }
    public Set<Post> idUpdated;
    public boolean haveUpdateEvent;
    public List<IResolvable> eventQue;
    public boolean resolving=false;
    //提醒更新
    public void notifyStart()
    {
         if(resolving) return ;
         else
         {
             CompletableFuture.runAsync(this::ResolveEvents);
         }
    }
    public void notifyEventEnd(){

    }
    //处理事件队列
    public void ResolveEvents(){
         resolving=true;
         try
         {
             while(!eventQue.isEmpty()){
                 eventQue.get(0).Resolve();
                 eventQue.remove(0);
             }
         }
         catch(Exception e)
         {
             resolving=false;
             throw e;
         }
         resolving=false;
    }
    //增添至事件队列事件并自动提醒进行更行
    public void addEvent(IResolvable event)
    {
        this.eventQue.add(event);
        notifyStart();
    }

}
