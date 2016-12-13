package com.appchina.ios.mgnt.controller.info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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

import com.appchina.ios.core.dto.info.CategoryRecom;
import com.appchina.ios.core.dto.info.StoreCategory;
import com.appchina.ios.core.utils.CategoryRecomUtils;
import com.appchina.ios.core.utils.CategoryRecomUtils.CateRecomSubCate;
import com.appchina.ios.mgnt.controller.model.StoreCateRecomParameter;
import com.appchina.ios.mgnt.controller.model.StoreVideoCateParameter;
import com.appchina.ios.mgnt.controller.model.info.SubReom;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.mgnt.service.IosStoreApiService;
import com.appchina.ios.mgnt.service.impl.StoreCommonParameterService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;
import com.appchina.ios.web.model.StartSizeParameter;

@Controller
@RequestMapping(value = "/auth/store/cate/*")
public class AppStoreCateController {
    private static final Logger log = Logger.getLogger(AppStoreCateController.class);
    @Autowired
    private IosStoreApiService iosStoreApiService;
    @Autowired
    private BannerStorageService bannerStorageService;
    @Autowired
    private StoreCommonParameterService storeCommonParameterService;

    @RequestMapping(value = "/videocatedetail")
    public String videocatedetail(int id, Model model) {
        ApiRespWrapper<StoreCategory> resp = null;
        try {
            resp = this.iosStoreApiService.getStoreVideoCate(id);
        } catch (Exception e) {
            log.error("获取视频分类失败.", e);
        }
        StoreCategory data;
        if (resp == null || resp.getData() == null) {
            data = null;
        } else {
            data = resp.getData();
        }
        model.addAttribute("data", data);
        return "store/cate/videocate.ftl";
    }

    @RequestMapping(value = "/videocates")
    public String videocates(@ModelAttribute("errMsg") String errMsg, Model model) {
        ApiRespWrapper<ListWrapResp<StoreCategory>> resp = null;
        try {
            StartSizeParameter para = new StartSizeParameter();
            para.setSize(Integer.MAX_VALUE);
            resp = this.iosStoreApiService.getStoreVideoCates(para);
        } catch (Exception e) {
            log.error("获取视频分类失败.", e);
        }
        List<StoreCategory> datas;
        if (resp == null || resp.getData() == null || resp.getData().getResultList() == null) {
            datas = Collections.emptyList();
        } else {
            datas = resp.getData().getResultList();
        }
        model.addAttribute("values", datas);
        model.addAttribute("errMsg", errMsg);
        return "store/cate/videocates.ftl";
    }

