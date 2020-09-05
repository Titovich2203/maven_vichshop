package vichshop.service;

import vichshop.model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IUser extends Remote {

    // ================ ROLE ====================
//
//    public void addRole(Role p) throws RemoteException;
//    public List<Role> getAllRole() throws RemoteException;
//    public List<Role> searchListRole(String libelle) throws RemoteException;
//    public Role findRole(long id) throws RemoteException;
//    public void updateRole(Role p) throws RemoteException;
//    public void deleteRole(Role p) throws RemoteException;

    // ================ ROLE ====================


    // ================ PROFIL ====================

    public void addProfil(Profil p) throws RemoteException;
    public List<Profil> getAllProfil() throws RemoteException;
    public List<Profil> searchListProfil(String libelle) throws RemoteException;
    public Profil findProfil(long id) throws RemoteException;
    public void updateProfil(Profil p) throws RemoteException;
    public void deleteProfil(Profil p) throws RemoteException;

    // ================ PROFIL ====================

    // ================ USER ====================

    public void addUser(User p) throws RemoteException;
    public List<User> getAllUser() throws RemoteException;
    public List<User> searchListUser(String nom, String email, String tel, Profil p) throws RemoteException;
    public User findUser(long id) throws RemoteException;
    public User findUser(String email, String mdp) throws RemoteException;
    public void updateUser(User p) throws RemoteException;
    public void deleteUser(User p) throws RemoteException;

    // ================ USER ====================

    // ================ CLIENT ====================

    public void addClient(Client p) throws RemoteException;
    public List<Client> getAllClient() throws RemoteException;
    public List<Client> searchListClient(String nom, TypeClient typeClient) throws RemoteException;
    public Client findClient(long id) throws RemoteException;
    public void updateClient(Client p) throws RemoteException;
    public void deleteClient(Client p) throws RemoteException;

    // ================ CLIENT ====================


    public List<TypeClient> getAllTypeClient() throws RemoteException;
    public TypeClient findTypeClient(long id) throws RemoteException;
}
