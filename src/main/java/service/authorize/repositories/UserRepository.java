package service.authorize.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import service.authorize.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
