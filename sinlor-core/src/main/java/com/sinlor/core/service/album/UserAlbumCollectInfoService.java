package com.sinlor.core.service.album;

import java.util.List;

import com.dreambox.web.exception.ServiceException;
import com.sinlor.core.dto.album.UserAlbumCollectInfo;
import com.sinlor.core.service.AbsCommonDataService;

public abstract class UserAlbumCollectInfoService extends AbsCommonDataService<UserAlbumCollectInfo> {

    public abstract List<Integer> getCollectAlbumInfoId(Integer userId) throws ServiceException;

}
