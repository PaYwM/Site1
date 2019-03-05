package org.brainacad.controller;

import org.brainacad.controller.form.UserForm;
import org.brainacad.controller.validation.UserValidator;
import org.brainacad.model.User;
import org.brainacad.service.UserService;
import org.brainacad.service.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller // Помечаем класс как контроллер обрабатывающий роутинг для стр. авторизации
public class AuthController {

	// Иньектируем сервис для работы с пользователями
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private UserValidator userValidator;

	@GetMapping({"/registration"})
	public String registration(Model model) {
		model.addAttribute("userForm", new UserForm());

		return "registration";
	}

	@PostMapping(value = "/registration")
	public String registration(@ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult, Model model) {
		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "registration";
		}
		User newUser = new User();
		newUser.setEmail(userForm.getEmail());
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		newUser.setPassword(encoder.encode(userForm.getPassword()));
		userService.save(newUser);

		securityService.autoLogin(userForm.getEmail(), userForm.getPassword());
		return "redirect:/index";
	}

	@GetMapping(value = "/login")
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}

}

















