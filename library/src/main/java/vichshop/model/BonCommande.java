package vichshop.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class BonCommande implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String numero;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(nullable = false)
    private double montant;

    @Temporal(TemporalType.DATE)
    private Date dateLivrVoulu;

    @Temporal(TemporalType.DATE)
    private Date dateLivrReel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="bon")
    private List<DetailCommande> details = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="etat_bon_id", nullable=false)
    private EtatBon etatBon;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="etat_payement_id", nullable=false)
    private EtatPaiement etatPaiement;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id", nullable=false)
    private Client client;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="bon")
    private List<Facture> factures = new ArrayList<>();

    public BonCommande() {
    }

    public BonCommande(String numero, Date date, long montant, Date dateLivrVoulu, Date dateLivrReel, User user, List<DetailCommande> details, EtatBon etatBon, EtatPaiement etatPaiement, Client client, List<Facture> factures) {
        this.numero = numero;
        this.date = date;
        this.montant = montant;
        this.dateLivrVoulu = dateLivrVoulu;
        this.dateLivrReel = dateLivrReel;
        this.user = user;
        this.details = details;
        this.etatBon = etatBon;
        this.etatPaiement = etatPaiement;
        this.client = client;
        this.factures = factures;
    }

    public void setState(BonCommande b) {
        //this.numero = b.numero;
        this.date = b.date;
        this.montant = b.montant;
        this.dateLivrVoulu = b.dateLivrVoulu;
        this.dateLivrReel = b.dateLivrReel;
        //this.user = b.user;
       // this.details = b.details;
        this.etatBon = b.etatBon;
        this.etatPaiement = b.etatPaiement;
        //this.client = b.client;
        //this.factures = b.factures;
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

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Date getDateLivrVoulu() {
        return dateLivrVoulu;
    }

    public void setDateLivrVoulu(Date dateLivrVoulu) {
        this.dateLivrVoulu = dateLivrVoulu;
    }

    public Date getDateLivrReel() {
        return dateLivrReel;
    }

    public void setDateLivrReel(Date dateLivrReel) {
        this.dateLivrReel = dateLivrReel;
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

    public EtatBon getEtatBon() {
        return etatBon;
    }

    public void setEtatBon(EtatBon etatBon) {
        this.etatBon = etatBon;
    }

    public EtatPaiement getEtatPaiement() {
        return etatPaiement;
    }

    public void setEtatPaiement(EtatPaiement etatPaiement) {
        this.etatPaiement = etatPaiement;
    }

    public List<Facture> getFactures() {
        return factures;
    }

    public void setFactures(List<Facture> factures) {
        this.factures = factures;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
