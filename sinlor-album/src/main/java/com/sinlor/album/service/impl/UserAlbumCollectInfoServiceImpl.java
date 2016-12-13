package com.sinlor.album.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreambox.web.exception.ServiceException;
import com.sinlor.album.dao.UserAlbumCollectInfoDao;
import com.sinlor.core.dao.CommonDao;
import com.sinlor.core.dto.album.UserAlbumCollectInfo;
import com.sinlor.core.service.album.UserAlbumCollectInfoService;

@Service("userAlbumCollectInfo")
public class UserAlbumCollectInfoServiceImpl extends UserAlbumCollectInfoService {

    @Autowired
    private UserAlbumCollectInfoDao userAlbumCollectInfoDao;

    @Override
    public CommonDao<UserAlbumCollectInfo> getCommonDao() {
        return userAlbumCollectInfoDao;
    }

    @Override
    public List<Integer> getCollectAlbumInfoId(Integer userId) throws ServiceException {
        try {
            return userAlbumCollectInfoDao.getUserAlbumCollectInfoAlbumId(userId);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e);
        }
    }

}
