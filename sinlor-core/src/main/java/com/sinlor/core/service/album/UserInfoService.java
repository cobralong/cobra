package com.sinlor.core.service.album;

import com.dreambox.web.exception.ServiceException;
import com.sinlor.core.dto.album.UserInfo;
import com.sinlor.core.service.AbsCommonDataService;

public abstract class UserInfoService extends AbsCommonDataService<UserInfo> {

    public abstract int addDataAndReturnId(UserInfo g) throws ServiceException;

    public abstract UserInfo getDirectFromDbByOpenId(String openId) throws ServiceException;

    public abstract void modifyUserInfo(UserInfo g) throws ServiceException;

}
