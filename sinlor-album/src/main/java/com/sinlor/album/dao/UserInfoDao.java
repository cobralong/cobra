package com.sinlor.album.dao;

import java.sql.SQLException;

import com.sinlor.core.dao.AbsCommonDao;
import com.sinlor.core.dto.album.UserInfo;

public abstract class UserInfoDao extends AbsCommonDao<UserInfo> {

    public abstract int insertAndReturnId(UserInfo g) throws SQLException;

    public abstract UserInfo getUserInfoByOpenId(String openId) throws SQLException ;

}
