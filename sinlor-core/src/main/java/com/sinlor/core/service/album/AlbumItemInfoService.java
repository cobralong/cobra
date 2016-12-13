// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.sinlor.core.service.album;

import com.dreambox.web.exception.ServiceException;
import com.sinlor.core.dto.album.AlbumItemInfo;
import com.sinlor.core.service.AbstractSortedListCacheService;

/**
 * @author mokous86@gmail.com create date: Dec 6, 2016
 *
 */
public abstract class AlbumItemInfoService extends AbstractSortedListCacheService<AlbumItemInfo> {

    public abstract AlbumItemInfo getAlbumItemInfoByUk(AlbumItemInfo info) throws ServiceException;

    public abstract String getEditImePath(AlbumItemInfo info);
}
