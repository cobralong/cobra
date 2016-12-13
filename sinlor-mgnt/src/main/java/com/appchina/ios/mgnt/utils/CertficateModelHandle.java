package com.appchina.ios.mgnt.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.appchina.ios.mgnt.controller.model.funny.CertificateMadeModel;
import com.appchina.ios.web.utils.CollectionUtils;

/**
 * @author liuxinglong
 * @date 2016年11月8日
 */
public class CertficateModelHandle {
    private List<CertificateMadeModel> list = new ArrayList<CertificateMadeModel>();

    public class CertificateType {
        private String typename;
        private String picName;

        public CertificateType(String typename, String picName) {
            super();
            this.typename = typename;
            this.picName = picName;
        }

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public String getPicName() {
            return picName;
        }

        public void setPicName(String picName) {
            this.picName = picName;
        }
    }

    /**
     * chunv.png heibang.png nvrenwei.png haolaopo.png
     * 
     * chunan.png guanggun.png haonanren.png haolaogong.png
     * 
     * shilian.png chiqing.png jiehun.png zhenghun.png
     * 
     * liaotian.png qigai.png chuiniu.png jiechu.png
     * 
     * buyaolian.png fangpi.png maiba.png tiancai.png
     * 
     */
    public CertficateModelHandle() {
        list.add(new CertificateMadeModel(0, "chunv.png", 1, "girl", "处女证"));
        list.add(new CertificateMadeModel(1, "heibang.png", 1, "girl", "黑帮大姐大"));
        list.add(new CertificateMadeModel(2, "nvrenwei.png", 1, "girl", "最有女人味"));
        list.add(new CertificateMadeModel(3, "haolaopo.png", 2, "girl", "最佳好老婆"));
        list.add(new CertificateMadeModel(4, "chunan.png", 1, "boy", "处男证"));
        list.add(new CertificateMadeModel(5, "guanggun.png", 1, "boy", "光棍证"));
        list.add(new CertificateMadeModel(6, "haonanren.png", 1, "boy", "好男人证"));
        list.add(new CertificateMadeModel(7, "haolaogong.png", 2, "boy", "最佳好老公"));
        list.add(new CertificateMadeModel(8, "shilian.png", 1, "love", "失恋证"));
        list.add(new CertificateMadeModel(9, "chiqing.png", 1, "love", "痴情证"));
        list.add(new CertificateMadeModel(10, "jiehun.png", 3, "love", "结婚证"));
        list.add(new CertificateMadeModel(11, "zhenghun.png", 1, "love", "征婚证"));
        list.add(new CertificateMadeModel(12, "liaotian.png", 1, "work", "职业聊天证"));
        list.add(new CertificateMadeModel(13, "qigai.png", 1, "work", "乞丐行乞证"));
        list.add(new CertificateMadeModel(14, "chuiniu.png", 1, "work", "吹牛证"));
        list.add(new CertificateMadeModel(15, "jiechu.png", 1, "work", "十大杰出青年"));
        list.add(new CertificateMadeModel(16, "buyaolian.png", 1, "package", "死不要脸证"));
        list.add(new CertificateMadeModel(17, "fangpi.png", 1, "package", "放屁许可证"));
        list.add(new CertificateMadeModel(18, "maiba.png", 1, "package", "麦霸证"));
        list.add(new CertificateMadeModel(19, "tiancai.png", 1, "package", "天才证"));
    }

    public List<CertificateMadeModel> getModels() {
        return list;
    }

    public Map<String, List<CertificateType>> getTypes() {
        if (CollectionUtils.notEmptyAndNull(list)) {
            Map<String, List<CertificateType>> map = new HashMap<String, List<CertificateType>>();
            List<CertificateType> boytypes = new ArrayList<CertificateType>();
            List<CertificateType> girltypes = new ArrayList<CertificateType>();
            List<CertificateType> lovetypes = new ArrayList<CertificateType>();
            List<CertificateType> worktypes = new ArrayList<CertificateType>();
            List<CertificateType> packagetypes = new ArrayList<CertificateType>();
            for (CertificateMadeModel model : list) {
                if (StringUtils.equalsIgnoreCase("boy", model.getType())) {
                    boytypes.add(new CertificateType(model.getTypeName(), model.getPicName()));
                } else if (StringUtils.equalsIgnoreCase("girl", model.getType())) {
                    girltypes.add(new CertificateType(model.getTypeName(), model.getPicName()));
                } else if (StringUtils.equalsIgnoreCase("love", model.getType())) {
                    lovetypes.add(new CertificateType(model.getTypeName(), model.getPicName()));
                } else if (StringUtils.equalsIgnoreCase("work", model.getType())) {
                    worktypes.add(new CertificateType(model.getTypeName(), model.getPicName()));
                } else if (StringUtils.equalsIgnoreCase("package", model.getType())) {
                    packagetypes.add(new CertificateType(model.getTypeName(), model.getPicName()));
                }
            }
            map.put("boy", boytypes);
            map.put("girl", girltypes);
            map.put("love", lovetypes);
            map.put("work", worktypes);
            map.put("package", packagetypes);
            return map;
        } else {
            return null;
        }
    }
}
