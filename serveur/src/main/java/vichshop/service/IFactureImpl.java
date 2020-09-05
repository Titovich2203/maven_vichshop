package vichshop.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import vichshop.model.Facture;
import vichshop.model.TypeFacture;
import vichshop.utils.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class IFactureImpl extends UnicastRemoteObject implements IFacture {


    Session session;
    public IFactureImpl() throws Exception
    {
        session = HibernateUtil.getSession();
    }

    @Override
    public void addFacture(Facture facture) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            t.begin();
            session.save(facture);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Facture> getAllFacture() throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM Facture a",Facture.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Facture findFacture(long i) throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM Facture a WHERE a.id = :idA",Facture .class)
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
    public void updateFacture(Facture facture) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            Facture r = session.get(Facture.class,facture.getId());
            r.setState(facture);
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
    public void deleteFacture(Facture facture) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            Facture p = session.get(Facture.class,facture.getId());
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
    public void addTypeFacture(TypeFacture typeFacture) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            t.begin();
            session.save(typeFacture);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<TypeFacture> getAllTypeFacture() throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM TypeFacture a",TypeFacture.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TypeFacture findTypeFacture(long i) throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM TypeFacture a WHERE a.id = :idA",TypeFacture.class)
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
    public void updateTypeFacture(TypeFacture typeFacture) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            TypeFacture r = session.get(TypeFacture.class,typeFacture.getId());
            r.setState(typeFacture);
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
    public void deleteTypeFacture(TypeFacture typeFacture) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            TypeFacture p = session.get(TypeFacture.class,typeFacture.getId());
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
