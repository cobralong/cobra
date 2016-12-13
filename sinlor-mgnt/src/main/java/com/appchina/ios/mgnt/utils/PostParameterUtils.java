package com.appchina.ios.mgnt.utils;

import com.appchina.ios.web.model.PostParametersHandle;
import com.appchina.ios.web.utils.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouyanhui on 3/31/16.
 */
public class PostParameterUtils {
    public static final PostParametersHandle<List<Integer>> ROOT_APPSIMPLE_HANDLE = new PostParametersHandle<List<Integer>>() {
        @Override
        public Map<String, Object> handle(List<Integer> values) {
            if (values == null) {
                return Collections.emptyMap();
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("rootIds", CollectionUtils.listToString(values, ","));
            return map;
        }
    };
}
