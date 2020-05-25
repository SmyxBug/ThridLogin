package com.smyx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.smyx.util.AuthUtils;
import com.smyx.util.InstagramConfig;

@Controller
public class InstagramController {

	/**
	 * 点击instagram登录按钮触发
	 * @param request
	 * @param response0
	 */
	@RequestMapping("/instagramLogin")
	public void instagramLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("click instagramLogin button!!!!");
			String url = "https://api.instagram.com/oauth/authorize?client_id=" + InstagramConfig.getValue("client_ID")
					+ "&redirect_uri=" + InstagramConfig.getValue("redirect_URI")
					+ "&scope=user_profile,user_media&response_type=code";
			response.sendRedirect(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 回调方法
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/instagramRedirect")
	public String instagramRedirect(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");
		System.out.println("click instagramLogin button callback!!!!");
		System.out.println("code=" + code);
		// 获取 access_token 和 用户编号 eg: { "access_token": "IGQVJ...", "user_id": 17841405793187218 }
		String getAccessTokenUrl = "https://api.instagram.com/oauth/access_token?client_id=" + InstagramConfig.getValue("client_ID")
				+ "&client_secret=" + InstagramConfig.getValue("client_SERCRET") + "grant_type=authorization_code" 
				+ "&redirect_uri=" + InstagramConfig.getValue("redirect_URI") + "&code=" + code;
		JSONObject jsonObject = AuthUtils.doGetJson(getAccessTokenUrl);
		String accessToken = jsonObject.getString("access_token");
		System.out.println("accessToken=" + accessToken);
		// 获取用户编号和用户名  eg: { "id": "17841405793187218", "username": "jayposiris" }
		String getUserUrl = "https://graph.instagram.com/me?fields=id,username&access_token=" + accessToken;
		JSONObject userInfo = AuthUtils.doGetJson(getUserUrl);
		System.out.println(userInfo);
		return "/success";
	}
	
}
