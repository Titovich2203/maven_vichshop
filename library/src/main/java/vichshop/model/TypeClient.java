package vichshop.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TypeClient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(length = 50, nullable = false)
    private String libelle;

    @OneToMany(mappedBy="typeClient")
    private List<Client> clients = new ArrayList<>();

    public TypeClient() {
    }

    public TypeClient(String libelle, List<Client> clients) {
        this.libelle = libelle;
        this.clients = clients;
    }

    public void setState(TypeClient t) {
        this.libelle = t.libelle;
        this.clients = t.clients;
    }

    public long getId() {
        return id;
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

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        return libelle;
    }
}
