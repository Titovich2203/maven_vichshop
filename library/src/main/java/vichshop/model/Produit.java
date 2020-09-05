package vichshop.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Produit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String libelle;

    @Column(nullable = false)
    private long prixU;

    @Column(nullable = false)
    private long prixMin;

    @Column(nullable = false)
    private int qteStock;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="produit")
    private List<Entree> entrees = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy="produit")
    private List<Sortie> sorties = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="produit")
    private List<DetailCommande> details = new ArrayList<>();


    public Produit() {
    }

    public Produit(String libelle, long prixU, long prixMin, int qteStock, List<Entree> entrees, List<Sortie> sorties, User user, List<DetailCommande> details) {
        this.libelle = libelle;
        this.prixU = prixU;
        this.prixMin = prixMin;
        this.qteStock = qteStock;
        this.entrees = entrees;
        this.sorties = sorties;
        this.user = user;
        this.details = details;
    }

    public void setState(Produit p) {
        this.libelle = p.libelle;
        this.prixU = p.prixU;
        this.prixMin = p.prixMin;
        this.qteStock = p.qteStock;
        this.entrees = p.entrees;
        this.sorties = p.sorties;
        this.user = p.user;
        this.details = p.details;
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

    public long getPrixU() {
        return prixU;
    }

    public void setPrixU(long prixU) {
        this.prixU = prixU;
    }

    public long getPrixMin() {
        return prixMin;
    }

    public void setPrixMin(long prixMin) {
        this.prixMin = prixMin;
    }

    public int getQteStock() {
        return qteStock;
    }

    public void setQteStock(int qteStock) {
        this.qteStock = qteStock;
    }

    public List<Entree> getEntrees() {
        return entrees;
    }

    public void setEntrees(List<Entree> entrees) {
        this.entrees = entrees;
    }

    public List<Sortie> getSorties() {
        return sorties;
    }

    public void setSorties(List<Sortie> sorties) {
        this.sorties = sorties;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<DetailCommande> getDetails() {
        return details;
    }

    public void setDetails(List<DetailCommande> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return libelle;
    }
}
