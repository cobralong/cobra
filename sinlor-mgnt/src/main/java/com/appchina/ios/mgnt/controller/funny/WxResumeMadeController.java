package com.appchina.ios.mgnt.controller.funny;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appchina.ios.mgnt.controller.model.JsonResult;
import com.appchina.ios.mgnt.utils.WordUtils;

/**
 * @author liuxinglong
 * @date 2016年11月16日
 */
@Controller
@RequestMapping("/funny/resume/*")
public class WxResumeMadeController {
    private static final Logger log = Logger.getLogger(WxResumeMadeController.class);

    @RequestMapping("/made.json")
    @ResponseBody
    private JsonResult<String> madeResume(String name, String sex, String birthday, String telephone, String email,
            String address, String education, String company, String position, String content, String certificate,
            String skill, String introduce) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
        params.put("sex", sex);
        params.put("birthday", birthday);
        params.put("telephone", telephone);
        params.put("email", email);
        params.put("address", address);
        params.put("education", education);
        params.put("company", company);
        params.put("position", position);
        params.put("content", content);
        params.put("certificate", certificate);
        params.put("skill", skill);
        params.put("introduce", introduce);
        String file = "/srv/images/store/funny/resume/demo.docx";
        String suffix = System.currentTimeMillis() + "_" + (int) Math.random() * 10000 + ".doc";
        String outfile = "/srv/images/store/funny/resume/" + suffix;
        WordUtils.createWord2007(params, file, outfile);
        return new JsonResult<String>(true, "ok", "http://img.huiyoobao.com/funny/resume/" + suffix);
    }
}
