package vn.taidung.springsocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.taidung.springsocial.model.Follower;
import vn.taidung.springsocial.model.FollowerId;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, FollowerId> {
    boolean existsById(FollowerId id);
}
