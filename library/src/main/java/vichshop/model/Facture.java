package vichshop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Facture implements Serializable {

    static double tva = 0.18;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(nullable = false)
    private double mntHt;

    @Column(nullable = false)
    private double mntTva;

    @Column(nullable = false)
    private double remise;

    @Column(nullable = false)
    private double mntTtc;

    @Column(length = 50, nullable = false)
    private String etat;

    @Column(nullable = false)
    private double pourcentage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="bon_id", nullable=false)
    private BonCommande bon;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="type_id", nullable=false)
    private TypeFacture typeFacture;

    public Facture() {
    }

    public Facture(Date date, double mntHt, double mntTva, double remise, double mntTtc, String etat, double pourcentage, BonCommande bon, TypeFacture typeFacture) {
        this.date = date;
        this.mntHt = mntHt;
        this.mntTva = mntTva;
        this.remise = remise;
        this.mntTtc = mntTtc;
        this.etat = etat;
        this.pourcentage = pourcentage;
        this.bon = bon;
        this.typeFacture = typeFacture;
    }

    public void setState(Facture f) {
        this.date = f.date;
        this.mntHt = f.mntHt;
        this.mntTva = f.mntTva;
        this.remise = f.remise;
        this.mntTtc = f.mntTtc;
        this.etat = f.etat;
        this.pourcentage = f.pourcentage;
        this.bon = f.bon;
        this.typeFacture = f.typeFacture;
    }

    public static double getTva() {
        return tva;
    }

    public static void setTva(double tva) {
        Facture.tva = tva;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMntHt() {
        return mntHt;
    }

    public void setMntHt(double mntHt) {
        this.mntHt = mntHt;
    }

    public double getMntTva() {
        return mntTva;
    }

    public void setMntTva(double mntTva) {
        this.mntTva = mntTva;
    }

    public double getRemise() {
        return remise;
    }

    public void setRemise(double remise) {
        this.remise = remise;
    }

    public double getMntTtc() {
        return mntTtc;
    }

    public void setMntTtc(double mntTtc) {
        this.mntTtc = mntTtc;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }

    public BonCommande getBon() {
        return bon;
    }

    public void setBon(BonCommande bon) {
        this.bon = bon;
    }

    public TypeFacture getTypeFacture() {
        return typeFacture;
    }

    public void setTypeFacture(TypeFacture typeFacture) {
        this.typeFacture = typeFacture;
    }
}
