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
    @Column
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserOrder> userOrders = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            //cross table name
            name = DAOConstants.USER_HAS_ROLE_TABLE,
            //User foreign key
            joinColumns = @JoinColumn(name = DAOConstants.USER_ID_REF, referencedColumnName = "id"),
            //User_Role foreign key
            inverseJoinColumns = @JoinColumn(name = DAOConstants.USER_ROLE_ID_REF, referencedColumnName = "id"))
    private Set<Role> roles;

    public User(Long id, String name, String email, String password, Set<UserOrder> userOrders, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userOrders = userOrders;
        this.roles = roles;
    }

    public User(Link initialLink, Long id, String name, String email, String password, Set<UserOrder> userOrders, Set<Role> roles) {
        super(initialLink);
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userOrders = userOrders;
        this.roles = roles;
    }

    public User(Iterable<Link> initialLinks, Long id, String name, String email, String password, Set<UserOrder> userOrders, Set<Role> roles) {
        super(initialLinks);
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userOrders = userOrders;
        this.roles = roles;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserOrder> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(Set<UserOrder> userOrders) {
        this.userOrders = userOrders;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(userOrders, user.userOrders) &&
                Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, email, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + "nope" +
                ", userOrders=" + userOrders +
                ", roles=" + roles +
                '}';
    }
}