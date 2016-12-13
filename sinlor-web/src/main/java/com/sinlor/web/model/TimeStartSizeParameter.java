package com.sinlor.web.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Created by luofei@appchina.com on 6/17/16.
 */
public class TimeStartSizeParameter extends StartSizeParameter {
    private static final String TIME_MMDDYYYYHHMMSS_FORMAT = "MM/dd/yyyy HH:mm:ss";
    private static final String STET_YYYYMMDDHHMMSS_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private String st;
    private String et;
    private String time;

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getEt() {
        return et;
    }

    public void setEt(String et) {
        this.et = et;
    }

    public void transfer() {
        if (!StringUtils.isEmpty(time)) {
            String[] times = time.split(RANGE_TIME_SPLIT);
            if (times.length == 2) {
                st = times[0];
                et = times[1];
                if (!StringUtils.isEmpty(st)) {
                    try {
                        st = new SimpleDateFormat(STET_YYYYMMDDHHMMSS_FORMAT).format((new SimpleDateFormat(
                                TIME_MMDDYYYYHHMMSS_FORMAT).parse(st.trim())));
                    } catch (ParseException e) {
                    }
                }
                if (!StringUtils.isEmpty(et)) {
                    try {
                        et = new SimpleDateFormat(STET_YYYYMMDDHHMMSS_FORMAT).format((new SimpleDateFormat(
                                TIME_MMDDYYYYHHMMSS_FORMAT).parse(et.trim())));
                    } catch (ParseException e) {
                    }
                }
            }
        }
        start = (pager.getPage() - 1) * pager.getSize();
        size = pager.getSize();
        if (StringUtils.isEmpty(st)) {
            st = new SimpleDateFormat(STET_YYYYMMDDHHMMSS_FORMAT).format(DateUtils.addDays(new Date(), -30));
        }
        if (StringUtils.isEmpty(et)) {
            et = new SimpleDateFormat(STET_YYYYMMDDHHMMSS_FORMAT).format(new Date());
        }
        if (StringUtils.isEmpty(time)) {
            try {
                time = new SimpleDateFormat(TIME_MMDDYYYYHHMMSS_FORMAT).format((new SimpleDateFormat(
                        STET_YYYYMMDDHHMMSS_FORMAT).parse(st.trim())))
                        + RANGE_TIME_SPLIT
                        + new SimpleDateFormat(TIME_MMDDYYYYHHMMSS_FORMAT).format((new SimpleDateFormat(
                                STET_YYYYMMDDHHMMSS_FORMAT).parse(et.trim())));
            } catch (ParseException e) {
            }
        }
    }

    public void transfer(int pageSize) {
        pager.setSize(pageSize);
        transfer();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
