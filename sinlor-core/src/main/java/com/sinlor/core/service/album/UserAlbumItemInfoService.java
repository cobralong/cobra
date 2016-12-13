// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.sinlor.core.service.album;

import com.sinlor.core.dto.album.UserAlbumItemInfo;
import com.sinlor.core.service.AbstractSortedListCacheService;

/**
 * @author mokous86@gmail.com create date: Dec 6, 2016
 *
 */
public abstract class UserAlbumItemInfoService extends AbstractSortedListCacheService<UserAlbumItemInfo> {
    public abstract String getPreviewImgPath(UserAlbumItemInfo g);
}
