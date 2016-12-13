package com.appchina.ios.mgnt.controller.funny;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appchina.ios.core.dto.info.AppStoreFunnyColumnDetail;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumn;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumnAuthor;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumnEdit;
import com.appchina.ios.core.dto.info.FunnyClientSpecialColumnType;
import com.appchina.ios.core.utils.DateUtils;
import com.appchina.ios.mgnt.controller.model.JsonResult;
import com.appchina.ios.mgnt.controller.model.funny.FunnyClientSpecialColumnAuthorParameter;
import com.appchina.ios.mgnt.controller.model.funny.FunnyClientSpecialColumnParameter;
import com.appchina.ios.mgnt.controller.model.funny.FunnyClientSpecialColumnTypeParameter;
import com.appchina.ios.mgnt.service.BannerStorageService;
import com.appchina.ios.mgnt.service.FunnyClientApiService;
import com.appchina.ios.mgnt.service.impl.FunnyCommonParameterService;
import com.appchina.ios.web.model.ApiRespWrapper;
import com.appchina.ios.web.model.ListWrapResp;

/**
 * 
 * @author liuxinglong
 * @date 2016年8月31日
 */
@Controller
@RequestMapping(value = "/funny/client/*")
public class FunnyClientColumnListController {
    @Autowired
    private FunnyClientApiService funnyClientApiService;
    @Autowired
    private FunnyCommonParameterService funnyCommonParameterService;
    @Autowired
    private BannerStorageService bannerStorageService;
    private Logger log = Logger.getLogger(FunnyClientColumnListController.class);

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(String errMsg, FunnyClientSpecialColumnParameter para, Model model) {
        funnyCommonParameterService.addColumnTypes("ctypes", model);
        funnyCommonParameterService.addColumnAuthors("authors", "authoricons", model);
        if (para.getColumnDetailId() > 0) {
            ApiRespWrapper<FunnyClientSpecialColumnEdit> columnEdit = funnyClientApiService
                    .getFunnySpecialColumnEditListByDetailId(para.getColumnDetailId());
            if (columnEdit != null && columnEdit.getData() != null) {
                FunnyClientSpecialColumnEdit edit = columnEdit.getData();
                para.setId(edit.getId());
                para.setTitle(edit.getTitle());
                para.setImg(edit.getImg());
                para.setAuthorId(edit.getAuthorId());
                para.setCtype(edit.getCtype());
                ApiRespWrapper<AppStoreFunnyColumnDetail> columnDetail = funnyClientApiService
                        .getAppStoreFunnyColumnDetailById(para.getColumnDetailId());
                if (columnDetail != null && columnDetail.getData() != null) {
                    para.setRelaeseTime(columnDetail.getData().getShowDate());
                } else {
                    para.setRelaeseTime(edit.getRelaeseTime());
                }
                para.setColumnDetailId(para.getColumnDetailId());
            }
        }
        model.addAttribute("para", para);
        return "store/funny/column/list/add.ftl";
    }

    @RequestMapping(value = "/android/add", method = RequestMethod.GET)
    public String addAndroid(String errMsg, FunnyClientSpecialColumnParameter para, Model model) {
        funnyCommonParameterService.addColumnTypes("ctypes", model);
        funnyCommonParameterService.addColumnAuthors("authors", "authoricons", model);
        if (para.getColumnDetailId() > 0) {
            ApiRespWrapper<FunnyClientSpecialColumnEdit> columnEdit = funnyClientApiService
                    .getFunnySpecialColumnEditListByOriDetailId(para.getColumnDetailId());
            if (columnEdit != null && columnEdit.getData() != null) {
                FunnyClientSpecialColumnEdit edit = columnEdit.getData();
                para.setId(edit.getId());
                para.setTitle(edit.getTitle());
                para.setImg(edit.getImg());
                para.setAuthorId(edit.getAuthorId());
                para.setCtype(edit.getCtype());
                ApiRespWrapper<AppStoreFunnyColumnDetail> columnDetail = funnyClientApiService
                        .getAppStoreFunnyColumnDetailByAndroidDid(para.getColumnDetailId());
                if (columnDetail != null && columnDetail.getData() != null) {
                    para.setRelaeseTime(columnDetail.getData().getShowDate());
                    para.setColumnDetailId(columnDetail.getData().getId());
                } else {
                    para.setRelaeseTime(edit.getRelaeseTime());
                    para.setColumnDetailId(para.getColumnDetailId());
                }

            }
        }
        model.addAttribute("para", para);
        return "store/funny/column/list/add.ftl";
    }

