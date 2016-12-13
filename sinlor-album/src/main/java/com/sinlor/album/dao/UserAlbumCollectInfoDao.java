package com.sinlor.album.dao;

import java.sql.SQLException;
import java.util.List;

import com.sinlor.core.dao.AbsCommonDao;
import com.sinlor.core.dto.album.UserAlbumCollectInfo;

public abstract class UserAlbumCollectInfoDao extends AbsCommonDao<UserAlbumCollectInfo> {

    public abstract List<Integer> getUserAlbumCollectInfoAlbumId(Integer userId) throws SQLException;

}
