package com.smyx.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.smyx.util.AuthUtils;
import com.smyx.util.WXConfig;

@Controller
public class WxController {
	
	/**
	 * 微信公众平台
	 * 文档地址:https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html
	 * 第一步：用户同意授权，获取code
	 * @return
	 */
	@RequestMapping("/wxPubliclogin")
	public Object wxPubliclogin() {
		System.out.println("wxPubliclogin...........");
		// 用户授权后微信回调地址
		String backUrl = "/pubCallback";
		@SuppressWarnings("deprecation")
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WXConfig.getValue("public_appid") + "&redirect_uri="
				+ URLEncoder.encode(backUrl) + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		System.out.println("wxPubliclogin.redirect()>>>" + url);
		return "redirect:" + url;
	}
	
	/**
	 * 微信公众平台
	 * 第二步：通过code换取网页授权access_token 
	 * 回调地址-得到code，从而去获得access_token 和 openid
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pubCallback")
	public ModelAndView pubCallback(HttpServletRequest req) throws Exception {
		String code = req.getParameter("code");
		System.out.println("wxPubliclogin>>>pubCallback>>>code=" + code);
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WXConfig.getValue("public_appid") + "&secret="
				+ WXConfig.getValue("public_appsecret") + "&code=" + code + "&grant_type=authorization_code";
		JSONObject jsonObject = AuthUtils.doGetJson(url);
		String openid = jsonObject.getString("openid");
		String access_token = jsonObject.getString("access_token");
		// TODO: 第三步：刷新access_token（如果需要）
		// 第四步：拉取用户信息(需scope为 snsapi_userinfo)
		String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
		JSONObject userInfo = AuthUtils.doGetJson(infoUrl);
		System.out.println("用户信息：" + userInfo);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("success");
		mav.addObject("info", userInfo);
		return mav;
		// 微信开放平台和微信公众号（微信公众平台）返回的openid不一致 需要在微信开发平台绑定微信公众然后获取uuionid 20200507
//        String unionid = userInfo.getString("unionid");

		// 1.使用微信用户信息直接登录，无须注册和绑定，直接跳转到登录成功界面
//        ModelAndView mv = new ModelAndView("success");
//        mv.addObject("info",userInfo);
//        return mv;

		// 2.将微信与当前系统的账号进行绑定，绑定后跳转到登录成功界面
//        User user = userRepository.findByunionid(unionid);
//        if(null != user && (!Objects.equals("", user.getNickname()))){
//            // 已绑定，直接跳转绑定成功的页面
//            mv.setViewName("bindsuccess");
//            mv.addObject("nickname", user.getNickname());
//            return mv;
//        }else{
//            // 未绑定，跳转到自己系统的登录页面
//            mv.setViewName("login");
//            mv.addObject("unionid", unionid);
//            return mv;
//        }
	}
	
	/**
	 * 微信开放平台
	 * 文档地址:https://developers.weixin.qq.com/doc/oplatform/Website_App/WeChat_Login/Wechat_Login.html
	 * 第一步：用户同意授权，获取code
	 * @param req
	 * @return
	 */
	@RequestMapping("/wxOpenlogin")
	public Object wxOpenlogin() {
		System.out.println("wxOpenlogin...........");
		// 用户授权后微信回调地址
		String backUrl = "/openCallback";
		@SuppressWarnings("deprecation")
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WXConfig.getValue("open_appid") + "&redirect_uri="
				+ URLEncoder.encode(backUrl) + "&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";
		System.out.println("wxOpenlogin.redirect()>>>" + url);
		return "redirect:" + url;
	}
	
	/**
	 * 微信公众平台
	 * 第二步：通过code换取网页授权access_token 
	 * 回调地址-得到code，从而去获得access_token 和 openid
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/openCallback")
	public ModelAndView openCallback(HttpServletRequest req) throws Exception {
		String code = req.getParameter("code");
		System.out.println("wxOpenlogin>>>openCallback>>>code=" + code);
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WXConfig.getValue("open_appid") + "&secret="
				+ WXConfig.getValue("open_appsecret") + "&code=" + code + "&grant_type=authorization_code";
		JSONObject jsonObject = AuthUtils.doGetJson(url);
		String openid = jsonObject.getString("openid");
		String access_token = jsonObject.getString("access_token");
		// TODO: 第三步：刷新access_token（如果需要）
		// 第四步：拉取用户信息(需scope为 snsapi_userinfo)
		String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
		JSONObject userInfo = AuthUtils.doGetJson(infoUrl);
		System.out.println("用户信息：" + userInfo);
		ModelAndView mv = new ModelAndView("success");
		mv.addObject("info", userInfo);
		return mv;
		// 微信开放平台和微信公众号（微信公众平台）返回的openid不一致 需要在微信开发平台绑定微信公众然后获取uuionid 20200507
//        String unionid = userInfo.getString("unionid");

		// 1.使用微信用户信息直接登录，无须注册和绑定，直接跳转到登录成功界面
//        ModelAndView mv = new ModelAndView("success");
//        mv.addObject("info",userInfo);
//        return mv;

		// 2.将微信与当前系统的账号进行绑定，绑定后跳转到登录成功界面
//        User user = userRepository.findByunionid(unionid);
//        if(null != user && (!Objects.equals("", user.getNickname()))){
//            // 已绑定，直接跳转绑定成功的页面
//            mv.setViewName("bindsuccess");
//            mv.addObject("nickname", user.getNickname());
//            return mv;
//        }else{
//            // 未绑定，跳转到自己系统的登录页面
//            mv.setViewName("login");
//            mv.addObject("unionid", unionid);
//            return mv;
//        }
	}

}
