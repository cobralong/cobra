package com.sinlor.album.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

import com.sinlor.album.dao.UserAlbumItemInfoDao;
import com.sinlor.album.service.ImgService;
import com.sinlor.core.cache.CacheFilter.StartSizeCacheFilter;
import com.sinlor.core.dao.CommonDao;
import com.sinlor.core.dao.LoadDao;
import com.sinlor.core.dto.album.UserAlbumItemInfo;
import com.sinlor.core.service.album.UserAlbumItemInfoService;
import com.sinlor.core.utils.RedisCacheUtils;

/**
 * @author liuxinglong
 * @date 2016年12月7日
 */
@Service("userAlbumItemInfoService")
public class UserAlbumItemInfoServiceImpl extends UserAlbumItemInfoService {
    private static final Logger log = Logger.getLogger(UserAlbumItemInfoServiceImpl.class);
    @Autowired
    private UserAlbumItemInfoDao userAlbumItemInfoDao;
    @Autowired
    private ImgService imgService;
    @Resource(name = "jmcx-wx-redisdbpool")
    private JedisPool jedisDbPool;
    @Resource(name = "jmcx-wx-rediscacheshardedpool")
    private ShardedJedisPool shardedJedisPool;
    private String infoKey = "user:album:item:info";

    // private String listKey = "user:album:info:item:ids";

    @Override
    protected String buildSortedSetKey(StartSizeCacheFilter filter) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected JedisPool getJedisPool() {
        return jedisDbPool;
    }

    @Override
    protected StartSizeCacheFilter buildCacheFilter(UserAlbumItemInfo value) {
        return null;
    }

    @Override
    protected LoadDao<UserAlbumItemInfo> getLoadDao() {
        return userAlbumItemInfoDao;
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
    public CommonDao<UserAlbumItemInfo> getCommonDao() {
        return userAlbumItemInfoDao;
    }

    @Override
    public String getPreviewImgPath(UserAlbumItemInfo g) {
        return imgService.getUserAlbumItemPreviewImgPath(g);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }

}
