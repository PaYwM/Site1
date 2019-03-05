package org.brainacad.controller;

import org.brainacad.model.User;
import org.brainacad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController // Помечаем класс как контроллер обрабатывающий REST запросы
@RequestMapping("api")
public class UserController {

	// Иньектируем сервис для работы с пользователями
	@Autowired
	private UserService userService;

	// Обработчик для запроса GET http://{host:port}/list
	@GetMapping(value = "/list")
	public ResponseEntity<List<User>> userDetails() {
		List<User> userDetails = userService.getList();
		return new ResponseEntity<List<User>>(userDetails, HttpStatus.OK);
	}

	// Обработчик для запроса на сохранение пользователя POST http://{host:port}/user
	@PostMapping(value = "/user")
	public ResponseEntity<?> saveUser( @RequestBody User data) {

		if(data.getCreationDate() == null) {
			data.setCreationDate(new Date());
		}

		userService.save(data);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}