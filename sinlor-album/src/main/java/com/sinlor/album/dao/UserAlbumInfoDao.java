package com.sinlor.album.dao;

import java.sql.SQLException;

import com.sinlor.core.dao.AbsCommonDao;
import com.sinlor.core.dto.album.UserAlbumInfo;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
public abstract class UserAlbumInfoDao extends AbsCommonDao<UserAlbumInfo> {

    public abstract void updateUserAlbumInfoStep(UserAlbumInfo info) throws SQLException;

    public abstract UserAlbumInfo queryUserAlbumInfoByUk(UserAlbumInfo info) throws SQLException;

    public abstract void updateUserAlbumInfoCompleteAndPreImg(UserAlbumInfo info) throws SQLException;

    public abstract UserAlbumInfo queryLatestByUserAlbumAndComplete(UserAlbumInfo info) throws SQLException;
}
