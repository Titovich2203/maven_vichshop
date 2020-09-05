package vichshop.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import vichshop.model.Entree;
import vichshop.model.Produit;
import vichshop.model.Sortie;
import vichshop.model.User;
import vichshop.utils.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class IProduitImpl extends UnicastRemoteObject implements IProduit {

    Session session;
    public IProduitImpl() throws Exception
    {
        session = HibernateUtil.getSession();
    }

    @Override
    public void addProduit(Produit produit) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            t.begin();
            session.save(produit);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Produit> getAllProduit() throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM Produit a",Produit.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Produit> searchListProduit(String s, long l, long l1, int i) throws RemoteException {
        try
        {
            List<Produit> produits =  session.createQuery("SELECT a FROM Produit a",Produit.class).list();
            if(!s.equals(""))
            {
                produits = produits.stream().filter(x -> x.getLibelle().toUpperCase().contains(s.toUpperCase())).collect(Collectors.toList());
            }
            if(l != Long.MIN_VALUE)
            {
                produits = produits.stream().filter(x -> x.getPrixU() == l).collect(Collectors.toList());
            }
            if(l1 != Long.MIN_VALUE)
            {
                produits = produits.stream().filter(x -> x.getPrixMin() == l1).collect(Collectors.toList());
            }
            if(i != Integer.MIN_VALUE)
            {
                produits = produits.stream().filter(x -> x.getQteStock() == i).collect(Collectors.toList());
            }
            return produits;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Produit findProduit(long i) throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM Produit a WHERE a.id = :idA",Produit.class)
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
    public void updateProduit(Produit produit) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            Produit r = session.get(Produit.class,produit.getId());
            r.setState(produit);
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
    public void deleteProduit(Produit produit) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            Produit p = session.get(Produit.class,produit.getId());
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
    public void addEntree(Entree entree) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            t.begin();
            session.save(entree);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Entree> getAllEntree() throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM Entree a",Entree.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Entree> searchListEntree(int i, Date date, Produit p, User u) throws RemoteException {
        try
        {
            List<Entree> entrees = session.createQuery("SELECT a FROM Entree a",Entree.class).list();
            if(date != null)
            {
                entrees = entrees.stream().filter(x -> x.getDate().compareTo(date) == 0).collect(Collectors.toList());
            }
            if(u != null)
            {
                entrees = entrees.stream().filter(x -> x.getUser().getNomComplet().equals(u.getNomComplet())).collect(Collectors.toList());
               // System.out.println("USER NOT NULL");
                //System.out.println(u.getNomComplet());
            }
            if(p != null)
            {
                entrees = entrees.stream().filter(x -> x.getProduit().getLibelle().equals(p.getLibelle())).collect(Collectors.toList());
            }
            if(i != Integer.MIN_VALUE)
            {
                entrees = entrees.stream().filter(x -> x.getQte() == i).collect(Collectors.toList());
            }
            return entrees;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Entree findEntree(long i) throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM Entree a WHERE a.id = :idA",Entree.class)
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
    public void updateEntree(Entree entree) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            Entree r = session.get(Entree.class,entree.getId());
            r.setState(entree);
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
    public void deleteEntree(Entree entree) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            Entree p = session.get(Entree.class,entree.getId());
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
    public void addSortie(Sortie sortie) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            t.begin();
            session.save(sortie);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Sortie> getAllSortie() throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM Sortie a",Sortie.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Sortie> searchListSortie(int i, Date date, Produit p, User u) throws RemoteException {
        try
        {
            List<Sortie> sorties = session.createQuery("SELECT a FROM Sortie a",Sortie.class).list();
            if(date != null)
            {
                sorties = sorties.stream().filter(x -> x.getDate() == date).collect(Collectors.toList());
            }
            if(p != null)
            {
                sorties = sorties.stream().filter(x -> x.getProduit().getLibelle() == p.getLibelle()).collect(Collectors.toList());
            }
            if(u != null)
            {
                sorties = sorties.stream().filter(x -> x.getUser().getNomComplet() == u.getNomComplet()).collect(Collectors.toList());
            }
            if(i != Integer.MIN_VALUE)
            {
                sorties = sorties.stream().filter(x -> x.getQte() == i).collect(Collectors.toList());
            }
            return sorties;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Sortie findSortie(long i) throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM Sortie a WHERE a.id = :idA",Sortie.class)
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
    public void updateSortie(Sortie sortie) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            Sortie r = session.get(Sortie.class,sortie.getId());
            r.setState(sortie);
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
    public void deleteSortie(Sortie sortie) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            Sortie p = session.get(Sortie.class,sortie.getId());
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
}
