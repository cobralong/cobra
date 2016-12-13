// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.sinlor.album.dao.ibatis;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sinlor.album.dao.AlbumInfoDao;

/**
 * @author mokous86@gmail.com create date: Dec 6, 2016
 *
 */
@Repository("albumInfoDao")
public class AlbumInfoDaoImpl extends AlbumInfoDao {
    @Resource(name = "dream-album-sql-client")
    private SqlMapClient sqlMapClient;

    @Override
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

}
