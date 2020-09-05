package vichshop.utils;


import vichshop.service.IBon;
import vichshop.service.IFacture;
import vichshop.service.IProduit;
import vichshop.service.IUser;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Fabrique {
    private static IUser iUser;
    private static IBon iBon;
    private static IFacture iFacture;
    private static IProduit iProduit;

    private static void init() throws Exception {
        try {
            Registry registry = LocateRegistry.getRegistry(5003);
            iUser = (IUser) registry.lookup("userRemote");
            iBon = (IBon) registry.lookup("bonRemote");
            iFacture = (IFacture) registry.lookup("factureRemote");
            iProduit = (IProduit) registry.lookup("produitRemote");
        }
        catch(Exception e){
            throw e;
        }
    }

    public static IUser getiUser() throws Exception{
        try {
            if(iUser == null) {
                init();
            }
            return iUser;
        }
        catch(Exception e){
            throw e;
        }
    }

    public static IBon getiBon() throws Exception{
        try {
            if(iBon == null) {
                init();
            }
            return iBon;
        }
        catch(Exception e){
            throw e;
        }
    }

    public static IFacture getiFacture() throws Exception{
        try {
            if(iFacture == null) {
                init();
            }
            return iFacture;
        }
        catch(Exception e){
            throw e;
        }
    }

    public static IProduit getiProduit() throws Exception{
        try {
            if(iProduit == null) {
                init();
            }
            return iProduit;
        }
        catch(Exception e){
            throw e;
        }
    }
}
