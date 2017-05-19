package com.zhang.util;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class WebUtil implements Serializable {

	private static final long serialVersionUID = 5L;

	private WebUtil() {

	}

	/**
	 * isAjaxRequest:判断请求是否为Ajax请求. <br/>
	 * 
	 * @author chenzhou
	 * @param request
	 *            请求对象
	 * @return boolean
	 * @since JDK 1.6
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		return "XMLHttpRequest".equals(header);
	}

	/**
	 * Customized to allow correct redirect for JSF partial HTTP request.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void redirect(HttpServletRequest request, HttpServletResponse response, String redirectURL)
		throws IOException {
		if (WebUtil.isAjaxRequest(request)) {
			response.setContentType("text/xml");
			response.setCharacterEncoding("UTF-8");
			response.setDateHeader("Expires", -1);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			Writer writer = response.getWriter();
			String encoding = "utf-8";
			writer.write("<?xml version='1.0' encoding='" + encoding + "'?>\n");
			writer.write("<partial-response>\n");
			writer.write("<redirect");
			writer.write(" url=\"" + redirectURL + "\"/>\n");
			writer.write("</partial-response>");
		} else {
			response.sendRedirect(response.encodeRedirectURL(redirectURL));
		}
	}
	 /**
     * 获取客户端IP
     * 
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
