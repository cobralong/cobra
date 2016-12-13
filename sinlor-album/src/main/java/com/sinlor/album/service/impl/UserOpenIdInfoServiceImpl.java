package com.sinlor.album.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sinlor.web.utils.GsonUtils;
import com.sinlor.web.utils.RemoteDataUtil;
import com.sinlor.album.model.UserOpenIdInfo;
import com.sinlor.album.service.UserOpenIdInfoService;
import com.sinlor.core.utils.WxCommanUtils;

@Service("userOpenIdInfoService")
public class UserOpenIdInfoServiceImpl implements UserOpenIdInfoService {

    public static Logger log = Logger.getLogger(UserOpenIdInfoServiceImpl.class);

    @Override
    public UserOpenIdInfo getUser3rdSesseion(String code) {
        String api = "https://api.weixin.qq.com/sns/jscode2session?appid=" + WxCommanUtils.appid + "&secret="
                + WxCommanUtils.secret + "&js_code=" + code + "&grant_type=" + WxCommanUtils.grantType;
        String ret = null;
        try {
            ret = RemoteDataUtil.getRemote(api, 30000, 3);
        } catch (Exception e) {
            log.error("gain user openid failed.code=" + code);
        }
        if (ret == null) {
            return null;
        } else {
            try {
                UserOpenIdInfo info = GsonUtils.convert(ret, UserOpenIdInfo.class);
                if (info != null) {
                    return info;
                }
            } catch (Exception e) {
                log.error("parse opendid failed.code=" + code);
            }
        }
        return null;
    }
}
