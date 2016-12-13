package com.appchina.ios.mgnt.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appchina.ios.core.DbStatus;
import com.appchina.ios.core.crawler.model.ApplicationSimple;
import com.appchina.ios.core.crawler.model.OnlineAppParameter;
import com.appchina.ios.core.crawler.model.OnlineAppResult;
import com.appchina.ios.core.dto.app.Application;
import com.appchina.ios.core.dto.app.IpaCertSignature;
import com.appchina.ios.core.dto.app.IpaPlist;
import com.appchina.ios.core.dto.app.UrgentOnlineRecord;
import com.appchina.ios.core.service.app.UrgentOnlineRecordService;
import com.appchina.ios.core.utils.AppStoreApplicationUtils;
import com.appchina.ios.mgnt.controller.model.IpaPlistParameter;
import com.appchina.ios.mgnt.controller.model.SignatureIpasParameter;
import com.appchina.ios.mgnt.controller.model.StatusStartSizeParameter;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.DaemonApiService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.StartSizeParameter;
import com.appchina.ios.web.utils.CollectionUtils;
import com.appchina.ios.web.utils.GsonUtils;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping(value = "/intern/*")
public class MgntNoAuthController {
    private static final Logger log = Logger.getLogger(MgntNoAuthController.class);
    @Autowired
    private DaemonApiService daemonApiService;
    @Autowired
    private BackendApiService backendApiService;
    @Autowired
    private UrgentOnlineRecordService urgentOnlineRecordService;

    @RequestMapping(value = "/urgentapps")
    @ResponseBody
    public ApiRespWrapper<List<Integer>> urgentapps(StartSizeParameter para) {
        if (para == null) {
            para = new StartSizeParameter();
        }
        List<Integer> rootIds = urgentOnlineRecordService.getRootIds(para.getStart(), para.getSize());
        return new ApiRespWrapper<List<Integer>>(rootIds);
    }

