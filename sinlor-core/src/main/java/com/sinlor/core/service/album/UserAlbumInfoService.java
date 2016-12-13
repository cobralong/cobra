// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.sinlor.core.service.album;

import com.dreambox.web.exception.ServiceException;
import com.sinlor.core.dto.album.UserAlbumInfo;
import com.sinlor.core.service.AbstractSortedListCacheService;

/**
 * 用户制作相册相关数据服务
 * 
 * @author mokous86@gmail.com create date: Dec 6, 2016
 *
 */
public abstract class UserAlbumInfoService extends AbstractSortedListCacheService<UserAlbumInfo> {

    public abstract void modifyUserAlbumInfoStep(UserAlbumInfo info) throws ServiceException;

    public abstract UserAlbumInfo getUserAlbumInfoByUk(UserAlbumInfo info) throws ServiceException;

    public abstract UserAlbumInfo findLatestUncompleteUserAlbum(UserAlbumInfo info) throws ServiceException;

    public abstract void modifyUserAlbumInfoCompleteAndPreImg(UserAlbumInfo info) throws ServiceException;
}
