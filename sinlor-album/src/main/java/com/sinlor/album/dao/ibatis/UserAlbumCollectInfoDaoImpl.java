package com.sinlor.album.dao.ibatis;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sinlor.album.dao.UserAlbumCollectInfoDao;
import com.sinlor.core.utils.SQLUtils;

@Service("userAlbumCollectInfoDao")
public class UserAlbumCollectInfoDaoImpl extends UserAlbumCollectInfoDao {

    @Resource(name = "dream-album-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    @Override
    public List<Integer> getUserAlbumCollectInfoAlbumId(Integer userId) throws SQLException {
        return SQLUtils.queryList(sqlMapClient, "getUserAlbumCollectInfoAlbumId", userId);
    }

}
