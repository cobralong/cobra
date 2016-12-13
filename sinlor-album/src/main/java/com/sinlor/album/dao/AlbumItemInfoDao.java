package com.sinlor.album.dao;

import java.sql.SQLException;

import com.sinlor.core.dao.AbsCommonDao;
import com.sinlor.core.dto.album.AlbumItemInfo;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
public abstract class AlbumItemInfoDao extends AbsCommonDao<AlbumItemInfo> {

    public abstract AlbumItemInfo queryAlbumItemInfoByUk(AlbumItemInfo info) throws SQLException;
}
