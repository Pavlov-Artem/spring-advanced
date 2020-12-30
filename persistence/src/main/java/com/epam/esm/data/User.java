package com.epam.esm.data;

import com.epam.esm.DAOConstants;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = DAOConstants.USER_TABLE)
public class User extends RepresentationModel<User> implements Serializable {

    private static final Long serialVersionUID = -1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DAOConstants.USER_ID)
    protected Long id;
    @Column(name = DAOConstants.USER_NAME, nullable = false)
    protected String name;
    @Column(name = DAOConstants.USER_EMAIL, nullable = false, unique = true)
    private String email;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserOrder> userOrders = new HashSet<>();

    public User(String name, String email, Set<UserOrder> userOrders) {
        this.name = name;
        this.email = email;
        this.userOrders = userOrders;
    }

    public User(Link initialLink, String name, String email, Set<UserOrder> userOrders) {
        super(initialLink);
        this.name = name;
        this.email = email;
        this.userOrders = userOrders;
    }

    public User(Iterable<Link> initialLinks, String name, String email, Set<UserOrder> userOrders) {
        super(initialLinks);
        this.name = name;
        this.email = email;
        this.userOrders = userOrders;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}