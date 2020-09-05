package vichshop.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class EtatPaiement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(nullable = false)
    private double pourcentage;

    @OneToMany(mappedBy="etatPaiement")
    private List<BonCommande> bons = new ArrayList<>();

    public EtatPaiement() {
    }

    public EtatPaiement(double pourcentage, List<BonCommande> bons) {
        this.pourcentage = pourcentage;
        this.bons = bons;
    }

    public void setState(EtatPaiement e) {
        this.pourcentage = e.pourcentage;
        this.bons = e.bons;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }

    public List<BonCommande> getBons() {
        return bons;
    }

    public void setBons(List<BonCommande> bons) {
        this.bons = bons;
    }
}
