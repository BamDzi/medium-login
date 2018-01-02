package pl.testowy.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pl.testowy.model.User;
import pl.testowy.repository.UserRepository;
import pl.testowy.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public String login() {
		return "login";
	}
	
	@GetMapping("registration")
	public String registration(@ModelAttribute User user) {
		return "registration";
	}
	
	@PostMapping("registration")
	public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) {

		User userExists = userService.findUserByEmail(user.getEmail());
		
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		
			userService.saveUser(user);
			model.addAttribute("successMessage", "User has been registered successfully");
			model.addAttribute("user", new User());

		
		return "login";
	}

	@GetMapping("/admin/home")
	public String home() {
		/*
		 * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 * User user = userService.findUserByEmail(auth.getName());
		 * modelAndView.addObject("userName", "Welcome " + user.getName() + " " +
		 * user.getLastName() + " (" + user.getEmail() + ")"); modelAndView.addObject(
		 * "adminMessage","Content Available Only for Users with Admin Role");
		 */
		return "admin/home";
	}

	@GetMapping("update")
	public String updatePersonForm(@ModelAttribute User user, @RequestParam String email) {

		user = userService.findUserByEmail(email);
		user.setActive(true);
		userRepository.save(user);
		
		return "update";
	}
	
	@GetMapping("hello")
	public String helloPage() {
		return "hello";
	}
}
