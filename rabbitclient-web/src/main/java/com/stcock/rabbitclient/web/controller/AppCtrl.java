package com.stcock.rabbitclient.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.stcock.rabbitclient.web.SessionUtil;
import com.stcock.rabbitclient.common.util.jsonresult.JsonResult;

@Controller
public class AppCtrl {

    @RequestMapping("/")
    public String app(HttpServletRequest request) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmSSS");
        request.setAttribute("sysVersion", df.format(new Date()));
        return "views/app";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmSSS");
        request.setAttribute("sysVersion", df.format(new Date()));
        return "views/login";
    }

	@ResponseBody
	@RequestMapping(value="/sys/login")
	public JsonResult login(@RequestBody Map<String, String> paraMap) {
		String userNmae = paraMap.get("username");
		SessionUtil.login(userNmae);
		return JsonResult.success();
	}
	
	@RequestMapping(value="/sys/logout")
	public String login() {
		SessionUtil.logout();
		return "redirect:/login";
	}

	@ResponseBody
	@RequestMapping("/sys/route")
	public List<Map<String, String>> route(HttpServletRequest request) throws Exception {
		List<Map<String, String>> routeList = Lists.newArrayList();
		Map<String, String> route = null;
		return routeList;
	}

	@ResponseBody
	@RequestMapping("/sys/menus")
	public List<Map<String, Object>> ngMenus() throws Exception {
		List<Map<String, Object>> menuList = Lists.newArrayList();
		Map<String, Object> menu = null;
		Map<String, String> subMenu = null;
		List<Map<String, String>> subMenuList = null;
		
		return menuList;
	}
}
