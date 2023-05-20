package ru.filenko.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User extends PanacheEntity {

    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    public String role;
    public User () {}

    //
//    public static void add(String username, String password, String role) {
//        User user = new User();
//        user.login = username;
//        user.password = BcryptUtil.bcryptHash(password);
//        user.role = role;
//        user.persist();
//    }

    public static Uni<User> getUserByLogin(String login) {
        return User.find("login", login).singleResult();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + login.hashCode();
        return result;
    }
}
