// Copyright 2016 https://mokous.com Inc. All Rights Reserved.

package com.sinlor.album.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.dreambox.web.exception.ServiceException;
import com.sinlor.album.dao.AlbumItemInfoDao;
import com.sinlor.album.service.ImgService;
import com.sinlor.core.cache.CacheFilter.IdStartSizeCacheFilter;
import com.sinlor.core.cache.CacheFilter.StartSizeCacheFilter;
import com.sinlor.core.dao.CommonDao;
import com.sinlor.core.dao.LoadDao;
import com.sinlor.core.dto.album.AlbumItemInfo;
import com.sinlor.core.service.album.AlbumItemInfoService;
import com.sinlor.core.utils.RedisCacheUtils;

/**
 * @author mokous86@gmail.com create date: Dec 7, 2016
 *
 */
@Service("albumItemInfoService")
public class AlbumItemInfoServiceImpl extends AlbumItemInfoService {
    private static final Logger log = Logger.getLogger(AlbumItemInfoServiceImpl.class);
    @Autowired
    private AlbumItemInfoDao albumItemInfoDao;
    @Autowired
    private ImgService imgService;
    @Resource(name = "jmcx-wx-redisdbpool")
    private JedisPool jedisDbPool;
    @Resource(name = "jmcx-wx-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    private String infoKey = "album:item:info";
    private String listKey = "album:info:item:ids";


    @Override
    protected String buildSortedSetKey(StartSizeCacheFilter filter) {
        IdStartSizeCacheFilter idFilter = (IdStartSizeCacheFilter) filter;
        return RedisCacheUtils.buildKey(listKey, idFilter.getId());
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected StartSizeCacheFilter buildCacheFilter(AlbumItemInfo value) {
        IdStartSizeCacheFilter idFilter = new IdStartSizeCacheFilter();
        idFilter.setId(value.getAlbumId());
        return idFilter;
    }

    @Override
    protected LoadDao<AlbumItemInfo> getLoadDao() {
        return albumItemInfoDao;
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
        return RedisCacheUtils.buildKey(infoKey, id);
    }

    @Override
    public CommonDao<AlbumItemInfo> getCommonDao() {
        return albumItemInfoDao;
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

    @Override
    public AlbumItemInfo getAlbumItemInfoByUk(AlbumItemInfo info) throws ServiceException {
        try {
            return albumItemInfoDao.queryAlbumItemInfoByUk(info);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

    @Override
    public String getEditImePath(AlbumItemInfo info) {
        return imgService.getAlbumItemEditImgPath(info);
    }

}
