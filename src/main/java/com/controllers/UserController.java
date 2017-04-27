package com.controllers;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.model.User;
import com.model.UserDTO;
import com.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private static final  Logger logger = Logger.getLogger(SearchPanelController.class);

	private static final String REGISTER_JSP = "register";
	private UserService userService;

	@Autowired(required = true)
	public void setUserService(UserService userService) {
		this.userService = userService; 
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddForm(Model model) {
		model.addAttribute("user", new UserDTO());
		return REGISTER_JSP;
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "redirect:/index.jsp";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addUserInfo(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult result) {
		List<User> users = userService.getUserList();
		if (users.stream().map(User::getLogin).collect(Collectors.toList()).contains(userDTO.getLogin())) {
			result.rejectValue("login", "error.user", "An account already exists for this login.");
			return REGISTER_JSP;
		}
		if (users.stream().map(User::getEmail).collect(Collectors.toList()).contains(userDTO.getEmail())) {
			result.rejectValue("email", "error.user", "An account already exists for this email.");
			return REGISTER_JSP;
		}
		if (result.hasErrors()) {
			return REGISTER_JSP;
		} else {
			User user = new User();
			user.setName(userDTO.getName());
			user.setSurname(userDTO.getSurname());
			user.setEmail(userDTO.getEmail());
			user.setLogin(userDTO.getLogin());
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			user.setSex(userDTO.getSex());

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date date = null;
			try {
				date = format.parse(userDTO.getDateOfBirth());
				user.setDateOfBirth(new java.sql.Date(date.getTime()));
				userService.saveOrUpdate(user);
			} catch (ParseException e) {
				logger.error("Sorry, something wrong!", e);
				return REGISTER_JSP;
			}
			

			return "redirect:/welcome";
		}

	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true, 10));
	}

}
