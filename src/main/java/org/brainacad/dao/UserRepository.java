package org.brainacad.dao;

import org.brainacad.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Репозиторий - компонент для взаимодействий с БД в Spring Data.
 * Каждый репозиторий работает со своим классом-сущностью.
 * NOTE! Используется Maven зависимость spring-boot-starter-data-jpa
 */
@Repository // Помечаем класс как репозиторий
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Переопределение метода из родительского интерфейса,
     * для возможности возвращать List.
     */
    @Override
    List<User> findAll();

    /**
     * Метод для поиска пользователя по email
     * Это так называемый "волшебный метод", т к называется по шаблону.
     * Для него будет автоматически создан запрос: select u from User u where email = ?
     */
    User findByEmail(String email);

    /**
     * Метод для поиска пользователей созданных за период времени.
     * Для него описан HQL запрос в @Query
     */
    @Query("select u from User u where u.creationDate between :startDate and :endDate")
    Stream<User> findUsersByPeriod(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}