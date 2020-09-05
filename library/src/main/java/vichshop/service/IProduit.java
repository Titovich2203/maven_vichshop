package vichshop.service;


import vichshop.model.Entree;
import vichshop.model.Produit;
import vichshop.model.Sortie;
import vichshop.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public interface IProduit extends Remote {

    // ================ PRODUIT ====================

    public void addProduit(Produit p) throws RemoteException;
    public List<Produit> getAllProduit() throws RemoteException;
    public List<Produit> searchListProduit(String libelle, long pu, long pmin, int qte) throws RemoteException;
    public Produit findProduit(long id) throws RemoteException;
    public void updateProduit(Produit p) throws RemoteException;
    public void deleteProduit(Produit p) throws RemoteException;

    // ================ PRODUIT ====================

    // ================ ENTREE ====================

    public void addEntree(Entree p) throws RemoteException;
    public List<Entree> getAllEntree() throws RemoteException;
    public List<Entree> searchListEntree(int qte, Date date, Produit p, User u) throws RemoteException;
    public Entree findEntree(long id) throws RemoteException;
    public void updateEntree(Entree p) throws RemoteException;
    public void deleteEntree(Entree p) throws RemoteException;

    // ================ ENTREE ====================

    // ================ SORTIE ====================

    public void addSortie(Sortie p) throws RemoteException;
    public List<Sortie> getAllSortie() throws RemoteException;
    public List<Sortie> searchListSortie(int qte, Date date, Produit p, User u) throws RemoteException;
    public Sortie findSortie(long id) throws RemoteException;
    public void updateSortie(Sortie p) throws RemoteException;
    public void deleteSortie(Sortie p) throws RemoteException;

    // ================ SORTIE ====================
}
