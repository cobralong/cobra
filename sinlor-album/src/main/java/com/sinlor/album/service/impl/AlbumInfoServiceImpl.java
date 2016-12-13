// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.sinlor.album.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dreambox.web.exception.ServiceException;
import com.dreambox.web.model.ListWrapResp;
import com.sinlor.album.dao.AlbumInfoDao;
import com.sinlor.core.cache.CacheFilter.StartSizeCacheFilter;
import com.sinlor.core.dao.CommonDao;
import com.sinlor.core.dao.LoadDao;
import com.sinlor.core.dto.album.AlbumInfo;
import com.sinlor.core.service.album.AlbumInfoService;
import com.sinlor.core.utils.RedisCacheUtils;

/**
 * @author mokous86@gmail.com create date: Dec 6, 2016
 *
 */
@Service("albumInfoService")
public class AlbumInfoServiceImpl extends AlbumInfoService {
    private static final Logger log = Logger.getLogger(AlbumInfoServiceImpl.class);
    @Autowired
    private AlbumInfoDao albumInfoDao;
    @Resource(name = "jmcx-wx-redisdbpool")
    private JedisPool jedisDbPool;
    @Resource(name = "jmcx-wx-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    private String infoKey = "album:info";
    private String listKey = "album:info:ids";

    @Override
    protected String buildSortedSetKey(StartSizeCacheFilter filter) {
        return listKey;
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected StartSizeCacheFilter buildCacheFilter(AlbumInfo value) {
        return null;
    }

    @Override
    protected LoadDao<AlbumInfo> getLoadDao() {
        return albumInfoDao;
    }

    @Override
    protected boolean isStop() {
        return false;
    }

    @Override
    protected ShardedJedisPool getSharedJedisPool() {
        return shardedJedisPool;
    }

    @Override
    protected String buildDataInfoKey(int id) {
        return RedisCacheUtils.buildKey(this.infoKey, id);
    }

    @Override
    public CommonDao<AlbumInfo> getCommonDao() {
        return albumInfoDao;
    }

    @Override
    public ListWrapResp<AlbumInfo> searchInfos(String keyword, int start, int size) throws ServiceException {
        if (StringUtils.isEmpty(keyword)) {
            StartSizeCacheFilter filter = new StartSizeCacheFilter();
            filter.setStart(start);
            filter.setSize(size);
            return listInfo(filter);
        }
        AlbumInfo g = new AlbumInfo();
        g.setKeyword("%" + keyword + "%");
        long total;
        try {
            total = albumInfoDao.count(g);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        List<AlbumInfo> infos;
        try {
            infos = albumInfoDao.queryList(g, start, size);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
        return new ListWrapResp<AlbumInfo>(total, infos, start, size);

    }

    @Override
    protected Logger getLogger() {
        return log;
    }
}
