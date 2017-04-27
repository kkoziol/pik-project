package com.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	/* pull request made by a random intern */
	
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String getPostList(ModelMap model, Principal principal) {
		String name = principal.getName();
		model.addAttribute("username", name);
		model.addAttribute("message", "Custom message to user");
		return "main";
	}
	
	 @RequestMapping(value = "/admin", method = RequestMethod.GET)
	    public String adminPage(ModelMap model, Principal principal) {
			String name = principal.getName();
			model.addAttribute("username", name);
	        return "admin";
	    }
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index() {
		return "redirect:/index.jsp";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
		return "redirect:/login?logout";
	}
	
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginError(ModelMap model) {
		model.addAttribute("error","true");
		return "redirect:/login?error";
	}
}
