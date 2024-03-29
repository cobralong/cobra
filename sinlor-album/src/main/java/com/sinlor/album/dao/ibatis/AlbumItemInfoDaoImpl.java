package com.sinlor.album.dao.ibatis;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sinlor.album.dao.AlbumItemInfoDao;
import com.sinlor.core.dto.album.AlbumItemInfo;
import com.sinlor.core.utils.SQLUtils;

/**
 * @author liuxinglong
 * @date 2016年12月6日
 */
@Repository("albumItemInfoDao")
public class AlbumItemInfoDaoImpl extends AlbumItemInfoDao {
    @Resource(name = "dream-album-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    @Override
    public AlbumItemInfo queryAlbumItemInfoByUk(AlbumItemInfo info) throws SQLException {
        return SQLUtils.queryObject(sqlMapClient, "queryAlbumItemInfoByAlbumIdAndRank", info);
    }
}
