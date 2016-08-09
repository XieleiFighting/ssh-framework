package com.hades.ssh.web.controller.sys;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hades.ssh.common.web.controller.BaseCRUDController;
import com.hades.ssh.entity.enums.UserStatus;
import com.hades.ssh.entity.sys.UserStatusHistory;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月9日 上午9:30:15
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/sys/user/statusHistory")
public class UserStatusHistoryController extends BaseCRUDController<UserStatusHistory, Long> {

    public UserStatusHistoryController() {
        setListAlsoSetCommonData(true);
        setResourceIdentity("sys:userStatusHistory");
    }


    @Override
    protected void setCommonData(Model model) {
        model.addAttribute("statusList", UserStatus.values());
    }

}
