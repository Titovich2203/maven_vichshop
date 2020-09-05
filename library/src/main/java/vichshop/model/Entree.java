package vichshop.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Entree implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int qte;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="produit_id", nullable=false)
    private Produit produit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public Entree() {
    }

    public Entree(int qte, Date date, Produit produit, User user) {
        this.qte = qte;
        this.date = date;
        this.produit = produit;
        this.user = user;
    }

    public void setState(Entree e) {
        this.qte = e.qte;
        this.date = e.date;
        this.produit = e.produit;
        this.user = e.user;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