    @RequestMapping(value = "/signatures")
    public String signatures(StatusStartSizeParameter para, Model model) {
        List<IpaCertSignature> values = null;
        if (para == null) {
            para = new StatusStartSizeParameter();
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<IpaCertSignature>> ret = backendApiService.getEnterpriseSignatures(para);

        para.getPager().setTotal(ret.getData().getTotalCount());
        values = ret.getData().getResultList();
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        model.addAttribute("status", DbStatus.STATUS);
        return "noauth/signatures.ftl";
    }

    @RequestMapping(value = "/signatureipas")
    public String signatureipas(SignatureIpasParameter para, Model model) {
        List<IpaPlist> values = null;
        if (para == null) {
            para = new SignatureIpasParameter(null);
        }
        para.transfer();
        ApiRespWrapper<ListWrapResp<IpaPlist>> ret = backendApiService.getEnterpriseSignatureIpas(para);
        ApiRespWrapper<IpaCertSignature> enterpriseSignatureResp = this.backendApiService.getEnterpriseSignature(para);
        para.getPager().setTotal(ret.getData().getTotalCount());
        values = ret.getData().getResultList();
        formatEnterplistUrl(values);
        model.addAttribute("values", values);
        model.addAttribute("enterpriseSignature", enterpriseSignatureResp.getData());
        model.addAttribute("para", para);
        model.addAttribute("status", SignatureIpasParameter.ALL_STATUS);
        model.addAttribute("cates", IpaPlistParameter.CATES);
        return "noauth/signatureipas.ftl";
    }

    private void formatEnterplistUrl(List<IpaPlist> values) {
        if (values == null) {
            return;
        }
        String host = this.backendApiService.getPlistHostUrl().getData();
        for (IpaPlist ipaPlist : values) {
            if (StringUtils.isEmpty(ipaPlist.getEnterprisePlistUrl())) {
                continue;
            }
            if (ipaPlist.getEnterprisePlistUrl().startsWith("http")) {
                continue;
            }
            ipaPlist.setEnterprisePlistUrl(host + ipaPlist.getEnterprisePlistUrl());
        }
    }

    @RequestMapping(value = "/listeditorneedonlinedatas")
    @ResponseBody
    public ApiRespWrapper<ListWrapResp<OnlineAppParameter>> listeditorneedonlinedatas(int endId, int size) {
        List<UrgentOnlineRecord> rootIds = urgentOnlineRecordService.listNoRootIdDatas(endId, size);
        List<Integer> itemIds = new ArrayList<Integer>();
        for (UrgentOnlineRecord urgentOnlineRecord : rootIds) {
            if (itemIds.contains(urgentOnlineRecord.getItemId())) {
                continue;
            }
            itemIds.add(urgentOnlineRecord.getItemId());
        }

        ApiRespWrapper<Map<Integer, ApplicationSimple>> itemIdAppSimpleResp = backendApiService
                .getRootSimpleByItemIds(itemIds);
        Map<Integer, ApplicationSimple> onlinedItemIdAppSimple = new HashMap<Integer, ApplicationSimple>();
        if (itemIdAppSimpleResp != null && !CollectionUtils.emptyOrNull(itemIdAppSimpleResp.getData())) {
            onlinedItemIdAppSimple = itemIdAppSimpleResp.getData();
        }
        List<OnlineAppParameter> datas = new ArrayList<OnlineAppParameter>();
        List<UrgentOnlineRecord> urgentOnlinedRecords = new ArrayList<UrgentOnlineRecord>();
        for (UrgentOnlineRecord urgentOnlineRecord : rootIds) {
            // update rootId
            ApplicationSimple appSimple = onlinedItemIdAppSimple.get(urgentOnlineRecord.getItemId());
            if (appSimple != null) {
                urgentOnlineRecord.setRootId(appSimple.getRootId());
                urgentOnlineRecord.setName(appSimple.getName());
                urgentOnlineRecord.setLocale(AppStoreApplicationUtils.formatLocale(appSimple.getAppStoreUrl()));
                urgentOnlineRecord.setUrl(appSimple.getAppStoreUrl());
                urgentOnlinedRecords.add(urgentOnlineRecord);;
            }
            datas.add(convert(urgentOnlineRecord));
        }
        if (!urgentOnlinedRecords.isEmpty()) {
            urgentOnlineRecordService.modifyAppInfo(urgentOnlinedRecords);
        }
        boolean hasMore = rootIds.size() >= size;
        int nextId = rootIds.isEmpty() ? 0 : rootIds.get(rootIds.size() - 1).getId();
        return new ApiRespWrapper<ListWrapResp<OnlineAppParameter>>(new ListWrapResp<OnlineAppParameter>(datas.size(),
                datas, hasMore, nextId));
    }

    @RequestMapping(value = "/editorneedonlinecallback")
    @ResponseBody
    public ApiRespWrapper<Boolean> editorneedonlinecallback(final String datas) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Type type = new TypeToken<List<OnlineAppResult>>() {}.getType();
                    List<OnlineAppResult> results = GsonUtils.fromJsonStr(datas, type);
                    List<UrgentOnlineRecord> values = new ArrayList<UrgentOnlineRecord>();
                    for (OnlineAppResult result : results) {
                        if (!result.isSuccess()) {
                            continue;
                        }
                        UrgentOnlineRecord record = new UrgentOnlineRecord();
                        record.setId(result.getSpiderId());
                        record.setLocale(AppStoreApplicationUtils.formatLocale(result.getAppStoreUrl()));
                        record.setRootId(result.getRootId());
                        record.setUrl(result.getAppStoreUrl());
                        try {
                            ApiRespWrapper<Application> applicationResp = backendApiService
                                    .getApplicationByRootId(result.getRootId());
                            if (applicationResp != null && applicationResp.getData() != null) {
                                record.setName(applicationResp.getData().getTitle());
                            }
                        } catch (Exception e) {
                        }
                        values.add(record);
                    }
                    log.info("Process editor online callback. Data:" + values);
                    urgentOnlineRecordService.modifyAppInfo(values);
                } catch (Exception e) {
                    log.error("Format data to onlineappresult list failed.Errmsg:" + e.getMessage(), e);
                }
            }
        }.start();
        return new ApiRespWrapper<Boolean>(true);
    }

    private OnlineAppParameter convert(UrgentOnlineRecord urgentOnlineRecord) {
        OnlineAppParameter ret = new OnlineAppParameter();
        ret.setId(urgentOnlineRecord.getId());
        ret.setItemId(urgentOnlineRecord.getItemId());
        return ret;
    }
}
