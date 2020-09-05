package vichshop.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TypeFacture implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(length = 50, nullable = false)
    private String libelle;

    @OneToMany(mappedBy="typeFacture")
    private List<Facture> factures = new ArrayList<>();

    public TypeFacture() {
    }

    public TypeFacture(String libelle, List<Facture> factures) {
        this.libelle = libelle;
        this.factures = factures;
    }
    public void setState(TypeFacture t) {
        this.libelle = t.libelle;
        this.factures = t.factures;
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

    public List<Facture> getFactures() {
        return factures;
    }

    public void setFactures(List<Facture> factures) {
        this.factures = factures;
    }
}
