package vichshop;

import vichshop.service.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            System.setSecurityManager(new SecurityManager());
            IBon iBon = new IBonImpl();
            IFacture iFacture = new IFactureImpl();
            IUser iUser = new IUserImpl();
            IProduit iProduit = new IProduitImpl();
            Registry registry = LocateRegistry.createRegistry(5003);
            registry.bind("bonRemote", iBon);
            registry.bind("factureRemote", iFacture);
            registry.bind("userRemote", iUser);
            registry.bind("produitRemote", iProduit);
            System.out.println("SUCCES !!");
        }
        catch (Exception e)
        {
            //e.printStackTrace();
            System.out.println(e);
        }
    }
}
