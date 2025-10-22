package vn.taidung.springsocial.model;

// import java.time.Instant;
// import java.util.ArrayList;
// import java.util.List;

// import jakarta.persistence.CascadeType;
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.PrePersist;
// import jakarta.persistence.Table;

// @Entity
// @Table(name = "users")
public class User {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;
    // private String username;
    // private String email;
    // private String password;

    // @Column(name = "created_at")
    // private Instant createdAt;

    // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch =
    // FetchType.LAZY)
    // private List<Post> posts = new ArrayList<>();

    // public Long getId() {
    // return id;
    // }

    // public void setId(Long id) {
    // this.id = id;
    // }

    // public String getUsername() {
    // return username;
    // }

    // public void setUsername(String username) {
    // this.username = username;
    // }

    // public String getEmail() {
    // return email;
    // }

    // public void setEmail(String email) {
    // this.email = email;
    // }

    // public String getPassword() {
    // return password;
    // }

    // public void setPassword(String password) {
    // this.password = password;
    // }

    // public Instant getCreatedAt() {
    // return createdAt;
    // }

    // public void setCreatedAt(Instant createdAt) {
    // this.createdAt = createdAt;
    // }

    // public List<Post> getPosts() {
    // return posts;
    // }

    // public void setPosts(List<Post> posts) {
    // this.posts = posts;
    // }

    // @PrePersist
    // public void handleBeforeCreate() {
    // this.createdAt = Instant.now();
    // }
}
