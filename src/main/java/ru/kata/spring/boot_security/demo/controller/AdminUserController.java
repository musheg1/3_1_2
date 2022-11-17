package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.security.AccountDetails;

import java.util.Arrays;
import java.util.List;

@Controller
public class AdminUserController {

	private UserService userService;

	public AdminUserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public String mainpage() {
		if(userService.getAllUsers().isEmpty())
			userService.create(new User("admin","admin", Arrays.asList("ROLE_ADMIN")));
		return "mainpage";
	}

	@GetMapping("/user")
	public String getUser(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();
		model.addAttribute("user", accountDetails.getUser());
		return "user";
	}

	@GetMapping("/admin")
	public String allUsers(Model model) {
		model.addAttribute("allUsers", userService.getAllUsers());
		return "admin";
	}

	@GetMapping("/admins")
	public List<User> allUserss() {

		return userService.getAllUsers();
	}

	@GetMapping("/admin/register")
	public String register(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "register";
	}

	@PostMapping("/admin/register")
	public String createUser(@ModelAttribute("user") User user){
		userService.create(user);
		return "redirect:/admin";
	}

	@GetMapping("/admin/{id}/edit")
	public String getEdit(Model model, @PathVariable("id") Long id) {
		model.addAttribute("update", userService.getUser(id));
		return "edit";
	}

	@PatchMapping("/admin/{id}")
	public String postEdit(@ModelAttribute("update") User user,@PathVariable("id") Long id) {
		userService.update(id, user);
		return "redirect:/admin";
	}

	@DeleteMapping("/admin/{id}")
	public String deleteUserById(@PathVariable Long id) {
		userService.delete(id);
		return "redirect:/admin";
	}

	@RequestMapping("/forbidden")
	public String accessDenied() {
		return "forbidden";
	}
}