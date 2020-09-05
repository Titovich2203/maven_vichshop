package vichshop.service;

import vichshop.model.Facture;
import vichshop.model.TypeFacture;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IFacture extends Remote {

    // ================ FACTURE ====================

    public void addFacture(Facture p) throws RemoteException;
    public List<Facture> getAllFacture() throws RemoteException;
    public Facture findFacture(long id) throws RemoteException;
    public void updateFacture(Facture p) throws RemoteException;
    public void deleteFacture(Facture p) throws RemoteException;

    // ================ FACTURE ====================

    // ================ TYPE FACTURE ====================

    public void addTypeFacture(TypeFacture p) throws RemoteException;
    public List<TypeFacture> getAllTypeFacture() throws RemoteException;
    public TypeFacture findTypeFacture(long id) throws RemoteException;
    public void updateTypeFacture(TypeFacture p) throws RemoteException;
    public void deleteTypeFacture(TypeFacture p) throws RemoteException;

    // ================ TYPE FACTURE ====================
}
