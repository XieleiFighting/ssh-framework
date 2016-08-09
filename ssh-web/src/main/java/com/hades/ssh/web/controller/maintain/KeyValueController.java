/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.hades.ssh.web.controller.maintain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hades.ssh.common.web.controller.BaseCRUDController;
import com.hades.ssh.common.web.validate.ValidateResponse;
import com.hades.ssh.entity.maintain.KeyValue;
import com.hades.ssh.service.maintain.KeyValueService;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月9日 上午10:25:10
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/maintain/keyvalue")
public class KeyValueController extends BaseCRUDController<KeyValue, Long> {

    private KeyValueService keyValueService;

    public KeyValueController() {
        setResourceIdentity("maintain:icon");
    }


    /**
     * 验证返回格式
     * 单个：[fieldId, 1|0, msg]
     * 多个：[[fieldId, 1|0, msg],[fieldId, 1|0, msg]]
     *
     * @param fieldId
     * @param fieldValue
     * @return
     */
    @RequestMapping(value = "validate", method = RequestMethod.GET)
    @ResponseBody
    public Object validate(
            @RequestParam("fieldId") String fieldId, @RequestParam("fieldValue") String fieldValue,
            @RequestParam(value = "id", required = false) Long id) {
        ValidateResponse response = ValidateResponse.newInstance();

        if ("key".equals(fieldId)) {
            KeyValue keyValue = keyValueService.findByKey(fieldValue);
            if (keyValue == null || (keyValue.getId().equals(id) && keyValue.getKey().equals(fieldValue))) {
                //如果msg 不为空 将弹出提示框
                response.validateSuccess(fieldId, "");
            } else {
                response.validateFail(fieldId, "该键已被使用");
            }
        }
        return response.result();
    }



}
