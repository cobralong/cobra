// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.sinlor.album.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dreambox.web.exception.ServiceException;
import com.sinlor.album.model.AlbumEditImgInfoModel;
import com.sinlor.album.model.JoinImgFileResp;
import com.sinlor.album.model.MergeImgFileResp;
import com.sinlor.album.model.UploadFileSaveResp;
import com.sinlor.core.dto.album.AlbumItemInfo;
import com.sinlor.core.dto.album.UserAlbumItemInfo;

/**
 * @author mokous86@gmail.com create date: Dec 12, 2016
 *
 */
public interface ImgService {
    /**
     * 保存用户上传图片
     * 
     * @param image
     * 
     * @return
     * @throws ServiceException
     */
    public UploadFileSaveResp handleUserUploadImg(MultipartFile image) throws ServiceException;

    public MergeImgFileResp mergeToPreviewImg(String editImePath, String localPath, AlbumItemInfo albumItemInfo,
            AlbumEditImgInfoModel model) throws ServiceException;

    public JoinImgFileResp joinPreviewImg(int userAlbumId, List<String> prwImgList, String type);

    public String getAlbumItemEditImgPath(AlbumItemInfo info);

    public String getUserAlbumItemPreviewImgPath(UserAlbumItemInfo g);

}
