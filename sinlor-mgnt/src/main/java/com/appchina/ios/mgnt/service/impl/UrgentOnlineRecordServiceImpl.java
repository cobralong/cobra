// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appchina.ios.core.dto.app.UrgentOnlineRecord;
import com.appchina.ios.core.service.app.UrgentOnlineRecordService;
import com.appchina.ios.mgnt.dao.UrgentOnlineRecordDao;
import com.appchina.ios.web.exception.ServiceException;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
@Service("urgentOnlineRecordService")
public class UrgentOnlineRecordServiceImpl implements UrgentOnlineRecordService {
    @Autowired
    private UrgentOnlineRecordDao urgentOnlineRecordDao;

    @Override
    public void addOnline(int itemId) throws ServiceException {
        UrgentOnlineRecord record = new UrgentOnlineRecord();
        record.setItemId(itemId);
        addOnline(record);
    }

    @Override
    public void addOnline(int itemId, String locale, String url) throws ServiceException {
        UrgentOnlineRecord record = new UrgentOnlineRecord();
        record.setItemId(itemId);
        record.setLocale(locale);
        record.setUrl(url);
        addOnline(record);
    }

    private void addOnline(UrgentOnlineRecord record) {
        try {
            urgentOnlineRecordDao.insertOrIgnore(record);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e.getMessage());
        }
    }

    @Override
    public List<UrgentOnlineRecord> list(Integer itemId, int start, int size) throws ServiceException {
        try {
            return urgentOnlineRecordDao.queryList(itemId, start, size);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e.getMessage());
        }
    }

    @Override
    public long count() throws ServiceException {
        try {
            return urgentOnlineRecordDao.count();
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e.getMessage());
        }
    }

    @Override
    public void modifyAppInfo(List<UrgentOnlineRecord> records) throws ServiceException {
        try {
            this.urgentOnlineRecordDao.update(records);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e.getMessage());
        }
    }

    @Override
    public List<Integer> getRootIds(int start, int size) throws ServiceException {
        try {
            return urgentOnlineRecordDao.queryRootIds(start, size);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e.getMessage());
        }
    }

    @Override
    public List<UrgentOnlineRecord> listNoRootIdDatas(int endId, int size) throws ServiceException {
        try {
            return urgentOnlineRecordDao.queryList(endId, size);
        } catch (SQLException e) {
            throw ServiceException.getSQLException(e.getMessage());
        }
    }

}
