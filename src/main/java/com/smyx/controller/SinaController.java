package com.smyx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.model.User;

@Controller
public class SinaController {

	/**
	 * 点击新浪登录按钮触发
	 * @param request
	 * @param response0
	 */
	@RequestMapping("/sinaLogin")
	public void sinaLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("click sinalogin button!!!!");
			response.sendRedirect(new Oauth().authorize("code", ""));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 回调方法
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sinaRedirect")
	public String sinaLoginRedirect(HttpServletRequest request, HttpServletResponse response) {
		String code = request.getParameter("code");
		System.out.println("click sinalogin button callback!!!!");
		System.out.println("code=" + code);
		try {
			Oauth oauth = new Oauth();
			String token = oauth.getAccessTokenByCode(code).toString();
			String str[] = token.split(",");
			String accessToken = str[0].split("=")[1];
			String str1[] = str[3].split("]");
			String uid = str1[0].split("=")[1];
			Users um = new Users(accessToken);
			User user = um.showUserById(uid);
			System.out.println(user.toString());
			return "/success";
		} catch (Exception e) {
			e.printStackTrace();
			return "/error";
		}
	}

}
