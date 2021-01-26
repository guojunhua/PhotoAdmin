package com.tools.common.util;

import com.tools.module.app.entity.AppImage;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 图片处理
 * 爪哇笔记：https://blog.52itstyle.vip
 */
@Component
public class ImageUtils {

    @Value("${file.path}")
    private String filePath;

    private static File watermark;

    static {
        try {
            watermark = ResourceUtils.getFile("classpath:static/images/watermark.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加水印
     * @param imageModel
     */
    public File watermark(AppImage imageModel){
        String inputPath = filePath +"/"+ imageModel.getFileName();
        File outputFile = new File(filePath +"/"+ imageModel.getImagePath());
        // 不透明度
        float opacity = 0.7f;
        try {
            // 获取原图文件
            File file = new File(inputPath);
            // ImageIO读取图片
            BufferedImage image = ImageIO.read(file);
            Thumbnails.of(image)
                    // 设置图片大小
                    .size(image.getWidth(), image.getHeight())
                    // 加水印 参数：1.水印位置 2.水印图片 3.不透明度0.0-1.0
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(watermark), opacity)
                    // 输出到文件
                    .toFile(outputFile);
            return outputFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        // 原图片地址
        String imageUrl = "F:\\111.gif";
        // 水印图片 相对于resource目录
        String watermark = "F:\\watermark.png";
        // 输出到文件
        String outputFile = "F:\\222.gif";
        // 不透明度
        float opacity = 0.7f;
        try {
            // 获取原图文件
            File file = new File(imageUrl);
            // ImageIO读取图片
            BufferedImage image = ImageIO.read(file);
            System.out.println(image.getWidth());
            Thumbnails.of(image)
                    // 设置图片大小
                    .size(image.getWidth(), image.getHeight())
                    // 加水印 参数：1.水印位置 2.水印图片 3.不透明度0.0-1.0
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(watermark)), opacity)
                    // 输出到文件
                    .outputQuality(1)
                    .toFile(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
