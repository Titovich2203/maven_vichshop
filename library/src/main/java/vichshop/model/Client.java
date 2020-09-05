package vichshop.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(length = 50, nullable = false)
    private String nom;

    @Column(length = 50, nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="typeclient_id", nullable=false)
    private TypeClient typeClient;


    @OneToMany(fetch = FetchType.EAGER, mappedBy="client")
    private List<BonCommande> bons = new ArrayList<>();

    public Client() {
    }

    public Client(String nom, String email, TypeClient typeClient, List<BonCommande> bons) {
        this.email = email;
        this.nom = nom;
        this.typeClient = typeClient;
        this.bons = bons;
    }

    public void setState(Client e) {
        this.nom = e.nom;
        this.email = e.email;
        this.typeClient = e.typeClient;
        this.bons = e.bons;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TypeClient getTypeClient() {
        return typeClient;
    }

    public void setTypeClient(TypeClient typeClient) {
        this.typeClient = typeClient;
    }

    public List<BonCommande> getBons() {
        return bons;
    }

    public void setBons(List<BonCommande> bons) {
        this.bons = bons;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return nom;
    }
}
