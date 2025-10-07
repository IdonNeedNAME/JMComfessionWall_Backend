package com.github.idonneedname.jmcomfessionwall_backend.helper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.idonneedname.jmcomfessionwall_backend.constant.Constant;
import com.github.idonneedname.jmcomfessionwall_backend.constant.ExceptionEnum;
import com.github.idonneedname.jmcomfessionwall_backend.entity.Picture;
import com.github.idonneedname.jmcomfessionwall_backend.exception.ApiException;
import com.github.idonneedname.jmcomfessionwall_backend.mapper.PictureMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PictureHelper {
    @jakarta.annotation.Resource
    PictureMapper pictureMapper;
    @jakarta.annotation.Resource
    FileStorageService fileStorageService;
    @jakarta.annotation.Resource
    private ResourceLoader resourceLoader;
    @Value("${file.allowed-types}")
    private String[] allowedTypes;
    public int storePicture(MultipartFile file) {
        BufferedImage image;
        try {
            image = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (image == null)
        {
            return -1;
        }
        if (!isValidFileType(file.getContentType())) {
            throw new ApiException(ExceptionEnum.INVAILD_TYPE_PICTURE);
        }

        int width = image.getWidth(), height = image.getHeight();
        int featureCode = image.getRGB(0, 0) + image.getRGB(width / 2, height / 2) + image.getRGB(width - 1, height-1);
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("featureCode", featureCode);
        List<Picture> picture = pictureMapper.selectList(queryWrapper);
        for (int i = 0; i < picture.size(); i++) {
            Resource resource = resourceLoader.getResource("file:" + Constant.basePath + picture.get(i).name);
            InputStream is;
            BufferedImage pic;
            try {
                is = resource.getInputStream();
                pic = ImageIO.read(is);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int width2 = pic.getWidth(), height2 = pic.getHeight();
            if(width2!=width || height2!=height) continue;
            boolean same=true;
            for(int y=0;y<height2;y++)
            {
                for(int x=0;x<width2;x++)
                {
                    if(pic.getRGB(x,y)!=image.getRGB(x,y))
                    {
                        same=false;
                        break;
                    }
                }
            }
            if(same)
            {
                return picture.get(i).getId();
            }
        }
        String filename=file.getOriginalFilename();
        String postfix=filename.substring(filename.lastIndexOf("."));
        String finalname= Instant.now().toEpochMilli()+(picture.size()+1)+postfix;
        Picture picture1=new Picture();
        picture1.setName(finalname);
        picture1.setWidth(width);
        picture1.setHeight(height);
        picture1.setFeaturecode(featureCode);
        pictureMapper.insert(picture1);
        try (InputStream inputStream = file.getInputStream()) {
            Path path = Paths.get(Constant.basePath + finalname);
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }
        catch ( IOException e)
        {
            throw new RuntimeException(e);
        }
        return picture1.getId();
    }
    private boolean isValidFileType(String contentType) {
        for (String type : allowedTypes) {
            if (type.equalsIgnoreCase(contentType)) {
                return true;
            }
        }
        return false;
    }
}

