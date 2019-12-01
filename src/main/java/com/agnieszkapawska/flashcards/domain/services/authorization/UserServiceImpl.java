package com.agnieszkapawska.flashcards.domain.services.authorization;

import com.agnieszkapawska.flashcards.domain.exceptions.EntityCouldNotBeFoundException;
import com.agnieszkapawska.flashcards.domain.exceptions.EntityNotCreatedException;
import com.agnieszkapawska.flashcards.domain.models.FlashcardsToLearn;
import com.agnieszkapawska.flashcards.domain.models.Role;
import com.agnieszkapawska.flashcards.domain.models.User;
import com.agnieszkapawska.flashcards.domain.repositories.RoleRepository;
import com.agnieszkapawska.flashcards.domain.repositories.UserRepository;
import com.agnieszkapawska.flashcards.domain.services.FlashcardsToLearnService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private FlashcardsToLearnService flashcardsToLearnService;

    @Override
    public void save(User user, Set<String> rolesNames) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = getRoles(rolesNames);
        user.setRoles(roles);
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new EntityNotCreatedException("Constraint violation exception. User name and email must be unique");
        } catch (Exception exception) {
            throw new EntityNotCreatedException("something went wrong");
        }
        FlashcardsToLearn flashcardsToLearn = new FlashcardsToLearn(user);
        flashcardsToLearnService.save(flashcardsToLearn);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityCouldNotBeFoundException(username + " couldn't be found"));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityCouldNotBeFoundException("User wit id: " + id + "couldn't be found"));
    }

    @Override
    public Optional<User> confirmCredentials(String username, String password) {
        User user =
                userRepository.findByUserName(username).orElseThrow(() -> new EntityCouldNotBeFoundException(username + " couldn't be found"));

        return bCryptPasswordEncoder.matches(password, user.getPassword())? Optional.of(user):Optional.empty();
    }

    private Set<Role> getRoles(Set<String> rolesNames) {
        Set<Role> roles = new HashSet<>();
        rolesNames.forEach(roleName -> {
            Role role = roleRepository.findByName(roleName)
                    .orElseGet(() -> {
                        Role role1 = new Role(roleName);
                        roleRepository.save(role1);
                        return role1;
                    });
            roles.add(role);
        });
        return roles;
    }
}
