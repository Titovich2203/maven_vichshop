package vichshop.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class EtatBon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(length = 50, nullable = false)
    private String libelle;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="etatBon")
    private List<BonCommande> bons = new ArrayList<>();

    public EtatBon() {
    }

    public EtatBon(String libelle, List<BonCommande> bons) {
        this.libelle = libelle;
        this.bons = bons;
    }

    public void setState(EtatBon e) {
        this.libelle = e.libelle;
        this.bons = e.bons;
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

    public List<BonCommande> getBons() {
        return bons;
    }

    public void setBons(List<BonCommande> bons) {
        this.bons = bons;
    }
}
