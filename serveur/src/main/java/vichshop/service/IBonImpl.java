package vichshop.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import vichshop.model.*;
import vichshop.utils.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class IBonImpl extends UnicastRemoteObject implements IBon {

    Session session;
    public IBonImpl() throws RemoteException{
        session = HibernateUtil.getSession();
    }
    @Override
    public void addBonCommande(BonCommande bonCommande) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            t.begin();
            session.save(bonCommande);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<BonCommande> getAllBonCommande() throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM BonCommande a",BonCommande.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<BonCommande> getAllBonCommandeValide() throws RemoteException {
        try {
            List<BonCommande> bons = getAllBonCommande();
            bons = bons.stream().filter(x -> x.getEtatBon().getLibelle().equals("VALIDE") ).collect(Collectors.toList());
            return bons;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<BonCommande> getAllBonCommandeNonValide() throws RemoteException {
        try {
            List<BonCommande> bons = getAllBonCommande();
            bons = bons.stream().filter(x -> x.getEtatBon().getLibelle().equals("NON_VALIDE") ).collect(Collectors.toList());
            return bons;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<BonCommande> searchListBonCommande(String s, Date date, Client client, Double aDouble) throws RemoteException {
        try
        {
            List<BonCommande> bons =  session.createQuery("SELECT a FROM BonCommande a",BonCommande.class).list();
            return trierListeBon(s, date, client, aDouble, bons);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<BonCommande> searchListBonCommandeValide(String s, Date date, Client client, Double aDouble) throws RemoteException {
        try
        {
            List<BonCommande> bons =  getAllBonCommandeValide();
            return trierListeBon(s, date, client, aDouble, bons);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<BonCommande> searchListBonCommandeNonValide(String s, Date date, Client client, Double aDouble) throws RemoteException {
        try
        {
            List<BonCommande> bons =  getAllBonCommandeNonValide();
            return trierListeBon(s, date, client, aDouble, bons);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private List<BonCommande> trierListeBon(String s, Date date, Client client, Double aDouble, List<BonCommande> bons) {
        if(!s.equals(""))
        {
            bons = bons.stream().filter(x -> x.getNumero().toUpperCase().contains(s.toUpperCase())).collect(Collectors.toList());
        }
        if(client != null)
        {
            bons = bons.stream().filter(x -> x.getClient().getId() == client.getId() ).collect(Collectors.toList());
        }
        if(aDouble != Double.MIN_VALUE)
        {
            bons = bons.stream().filter(x -> x.getMontant() == aDouble ).collect(Collectors.toList());
        }
        if(date != null)
        {
            bons = bons.stream().filter(x -> x.getDate().compareTo(date) == 0 ).collect(Collectors.toList());
        }
        return bons;
    }

    @Override
    public BonCommande findBonCommande(long i) throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM BonCommande a WHERE a.id = :idA",BonCommande.class)
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
    public BonCommande findBonCommande(String s) throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM BonCommande a WHERE a.numero = :idA",BonCommande.class)
                    .setParameter("idA", s)
                    .getSingleResult();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long lastBonID() throws RemoteException{
        Long id = (Long)(session.createQuery("SELECT max(a.id) FROM BonCommande a")
                .getSingleResult());
        if(id != null)
        {
            return id+1;
        }
        else
            return 1;
    }


    @Override
    public void updateBonCommande(BonCommande bonCommande) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            BonCommande r = session.get(BonCommande.class,bonCommande.getId());
            r.setState(bonCommande);
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
    public void deleteBonCommande(BonCommande bonCommande) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            BonCommande p = session.get(BonCommande.class,bonCommande.getId());
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
    public void addEtatBon(EtatBon etatBon) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            t.begin();
            session.save(etatBon);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<EtatBon> getAllEtatBon() throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM EtatBon a",EtatBon.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public EtatBon findEtatBon(long i) throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM EtatBon a WHERE a.id = :idA",EtatBon.class)
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
    public EtatBon findEtatBon(String s) throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM EtatBon a WHERE a.libelle = :lib",EtatBon.class)
                    .setParameter("lib", s)
                    .getSingleResult();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void updateEtatBon(EtatBon etatBon) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            EtatBon r = session.get(EtatBon.class,etatBon.getId());
            r.setState(etatBon);
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
    public void deleteEtatBon(EtatBon etatBon) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            EtatBon p = session.get(EtatBon.class,etatBon.getId());
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
    public void addEtatPaiement(EtatPaiement etatPaiement) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            t.begin();
            session.save(etatPaiement);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<EtatPaiement> getAllEtatPaiement() throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM EtatPaiement a",EtatPaiement.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public EtatPaiement findEtatPaiement(long i) throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM EtatPaiement a WHERE a.id = :idA",EtatPaiement.class)
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
    public EtatPaiement findEtatPaiement(double i) throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM EtatPaiement a WHERE a.pourcentage = :idA",EtatPaiement.class)
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
    public void updateEtatPaiement(EtatPaiement etatPaiement) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            EtatPaiement r = session.get(EtatPaiement.class,etatPaiement.getId());
            r.setState(etatPaiement);
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
    public void deleteEtatPaiement(EtatPaiement etatPaiement) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            EtatPaiement p = session.get(EtatPaiement.class,etatPaiement.getId());
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
    public void addDetailCommande(DetailCommande detailCommande) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            t.begin();
            session.save(detailCommande);
            t.commit();

            BonCommande bon = findBonCommande(detailCommande.getBon().getNumero());
            bon.getDetails().add(detailCommande);
            t.begin();
            session.update(bon);
            t.commit();
        }
        catch (Exception e)
        {
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<DetailCommande> getAllDetailCommande() throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM DetailCommande a",DetailCommande.class).list();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DetailCommande findDetailCommande(long i) throws RemoteException {
        try
        {
            return session.createQuery("SELECT a FROM DetailCommande a WHERE a.id = :idA",DetailCommande.class)
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
    public void updateDetailCommande(DetailCommande detailCommande) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            DetailCommande r = session.get(DetailCommande.class,detailCommande.getId());
            r.setState(detailCommande);
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
    public void deleteDetailCommande(DetailCommande detailCommande) throws RemoteException {
        Transaction t = session.getTransaction();
        try
        {
            DetailCommande p = session.get(DetailCommande.class,detailCommande.getId());
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
