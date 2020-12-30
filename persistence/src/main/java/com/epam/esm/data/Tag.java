package com.epam.esm.data;

import com.epam.esm.DAOConstants;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = DAOConstants.TAG_TABLE)
public class Tag extends RepresentationModel<Tag> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DAOConstants.TAG_ID)
    private Long id;
    @Column(name = DAOConstants.TAG_NAME, nullable = false)
    private String name;
    @ManyToMany(mappedBy = "certificateTags")
    private Set<GiftCertificate> giftCertificates = new HashSet<>();

    public Tag() {
    }

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag(String name, Set<GiftCertificate> giftCertificates) {
        this.name = name;
        this.giftCertificates = giftCertificates;
    }

    public Tag(Link initialLink, String name, Set<GiftCertificate> giftCertificates) {
        super(initialLink);
        this.name = name;
        this.giftCertificates = giftCertificates;
    }

    public Tag(Iterable<Link> initialLinks, String name, Set<GiftCertificate> giftCertificates) {
        super(initialLinks);
        this.name = name;
        this.giftCertificates = giftCertificates;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) &&
                Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}