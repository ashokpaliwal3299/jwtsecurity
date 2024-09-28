package SpringSecurity.JWT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import SpringSecurity.JWT.Repository.MyUserRepository;
import SpringSecurity.JWT.model.MyUser;

@RestController
public class RegistrationController {

	@Autowired
	private MyUserRepository myUserRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/register/user")
	public MyUser createUser(@RequestBody MyUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return myUserRepo.save(user);
	}
}
