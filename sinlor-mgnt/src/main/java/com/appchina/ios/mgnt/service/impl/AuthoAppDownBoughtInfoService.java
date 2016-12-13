package com.appchina.ios.mgnt.service.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.appchina.ios.core.crawler.model.AuthoAppDownBoughtInfoResp;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

/**
 * 
 * @author yhx
 *
 */
@Service("authoAppDownBoughtInfoService")
public class AuthoAppDownBoughtInfoService<T> {

    @Autowired
    private BackendApiService backendApiService;

    public Map<String, String> buildRootIdBoughtMap(List<T> datas) throws Exception {
        List<Integer> rootIds = new ArrayList<Integer>();
        Map<String, String> maps = new HashMap<String, String>();
        for (T data : datas) {
            BeanInfo info = Introspector.getBeanInfo(data.getClass());
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                if (pd.getPropertyType() == Class.class) {
                    continue;
                }
                if (pd.getName().equalsIgnoreCase("rootId")) {
                    data.getClass().getDeclaredField(pd.getName()).setAccessible(true);
                    Method reader = pd.getReadMethod();
                    rootIds.add((Integer) reader.invoke(data));
                    break;
                }
            }
        }
        ApiRespWrapper<ListWrapResp<AuthoAppDownBoughtInfoResp>> resp = backendApiService
                .getAuthorizerAppDownAndBuyInfoStatusList(rootIds);
        List<AuthoAppDownBoughtInfoResp> gg = new ArrayList<AuthoAppDownBoughtInfoResp>();
        if (resp != null && resp.getData() != null && resp.getData().getResultList() != null) {
            gg = resp.getData().getResultList();
        }
        for (AuthoAppDownBoughtInfoResp g : gg) {
            if (g.isDownload()) {
                maps.put(String.valueOf(g.getRootId()), "授权下载");
            } else if (g.isBought()) {
                maps.put(String.valueOf(g.getRootId()), "授权，下载失败");
            } else if (!g.isBought()) {
                maps.put(String.valueOf(g.getRootId()), "未购买");
            }
        }
        return maps;
    }

    public void wrapAuthoAppDownBoughtRootIdMap(Model model, List<T> datas) throws Exception {
        model.addAttribute("rootIdBoughtMap", buildRootIdBoughtMap(datas));
    }
}
