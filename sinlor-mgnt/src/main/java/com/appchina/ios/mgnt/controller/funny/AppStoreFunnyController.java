package com.appchina.ios.mgnt.controller.funny;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appchina.ios.core.StatusType;
import com.appchina.ios.core.dto.app.RootApplication;
import com.appchina.ios.core.dto.info.AppStoreFunnyColumnDetail;
import com.appchina.ios.core.dto.info.AppStoreFunnyColumnDetailEdit;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumn;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumnAuthor;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumnType;
import com.appchina.ios.core.utils.AppStoreFunnyColumnDetailUtils;
import com.appchina.ios.mgnt.controller.model.JsonResult;
import com.appchina.ios.mgnt.controller.model.UploadFileResp;
import com.appchina.ios.mgnt.controller.model.funny.FunnyClientSpecialColumnAuthorParameter;
import com.appchina.ios.mgnt.controller.model.funny.FunnyClientSpecialColumnTypeParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreApplicationWrapper;
import com.appchina.ios.mgnt.controller.model.info.AppStoreFunnyColumnDetailParameter;
import com.appchina.ios.mgnt.controller.model.info.AppStoreRootApplicationPara;
import com.appchina.ios.mgnt.service.BackendApiService;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.mgnt.service.FunnyClientApiService;
import com.appchina.ios.mgnt.service.IosFunnyApiService;
import com.appchina.ios.mgnt.service.impl.FunnyCommonParameterService;
import com.appchina.ios.web.exception.ServiceException;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

@Controller
@RequestMapping(value = "/auth/store/funny/*")
public class AppStoreFunnyController {

    @Autowired
    private BannerStorageService bannerStorageService;
    @Autowired
    private IosFunnyApiService iosFunnyApiService;
    @Autowired
    private BackendApiService backendApiService;
    @Autowired
    private FunnyCommonParameterService funnyCommonParameterService;
    @Autowired
    private FunnyClientApiService funnyClientApiService;

