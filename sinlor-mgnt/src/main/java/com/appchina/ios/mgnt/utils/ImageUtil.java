package com.appchina.ios.mgnt.utils;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

import com.appchina.ios.core.crawler.model.ImgRes;

/**
 * Created by zhouyanhui on 7/20/15.
 */
public class ImageUtil {
    public static ImgRes saveImage(File file, String suffix, byte[] imgBytes) throws IOException {
        file.getParentFile().mkdir();
        BufferedImage sourceImage = ImageIO.read(new ByteArrayInputStream(imgBytes));
        Image thumbnail = sourceImage.getScaledInstance(sourceImage.getWidth(), sourceImage.getHeight(),
                Image.SCALE_SMOOTH);
        BufferedImage bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null), thumbnail.getHeight(null),
                sourceImage.getColorModel().hasAlpha() ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
        bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
        ImageIO.write(bufferedThumbnail, suffix, new FileOutputStream(file));
        ImgRes ret = new ImgRes();
        ret.setHeight(sourceImage.getHeight());
        ret.setWidth(sourceImage.getWidth());
        return ret;
    }

    public static File saveImage(File file, String suffix, File optFile, String optSuffix, byte[] imgBytes,
            ImageType imageType) throws IOException {
        file.getParentFile().mkdir();
        optFile.getParentFile().mkdir();
        BufferedImage sourceImage = ImageIO.read(new ByteArrayInputStream(imgBytes));
        Image thumbnail = sourceImage.getScaledInstance(sourceImage.getWidth(), sourceImage.getHeight(),
                Image.SCALE_SMOOTH);
        BufferedImage bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null), thumbnail.getHeight(null),
                sourceImage.getColorModel().hasAlpha() ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
        bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
        ImageIO.write(bufferedThumbnail, suffix, new FileOutputStream(file));
        if (imgBytes.length >= 256 * 1024) {// convert and resize
            converScale(optFile, optSuffix, imgBytes, imgBytes.length, imageType);
            return optFile;
        }
        return file;
    }

    public static ArrayList<File> saveImages(File file, String suffix, File optFile, String optSuffix, byte[] imgBytes,
            ImageType imageType) throws IOException {
        ArrayList<File> files = new ArrayList<File>();
        file.getParentFile().mkdir();
        optFile.getParentFile().mkdir();
        FileOutputStream out = new FileOutputStream(file);
        out.write(imgBytes);
        out.close();
        files.add(file);
        if (imgBytes.length >= 256 * 1024) {
            BufferedImage sourceImage = ImageIO.read(new ByteArrayInputStream(imgBytes));
            Image thumbnail = sourceImage.getScaledInstance(sourceImage.getWidth(), sourceImage.getHeight(),
                    Image.SCALE_SMOOTH);
            BufferedImage bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null), thumbnail.getHeight(null),
                    sourceImage.getColorModel().hasAlpha() ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
            bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
            FileOutputStream output = new FileOutputStream(optFile);
            ImageIO.write(bufferedThumbnail, suffix, output);
            output.close();
            files.add(optFile);
        }
        return files;
    }

    public static void saveOrignImage(File originFile, byte[] bytes) throws IOException {
        originFile.getParentFile().mkdir();
        FileOutputStream out = new FileOutputStream(originFile);
        out.write(bytes);
        out.close();
    }

    /**
     * i 如果图片大小超过500K的进行格式转换
     * <p/>
     * 对于 banner图片 则调整宽高到(414， 391)
     * <p/>
     * 对于 vedio的图片 则调整宽高到(414， 270)
     * <p/>
     * 对于wallpaper图片 则调整宽高到(600,280)
     */
    private static void converScale(File out, String optSuffix, byte[] imgBytes, int size, ImageType imageType)
            throws IOException {
        BufferedImage origin = ImageIO.read(new ByteArrayInputStream(imgBytes));
        BufferedImage newBufferedImage = null;
        newBufferedImage = new BufferedImage(origin.getWidth(), origin.getHeight(), BufferedImage.TYPE_INT_RGB);
        newBufferedImage.createGraphics().drawImage(origin, 0, 0, Color.WHITE, null);

        BufferedImage scaled = null;
        if (imageType == ImageType.Banner) {
            scaled = Scalr.resize(newBufferedImage, 414, 391);
        } else if (imageType == ImageType.Vedio) {
            scaled = Scalr.resize(newBufferedImage, 414, 270);
        } else if (imageType == ImageType.AD) {
            scaled = Scalr.resize(newBufferedImage, 1242, 2208);
        } /*
           * else if(imageType==ImageType.Wallpaper){
           * scaled=Scalr.resize(newBufferedImage, 600, 280); }
           */else {
            scaled = newBufferedImage;
        }
        ImageIO.write(scaled, optSuffix, out);
    }

    public static void main(String[] args) throws IOException {
        String filePath = "/Users/luofei/Downloads/hyb/res/footer.png";
        BufferedImage bi = ImageIO.read(new File(filePath));
        BufferedImage newBufferedImage = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);
        newBufferedImage.createGraphics().drawImage(bi, 0, 0, Color.WHITE, null);
        ImageIO.write(newBufferedImage, "jpg", new File("/Users/luofei/Downloads/hyb/res/footer_opt.jpg"));
    }

    public static enum ImageType {
        Banner, Vedio, AD, Wallpaper;
    }

}
