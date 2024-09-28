package SpringSecurity.JWT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SpringSecurity.JWT.Service.JwtService;
import SpringSecurity.JWT.Service.MyUserDetailService;

@RestController
@RequestMapping
public class ContentController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailService myUserDetailService;
	
	@Autowired
	private JwtService jwtService;
	
	@GetMapping("/home")
	public String home() {
		return "hello ur in home page";
	}
	
	@GetMapping("/admin/home")
	public String adminhome() {
		return "hello ur in admin home page";
	}
	
	@GetMapping("/user/home")
	public String userhome() {
		return "hello ur in user home page";
	}
	
	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody LoginForm loginForm) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUserName(), loginForm.getPassword()));
	
		System.out.println(authentication.isAuthenticated());
		if(authentication.isAuthenticated()) {
			System.out.println("ready for token genrateion");
			System.out.println("toekn - " + jwtService.generateToken(myUserDetailService.loadUserByUsername(loginForm.getUserName())));
			return jwtService.generateToken(myUserDetailService.loadUserByUsername(loginForm.getUserName()));
		}else {
			throw new UsernameNotFoundException("Invalid Creditails");
		}
	}
}
