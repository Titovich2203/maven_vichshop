package vichshop.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import vichshop.model.*;
import vichshop.utils.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;

public class IUserImpl extends UnicastRemoteObject implements IUser {

    Session session;
    public IUserImpl() throws Exception
    {
        session = HibernateUtil.getSession();
    }
    /*
    @Override
    public void addRole(Role role) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            t.begin();
            session.save(role);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Role> getAllRole() throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM Role a",Role.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Role> searchListRole(final String s) throws RemoteException {
        try
        {
            List<Role> roles = session.createQuery("SELECT a FROM Role a",Role.class).list();
            if (!s.equals(""))
            {
               roles = roles.stream().filter(x -> x.getLibelle().toUpperCase().contains(s.toUpperCase())).collect(Collectors.toList());
            }
            return roles;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Role findRole(long i) throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM Role a WHERE a.id = :idA",Role.class)
                    .setParameter("idA", i)
                    .getSingleResult();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateRole(Role role) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            Role r = session.get(Role.class,role.getId());
            r.setState(role);
            t.begin();
            session.update(r);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRole(Role role) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            Role p = session.get(Role.class,role.getId());
            t.begin();
            session.delete(p);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }
    */
    @Override
    public void addProfil(Profil profil) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            t.begin();
            session.save(profil);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Profil> getAllProfil() throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM Profil a",Profil.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Profil> searchListProfil(String s) throws RemoteException {
        try
        {
            List<Profil> profils = session.createQuery("SELECT a FROM Profil a",Profil.class).list();
            if (!s.equals(""))
            {
                profils = profils.stream().filter(x -> x.getLibelle().toUpperCase().contains(s.toUpperCase())).collect(Collectors.toList());
            }
            return profils;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Profil findProfil(long i) throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM Profil a WHERE a.id = :idA",Profil.class)
                    .setParameter("idA", i)
                    .getSingleResult();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateProfil(Profil profil) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            Profil r = session.get(Profil.class,profil.getId());
            r.setState(profil);
            t.begin();
            session.update(r);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProfil(Profil profil) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            Profil p = session.get(Profil.class,profil.getId());
            t.begin();
            session.delete(p);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(User user) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            t.begin();
            session.save(user);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUser() throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM User a",User.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> searchListUser(String s, String s1, String s2, Profil profil) throws RemoteException {
        try
        {
            List<User> users = session.createQuery("SELECT a FROM User a",User.class).list();
            if (!s.equals(""))
            {
                users = users.stream().filter(x -> x.getNomComplet().toUpperCase().contains(s.toUpperCase())).collect(Collectors.toList());
            }
            if (!s1.equals(""))
            {
                users = users.stream().filter(x -> x.getEmail().toUpperCase().contains(s1.toUpperCase())).collect(Collectors.toList());
            }
            if (!s2.equals(""))
            {
                users = users.stream().filter(x -> x.getTel().toUpperCase().contains(s2.toUpperCase())).collect(Collectors.toList());
            }
            if (profil != null)
            {
                users = users.stream().filter(x -> x.getProfil().getId() == profil.getId()).collect(Collectors.toList());
            }
            return users;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User findUser(long i) throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM User a WHERE a.id = :idA",User.class)
                    .setParameter("idA", i)
                    .getSingleResult();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findUser(String s, String s1) throws RemoteException {
        try
        {
                return session.createQuery("SELECT a FROM User a WHERE a.email = :login AND a.password = :mdp ",User.class)
                    .setParameter("login", s)
                    .setParameter("mdp", s1)
                    .getSingleResult();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateUser(User user) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            User r = session.get(User.class,user.getId());
            r.setState(user);
            t.begin();
            session.update(r);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(User user) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            User p = session.get(User.class,user.getId());
            t.begin();
            session.delete(p);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void addClient(Client client) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            t.begin();
            session.save(client);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Client> getAllClient() throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM Client a",Client.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Client> searchListClient(String s, TypeClient typeClient) throws RemoteException {
        try
        {
            List<Client> clients = session.createQuery("SELECT a FROM Client a",Client.class).list();
            if (!s.equals(""))
            {
                clients = clients.stream().filter(x -> x.getNom().toUpperCase().contains(s.toUpperCase())).collect(Collectors.toList());
            }
            if (typeClient != null)
            {
                clients = clients.stream().filter(x -> x.getTypeClient().getLibelle().contains(typeClient.getLibelle())).collect(Collectors.toList());
            }
            return clients;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Client findClient(long l) throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM Client a WHERE a.id = :idA",Client.class)
                    .setParameter("idA", l)
                    .getSingleResult();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateClient(Client client) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            Client r = session.get(Client.class,client.getId());
            r.setState(client);
            t.begin();
            session.update(r);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteClient(Client client) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            Client p = session.get(Client.class,client.getId());
            t.begin();
            session.delete(p);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<TypeClient> getAllTypeClient() throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM TypeClient a",TypeClient.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TypeClient findTypeClient(long l) throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM TypeClient a WHERE a.id = :idA",TypeClient.class)
                    .setParameter("idA", l)
                    .getSingleResult();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
