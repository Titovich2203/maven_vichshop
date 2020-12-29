package vichshop.service;

import vichshop.model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public interface IBon extends Remote {

    // ================ BON ====================

    public void addBonCommande(BonCommande p) throws RemoteException;
    public List<BonCommande> getAllBonCommande() throws RemoteException;
    public List<BonCommande> getAllBonCommandeValide() throws RemoteException;
    public List<BonCommande> getAllBonCommandeNonLivre() throws RemoteException;
    public List<BonCommande> getAllBonCommandeNonValide() throws RemoteException;
    public List<BonCommande> searchListBonCommande(String num, Date date, Client cl, Double mnt) throws RemoteException;
    public List<BonCommande> searchListBonCommandeValide(String num, Date date, Client cl, Double mnt) throws RemoteException;
    public List<BonCommande> searchListBonCommandeNonValide(String num, Date date, Client cl, Double mnt) throws RemoteException;
    public BonCommande findBonCommande(long id) throws RemoteException;
    public BonCommande findBonCommande(String num) throws RemoteException;
    public long lastBonID() throws RemoteException;
    public void updateBonCommande(BonCommande p) throws RemoteException;
    public void deleteBonCommande(BonCommande p) throws RemoteException;

    // ================ BON ====================

    // ================ ETAT BON ====================

    public void addEtatBon(EtatBon p) throws RemoteException;
    public List<EtatBon> getAllEtatBon() throws RemoteException;
    public EtatBon findEtatBon(long id) throws RemoteException;
    public EtatBon findEtatBon(String libelle) throws RemoteException;
    public void updateEtatBon(EtatBon p) throws RemoteException;
    public void deleteEtatBon(EtatBon p) throws RemoteException;

    // ================ ETAT BON ====================

    // ================ ETAT PAIEMENT ====================

    public void addEtatPaiement(EtatPaiement p) throws RemoteException;
    public List<EtatPaiement> getAllEtatPaiement() throws RemoteException;
    public EtatPaiement findEtatPaiement(long id) throws RemoteException;
    public EtatPaiement findEtatPaiement(double pourcentage) throws RemoteException;
    public void updateEtatPaiement(EtatPaiement p) throws RemoteException;
    public void deleteEtatPaiement(EtatPaiement p) throws RemoteException;

    // ================ ETAT PAIEMENT ====================

    // ================ DETAIL COMMANDE ====================

    public void addDetailCommande(DetailCommande p) throws RemoteException;
    public List<DetailCommande> getAllDetailCommande() throws RemoteException;
    public DetailCommande findDetailCommande(long id) throws RemoteException;
    public void updateDetailCommande(DetailCommande p) throws RemoteException;
    public void deleteDetailCommande(DetailCommande p) throws RemoteException;

    // ================ DETAIL COMMANDE ====================
}
