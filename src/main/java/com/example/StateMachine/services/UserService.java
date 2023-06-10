package com.example.StateMachine.services;

import com.example.StateMachine.entities.User;
import com.example.StateMachine.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.security.cert.Extension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	private List<GrantedAuthority> getAuthorities(Set<Role> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
			for (Object permission : role.getRoleValue()) {
				authorities.add(new SimpleGrantedAuthority(permission.toString()));
			}
		}
		return authorities;
	}
	
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found!");
		} else {
			return new org.springframework.security.core.userdetails.User(
					user.getUsername(),
					user.getPassword(),
					user.isEnabled(),
					true,
					true,
					true,
					getAuthorities(user.getRoles())
			);
		}
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public User save(User user) {
		user.setPassword(passwordEncoder().encode(user.getPassword()));
		user.setEnabled(true);
		return userRepository.save(user);
	}
	
	public User findById(Long id) {
		Optional<User> userToFind = userRepository.findById(id);
		if (userToFind.isPresent()) {
			return userToFind.get();
		}
		throw new EntityNotFoundException("Not found");
	}
	
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
