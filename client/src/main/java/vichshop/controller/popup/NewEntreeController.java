package vichshop.controller.popup;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import vichshop.model.Entree;
import vichshop.model.Produit;
import vichshop.model.User;
import vichshop.modeles.UserSession;
import vichshop.utils.Fabrique;
import vichshop.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class NewEntreeController implements Initializable {

    @FXML
    private DialogPane dialogPane;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField txtQte;

    @FXML
    private ComboBox<Produit> cbxProduit;

    @FXML
    private ComboBox<User> cbxUser;

    @FXML
    private Button btnNewEntree;

    @FXML
    private Button btnModifier;
    private long EntreeId;
    ZoneId defaultZoneId = ZoneId.systemDefault();

    @FXML
    void NewEntree(ActionEvent event) throws IOException {
        int qte = Utils.convertToInt(txtQte.getText().trim());
        LocalDate date = datePicker.getValue();
        //User u = cbxUser.getSelectionModel().getSelectedItem();
        User u = UserSession.getInstace().getUser();
        Produit p = cbxProduit.getSelectionModel().getSelectedItem();

        if(qte == Integer.MIN_VALUE || qte <=0 || date == null || u == null || p == null)
        {
            Utils.showMessage("VICH SHOP","NOUVEL ENTREE","Veuillez remplir les champs !!");
        }
        else
        {
            Entree entree = new Entree();
            entree.setQte(qte);
            entree.setDate(Date.from(date.atStartOfDay(defaultZoneId).toInstant()));
            entree.setProduit(p);
            entree.setUser(u);
            try
            {
                Fabrique.getiProduit().addEntree(entree);

                Produit produit = Fabrique.getiProduit().findProduit(p.getId());
                produit.setQteStock(produit.getQteStock()+entree.getQte());
                Fabrique.getiProduit().updateProduit(produit);
                Utils.showMessage("VICH SHOP","NOUVEL ENTREE","Ajout avec succes !!!");
                initialise();

            }
            catch (Exception e)
            {
                Utils.showMessage("VICH SHOP","NOUVEL ENTREE","Echec d'jout !!!");
            }

        }
    }

    @FXML
    void updateEntree(ActionEvent event) {
        int qte = Utils.convertToInt(txtQte.getText().trim());
        LocalDate date = datePicker.getValue();
        //User u = cbxUser.getSelectionModel().getSelectedItem();
        User u = UserSession.getInstace().getUser();
        Produit p = cbxProduit.getSelectionModel().getSelectedItem();

        if(qte == Integer.MIN_VALUE || qte <=0 || date == null || u == null || p == null)
        {
            Utils.showMessage("VICH SHOP","MODIFICATION ROLE","Veuillez remplir les champs !!");
        }
        else
        {
            if(EntreeId != 0)
            {
                try {
                    Entree entree = Fabrique.getiProduit().findEntree(EntreeId);

                    if(entree != null) {
                        int ancienneEntree = entree.getQte();

                        entree.setQte(qte);
                        entree.setDate(Date.from(date.atStartOfDay(defaultZoneId).toInstant()));
                        entree.setProduit(p);
                        entree.setUser(u);
                        Fabrique.getiProduit().updateEntree(entree);

                        Produit produit = Fabrique.getiProduit().findProduit(p.getId());
                        produit.setQteStock(produit.getQteStock()-ancienneEntree);
                        produit.setQteStock(produit.getQteStock()+entree.getQte());
                        Fabrique.getiProduit().updateProduit(produit);

                        Utils.showMessage("VICH SHOP", "MODIFICATION ROLE", "modification avec succes !!!");
                        if(Utils.confirmMessage("VICH SHOP","Modification","Remodifier le meme ROLE ? "))
                        {
                            initData(entree);
                        }
                        else {
                            initialise();
                        }
                    }
                }
                catch (Exception e)
                {
                    Utils.showMessage("VICH SHOP","MODIFICATION ROLE","Echec !!!");
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //cbxUser.setEditable(false);

        initialise();

    }

    void initialise()
    {
        txtQte.setText("");
        refreshCbx();
        datePicker.setValue(LocalDate.now());
        btnModifier.setVisible(false);
        btnNewEntree.setVisible(true);
        EntreeId = 0;
        /*
        for (User p : cbxUser.getItems()) {
            if(p.getEmail() == UserSession.getInstace().getUser().getEmail())
            {
                cbxUser.getSelectionModel().select(p);
            }
        }*/
        cbxUser.setVisible(false);
    }

    public void initData(Entree u)
    {
        txtQte.setText(u.getQte()+"");
        datePicker.setValue(u.getDate().toInstant().atZone(defaultZoneId).toLocalDate());
        refreshCbx();
        for (Produit p : cbxProduit.getItems()) {
            if(p.getId() == u.getProduit().getId())
            {
                cbxProduit.getSelectionModel().select(p);
            }
        }
        /*
        for (User p : cbxUser.getItems()) {
            if(p.getId() == u.getProduit().getId())
            {
                cbxUser.getSelectionModel().select(p);
            }
        }*/
        EntreeId = u.getId();
        btnModifier.setVisible(true);
        btnNewEntree.setVisible(false);
    }

    private void refreshCbx()
    {
        try {
            cbxUser.getItems().clear();
            cbxUser.setItems(FXCollections.observableArrayList(Fabrique.getiUser().getAllUser()));
            cbxProduit.getItems().clear();
            cbxProduit.setItems(FXCollections.observableArrayList(Fabrique.getiProduit().getAllProduit()));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES PRODUITS ET USERS","ERREUR "+e);
        }
    }

}
