package SpringSecurity.JWT.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import SpringSecurity.JWT.model.MyUser;

public interface MyUserRepository extends JpaRepository<MyUser, Long>{

	Optional<MyUser> findByUserName(String userName);
}