    @RequestMapping(value = "/column/content/add", method = RequestMethod.POST)
    public String columnAddContentPost(Model model, AppStoreFunnyColumnDetailParameter para) {
        String errMsg = checkParameter(para);
        if (StringUtils.isEmpty(errMsg)) {
            if (para.isPublish()) {
                ApiRespWrapper<FunnyClientSpecialColumnAuthor> authorResp = funnyClientApiService
                        .getFunnySpecialColumnAuthor(Integer.valueOf(para.getAuthorId()));
                FunnyClientSpecialColumnAuthor author = authorResp.getData();
                String authorName = author.getName();
                String authorImage = author.getIcon();
                List<FunnyClientSpecialColumn> gg = new ArrayList<FunnyClientSpecialColumn>();
                ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumn>> resp = this.iosFunnyApiService
                        .listFunnyClientSpecialColumnByIds(para.getReferenceColumnId());
                if (resp != null && resp.getData() != null && resp.getData().getResultList() != null
                        && resp.getData().getResultList().size() > 0) {
                    gg = resp.getData().getResultList();
                }
                Map<String, String> ctypesall = findAllCtypes();
                String viewUrl = bannerStorageService.saveStoreFunnyColumnDetail(authorName, authorImage, para, gg,
                        true, ctypesall);
                if (StringUtils.isEmpty(para.getViewNoApplicationUrl())) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw ServiceException.getInternalException("Thread error.");
                    }
                }
                String viewNoApplicationUrl = bannerStorageService.saveStoreFunnyColumnDetail(authorName, authorImage,
                        para, gg, false, ctypesall);
                para.setViewUrl(viewUrl);
                para.setViewNoApplicationUrl(viewNoApplicationUrl);
            }
            ApiRespWrapper<Boolean> resp = this.iosFunnyApiService.addFunnyColumnDetail(para);
            if (resp != null && resp.getData()) {
                errMsg = resp.getMessage();
                model.addAttribute("saveStatus", resp.getStatus());
            } else {
                throw ServiceException.getInternalException(resp.getMessage());
            }
            para = new AppStoreFunnyColumnDetailParameter();
            model.addAttribute("value", para);
        } else {
            model.addAttribute("value", para);
        }
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("currentStatus", para.getCurrentType());
        findAllTypes(model);
        return "store/funny/column/content/add.ftl";
    }

    private String checkParameter(AppStoreFunnyColumnDetailParameter para) {
        String errMsg = "";
        if (StringUtils.isEmpty(para.getTitle())) {
            errMsg = "标题不能为空！";
        } else if (StringUtils.isEmpty(para.getAuthorId())
                || para.getAuthorId().equals(FunnyClientSpecialColumnType.ALL_TYPE)) {
            errMsg = "请选择一个作者！";
        } else if (StringUtils.isEmpty(para.getCtypeId())
                || para.getCtypeId().equals(FunnyClientSpecialColumnType.ALL_TYPE)) {
            errMsg = "请选择一个分类！";
        } else if (StringUtils.isEmpty(para.getShowDate())) {
            errMsg = "请选择展示日期！";
        } else if (StringUtils.isEmpty(para.getContent())) {
            errMsg = "请输入内容！";
        }
        return errMsg;
    }

    @RequestMapping(value = "/column/content/add", method = RequestMethod.GET)
    public String columnAddContentGet(Model model, AppStoreFunnyColumnDetailParameter para) {
        findAllTypes(model);
        model.addAttribute("value", para);
        return "store/funny/column/content/add.ftl";
    }

    @RequestMapping(value = "/column/content/addimage")
    public void ColumnAddImage(@RequestParam(required = false) CommonsMultipartFile upload,
            @ModelAttribute("errMsg") String errMsg, String CKEditorFuncNum, Model model, HttpServletResponse response) {
        UploadFileResp resp = bannerStorageService.saveColumnContentImg(upload);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ",'" + resp.getUrl() + "','')");
            out.println("</script>");
            out.flush();
        } catch (IOException e) {
            throw ServiceException.getInternalException("保存图片失败！" + e.getMessage());
        }
    }

    @RequestMapping(value = "/column/content/list")
    public String listFunnyCloumnDetail(@ModelAttribute("errMsg") String errMsg,
            AppStoreFunnyColumnDetailParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppStoreFunnyColumnDetail>> resp = this.iosFunnyApiService
                .listAppStoreFunnyColumnDetail(para);
        List<AppStoreFunnyColumnDetail> values = new ArrayList<AppStoreFunnyColumnDetail>();
        if (resp != null && resp.getData() != null && resp.getData().getResultList() != null) {
            values = resp.getData().getResultList();
            para.getPager().setTotal(resp.getData().getTotalCount());
        }
        ApiRespWrapper<ListWrapResp<Integer>> columnContentIds = this.funnyClientApiService
                .getFunnySpecialColumnListColumnContentIds();
        if (columnContentIds != null && columnContentIds.getData() != null
                && columnContentIds.getData().getResultList().size() > 0) {
            Map<String, String> map = new HashMap<String, String>();
            for (Integer id : columnContentIds.getData().getResultList()) {
                map.put(String.valueOf(id), "trues");
            }
            model.addAttribute("columncontentids", map);
        }
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        findAllTypes(model);
        return "store/funny/column/content/list.ftl";
    }

    @RequestMapping(value = "/column/content/editlist")
    public String listFunnyCloumnDetailEdit(@ModelAttribute("errMsg") String errMsg,
            AppStoreFunnyColumnDetailParameter para, Model model) {
        para.transfer();
        ApiRespWrapper<ListWrapResp<AppStoreFunnyColumnDetailEdit>> resp = this.iosFunnyApiService
                .listAppStoreFunnyColumnDetailEdit(para);
        List<AppStoreFunnyColumnDetailEdit> values = new ArrayList<AppStoreFunnyColumnDetailEdit>();
        if (resp != null && resp.getData() != null && resp.getData().getResultList() != null) {
            values = resp.getData().getResultList();
            para.getPager().setTotal(resp.getData().getTotalCount());
        }
        model.addAttribute("values", values);
        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        findAllTypes(model);
        return "store/funny/column/content/editlist.ftl";
    }

    @RequestMapping(value = "/column/content/detail")
    public String funnyCloumnContentDetail(AppStoreFunnyColumnDetailParameter para, Model model,
            RedirectAttributes rattrs) {
        String errMsg = "";
        if (para.getId() == null || para.getId().intValue() < 0 || para.getType() == null) {
            errMsg = "id无效！";
            model.addAttribute("errMsg", errMsg);
            return "redirect:/auth/store/funny/column/content/list.ftl";
        }
        para.transfer();
        if (para.getType() == AppStoreFunnyColumnDetailUtils.FUNNY_COLUMN_DETAIL_PUBLISH_TYRE) {
            ApiRespWrapper<ListWrapResp<AppStoreFunnyColumnDetail>> resp = this.iosFunnyApiService
                    .listAppStoreFunnyColumnDetail(para);
            List<AppStoreFunnyColumnDetail> values = new ArrayList<AppStoreFunnyColumnDetail>();
            if (resp != null && resp.getData() != null && resp.getData().getResultList() != null) {
                values = resp.getData().getResultList();
            }
            model.addAttribute("value", values.get(0));
            model.addAttribute("currentStatus", para.getType());
        } else if (para.getType() == AppStoreFunnyColumnDetailUtils.FUNNY_COLUMN_DETAIL_EDIY_TYRE) {
            ApiRespWrapper<ListWrapResp<AppStoreFunnyColumnDetailEdit>> resp = this.iosFunnyApiService
                    .listAppStoreFunnyColumnDetailEdit(para);
            List<AppStoreFunnyColumnDetailEdit> values = new ArrayList<AppStoreFunnyColumnDetailEdit>();
            if (resp != null && resp.getData() != null && resp.getData().getResultList() != null) {
                values = resp.getData().getResultList();
            }
            model.addAttribute("value", values.get(0));
            model.addAttribute("currentStatus", para.getType());
        }
        findAllTypes(model);
        return "store/funny/column/content/add.ftl";
    }

    @RequestMapping(value = "/column/content/search")
    @ResponseBody
    public JsonResult<ArrayList<AppStoreApplicationWrapper>> search(String rootIds, AppStoreRootApplicationPara para) {
        JsonResult<ArrayList<AppStoreApplicationWrapper>> json = new JsonResult<ArrayList<AppStoreApplicationWrapper>>();
        ArrayList<AppStoreApplicationWrapper> wrapperList = new ArrayList<AppStoreApplicationWrapper>();
        if (!StringUtils.isEmpty(rootIds)) {
            String[] rootIdsList = rootIds.split(",");
            for (String rootId : rootIdsList) {
                if (!StringUtils.isEmpty(rootId)) {
                    fetchApplicationWrapper(Integer.valueOf(rootId.trim()), para, wrapperList);
                }
            }
        } else if (!StringUtils.isEmpty(para.getBundleId())) {
            String[] bundleIdsList = para.getBundleId().split(",");
            for (String bundleId : bundleIdsList) {
                if (!StringUtils.isEmpty(bundleId.trim())) {
                    ApiRespWrapper<RootApplication> rootApplication = null;
                    para.setBundleId(bundleId.trim());
                    rootApplication = this.iosFunnyApiService.searchRootApplication(para);
                    if (rootApplication != null && rootApplication.getData() != null) {
                        fetchApplicationWrapper(rootApplication.getData().getRootId(), para, wrapperList);
                    }
                }
            }
        } else if (!StringUtils.isEmpty(para.getItemId())) {
            String[] itemIdsList = para.getItemId().split(",");
            for (String itemId : itemIdsList) {
                if (!StringUtils.isEmpty(itemId.trim())) {
                    ApiRespWrapper<RootApplication> rootApplication = null;
                    para.setItemId(itemId.trim());
                    rootApplication = this.iosFunnyApiService.searchRootApplication(para);
                    if (rootApplication != null && rootApplication.getData() != null) {
                        fetchApplicationWrapper(rootApplication.getData().getRootId(), para, wrapperList);
                    }
                }
            }
        }
        json.setData(wrapperList);
        json.setSuccess(true);
        return json;
    }

    private void fetchApplicationWrapper(Integer rootId, AppStoreRootApplicationPara para,
            ArrayList<AppStoreApplicationWrapper> wrapperList) {
        ApiRespWrapper<AppStoreApplicationWrapper> resp = null;
        if (rootId != null) {
            resp = backendApiService.getApplicationWrapper(rootId);
        }
        if (resp != null && resp.getData() != null) {
            double size = new BigDecimal(Double.valueOf(resp.getData().getSize()) / 1024 / 1024).setScale(2,
                    BigDecimal.ROUND_HALF_UP).doubleValue();
            resp.getData().setRealSize(size);
            wrapperList.add(resp.getData());
        }
    }

    @RequestMapping(value = "/column/content/preview")
    @ResponseBody
    public JsonResult<String> preview(AppStoreFunnyColumnDetailParameter para) {
        JsonResult<String> json = new JsonResult<String>();
        if (StringUtils.isEmpty(para.getAuthorId())
                || para.getAuthorId().equals(AppStoreFunnyColumnDetailUtils.FUNNY_ALL)) {
            json.setSuccess(false);
            json.setMessage("请选择作者!");
            return json;
        }
        if (StringUtils.isEmpty(para.getShowDate())) {
            json.setSuccess(false);
            json.setMessage("请选择展示日期!");
            return json;
        }
        ApiRespWrapper<FunnyClientSpecialColumnAuthor> authorResp = funnyClientApiService
                .getFunnySpecialColumnAuthor(Integer.valueOf(para.getAuthorId()));
        FunnyClientSpecialColumnAuthor author = authorResp.getData();
        String authorName = author.getName();
        String authorImage = author.getIcon();
        List<FunnyClientSpecialColumn> gg = new ArrayList<FunnyClientSpecialColumn>();
        ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumn>> resp = this.iosFunnyApiService
                .listFunnyClientSpecialColumnByIds(para.getReferenceColumnId());
        if (resp != null && resp.getData() != null && resp.getData().getResultList() != null
                && resp.getData().getResultList().size() > 0) {
            gg = resp.getData().getResultList();
        }
        Map<String, String> ctypesall = findAllCtypes();
        json.setData(bannerStorageService.previewHtml(para, authorName, authorImage, gg, ctypesall));
        json.setSuccess(true);
        return json;
    }

    private Map<String, String> findAllTypes(Model model) {
        FunnyClientSpecialColumnAuthorParameter para = new FunnyClientSpecialColumnAuthorParameter();
        para.setStatus(FunnyClientSpecialColumnAuthor.STATUS_OK);
        para.setSize(Integer.MAX_VALUE);
        Map<String, String> returnMap = new HashMap<String, String>();
        ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnAuthor>> authors = funnyClientApiService
                .listFunnySpecialColumnAuthors(para);
        if (authors != null && authors.getData() != null && authors.getData().getResultList() != null) {
            List<FunnyClientSpecialColumnAuthor> gg = authors.getData().getResultList();
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put(AppStoreFunnyColumnDetailUtils.FUNNY_ALL, "全部");
            for (FunnyClientSpecialColumnAuthor g : gg) {
                map.put(String.valueOf(g.getId()), g.getName());
            }
            model.addAttribute("authorsall", map);
        }
        FunnyClientSpecialColumnTypeParameter cType = new FunnyClientSpecialColumnTypeParameter();
        cType.setStatus(StatusType.STATUS_ALL.getStatus());
        ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnType>> types = funnyClientApiService
                .listFunnySpecialColumnTypes(cType);
        if (types != null && types.getData() != null && types.getData().getResultList() != null) {
            List<FunnyClientSpecialColumnType> gg = types.getData().getResultList();
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put(AppStoreFunnyColumnDetailUtils.FUNNY_ALL, "全部");
            for (FunnyClientSpecialColumnType g : gg) {
                map.put(String.valueOf(g.getId()), g.getName());
            }
            returnMap = map;
            model.addAttribute("ctypesall", map);
        }
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put(AppStoreFunnyColumnDetailUtils.FUNNY_ALL_INTEGER, "全部");
        map.put(String.valueOf(AppStoreFunnyColumnDetailUtils.FUNNY_COLUMN_STATUS_EDIT),
                AppStoreFunnyColumnDetailUtils.FUNNY_COLUMN_STATUS_EDIT_NAME);
        map.put(String.valueOf(AppStoreFunnyColumnDetailUtils.FUNNY_COLUMN_STATUS_PUBLISH),
                AppStoreFunnyColumnDetailUtils.FUNNY_COLUMN_STATUS_PUBLIS_NAME);
        model.addAttribute("editStatusAll", map);

        Map<String, String> srcMap = new LinkedHashMap<String, String>();
        srcMap.put(AppStoreFunnyColumnDetailUtils.FUNNY_ALL_INTEGER, "全部");
        srcMap.put(String.valueOf(AppStoreFunnyColumnDetailUtils.FUNNY_COLUMN_CONTENT_FROM_EDIT),
                AppStoreFunnyColumnDetailUtils.FUNNY_COLUMN_CONTENT_FROM_LEDIT);
        srcMap.put(String.valueOf(AppStoreFunnyColumnDetailUtils.FUNNY_COLUMN_CONTENT_ANDROID),
                AppStoreFunnyColumnDetailUtils.FUNNY_COLUMN_CONTENT_FROM_ANDROID);
        model.addAttribute("srcTypesall", srcMap);

        Map<String, String> applicationSetsMap = new LinkedHashMap<String, String>();
        applicationSetsMap.put(AppStoreFunnyColumnDetailUtils.FUNNY_ALL_INTEGER, "全部");
        applicationSetsMap.put(String.valueOf(AppStoreFunnyColumnDetailUtils.FUNNY_APPLICATIONSETS_LATEST_NUMBER),
                AppStoreFunnyColumnDetailUtils.FUNNY_APPLICATIONSETS_LATEST);
        applicationSetsMap.put(String.valueOf(AppStoreFunnyColumnDetailUtils.FUNNY_APPLICATIONSETS_HOT_NUMBER),
                AppStoreFunnyColumnDetailUtils.FUNNY_APPLICATIONSETS_HOT);
        model.addAttribute("applicationSetsall", applicationSetsMap);
        return returnMap;
    }

    private Map<String, String> findAllCtypes() {
        FunnyClientSpecialColumnTypeParameter cType = new FunnyClientSpecialColumnTypeParameter();
        cType.setStatus(StatusType.STATUS_ALL.getStatus());
        ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnType>> types = funnyClientApiService
                .listFunnySpecialColumnTypes(cType);
        if (types != null && types.getData() != null && types.getData().getResultList() != null) {
            List<FunnyClientSpecialColumnType> gg = types.getData().getResultList();
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put(AppStoreFunnyColumnDetailUtils.FUNNY_ALL, "全部");
            for (FunnyClientSpecialColumnType g : gg) {
                map.put(String.valueOf(g.getId()), g.getName());
            }
            return map;
        }
        return null;
    }
}
