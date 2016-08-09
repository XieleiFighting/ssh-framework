package com.hades.ssh.web.controller.sys;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Sets;
import com.hades.ssh.common.Constants;
import com.hades.ssh.common.entity.enums.AvailableEnum;
import com.hades.ssh.common.entity.search.Searchable;
import com.hades.ssh.common.web.controller.BaseCRUDController;
import com.hades.ssh.entity.sys.Role;
import com.hades.ssh.entity.sys.RoleResourcePermission;
import com.hades.ssh.service.sys.PermissionService;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月9日 上午9:34:37
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/sys/permission/role")
public class RoleController extends BaseCRUDController<Role, Long> {

    @Autowired
    private PermissionService permissionService;

    public RoleController() {
        setResourceIdentity("sys:role");
    }

    @Override
    protected void setCommonData(Model model) {
        super.setCommonData(model);
        model.addAttribute("availableList", AvailableEnum.values());

        Searchable searchable = Searchable.newSearchable();
//        searchable.addSearchFilter("show", SearchOperator.eq, true);
        model.addAttribute("permissions", permissionService.findAllWithNoPageNoSort(searchable));
    }


    @RequestMapping(value = "create/discard", method = RequestMethod.POST)
    @Override
    public String create(
            Model model, @Valid @ModelAttribute("m") Role m, BindingResult result,
            RedirectAttributes redirectAttributes) {
        throw new RuntimeException("discarded method");
    }

    @RequestMapping(value = "{id}/update/discard", method = RequestMethod.POST)
    @Override
    public String update(
            Model model, @Valid @ModelAttribute("m") Role m, BindingResult result,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes) {

        throw new RuntimeException("discarded method");
    }


    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String createWithResourcePermission(
            Model model,
            @Valid @ModelAttribute("m") Role role, BindingResult result,
            @RequestParam("resourceId") Long[] resourceIds,
            @RequestParam("permissionIds") Long[][] permissionIds,
            RedirectAttributes redirectAttributes) {

        fillResourcePermission(role, resourceIds, permissionIds);

        return super.create(model, role, result, redirectAttributes);
    }

    @RequestMapping(value = "{id}/update", method = RequestMethod.POST)
    public String updateWithResourcePermission(
            Model model,
            @Valid @ModelAttribute("m") Role role, BindingResult result,
            @RequestParam("resourceId") Long[] resourceIds,
            @RequestParam("permissionIds") Long[][] permissionIds,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes) {

        fillResourcePermission(role, resourceIds, permissionIds);

        return super.update(model, role, result, backURL, redirectAttributes);
    }

    private void fillResourcePermission(Role role, Long[] resourceIds, Long[][] permissionIds) {
        int resourceLength = resourceIds.length;
        if (resourceIds.length == 0) {
            return;
        }

        if (resourceLength == 1) { //如果长度为1  那么permissionIds就变成如[[0],[1],[2]]这种
            Set<Long> permissionIdSet = Sets.newHashSet();
            for (Long[] permissionId : permissionIds) {
                permissionIdSet.add(permissionId[0]);
            }
            role.addResourcePermission(
                    new RoleResourcePermission(resourceIds[0], permissionIdSet)
            );

        } else {
            for (int i = 0; i < resourceLength; i++) {
                role.addResourcePermission(
                        new RoleResourcePermission(resourceIds[i], Sets.newHashSet(permissionIds[i]))
                );
            }
        }

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
            Role role = baseService.findOne(id);
            role.setShow(newStatus);
            baseService.update(role);
        }

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "操作成功！");

        return "redirect:" + request.getAttribute(Constants.BACK_URL);
    }


    @RequestMapping("{role}/permissions")
    public String permissions(@PathVariable("role") Role role) {
        return viewName("permissionsTable");
    }

}