    @RequestMapping(value = "/addvideocate", method = RequestMethod.POST)
    public String addvideocate(String name, String desc, Integer position,
            @RequestParam(required = false) CommonsMultipartFile icon, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(name) || icon == null || icon.getSize() == 0) {
            errMsg = "未知分类数据";
        }
        String iconUrl = null;
        if (StringUtils.isEmpty(errMsg)) {
            try {
                iconUrl = bannerStorageService.saveStoreVideoCateIcon(UUID.randomUUID().variant(), icon);
            } catch (Exception e) {
                errMsg = "存储图标失败";
                log.error("存储图标失败", e);
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            StoreVideoCateParameter para = new StoreVideoCateParameter(position, name, desc, iconUrl);
            ApiRespWrapper<Boolean> resp = this.iosStoreApiService.addStoreVideoCate(para);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getData() == null) {
                errMsg = resp.getMessage();
            } else {
                if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/store/cate/videocates";
    }

    @RequestMapping(value = "/modifyvideocatename", method = RequestMethod.POST)
    @ResponseBody
    public String modifyvideocatename(int id, String name) {
        if (id <= 0 || StringUtils.isEmpty(name)) {
            return "未知的分类参数";
        }
        StoreVideoCateParameter para = new StoreVideoCateParameter();
        para.setId(id);
        para.setName(name);
        ApiRespWrapper<Boolean> resp = this.iosStoreApiService.modifyVideoCate(para);
        if (resp == null) {
            return "未知错误";
        } else if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/modifyvideocateicon", method = RequestMethod.POST)
    public String modifyvideocateicon(int id, @RequestParam(required = false) CommonsMultipartFile icon,
            RedirectAttributes rattrs) {
        String errMsg = "";
        if (icon == null || icon.getSize() == 0) {
            errMsg = "未知分类数据";
        }
        String iconUrl = null;
        if (StringUtils.isEmpty(errMsg)) {
            try {
                iconUrl = bannerStorageService.saveStoreVideoCateIcon(id, icon);
            } catch (Exception e) {
                errMsg = "存储图标失败";
                log.error("存储图标失败", e);
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            StoreVideoCateParameter para = new StoreVideoCateParameter();
            para.setId(id);
            para.setIcon(iconUrl);
            ApiRespWrapper<Boolean> resp = this.iosStoreApiService.modifyVideoCate(para);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getData() == null) {
                errMsg = resp.getMessage();
            } else {
                if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/store/cate/videocates";
    }

    @RequestMapping(value = "/apptagdetail")
    public String appTagDetail(int id, Model model) {
        ApiRespWrapper<StoreCategory> resp = null;
        try {
            resp = this.iosStoreApiService.getStoreAppTag(id);
        } catch (Exception e) {
            log.error("获取视频分类失败.", e);
        }
        StoreCategory data;
        if (resp == null || resp.getData() == null) {
            data = null;
        } else {
            data = resp.getData();
        }
        model.addAttribute("data", data);
        return "store/cate/apptag.ftl";
    }

    @RequestMapping(value = "/apptags")
    public String appTags(@ModelAttribute("errMsg") String errMsg, Model model) {
        ApiRespWrapper<ListWrapResp<StoreCategory>> resp = null;
        try {
            StartSizeParameter para = new StartSizeParameter();
            para.setSize(Integer.MAX_VALUE);
            resp = this.iosStoreApiService.getStoreAppTags(para);
        } catch (Exception e) {
            log.error("获取视频分类失败.", e);
        }
        List<StoreCategory> datas;
        if (resp == null || resp.getData() == null || resp.getData().getResultList() == null) {
            datas = Collections.emptyList();
        } else {
            datas = resp.getData().getResultList();
        }
        model.addAttribute("values", datas);
        model.addAttribute("errMsg", errMsg);
        return "store/cate/apptags.ftl";
    }

    @RequestMapping(value = "/addapptag", method = RequestMethod.POST)
    public String addAppTag(String name, String desc, Integer position,
            @RequestParam(required = false) CommonsMultipartFile icon, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(name) || icon == null || icon.getSize() == 0) {
            errMsg = "未知分类数据";
        }
        String iconUrl = null;
        if (StringUtils.isEmpty(errMsg)) {
            try {
                iconUrl = bannerStorageService.saveStoreAppCateIcon(UUID.randomUUID().variant(), icon);
            } catch (Exception e) {
                errMsg = "存储图标失败";
                log.error("存储图标失败", e);
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            StoreVideoCateParameter para = new StoreVideoCateParameter(position, name, desc, iconUrl);
            ApiRespWrapper<Boolean> resp = this.iosStoreApiService.addStoreAppTag(para);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getData() == null) {
                errMsg = resp.getMessage();
            } else {
                if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/store/cate/apptags";
    }

    @RequestMapping(value = "/modifyapptagname", method = RequestMethod.POST)
    @ResponseBody
    public String modifyAppTagName(int id, String name) {
        if (id <= 0 || StringUtils.isEmpty(name)) {
            return "未知的分类参数";
        }
        StoreVideoCateParameter para = new StoreVideoCateParameter();
        para.setId(id);
        para.setName(name);
        ApiRespWrapper<Boolean> resp = this.iosStoreApiService.modifyAppTag(para);
        if (resp == null) {
            return "未知错误";
        } else if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/modifyapptagicon", method = RequestMethod.POST)
    public String modifyAppTagIcon(int id, @RequestParam(required = false) CommonsMultipartFile icon,
            RedirectAttributes rattrs) {
        String errMsg = "";
        if (icon == null || icon.getSize() == 0) {
            errMsg = "未知分类数据";
        }
        String iconUrl = null;
        if (StringUtils.isEmpty(errMsg)) {
            try {
                iconUrl = bannerStorageService.saveStoreAppCateIcon(id, icon);
            } catch (Exception e) {
                errMsg = "存储图标失败";
                log.error("存储图标失败", e);
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            StoreVideoCateParameter para = new StoreVideoCateParameter();
            para.setId(id);
            para.setIcon(iconUrl);
            ApiRespWrapper<Boolean> resp = this.iosStoreApiService.modifyAppTag(para);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getData() == null) {
                errMsg = resp.getMessage();
            } else {
                if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            }
        }
        rattrs.addFlashAttribute("errMsg", errMsg);
        return "redirect:/auth/store/cate/apptags";
    }

    // 审核版全部分类推荐
    @RequestMapping(value = "/listrecomcatesforauditor")
    public String listrecomcatesforauditor(@ModelAttribute("errMsg") String errMsg,
            @ModelAttribute("showInAudit") String showInAudit, @ModelAttribute("bundleId") String bundleId,
            StoreCateRecomParameter para, Model model) {
        if (!StringUtils.isEmpty(showInAudit)) {
            para.setShowInAudit(Integer.valueOf(showInAudit));
        }
        if (!StringUtils.isEmpty(bundleId)) {
            para.setBundleId(bundleId);
        }
        model.addAttribute("errMsg", errMsg);
        return recomCate(para, true, model);
    }

    // 审核版全部分类推荐
    @RequestMapping(value = "/listrecomcatesforuser")
    public String listrecomcatesforuser(@ModelAttribute("errMsg") String errMsg,
            @ModelAttribute("showInAudit") String showInAudit, @ModelAttribute("bundleId") String bundleId,
            StoreCateRecomParameter para, Model model) {
        model.addAttribute("errMsg", errMsg);
        if (!StringUtils.isEmpty(showInAudit)) {
            para.setShowInAudit(Integer.valueOf(showInAudit));
        }
        if (!StringUtils.isEmpty(bundleId)) {
            para.setBundleId(bundleId);
        }
        return recomCate(para, false, model);
    }

    private String recomCate(StoreCateRecomParameter para, boolean auditing, Model model) {
        ApiRespWrapper<ListWrapResp<CategoryRecom>> resp = null;
        para.transfer();
        try {
            resp = this.iosStoreApiService.getAppStoreCateRecoms(para, auditing);
        } catch (Exception e) {
            log.error("获取全部分类失败.", e);
        }
        List<CategoryRecom> datas;
        if (resp == null || resp.getData() == null) {
            datas = null;
        } else {
            datas = resp.getData().getResultList();
            para.getPager().setTotal(resp.getData().getTotalCount());
        }
        model.addAttribute("datas", datas);
        initCateModelMaps(model);
        if (auditing) {
            model.addAttribute("pageName", "审核可见列表");
        } else {
            model.addAttribute("pageName", "用户可见列表");
        }
        if (auditing) {
            para.setShowInAudit(CategoryRecom.SHOW_IN_AUDITING_YES);
        } else {
            para.setShowInAudit(CategoryRecom.SHOW_IN_AUDITED_YES);
        }
        model.addAttribute("para", para);
        storeCommonParameterService.addBundles("bundleIds", model);
        return "store/cate/recomcatescachemodel.ftl";
    }

    private void initCateModelMaps(Model model) {
        model.addAttribute("types", CategoryRecom.TYPES);
        model.addAttribute("shows", CategoryRecom.SHOWS);
        model.addAttribute("status", CategoryRecom.STATUS);
    }

    // 审核版全部分类推荐
    @RequestMapping(value = "/recomcatedetail")
    public String recomcatedetail(int id, @ModelAttribute("errMsg") String errMsg, Model model) {
        ApiRespWrapper<CategoryRecom> resp = null;
        try {
            resp = this.iosStoreApiService.getAppStoreCateRecom(id);
        } catch (Exception e) {
            log.error("获取视频分类失败.", e);
        }
        CategoryRecom data;
        if (resp == null || resp.getData() == null) {
            data = null;
        } else {
            data = resp.getData();
        }
        model.addAttribute("data", data);
        List<SubReom> subRecoms = new ArrayList<SubReom>();
        if (data != null) {
            List<StoreCategory> cates = null;
            if (data.getType() == CategoryRecom.TYPE_APP_LIST) {
                cates = listAppCategory();
            } else if (data.getType() == CategoryRecom.TYPE_VIDEO_LIST) {
                cates = listVideoCategory();
            }
            CateRecomSubCate cateRecomSubCate = CategoryRecomUtils.parse(data);
            for (StoreCategory cate : cates) {
                int index = cateRecomSubCate.getSubCateIdList().indexOf(cate.getId());
                if (index >= 0) {
                    subRecoms.add(new SubReom(cate.getId(), cate.getName(), cateRecomSubCate.getSubCatePositionList()
                            .get(index).intValue(), true));
                } else {
                    subRecoms.add(new SubReom(cate.getId(), cate.getName(), 0, false));
                }
            }
        }
        model.addAttribute("errMsg", errMsg);
        model.addAttribute("subRecoms", subRecoms);
        initCateModelMaps(model);
        return "store/cate/recomcate.ftl";
    }

    @RequestMapping(value = "/modifyrecomcate")
    public String modifyRecomCate(StoreCateRecomParameter para, RedirectAttributes rattrs) {
        String errMsg = "";
        ApiRespWrapper<Boolean> resp = null;
        if (StringUtils.isEmpty(errMsg)) {
            resp = this.iosStoreApiService.modifyAppStoreCateRecom(para);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getData() == null) {
                errMsg = resp.getMessage();
            } else {
                if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            rattrs.addFlashAttribute("errMsg", "修改成功");
        } else {
            rattrs.addFlashAttribute("errMsg", errMsg);
        }
        rattrs.addAttribute("id", para.getId());
        return "redirect:/auth/store/cate/recomcatedetail";
    }

    // 审核版全部分类推荐
    @RequestMapping(value = "/modifysubcate")
    @ResponseBody
    public String modifySubCate(int id, int subCateId, Integer position, boolean checked, RedirectAttributes rattrs) {
        String errMsg = "";
        if (id <= 0 || subCateId <= 0 || (checked && position <= 0)) {
            errMsg = "无效的推荐Id或推荐位置";
        }
        if (StringUtils.isEmpty(errMsg)) {
            StoreCateRecomParameter para = new StoreCateRecomParameter();
            para.setId(id);
            para.setCateId(subCateId);
            ApiRespWrapper<Boolean> resp = null;
            try {
                if (checked) {
                    para.setCatePosition(position);
                    resp = this.iosStoreApiService.modifyAppStoreCateRecomSubCate(para);
                } else {
                    resp = this.iosStoreApiService.removeAppStoreCateRecomSubCate(para);
                }
            } catch (Exception e) {
                errMsg = "未知错误";
            }

            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getData() == null) {
                errMsg = resp.getMessage();
            } else {
                if (!resp.getData()) {
                    errMsg = resp.getMessage();
                }
            }
        }
        return errMsg;
    }

    private List<StoreCategory> listVideoCategory() {
        ApiRespWrapper<ListWrapResp<StoreCategory>> resp = null;
        try {
            resp = this.iosStoreApiService.getStoreVideoCates(StartSizeParameter.instanceAllList());
        } catch (Exception e) {
            log.error("获取视频分类失败.", e);
        }
        List<StoreCategory> datas;
        if (resp == null || resp.getData() == null || resp.getData().getResultList() == null) {
            datas = Collections.emptyList();
        } else {
            datas = resp.getData().getResultList();
        }
        return datas;
    }

    private List<StoreCategory> listAppCategory() {
        ApiRespWrapper<ListWrapResp<StoreCategory>> resp = null;
        try {
            resp = this.iosStoreApiService.getStoreAppTags(StartSizeParameter.instanceAllList());
        } catch (Exception e) {
            log.error("获取视频分类失败.", e);
        }
        List<StoreCategory> datas;
        if (resp == null || resp.getData() == null || resp.getData().getResultList() == null) {
            datas = Collections.emptyList();
        } else {
            datas = resp.getData().getResultList();
        }
        return datas;
    }

    // 审核版全部分类推荐
    @RequestMapping(value = "/addrecomcate")
    public String addrecomcate(String bundleId, String name, String color, int type, int showInAudit,
            int auditingPosition, int auditedPosition, RedirectAttributes rattrs) {
        String errMsg = "";
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(color)) {
            errMsg = "名称或颜色不能为空";
        }
        StoreCateRecomParameter para = new StoreCateRecomParameter();
        if (StringUtils.isEmpty(errMsg)) {
            ApiRespWrapper<Boolean> resp = null;
            para.setName(name);
            if (color.startsWith("#")) {
                color = color.replace("#", "");
            }
            para.setColor(color);
            para.setType(type);
            para.setBundleId(bundleId);
            para.setAuditedPosition(auditedPosition);
            para.setAuditingPosition(auditingPosition);
            para.setShowInAudit(showInAudit);
            try {
                resp = this.iosStoreApiService.addAppStoreCateRecom(para);
                if (resp == null) {
                    errMsg = "未知错误";
                } else if (resp.getData() == null) {
                    errMsg = resp.getMessage();
                } else {
                    if (!resp.getData()) {
                        errMsg = resp.getMessage();
                    } else {
                        errMsg = "添加成功";
                    }
                }
            } catch (Exception e) {
                log.error("添加推荐分类失败.", e);
            }
        }
        rattrs.addFlashAttribute("bundleId", para.getBundleId());
        rattrs.addFlashAttribute("showInAudit", para.getShowInAudit().toString());
        rattrs.addFlashAttribute("errMsg", errMsg);
        if (showInAudit == CategoryRecom.SHOW_IN_AUDITING_YES) {
            return "redirect:/auth/store/cate/listrecomcatesforauditor";
        }
        return "redirect:/auth/store/cate/listrecomcatesforuser";
    }
}
