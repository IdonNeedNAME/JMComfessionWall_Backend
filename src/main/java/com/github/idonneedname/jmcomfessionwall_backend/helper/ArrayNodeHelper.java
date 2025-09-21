package com.github.idonneedname.jmcomfessionwall_backend.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class ArrayNodeHelper {
    public static int idInArray(String array,int id)//判断数组中是否含有某个数，并返回位置
    {
        JsonNode jsonNode=ArrayNodeHelper.translate(array);
        if(jsonNode==null)
            return -1;
        if(jsonNode.isArray())
            return idInArray((ArrayNode) jsonNode,id);
        else
            return -1;
    }
    public static int idInArray(ArrayNode arrayNode, int id)//判断数组中是否含有某个数，并返回位置
    {
        for(int i=0;i<arrayNode.size();i++)
        {
            if(arrayNode.get(i).isNumber())
            {
                if(arrayNode.get(i).intValue()==id)
                    return i;
            }
        }
        return -1;
    }
    public static String add(String array,int addition)//添加一个数到数组中
    {
        if(array==null)
            array="[]";
        JsonNode jsonNode=ArrayNodeHelper.translate(array);
        if(jsonNode==null)
            return null;
        if(jsonNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonNode;
            arrayNode.add(addition);
            return arrayNode.toString();
        }
        else
            return null;
    }
    public static JsonNode translate(String node)//把字符串翻译成JsonNode
    {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try{
            jsonNode= objectMapper.readTree(node);
        }
        catch(Exception e){
            return null;
        }
        return jsonNode;
    }
    public static String delete(String node,int id)//删除某个值
    {
        JsonNode jsonNode=ArrayNodeHelper.translate(node);
        ArrayNode arrayNode = (ArrayNode) jsonNode;
        if(arrayNode==null) return node;
        int position=idInArray(arrayNode,id);
        if(position==-1) return node;
        arrayNode.remove(position);
        return arrayNode.toString();
    }
    public static ArrayList<Integer> translateToArray(String node)
    {
        JsonNode jsonNode=translate(node);
        if(jsonNode==null) return null;
        if(!jsonNode.isArray()) return null;
        ArrayNode arrayNode = (ArrayNode) jsonNode;
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=0;i<arrayNode.size();i++)
        {
            list.add(arrayNode.get(i).asInt());
        }
        return list;
    }

}
