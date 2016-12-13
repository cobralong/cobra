package com.appchina.ios.mgnt.controller.funny;

import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.appchina.ios.mgnt.controller.model.JsonResult;
import com.appchina.ios.mgnt.controller.model.funny.CertificateMadeModel;
import com.appchina.ios.mgnt.utils.CertficateModelHandle;
import com.appchina.ios.mgnt.utils.CertficateModelHandle.CertificateType;
import com.appchina.ios.mgnt.utils.CertificateMadeHandle;

/**
 * @author liuxinglong
 * @date 2016年11月7日
 */
@Controller
@RequestMapping("/funny/cert/*")
public class CertificateMadeController {
    private static final Logger log = Logger.getLogger("CertificateMadeController.class");

    @RequestMapping("/made.json")
    @ResponseBody
    private JsonResult<String> made(String name, int sex, String age, String certname, MultipartFile file) {
        CertificateMadeHandle tt = new CertificateMadeHandle();
        if (StringUtils.isBlank(certname)) {
            // 默认提交的模版
            certname = "guanggun.png";
        }
        BufferedImage d = tt.loadImageLocal("/srv/images/store/funny/certmodel/" + certname);
        tt.modifyImage(d, name, 320, 53);
        tt.modifyImage(d, sex == 0 ? '女' : '男', 320, 82);
        tt.modifyImage(d, age, 320, 110);
        tt.modifyImage(d, name, 370, 153);
        tt.modifyImage(d, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 355, 270);
        tt.modifyImage(d, "007", 400, 286);
        // 将多张图片合在一起
        BufferedImage b = tt.loadImageLocal(file);
        long ctime = new Date().getTime();
        tt.writeImageLocal("/srv/images/store/funny/cert/cert_" + ctime + ".png", tt.modifyImagetogeter(b, d));
        return new JsonResult<String>(true, "ok", "http://img.huiyoobao.com/funny/cert/cert_" + ctime + ".png");
    }

    @RequestMapping("/listmodel.json")
    @ResponseBody
    private JsonResult<List<CertificateMadeModel>> listmodels() {
        CertficateModelHandle handle = new CertficateModelHandle();
        return new JsonResult<List<CertificateMadeModel>>(true, "ok", handle.getModels());
    }

    @RequestMapping("/listtypes.json")
    @ResponseBody
    private JsonResult<Map<String, List<CertificateType>>> listtypes() {
        CertficateModelHandle handle = new CertficateModelHandle();
        return new JsonResult<Map<String, List<CertificateType>>>(true, "ok", handle.getTypes());
    }
}
