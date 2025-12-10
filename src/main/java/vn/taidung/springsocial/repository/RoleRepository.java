package vn.taidung.springsocial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.taidung.springsocial.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
