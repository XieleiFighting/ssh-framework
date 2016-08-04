package com.hades.ssh.common.utils;

import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>User: XieLei
 * <p>Date: 2016年8月3日 上午11:13:58
 * <p>Version: 1.0
 */
public class ServletUtils {

    /**
     * 判断指定请求url是否以method请求的 firstPrefix+lastPrefixes开头
     * 如当前请求url是/sample/create 则匹配firstPrefix:/sample lastPrefixed /create
     *
     * @param request
     * @param method       请求的方法
     * @param firstPrefix
     * @param lastPrefixes
     * @return
     */
    public static boolean startWith(HttpServletRequest request, RequestMethod method, String firstPrefix, String... lastPrefixes) {
        String requestMethod = request.getMethod();
        if (!requestMethod.equalsIgnoreCase(method.name())) {
            return false;
        }
        String url = request.getServletPath();
        if (!url.startsWith(firstPrefix)) {
            return false;
        }

        if (lastPrefixes.length == 0) {
            return true;
        }

        url = url.substring(firstPrefix.length());
        for (String lastPrefix : lastPrefixes) {
            if (url.startsWith(lastPrefix)) {
                return true;
            }
        }

        return false;
    }
}
