package com.hades.ssh.web.controller.index;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hades.ssh.common.web.bind.annotation.CurrentUser;
import com.hades.ssh.entity.sys.User;
import com.hades.ssh.entity.tmp.Menu;
import com.hades.ssh.service.maintain.PushApi;
import com.hades.ssh.service.personal.CalendarService;
import com.hades.ssh.service.personal.MessageService;
import com.hades.ssh.service.sys.ResourceService;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月8日 下午3:00:18
 * <p>Version: 1.0
 */
@Controller("adminIndexController")
@RequestMapping("/admin")
public class IndexController {
	
	@Autowired
    private ResourceService resourceService;
	@Autowired
    private MessageService messageService;
	@Autowired
    private CalendarService calendarService;
	@Autowired
    private PushApi pushApi;

	@RequestMapping(value = {"/{index:index;?.*}"})
	public String index(@CurrentUser User user, Model model) {
		List<Menu> menus = resourceService.findMenus(user);
        model.addAttribute("menus", menus);

        pushApi.offline(user.getId());

        return "admin/index/index";
	}
	
	@RequestMapping(value = "/welcome")
    public String welcome(@CurrentUser User loginUser, Model model) {

        //未读消息
        Long messageUnreadCount = messageService.countUnread(loginUser.getId());
        model.addAttribute("messageUnreadCount", messageUnreadCount);

        //最近3天的日历
        model.addAttribute("calendarCount", calendarService.countRecentlyCalendar(loginUser.getId(), 2));

        return "admin/index/welcome";
    }
}
