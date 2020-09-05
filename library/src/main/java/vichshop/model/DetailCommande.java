package vichshop.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class DetailCommande implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int qte;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="produit_id", nullable=false)
    private Produit produit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="bon_id", nullable=false)
    private BonCommande bon;

    public DetailCommande() {
    }

    public DetailCommande(int qte, Produit produit, BonCommande bon) {
        this.qte = qte;
        this.produit = produit;
        this.bon = bon;
    }

    public void setState(DetailCommande d) {
        this.qte = d.qte;
        this.produit = d.produit;
        this.bon = d.bon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public BonCommande getBon() {
        return bon;
    }

    public void setBon(BonCommande bon) {
        this.bon = bon;
    }
}
