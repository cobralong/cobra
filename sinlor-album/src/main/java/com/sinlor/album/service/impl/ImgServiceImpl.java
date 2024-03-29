// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.sinlor.album.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.utils.IOUtils;
import com.sinlor.album.model.AlbumEditImgInfoModel;
import com.sinlor.album.model.JoinImgFileResp;
import com.sinlor.album.model.MergeImgFileResp;
import com.sinlor.album.model.UploadFileSaveResp;
import com.sinlor.album.service.ImgService;
import com.sinlor.core.dto.album.AlbumItemInfo;
import com.sinlor.core.dto.album.UserAlbumItemInfo;
import com.sinlor.core.utils.EasyImage;
import com.sinlor.core.utils.ImagePsUtils;

/**
 * @author mokous86@gmail.com create date: Dec 12, 2016
 *
 */
@Service("imgService")
public class ImgServiceImpl implements ImgService {
    private static final Logger log = Logger.getLogger(ImgServiceImpl.class);
    @Value("${dream.album.useralbumitemuploadimglocalpath}")
    private String userAlbumItemUploadImgLocalPath;
    @Value("${dream.album.useralbumitemuploadimgprefixurl}")
    private String userAlbumItemUploadImgPrefixUrl;
    @Value("${dream.album.useralbumitempreviewimgurlprefix}")
    private String userAlbumItemPreviewImgUrlPrefix = "";
    @Value("${dream.album.useralbumitempreviewimglocaldir}")
    private String userAlbumItemPreviewImgLocalDir = "";

    @Value("${dream.album.useralbumpriviewimgprefixurl}")
    private String userAlbumPriviewImgPrefixUrl;
    @Value("${dream.album.useralbumpriviewimglocalpath}")
    private String userAlbumPriviewImgLocalPath;

    @Value("${dream.album.albumitemeditimgurlprefix}")
    private String albumItemEditImgUrlPrefix = "";
    @Value("${dream.album.albumitemeditimglocaldir}")
    private String albumItemEditImgLocalDir = "";


    @Override
    public UploadFileSaveResp handleUserUploadImg(MultipartFile image) throws ServiceException {
        // 保存用户自己上传的图片
        String picName = "album_user_" + new Date().getTime() + ".png";
        File outputfile = new File(userAlbumItemUploadImgLocalPath + picName);
        try {
            ImageIO.write(ImageIO.read(image.getInputStream()), "png", outputfile);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        String picUrl = userAlbumItemUploadImgPrefixUrl + picName;
        return new UploadFileSaveResp(outputfile.getPath(), picUrl);
    }

    @Override
    public MergeImgFileResp mergeToPreviewImg(String editImePath, String localPath, AlbumItemInfo albumItemInfo,
            AlbumEditImgInfoModel model) throws ServiceException {
        // Integer cssImgHeight = Math.round((float) (model.getCssImgHeight() *
        // yTimes + 0.5));
        ImagePsUtils img = new ImagePsUtils();
        String picName = "album_item_pre_" + new Date().getTime() + ".png";
        try {
            img.mergeBothImage(editImePath, localPath, model.getCssElmMoveX(), model.getCssElmMoveY(),
                    model.getCssElmWidth(), model.getCssElmHeight(), model.getCssElmRotate(),
                    userAlbumItemPreviewImgLocalDir + picName);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return new MergeImgFileResp(userAlbumItemPreviewImgLocalDir + picName, userAlbumItemPreviewImgUrlPrefix
                + picName);
    }

    @Override
    public JoinImgFileResp joinPreviewImg(int userAlbumId, List<String> prwImgList, String type) {
        EasyImage e = new EasyImage();
        String subDir = String.valueOf(userAlbumId);
        String fileName = System.currentTimeMillis() + "." + type;
        String productPreImg = IOUtils.spliceFileName(IOUtils.spliceDirPath(userAlbumPriviewImgLocalPath, subDir),
                fileName);
        // 纵向拼接成品相册预览图
        e.joinImageListVertical(prwImgList.toArray(new String[prwImgList.size()]), "png", productPreImg);
        return new JoinImgFileResp(productPreImg, productPreImg.replace(userAlbumPriviewImgLocalPath,
                userAlbumPriviewImgPrefixUrl));
    }

    @Override
    public String getAlbumItemEditImgPath(AlbumItemInfo info) {
        return StringUtils.replace(info.getEditImgUrl(), this.albumItemEditImgUrlPrefix, this.albumItemEditImgLocalDir);
    }

    @Override
    public String getUserAlbumItemPreviewImgPath(UserAlbumItemInfo g) {
        String previewImgUrl = g.getPreviewImgUrl();
        return previewImgUrl.replace(userAlbumItemPreviewImgUrlPrefix, userAlbumItemPreviewImgLocalDir);
    }
}
