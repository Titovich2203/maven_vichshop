package vichshop.controller.popup;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.util.Callback;
import vichshop.model.Profil;
import vichshop.utils.Fabrique;
import vichshop.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewProfilController implements Initializable {

    @FXML
    private DialogPane dialogPane;

    @FXML
    private TextField txtLibelle;

    @FXML
    private Button btnNewProfil;

    @FXML
    private Button btnModifier;

    private long ProfilId;


    @FXML
    void NewProfil(ActionEvent event){
        String libelle = txtLibelle.getText().trim();

        if(libelle.equals(""))
        {
            Utils.showMessage("VICH SHOP","NOUVEL Profil","Veuillez remplir les champs !!");
        }
        else
        {
            Profil p = new Profil();
            p.setLibelle(libelle);
            try
            {
                if(existProfil(p.getLibelle()))
                {
                    Utils.showMessage("VICH SHOP","NOUVEL Profil","PROFIL DEJA EXISTANT !!!");
                }
                else {
                    Fabrique.getiUser().addProfil(p);
                    Utils.showMessage("VICH SHOP","NOUVEL Profil","Ajout avec succes !!!");
                }
                initialise();

            }
            catch (Exception e)
            {
                e.printStackTrace();
                Utils.showMessage("VICH SHOP","NOUVEL Profil","Echec d'jout !!!");
            }

        }
    }

    @FXML
    void updateProfil(ActionEvent event) {
        String libelle = txtLibelle.getText().trim();

        if(libelle.equals("") )
        {
            Utils.showMessage("VICH SHOP","MODIFICATION Profil","Veuillez remplir les champs !!");
        }
        else
        {
            if(ProfilId != 0)
            {
                try {
                    if(existProfil(libelle))
                    {
                        Utils.showMessage("VICH SHOP","NOUVEL Profil","PROFIL DEJA EXISTANT !!!");
                        initialise();
                    }
                    else {
                        Profil u = Fabrique.getiUser().findProfil(ProfilId);
                        if (u != null) {
                            u.setLibelle(libelle);
                            Fabrique.getiUser().updateProfil(u);
                            Utils.showMessage("VICH SHOP", "MODIFICATION Profil", "modification avec succes !!!");
                            if (Utils.confirmMessage("VICH SHOP", "Modification", "Remodifier le meme Profil ? ")) {
                                initData(u);
                            } else {
                                initialise();
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    Utils.showMessage("VICH SHOP","MODIFICATION Profil","Echec !!!");
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialise();
    }

    void initialise()
    {
        txtLibelle.setText("");


        btnModifier.setVisible(false);
        btnNewProfil.setVisible(true);
        ProfilId = 0;
    }

    public void initData(Profil u)
    {
        txtLibelle.setText(u.getLibelle());
        ProfilId = u.getId();
        btnModifier.setVisible(true);
        btnNewProfil.setVisible(false);
    }


    boolean existProfil(String libelle)
    {
        try {

            for (Profil profil : Fabrique.getiUser().searchListProfil(libelle)) {
                if (libelle.equals(profil.getLibelle()))
                    return true;
            }
            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
