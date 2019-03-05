package org.brainacad.model;

import javax.persistence.*;
import java.util.Date;

@Entity // аннотация помечающая класс как персистентную сущность
@Table(name = "users", schema = "store") // укзание что данный класс соответствует таблице в БД
public class User {

    @Id // указание что это поле являеться ключем
    // Описание последовательности для генерации ID
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "store.users_id_seq")
    @SequenceGenerator(name = "store.users_id_seq", sequenceName = "store.users_id_seq", allocationSize = 1)
    @Column // аннотация помечающая поле как колонку в таблице БД, название должно соответствовать
    private int id;

    @Column(nullable = false) // установка запрета на сохранение null значений
    private String email;

    @Column
    private String password;

    // Можно указать другое название, если явно указать имя колонки в параметре name
    // Или можно назвать переменную, заменив разделители на camelCase ( createAt )
    @Column(name = "created_at")
    private Date creationDate;

    @Column
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
