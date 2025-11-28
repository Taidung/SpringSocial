package vn.taidung.springsocial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.taidung.springsocial.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdAndIsActiveTrue(Long id);

    Optional<User> findByEmailAndIsActiveTrue(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
