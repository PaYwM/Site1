package org.brainacad.controller;

import java.util.Date;

import org.brainacad.controller.form.UserForm;
import org.brainacad.model.User;
import org.brainacad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;

@Controller // Помечаем класс как контроллер обрабатывающий REST запросы
public class MainController {

	@Autowired
	private UserService userService;

	// Вводится (inject) из application.properties.
	@Value("${welcome.message}")
	private String message;

	@Value("${error.message}")
	private String errorMessage;

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String index(Model model) {

		model.addAttribute("message", message);
		return "index";
	}

	@GetMapping(value = { "/list" })
	public String personList(Model model) {

		model.addAttribute("users", userService.getList());
		return "list";
	}

	@GetMapping(value = { "/add" })
	public String showAddPersonPage(Model model) {

		UserForm userForm = new UserForm();
		model.addAttribute("userForm", userForm);
		return "add";
	}

	@PostMapping(value = { "/add" })
	public String savePerson(Model model, @ModelAttribute("userForm") UserForm userForm) {

		String login = userForm.getEmail();
		String password = userForm.getPassword();

		if (login != null && !login.isEmpty() && password != null && !password.isEmpty()) {
			User newUser = new User();
			newUser.setEmail(login);
			newUser.setPassword(password);
			newUser.setCreationDate(new Date());
			userService.save(newUser);

			return "redirect:/list";
		}
		model.addAttribute("errorMessage", errorMessage);
		return "add";
	}

}

