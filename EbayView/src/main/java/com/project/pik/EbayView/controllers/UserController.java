package com.project.pik.EbayView.controllers;

//@Controller
//@RequestMapping("/user")
public class UserController {
	
//	private static final  Logger logger = Logger.getLogger(UserController.class);
//
//	private static final String REGISTER_JSP = "register";
//	private UserService userService;
//
//	@Autowired(required = true)
//	public void setUserService(UserService userService) {
//		this.userService = userService; 
//	}
//
//	@RequestMapping(value = "/add", method = RequestMethod.GET)
//	public String getAddForm(Model model) {
//		model.addAttribute("user", new UserDTO());
//		return REGISTER_JSP;
//	}
//
//	@RequestMapping(value = "/add", method = RequestMethod.POST)
//	public String addUserInfo(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult result) {
//		List<User> users = userService.getUserList();
//		if (users.stream().map(User::getLogin).collect(Collectors.toList()).contains(userDTO.getLogin())) {
//			result.rejectValue("login", "error.user", "An account already exists for this login.");
//			return REGISTER_JSP;
//		}
//		if (result.hasErrors()) {
//			return REGISTER_JSP;
//		} else {
//			User user = new User();
//			user.setName(userDTO.getName());
//			user.setSurname(userDTO.getSurname());
//			Email email = new Email();
//			email.setValue(userDTO.getEmail());
//			Set<Email> set = new HashSet<>();
//			set.add(email);
//			user.setEmails(set);
//			user.setLogin(userDTO.getLogin());
//			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//			user.setSex(userDTO.getSex());
//
//			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//			java.util.Date date = null;
//			try {
//				date = format.parse(userDTO.getDateOfBirth());
//				user.setDateOfBirth(new java.sql.Date(date.getTime()));
//				userService.saveOrUpdate(user);
//			} catch (ParseException e) {
//				logger.error("Sorry, something wrong!", e);
//				return REGISTER_JSP;
//			}
//			
//
//			return "redirect:/welcome";
//		}
//
//	}
//
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true, 10));
//	}

}
