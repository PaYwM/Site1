package org.brainacad.service;


import org.brainacad.model.User;

import java.util.Date;
import java.util.List;

/**
 * Интерфейс для взаимодействия с репозиторием.
 */
public interface UserService {

	List<User> getList();

	User findByEmail(String email);

	List<User> findByPeriod(Date startDate, Date endDate);

	User save(User user);
}
