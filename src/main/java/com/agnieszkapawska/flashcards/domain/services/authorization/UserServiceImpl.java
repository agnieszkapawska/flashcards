package com.agnieszkapawska.flashcards.domain.services.authorization;

import com.agnieszkapawska.flashcards.domain.exceptions.EntityCouldNotBeFoundException;
import com.agnieszkapawska.flashcards.domain.exceptions.EntityNotCreatedException;
import com.agnieszkapawska.flashcards.domain.models.Role;
import com.agnieszkapawska.flashcards.domain.models.User;
import com.agnieszkapawska.flashcards.domain.repositories.RoleRepository;
import com.agnieszkapawska.flashcards.domain.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user, Set<String> rolesNames) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = getRoles(rolesNames);
        user.setRoles(roles);
        try {
            userRepository.save(user);
        } catch (
                DataIntegrityViolationException exception) {
            throw new EntityNotCreatedException("Constraint violation exception. User name and email must be unique");
        } catch (Exception exception) {
            throw new EntityNotCreatedException("something went wrong");
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUserName(username).orElseThrow(() -> new EntityCouldNotBeFoundException(username + " couldn't be found"));
    }

    @Override
    public boolean confirmCredentials(String username, String password) {
        User user =
                userRepository.findByUserName(username).orElseThrow(() -> new EntityCouldNotBeFoundException(username + " couldn't be found"));
        return user.getPassword().equals(password);
    }

    private Set<Role> getRoles(Set<String> rolesNames) {
        Set<Role> roles = new HashSet<>();
        rolesNames.forEach(roleName -> {
            Optional<Role> roleOptional = roleRepository.findByName(roleName);
            if (roleOptional.isPresent()) {
                roles.add(roleOptional.get());
            } else {
                Role role = new Role(roleName);
                roleRepository.save(role);
                roles.add(role);
            }
        });
        return roles;
    }
}
