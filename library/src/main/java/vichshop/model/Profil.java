package vichshop.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Profil implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String libelle;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="profil")
    private List<User> users;

    public Profil() {
    }


    public long getId() {
        return id;
    }

    public Profil(String libelle, List<User> users) {
        this.libelle = libelle;
        this.users = users;
    }

    public void setState(Profil p) {
        this.libelle = p.libelle;
        this.users = p.users;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return libelle;
    }
}
