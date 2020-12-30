package com.epam.esm.data;


import com.epam.esm.DAOConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Access(AccessType.FIELD)
@Table(name = DAOConstants.ORDER_TABLE)
public class UserOrder extends RepresentationModel<UserOrder> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DAOConstants.ORDER_ID)
    protected Long id;
    @Column(name = DAOConstants.ORDER_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm'Z'")
    private Timestamp purchaseDate;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = DAOConstants.ORDER_USER_ID)
    private User user;
    @Column(name = DAOConstants.ORDER_DETAILS_COST)
    private BigDecimal cost;
    @OneToOne(targetEntity = GiftCertificate.class, fetch = FetchType.EAGER)
    @JoinColumn(name = DAOConstants.ORDER_DETAILS_CERTIFICATE_ID, nullable = false, updatable = false)
    private GiftCertificate giftCertificate;

    public UserOrder() {
    }

    public UserOrder(Timestamp purchaseDate, User user, BigDecimal cost, GiftCertificate giftCertificate) {
        this.purchaseDate = purchaseDate;
        this.user = user;
        this.cost = cost;
        this.giftCertificate = giftCertificate;
    }

    public UserOrder(Link initialLink, Timestamp purchaseDate, User user, BigDecimal cost, GiftCertificate giftCertificate) {
        super(initialLink);
        this.purchaseDate = purchaseDate;
        this.user = user;
        this.cost = cost;
        this.giftCertificate = giftCertificate;
    }

    public UserOrder(Iterable<Link> initialLinks, Timestamp purchaseDate, User user, BigDecimal cost, GiftCertificate giftCertificate) {
        super(initialLinks);
        this.purchaseDate = purchaseDate;
        this.user = user;
        this.cost = cost;
        this.giftCertificate = giftCertificate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Timestamp purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserOrder)) return false;
        UserOrder userOrder = (UserOrder) o;
        return Objects.equals(id, userOrder.id) &&
                Objects.equals(purchaseDate, userOrder.purchaseDate) &&
                Objects.equals(user, userOrder.user) &&
                Objects.equals(cost, userOrder.cost) &&
                Objects.equals(giftCertificate, userOrder.giftCertificate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, purchaseDate, user, cost, giftCertificate);
    }

    @Override
    public String toString() {
        return "UserOrder{" +
                "id=" + id +
                ", purchaseDate=" + purchaseDate +
                ", user=" + user +
                ", cost=" + cost +
                ", giftCertificate=" + giftCertificate +
                '}';
    }
}
