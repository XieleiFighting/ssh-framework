package com.hades.ssh.web.controller.maintain;

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

import com.hades.ssh.common.Constants;
import com.hades.ssh.common.web.controller.BaseCRUDController;
import com.hades.ssh.entity.maintain.TaskDefinition;
import com.hades.ssh.service.maintain.DynamicTaskApi;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月9日 上午10:11:13
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/admin/maintain/dynamicTask")
public class DynamicTaskController extends BaseCRUDController<TaskDefinition, Long> {

    @Autowired
    private DynamicTaskApi dynamicTaskApi;

    @Override
    public String create(Model model, @Valid @ModelAttribute("m") TaskDefinition m, BindingResult result, RedirectAttributes redirectAttributes) {
        if (permissionList != null) {
            this.permissionList.assertHasCreatePermission();
        }

        if (hasError(m, result)) {
            return showCreateForm(model);
        }
        dynamicTaskApi.addTaskDefinition(m);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "新增成功");
        return redirectToUrl(null);
    }

    @Override
    public String update(Model model, @Valid @ModelAttribute("m") TaskDefinition m, BindingResult result, @RequestParam(value = Constants.BACK_URL, required = false) String backURL, RedirectAttributes redirectAttributes) {
        if (permissionList != null) {
            this.permissionList.assertHasUpdatePermission();
        }

        if (hasError(m, result)) {
            return showUpdateForm(m, model);
        }
        dynamicTaskApi.updateTaskDefinition(m);
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "修改成功");
        return redirectToUrl(backURL);
    }

    @RequestMapping(value = "{id}/delete", method = RequestMethod.POST)
    public String delete(
            @RequestParam(value = "forceTermination") boolean forceTermination,
            @PathVariable("id") TaskDefinition m,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes) {
        if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }

        dynamicTaskApi.deleteTaskDefinition(forceTermination, m.getId());
        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "删除成功");
        return redirectToUrl(backURL);
    }

    @RequestMapping(value = "batch/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteInBatch(
            @RequestParam(value = "forceTermination") boolean forceTermination,
            @RequestParam(value = "ids", required = false) Long[] ids,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes) {
        if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }

        dynamicTaskApi.deleteTaskDefinition(forceTermination, ids);

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "删除成功");
        return redirectToUrl(backURL);
    }


    @RequestMapping("/start")
    public String startTask(
            @RequestParam(value = "ids", required = false) Long[] ids,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes) {
        if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }

        dynamicTaskApi.startTask(ids);

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "启动任务成功");
        return redirectToUrl(backURL);
    }

    @RequestMapping("/stop")
    public String stopTask(
            @RequestParam(value = "forceTermination") boolean forceTermination,
            @RequestParam(value = "ids", required = false) Long[] ids,
            @RequestParam(value = Constants.BACK_URL, required = false) String backURL,
            RedirectAttributes redirectAttributes) {
        if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }

        dynamicTaskApi.stopTask(forceTermination, ids);

        redirectAttributes.addFlashAttribute(Constants.MESSAGE, "停止任务成功");
        return redirectToUrl(backURL);
    }





    @RequestMapping(value = "{id}/delete/discard", method = RequestMethod.POST)
    @Override
    public String delete(@PathVariable("id") TaskDefinition taskDefinition, @RequestParam(value = Constants.BACK_URL, required = false) String backURL, RedirectAttributes redirectAttributes) {
        throw new RuntimeException("discard method");
    }

    @RequestMapping(value = "batch/delete/discard", method = {RequestMethod.GET, RequestMethod.POST})
    @Override
    public String deleteInBatch(@RequestParam(value = "ids", required = false) Long[] longs, @RequestParam(value = Constants.BACK_URL, required = false) String backURL, RedirectAttributes redirectAttributes) {
        throw new RuntimeException("discard method");
    }
}
