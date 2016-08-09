package com.hades.ssh.web.controller.sys;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hades.ssh.common.Constants;
import com.hades.ssh.common.plugin.web.controller.BaseTreeableController;
import com.hades.ssh.entity.enums.OrganizationType;
import com.hades.ssh.entity.sys.Organization;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月9日 上午9:33:52
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/sys/organization/organization")
public class OrganizationController extends BaseTreeableController<Organization, Long> {

    public OrganizationController() {
        setResourceIdentity("sys:organization");
    }

    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("types", OrganizationType.values());
    }


    @RequestMapping(value = "/changeStatus/{newStatus}")
    public String changeStatus(
            HttpServletRequest request,
            @PathVariable("newStatus") Boolean newStatus,
            @RequestParam("ids") Long[] ids,
            RedirectAttributes redirectAttributes
    ) {

        this.permissionList.assertHasUpdatePermission();

        for (Long id : ids) {
            Organization organization = baseService.findOne(id);
            organization.setShow(newStatus);
            baseService.update(organization);
        }

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "操作成功！");

        return "redirect:" + request.getAttribute(Constants.BACK_URL);
    }

}
