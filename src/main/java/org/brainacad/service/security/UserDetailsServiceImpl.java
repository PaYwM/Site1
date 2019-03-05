package org.brainacad.service.security;

import org.brainacad.dao.UserRepository;
import org.brainacad.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    // Метод вызовится при сабмите логин формы
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException(username); // бросаем ошибку, если юзер не найден
        }
        // тут будут реальные роли пользователей (ADMIN, USER), ока без них
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        // Возвращаем объект внутреннего spring user
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), grantedAuthorities);
    }
}
