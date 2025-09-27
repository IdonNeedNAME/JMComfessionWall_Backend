package com.github.idonneedname.jmcomfessionwall_backend.helper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Picture;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.PictureMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PictureHelper {
    @Resource
    PictureMapper  pictureMapper;
    public ArrayList<ArrayList<Integer>> getPixels(int id)//获取图片像素
    {
        if(id==-1) return null;
        StringHelper.log(id);
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Picture picture = pictureMapper.selectOne(queryWrapper);
        if(picture == null) return null;
        ArrayList<ArrayList<Integer>> pixels = new ArrayList<>();
        pixels.add(new ArrayList<>());
        byte[] pixs = picture.getPixels();
        int pix,cacu_W=0,cacu_H=0;
        for(int i=0;i<pixs.length;i+=4)
        {
            if(cacu_W==picture.width)
            {
                pixels.add(new ArrayList<>());
                cacu_W=0;
                cacu_H++;
            }
            pix=pixs[i]<<24|pixs[i+1]<<16|pixs[i+2]<<8|pixs[i+3];
            pixels.get(cacu_H).add(pix);
            cacu_W++;
        }
        return pixels;
    }
    public int storeOne(MultipartFile file)//存储一个图片
    {
        BufferedImage image;
        try {
             image= ImageIO.read(file.getInputStream());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        if(image==null)
            return -1;
        int width=image.getWidth(),height=image.getHeight(),pix,cacu=0;
        byte[] pixels=new byte[width*height*4];
        log.info("width:"+width+" ,height:"+height);
        for(int i=0;i<height;i++)
        {
            for(int j=0;j<width;j++,cacu+=4)
            {
                pix=image.getRGB(j,i);
                pixels[cacu+3]=(byte)pix;
                pix>>=8;
                pixels[cacu+2]=(byte)pix;
                pix>>=8;
                pixels[cacu+1]=(byte)pix;
                pix>>=8;
                pixels[cacu]=(byte)pix;
            }
        }
        return storeOne(pixels,width,height);
    }
    public int storeOne(byte[] pixels,int width,int height)//存储一个图片返回该图片的编号
    {
        int featurecode=getFeatureCode(pixels,width,height);

        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("featureCode",featurecode);
        List<Picture> picture = pictureMapper.selectList(queryWrapper);
        Picture  pic=new Picture(pixels,width,height);
        pic.featurecode =featurecode;
        if (picture != null) {
            for (int i = 0; i < picture.size(); i++) {
                if (compare(pic, picture.get(i))) {
                    return picture.get(i).getId();
                }

            }
        }
        pictureMapper.insert(pic);
        return pic.getId();
    }
    public int getFeatureCode(byte[] pixels,int width,int height)//获取图片的特征码用来粗比对
    {
        int a=pixels[1]<<16|pixels[2]<<8|pixels[3];
        int lenth=pixels.length-1;
        int b=pixels[lenth-2]<<16|pixels[lenth-1]<<8|pixels[lenth];
        int c=pixels[lenth/2]<<16|pixels[lenth/2]<<8|pixels[lenth/2];
        return a+b+c;
    }
    public boolean compare(Picture p1, Picture p2)//详细比对
    {
        if(p1.width!=p2.width||p1.height!=p2.height)
            return false;
        for(int i=0;i<p1.pixels.length;i++)
        {
            if(p1.pixels[i]!=p2.pixels[i])
                return false;
        }
        return true;
    }
}
