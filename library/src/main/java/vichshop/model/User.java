package vichshop.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 70, nullable = false)
    private String nomComplet;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 200, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String tel;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="profil_id", nullable=false)
    private Profil profil;

    @OneToMany(mappedBy="user")
    private List<BonCommande> bonCommandes = new ArrayList<>();

    @OneToMany(mappedBy="user")
    private List<Produit> produits = new ArrayList<>();

    @OneToMany(mappedBy="user")
    private List<Entree> entrees = new ArrayList<>();

    @OneToMany(mappedBy="user")
    private List<Sortie> sorties = new ArrayList<>();

    public User() {
    }

    public User(String nomComplet, String email, String password, String tel, Profil profil, List<BonCommande> bonCommandes, List<Produit> produits, List<Entree> entrees, List<Sortie> sorties) {
        this.nomComplet = nomComplet;
        this.email = email;
        this.password = password;
        this.tel = tel;
        this.profil = profil;
        this.bonCommandes = bonCommandes;
        this.produits = produits;
        this.entrees = entrees;
        this.sorties = sorties;
    }

    public void setState(User u)
    {
        this.nomComplet = u.nomComplet;
        this.email = u.email;
        this.password = u.password;
        this.tel = u.tel;
        this.profil = u.profil;
        this.bonCommandes = u.bonCommandes;
        this.produits = u.produits;
        this.entrees = u.entrees;
        this.sorties = u.sorties;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public List<BonCommande> getBonCommandes() {
        return bonCommandes;
    }

    public void setBonCommandes(List<BonCommande> bonCommandes) {
        this.bonCommandes = bonCommandes;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
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

    @Override
    public String toString() {
        return nomComplet;
    }
}