    @RequestMapping(value = "/appset/add", method = RequestMethod.GET)
    public String addAppset(String errMsg, FunnyClientSpecialColumnParameter para, Model model) {
        funnyCommonParameterService.addColumnTypes("ctypes", model);
        funnyCommonParameterService.addColumnAuthors("authors", "authoricons", model);
        if (para.getColumnDetailId() > 0) {
            ApiRespWrapper<FunnyClientSpecialColumnEdit> columnEdit = funnyClientApiService
                    .getFunnySpecialColumnEditListByOriAppsetId(para.getColumnDetailId());
            if (columnEdit != null && columnEdit.getData() != null) {
                FunnyClientSpecialColumnEdit edit = columnEdit.getData();
                para.setId(edit.getId());
                para.setTitle(edit.getTitle());
                para.setImg(edit.getImg());
                para.setAuthorId(edit.getAuthorId());
                para.setCtype(edit.getCtype());
                ApiRespWrapper<AppStoreFunnyColumnDetail> columnDetail = funnyClientApiService
                        .getAppStoreFunnyColumnDetailByAndroidAppsetId(para.getColumnDetailId());
                if (columnDetail != null && columnDetail.getData() != null) {
                    para.setRelaeseTime(columnDetail.getData().getShowDate());
                    para.setColumnDetailId(columnDetail.getData().getId());
                } else {
                    para.setRelaeseTime(edit.getRelaeseTime());
                    para.setColumnDetailId(para.getColumnDetailId());
                }
            }
        }
        model.addAttribute("para", para);
        return "store/funny/column/list/add.ftl";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(FunnyClientSpecialColumnParameter para,
            @RequestParam(required = false) CommonsMultipartFile image, Model model) {
        Date startTime = new Date();
        String errMsg = "";
        ApiRespWrapper<Boolean> resp = null;
        try {
            if (para.getPublish() == null) {
                errMsg = "是否保存参数传递异常!";
            } else if (para.getPublish() == 1) {
                if ((image != null && image.getSize() > 0)) {
                    String imgUrl = bannerStorageService.saveFunnyClientColumnListBackImg(image);
                    para.setImg(imgUrl);
                }
                resp = this.funnyClientApiService.addFunnySpecialColumnEdit(para);
            } else if (para.getPublish() == 0) {
                errMsg = checkAndFormatAddParameter(para.getTitle(), para.getCtype(), para.getAuthorId(), para.getSt(),
                        startTime, image, para.getImg(), para.getColumnDetailId());

                if (StringUtils.isBlank(errMsg)) {
                    ApiRespWrapper<FunnyClientSpecialColumn> columnList = funnyClientApiService
                            .getFunnySpecialColumnListByDetailId(para.getColumnDetailId());
                    if (columnList == null || columnList.getData() == null) {
                        String imgUrl = para.getImg();
                        if (StringUtils.isBlank(imgUrl)) {
                            imgUrl = bannerStorageService.saveFunnyClientColumnListBackImg(image);
                        }
                        para.setImg(imgUrl);
                        resp = this.funnyClientApiService.addFunnySpecialColumn(para);
                    } else {
                        errMsg = "详情ID为:" + para.getColumnDetailId() + " 对应的专栏列表已经存在!";
                    }
                }
            }
        } catch (Exception e) {
            log.error("Add column list failed.Data:" + para + ",errMsg:" + e.getMessage(), e);
            errMsg = "添加失败!";
        }
        if (StringUtils.isNotBlank(errMsg)) {
            // nothing to do
        } else if (resp == null) {
            errMsg = "未知错误!";
        } else if ((resp.getStatus() != 0 || resp.getData() == null)) {
            errMsg = StringUtils.isEmpty(resp.getMessage()) ? "未知错误!" : resp.getMessage();
        } else {
            errMsg = para.getPublish() == 0 ? "添加成功!" : "添加草稿成功!";
        }
        model.addAttribute("para", para);
        funnyCommonParameterService.addColumnTypes("ctypes", model);
        funnyCommonParameterService.addColumnAuthors("authors", "authoricons", model);
        model.addAttribute("errMsg", errMsg);
        return "store/funny/column/list/add.ftl";
    }

    @RequestMapping("/list")
    public String list(FunnyClientSpecialColumnParameter para, Model model) {
        para.transfer();
        if (para.getStatus() == null) {
            para.setStatus(FunnyClientSpecialColumn.STATUS_OK);
        }
        ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumn>> resp = funnyClientApiService
                .listFunnySpecialColumns(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<FunnyClientSpecialColumn> values = (resp == null || resp.getData() == null || resp.getData()
                .getResultList() == null) ? null : resp.getData().getResultList();
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        funnyCommonParameterService.addColumnTypes("ctypes", model);
        funnyCommonParameterService.addColumnAuthors("authors", "authoricons", model);
        model.addAttribute("para", para);
        return "store/funny/column/list/list.ftl";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modifyColumnList(int id, Integer promoteStatus, Integer status, String title, String st,
            Integer ctype, Integer authorId) {
        if (id <= 0) {
            return "id无效.";
        }
        FunnyClientSpecialColumnParameter para = new FunnyClientSpecialColumnParameter();
        para.setId(id);
        para.setStatus(status);
        para.setPromoteStatus(promoteStatus);
        para.setTitle(title);
        para.setSt(st);
        para.setCtype(ctype);
        para.setAuthorId(authorId);
        ApiRespWrapper<Boolean> resp = this.funnyClientApiService.modifyFunnySpecialColumn(para);
        if (resp == null) {
            return "未知错误";
        } else if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/modifyimg", method = RequestMethod.POST)
    public String modifyColumnImg(int id, @RequestParam(required = false) CommonsMultipartFile image, Model model) {
        String errMsg = "";
        if (id <= 0) {
            errMsg = "未知的记录Id";
        }
        if (image == null || image.isEmpty()) {
            errMsg = "未知的背景图";
        }
        String img = null;
        if (StringUtils.isBlank(errMsg)) {
            try {
                img = bannerStorageService.saveFunnyClientColumnListBackImg(image);
            } catch (Exception e) {
                errMsg = e.getMessage();
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            FunnyClientSpecialColumnParameter para = new FunnyClientSpecialColumnParameter();
            para.setId(id);
            para.setIcon(img);
            ApiRespWrapper<Boolean> resp = funnyClientApiService.modifyFunnySpecialColumn(para);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getStatus() != 0 || !Boolean.TRUE.equals(resp.getData())) {
                errMsg = resp.getMessage();
            } else {
                errMsg = "添加成功";
            }
        }
        model.addAttribute("id", id);
        model.addAttribute("errMsg", errMsg);
        return "redirect:/funny/client/detail";
    }

    @RequestMapping("/detail")
    public String columnListDetail(int id, String errMsg, Model model) {
        ApiRespWrapper<FunnyClientSpecialColumn> resp = funnyClientApiService.getFunnySpecialColumnList(id);
        FunnyClientSpecialColumn value = resp == null ? null : resp.getData();
        funnyCommonParameterService.addColumnTypes("ctypes", model);
        funnyCommonParameterService.addColumnAuthors("authors", "authoricons", model);
        model.addAttribute("value", value);
        if (StringUtils.isNotBlank(errMsg)) {
            model.addAttribute("errMsg", errMsg);
        }
        return "store/funny/column/list/detail.ftl";
    }

    @RequestMapping("/edit/list")
    public String listEdit(FunnyClientSpecialColumnParameter para, String errMsg, Model model) {
        para.transfer();
        if (para.getStatus() == null) {
            para.setStatus(FunnyClientSpecialColumnEdit.STATUS_OK);
        }
        ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnEdit>> resp = funnyClientApiService
                .listFunnySpecialColumnEdits(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<FunnyClientSpecialColumnEdit> values = (resp == null || resp.getData() == null || resp.getData()
                .getResultList() == null) ? null : resp.getData().getResultList();
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        funnyCommonParameterService.addColumnTypes("ctypes", model);
        funnyCommonParameterService.addColumnAuthors("authors", "authoricons", model);
        model.addAttribute("para", para);
        model.addAttribute("errMsg", errMsg);
        return "store/funny/column/list/edit/list.ftl";
    }

    @RequestMapping(value = "/edit/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modifyColumnListEdit(int id, Integer status, String title, String st, Integer ctype, Integer authorId) {
        if (id <= 0) {
            return "id无效.";
        }
        FunnyClientSpecialColumnParameter para = new FunnyClientSpecialColumnParameter();
        para.setId(id);
        para.setStatus(status);
        para.setTitle(title);
        para.setSt(st);
        para.setCtype(ctype);
        para.setAuthorId(authorId);
        ApiRespWrapper<Boolean> resp = this.funnyClientApiService.modifyFunnySpecialColumnEdit(para);
        if (resp == null) {
            return "未知错误";
        } else if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/edit/modifyimg", method = RequestMethod.POST)
    public String modifyColumnEditImg(int id, @RequestParam(required = false) CommonsMultipartFile image, Model model) {
        String errMsg = "";
        if (id <= 0) {
            errMsg = "未知的记录Id";
        }
        if (image == null || image.isEmpty()) {
            errMsg = "未知的背景图";
        }
        String img = null;
        if (StringUtils.isBlank(errMsg)) {
            try {
                img = bannerStorageService.saveFunnyClientColumnListBackImg(image);
            } catch (Exception e) {
                errMsg = e.getMessage();
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            FunnyClientSpecialColumnParameter para = new FunnyClientSpecialColumnParameter();
            para.setId(id);
            para.setImg(img);
            ApiRespWrapper<Boolean> resp = funnyClientApiService.modifyFunnySpecialColumnEdit(para);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getStatus() != 0 || !Boolean.TRUE.equals(resp.getData())) {
                errMsg = resp.getMessage();
            } else {
                errMsg = "添加成功";
            }
        }
        model.addAttribute("id", id);
        model.addAttribute("errMsg", errMsg);
        return "redirect:/funny/client/edit/detail";
    }

    @RequestMapping("/edit/detail")
    public String columnListEditDetail(int id, String errMsg, Model model) {
        ApiRespWrapper<FunnyClientSpecialColumnEdit> resp = funnyClientApiService.getFunnySpecialColumnEditList(id);
        FunnyClientSpecialColumnEdit value = resp == null ? null : resp.getData();
        funnyCommonParameterService.addColumnTypes("ctypes", model);
        funnyCommonParameterService.addColumnAuthors("authors", "authoricons", model);
        model.addAttribute("value", value);
        if (StringUtils.isNotBlank(errMsg)) {
            model.addAttribute("errMsg", errMsg);
        }
        return "store/funny/column/list/edit/detail.ftl";
    }

    @RequestMapping("/edit/exist")
    @ResponseBody
    public JsonResult<Boolean> columnEditExist(String id) {
        boolean success = false;
        String message;
        Boolean ret = false;
        int cid = NumberUtils.toInt(id, -1);
        if (cid <= 0) {
            message = "无效的Id--请从详情内容列表-列表编辑跳转.";
        } else {
            ApiRespWrapper<FunnyClientSpecialColumnEdit> editColumn = this.funnyClientApiService
                    .getFunnySpecialColumnEditListByDetailId(cid);
            ApiRespWrapper<FunnyClientSpecialColumn> columnList = funnyClientApiService
                    .getFunnySpecialColumnListByDetailId(cid);
            if ((editColumn == null || editColumn.getData() == null)
                    && (columnList == null || columnList.getData() == null)) {
                message = "未发布,请继续编辑!";
            } else {
                success = true;
                message = "ok";
                ret = true;
            }
        }
        return new JsonResult<Boolean>(success, message, ret);
    }

    @RequestMapping(value = "/edit/publish")
    public String publishEdit(int id, Model model) {
        ApiRespWrapper<FunnyClientSpecialColumnEdit> resp = funnyClientApiService.getFunnySpecialColumnEditList(id);
        FunnyClientSpecialColumnEdit value = resp == null ? null : resp.getData();
        String errMsg = "";
        if (StringUtils.isEmpty(errMsg) && StringUtils.isBlank(value.getImg())) {
            errMsg = "请提供背景图";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isBlank(value.getTitle())) {
            errMsg = "请填写正确的标题";
        }
        if (StringUtils.isEmpty(errMsg) && (value.getCtype() == 0)) {
            errMsg = "无效类型";
        }
        if (StringUtils.isEmpty(errMsg) && (value.getAuthorId() == 0)) {
            errMsg = "无效作者";
        }
        if (StringUtils.isEmpty(errMsg) && value.getColumnDetailId() <= 0) {
            errMsg = "列表未绑定详情页面ID";
        }
        ApiRespWrapper<FunnyClientSpecialColumn> columnList = funnyClientApiService
                .getFunnySpecialColumnListByDetailId(value.getOriColumnDetailId());
        if (columnList != null && columnList.getData() != null) {
            errMsg = "详情ID为:" + value.getColumnDetailId() + " 的对应专栏列表已经存在!";
        }
        if (StringUtils.isEmpty(errMsg)) {
            FunnyClientSpecialColumnParameter column = new FunnyClientSpecialColumnParameter();
            try {
                BeanUtils.copyProperties(column, value);
                // 发布
                column.setSt(DateUtils.formatStr(value.getRelaeseTime(), DateUtils.YYYYMMDDHHMMSS_FORMAT));
                ApiRespWrapper<Boolean> res = funnyClientApiService.addFunnySpecialColumn(column);
                if (res == null) {
                    errMsg = "未知错误!";
                } else if ((res.getStatus() != 0 || res.getData() == null)) {
                    errMsg = StringUtils.isEmpty(res.getMessage()) ? "未知错误!" : res.getMessage();
                } else {
                    FunnyClientSpecialColumnParameter delData = new FunnyClientSpecialColumnParameter();
                    delData.setId(id);
                    delData.setEditStatus(FunnyClientSpecialColumnEdit.TYPE_IS_PUBLISH);
                    funnyClientApiService.modifyFunnySpecialColumnEdit(delData);
                    errMsg = "发布成功!";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("errMsg", errMsg);
        return "redirect:/funny/client/edit/list";
    }

    private String checkAndFormatAddParameter(String title, Integer ctype, Integer author, String st, Date startTime,
            CommonsMultipartFile img, String image, int columnDetailId) {
        String errMsg = "";
        if (StringUtils.isEmpty(errMsg) && (img == null || img.getSize() == 0) && StringUtils.isEmpty(image)) {
            errMsg = "请提供背景图";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isBlank(title)) {
            errMsg = "请填写正确的标题";
        }
        if (StringUtils.isEmpty(errMsg) && (ctype == null)) {
            errMsg = "无效类型";
        }
        if (StringUtils.isEmpty(errMsg) && (author == null)) {
            errMsg = "无效作者";
        }
        if (StringUtils.isEmpty(errMsg) && StringUtils.isEmpty(st)) {
            errMsg = "无效的时间";
        }
        if (StringUtils.isEmpty(errMsg)) {
            try {
                startTime.setTime(DateUtils.parseDateStr(st).getTime());
            } catch (Exception e) {
                errMsg = "无效的时间";
            }
        }
        if (StringUtils.isEmpty(errMsg) && columnDetailId <= 0) {
            errMsg = "列表未绑定详情页面ID";
        }
        return errMsg;
    }

    @RequestMapping("/type/list")
    public String listType(FunnyClientSpecialColumnTypeParameter para, Model model) {
        para.transfer();
        if (para.getStatus() == null) {
            para.setStatus(FunnyClientSpecialColumnType.STATUS_OK);
        }
        ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnType>> resp = funnyClientApiService
                .listFunnySpecialColumnTypes(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<FunnyClientSpecialColumnType> values = (resp == null || resp.getData() == null || resp.getData()
                .getResultList() == null) ? null : resp.getData().getResultList();
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("status", FunnyClientSpecialColumnType.STATUS);
        model.addAttribute("para", para);
        return "store/funny/column/type/list.ftl";
    }

    @RequestMapping(value = "/type/add")
    public String addType(String name, Integer appPromoteStatus, Integer appNotPromoteStatus, RedirectAttributes rattrs) {
        String errMsg = "";
        FunnyClientSpecialColumnTypeParameter para = null;

        if (StringUtils.isEmpty(name)) {
            errMsg = "类型名称不能为空";
        }
        if (StringUtils.isEmpty(errMsg)) {
            para = new FunnyClientSpecialColumnTypeParameter();
            para.setName(name);
            para.setAppPromoteStatus(appPromoteStatus);
            para.setAppNotPromoteStatus(appNotPromoteStatus);
        }
        ApiRespWrapper<Boolean> resp = null;
        if (StringUtils.isEmpty(errMsg)) {
            resp = this.funnyClientApiService.addFunnySpecialColumnType(para);
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
            rattrs.addFlashAttribute("errMsg", "添加成功");
        } else {
            rattrs.addFlashAttribute("errMsg", errMsg);
        }
        return "redirect:/funny/client/type/list";
    }

    @RequestMapping(value = "/type/modify")
    @ResponseBody
    public String modifyType(Integer id, String name, Integer status, Integer appPromoteStatus,
            Integer appNotPromoteStatus) {
        if (id == null) {
            return "未知的分类id";
        }
        FunnyClientSpecialColumnTypeParameter para = new FunnyClientSpecialColumnTypeParameter();
        para.setId(id);
        para.setName(name);
        para.setStatus(status);
        para.setAppPromoteStatus(appPromoteStatus);
        para.setAppNotPromoteStatus(appNotPromoteStatus);
        ApiRespWrapper<Boolean> resp = this.funnyClientApiService.modifyFunnySpecialColumnType(para);
        if (resp == null) {
            return "未知错误";
        } else if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping("/author/list")
    public String listAuthors(FunnyClientSpecialColumnAuthorParameter para, Model model) {
        para.transfer();
        if (para.getStatus() == null) {
            para.setStatus(FunnyClientSpecialColumnAuthor.STATUS_OK);
        }
        ApiRespWrapper<ListWrapResp<FunnyClientSpecialColumnAuthor>> resp = funnyClientApiService
                .listFunnySpecialColumnAuthors(para);
        long totalCount = (resp == null || resp.getData() == null) ? 0 : resp.getData().getTotalCount();
        List<FunnyClientSpecialColumnAuthor> values = (resp == null || resp.getData() == null || resp.getData()
                .getResultList() == null) ? null : resp.getData().getResultList();
        para.getPager().setTotal(totalCount);
        model.addAttribute("values", values);
        model.addAttribute("status", FunnyClientSpecialColumnAuthor.STATUS);
        model.addAttribute("para", para);
        return "store/funny/column/author/list.ftl";
    }

    @RequestMapping(value = "/author/add")
    public String addAuthor(String userName, String name, String icon, RedirectAttributes rattrs) {
        String errMsg = "";
        FunnyClientSpecialColumnAuthorParameter para = null;

        if (StringUtils.isEmpty(userName)) {
            errMsg = "用户名不能为空";
        }
        if (StringUtils.isEmpty(errMsg)) {
            para = new FunnyClientSpecialColumnAuthorParameter();
            para.setUserName(userName);
            para.setName(name);
            para.setIcon(icon);
        }
        ApiRespWrapper<Boolean> resp = null;
        if (StringUtils.isEmpty(errMsg)) {
            resp = this.funnyClientApiService.addFunnySpecialColumnAuthor(para);
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
            rattrs.addFlashAttribute("errMsg", "添加成功");
        } else {
            rattrs.addFlashAttribute("errMsg", errMsg);
        }
        return "redirect:/funny/client/author/list";
    }

    @RequestMapping(value = "/author/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modifyAuthor(int id, String name, Integer status) {
        if (id <= 0) {
            return "id无效.";
        }
        FunnyClientSpecialColumnAuthorParameter para = new FunnyClientSpecialColumnAuthorParameter();
        para.setId(id);
        para.setName(name);
        para.setStatus(status);
        ApiRespWrapper<Boolean> resp = this.funnyClientApiService.modifyFunnySpecialColumnAuthor(para);
        if (resp == null) {
            return "未知错误";
        } else if (resp.getData() == null || !resp.getData()) {
            return resp.getMessage();
        }
        return "";
    }

    @RequestMapping(value = "/author/modifyicon", method = RequestMethod.POST)
    public String modifyicon(int id, @RequestParam(required = false) CommonsMultipartFile icon, Model model) {
        String errMsg = "";
        if (id <= 0) {
            errMsg = "未知的记录Id";
        }
        if (icon == null || icon.isEmpty()) {
            errMsg = "未知的图标";
        }
        String iconUrl = null;
        if (StringUtils.isBlank(errMsg)) {
            try {
                iconUrl = bannerStorageService.saveFunnyClientColumnAuthorImg(icon);
            } catch (Exception e) {
                errMsg = e.getMessage();
            }
        }
        if (StringUtils.isEmpty(errMsg)) {
            FunnyClientSpecialColumnAuthorParameter para = new FunnyClientSpecialColumnAuthorParameter();
            para.setId(id);
            para.setIcon(iconUrl);
            ApiRespWrapper<Boolean> resp = funnyClientApiService.modifyFunnySpecialColumnAuthor(para);
            if (resp == null) {
                errMsg = "未知错误";
            } else if (resp.getStatus() != 0 || !Boolean.TRUE.equals(resp.getData())) {
                errMsg = resp.getMessage();
            } else {
                errMsg = "添加成功";
            }
        }
        model.addAttribute("id", id);
        model.addAttribute("errMsg", errMsg);
        return "redirect:/funny/client/author/detail";
    }

    @RequestMapping("/author/detail")
    public String detail(int id, String errMsg, Model model) {
        ApiRespWrapper<FunnyClientSpecialColumnAuthor> resp = funnyClientApiService.getFunnySpecialColumnAuthor(id);
        FunnyClientSpecialColumnAuthor value = resp == null ? null : resp.getData();
        model.addAttribute("value", value);
        if (StringUtils.isNotBlank(errMsg)) {
            model.addAttribute("errMsg", errMsg);
        }
        return "store/funny/column/author/detail.ftl";
    }

    @RequestMapping(value = "/author/search")
    @ResponseBody
    public JsonResult<FunnyClientSpecialColumnAuthor> search(String id) {
        boolean success = false;
        String message;
        FunnyClientSpecialColumnAuthor ret = null;
        int rid = NumberUtils.toInt(id, -1);
        if (rid == -1) {
            message = "无效的Id";
        } else if (rid == 0) {
            String defaultIcon = "http://iosimg.yingyonghui.com/icon/664/810/810664_icon72x72.jpeg";
            success = true;
            message = "ok";
            ret = new FunnyClientSpecialColumnAuthor();
            ret.setId(0);
            ret.setIcon(defaultIcon);
        } else {
            ApiRespWrapper<FunnyClientSpecialColumnAuthor> resp = this.funnyClientApiService
                    .getFunnySpecialColumnAuthor(rid);
            if (resp == null || resp.getData() == null) {
                message = "无效的Id";
            } else {
                success = true;
                message = "ok";
                ret = resp.getData();
            }
        }
        return new JsonResult<FunnyClientSpecialColumnAuthor>(success, message, ret);
    }
}
