package SpringSecurity.JWT.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import SpringSecurity.JWT.Repository.MyUserRepository;
import SpringSecurity.JWT.model.MyUser;

@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private MyUserRepository myUserRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<MyUser> user = myUserRepo.findByUserName(username);

		if (user.isPresent()) {
			MyUser currentUser = user.get();

			return User.builder().username(currentUser.getUserName()).password(currentUser.getPassword())
					.roles(getRoles(currentUser)).build();
		} else {
			throw new UsernameNotFoundException(username);
		}
	}

	private String[] getRoles(MyUser currentUser) {
		if (currentUser == null) {
			return new String[] { "USER" };
		}
		return currentUser.getRole().split(",");
	}
}
