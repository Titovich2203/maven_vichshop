package vichshop.controller.popup;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import vichshop.model.Client;
import vichshop.model.Produit;
import vichshop.model.TypeClient;
import vichshop.model.User;
import vichshop.utils.Fabrique;
import vichshop.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class NewClientController implements Initializable {

    @FXML
    private DialogPane dialogPane;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtEmail;

    @FXML
    private ComboBox<TypeClient> cbxTypeClient;

    @FXML
    private Button btnNewClient;

    @FXML
    private Button btnModifier;

    private long ClientId;

    @FXML
    void NewClient(ActionEvent event) throws IOException {
        String nom = txtNom.getText().trim();
        String email = txtEmail.getText().trim();
        TypeClient t = cbxTypeClient.getSelectionModel().getSelectedItem();

        if(nom.equals("") || t == null || email.equals(""))
        {
            Utils.showMessage("VICH SHOP","NOUVEL CLIENT","Veuillez remplir les champs !!");
        }
        else
        {
            Client client = new Client();
            client.setNom(nom);
            client.setEmail(email);
            try
            {
                client.setTypeClient(Fabrique.getiUser().findTypeClient(t.getId()));
                Fabrique.getiUser().addClient(client);

                Utils.showMessage("VICH SHOP","NOUVEAU CLIENT","Ajout avec succes !!!");
                initialise();

            }
            catch (Exception e)
            {
                Utils.showMessage("VICH SHOP","NOUVEL CLIENT","Echec d'jout !!!");
            }

        }
    }

    @FXML
    void updateClient(ActionEvent event) {
        String nom = txtNom.getText().trim();
        String email = txtEmail.getText().trim();
        TypeClient t = cbxTypeClient.getSelectionModel().getSelectedItem();

        if(nom.equals("") || t == null)
        {
            Utils.showMessage("VICH SHOP","MODIFICATION ROLE","Veuillez remplir les champs !!");
        }
        else
        {
            if(ClientId != 0)
            {
                try {
                    Client client = Fabrique.getiUser().findClient(ClientId);

                    if(client != null) {

                        client.setNom(nom);
                        client.setEmail(email);
                        client.setTypeClient(Fabrique.getiUser().findTypeClient(t.getId()));
                        Fabrique.getiUser().updateClient(client);

                        Utils.showMessage("VICH SHOP", "MODIFICATION ROLE", "modification avec succes !!!");
                        if(Utils.confirmMessage("VICH SHOP","Modification","Remodifier le meme ROLE ? "))
                        {
                            initData(client);
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
        initialise();
    }

    void initialise()
    {
        txtNom.setText("");
        txtEmail.setText("");
        refreshCbx();
        btnModifier.setVisible(false);
        btnNewClient.setVisible(true);
        ClientId = 0;
    }

    public void initData(Client u)
    {
        txtNom.setText(u.getNom());
        txtEmail.setText(u.getEmail());
        refreshCbx();
        for (TypeClient t : cbxTypeClient.getItems()) {
            if(t.getId() == u.getTypeClient().getId())
            {
                cbxTypeClient.getSelectionModel().select(t);
            }
        }
        ClientId = u.getId();
        btnModifier.setVisible(true);
        btnNewClient.setVisible(false);
    }

    private void refreshCbx()
    {
        try {
            cbxTypeClient.getItems().clear();
            cbxTypeClient.setItems(FXCollections.observableArrayList(Fabrique.getiUser().getAllTypeClient()));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES PRODUITS ET USERS","ERREUR "+e);
        }
    }

}
