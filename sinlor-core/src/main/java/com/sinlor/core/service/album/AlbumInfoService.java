// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.sinlor.core.service.album;

import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.model.ListWrapResp;
import com.sinlor.core.dto.album.AlbumInfo;
import com.sinlor.core.service.AbstractSortedListCacheService;

/**
 * @author mokous86@gmail.com create date: Dec 6, 2016
 *
 *         相册模板服务
 *
 *         提供相册模板具体详情数据
 *
 *         相册列表数据
 */
public abstract class AlbumInfoService extends AbstractSortedListCacheService<AlbumInfo> {

    public abstract ListWrapResp<AlbumInfo> searchInfos(String keyword, int start, int size) throws ServiceException;

}
