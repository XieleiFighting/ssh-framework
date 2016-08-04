package com.hades.ssh.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hades.ssh.common.utils.IpUtils;
import com.hades.ssh.common.utils.LogUtils;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月4日 上午9:51:36
 * <p>Version: 1.0
 */
public class UserLogUtils {

    private static final Logger SYS_USER_LOGGER = LoggerFactory.getLogger("es-sys-user");

    /**
     * 记录格式 [ip][用户名][操作][错误消息]
     * <p/>
     * 注意操作如下：
     * loginError 登录失败
     * loginSuccess 登录成功
     * passwordError 密码错误
     * changePassword 修改密码
     * changeStatus 修改状态
     *
     * @param username
     * @param op
     * @param msg
     * @param args
     */
    public static void log(String username, String op, String msg, Object... args) {
        StringBuilder s = new StringBuilder();
        s.append(LogUtils.getBlock(getIp()));
        s.append(LogUtils.getBlock(username));
        s.append(LogUtils.getBlock(op));
        s.append(LogUtils.getBlock(msg));

        SYS_USER_LOGGER.info(s.toString(), args);
    }

    @SuppressWarnings("unused")
	public static Object getIp() {
        RequestAttributes requestAttributes = null;

        try {
            RequestContextHolder.currentRequestAttributes();
        } catch (Exception e) {
            //ignore  如unit test
        }

        if (requestAttributes != null && requestAttributes instanceof ServletRequestAttributes) {
            return IpUtils.getIpAddr(((ServletRequestAttributes) requestAttributes).getRequest());
        }

        return "unknown";

    }

}
