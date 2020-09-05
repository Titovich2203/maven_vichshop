package vichshop.controller.popup;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import vichshop.controller.UserController;
import vichshop.model.Profil;
import vichshop.model.User;
import vichshop.utils.Fabrique;
import vichshop.utils.LoadPopup;
import vichshop.utils.LoadView;
import vichshop.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewUserController implements Initializable {

    @FXML
    private DialogPane dialogPane;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtNum;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtMdp;

    @FXML
    private ChoiceBox<Profil> cbxProfil;

    @FXML
    private Button btnNewUser;

    @FXML
    private Button btnModifier;

    private long userId;

    @FXML
    void NewUser(ActionEvent event) throws IOException {
        String nom = txtNom.getText().trim();
        String tel = txtNum.getText().trim();
        String email = txtEmail.getText().trim();
        String mdp = txtMdp.getText().trim();
        Profil p = cbxProfil.getSelectionModel().getSelectedItem();

        if(nom.equals("") || tel.equals("") || email.equals("") || mdp.equals("") || p == null)
        {
            Utils.showMessage("VICH SHOP","NOUVEL UTILISATEUR","Veuillez remplir les champs !!");
        }
        else
        {
            User u = new User();
            u.setNomComplet(nom);
            u.setEmail(email);
            u.setTel(tel);
            u.setPassword(mdp);
            u.setProfil(p);
            try
            {
                Fabrique.getiUser().addUser(u);
                Utils.showMessage("VICH SHOP","NOUVEL UTILISATEUR","Ajout avec succes !!!");
                initialise();

            }
            catch (Exception e)
            {
                Utils.showMessage("VICH SHOP","NOUVEL UTILISATEUR","Echec d'jout !!!");
            }

        }
    }

    @FXML
    void updateUser(ActionEvent event) {
        String nom = txtNom.getText().trim();
        String tel = txtNum.getText().trim();
        String email = txtEmail.getText().trim();
        String mdp = txtMdp.getText().trim();
        Profil p = cbxProfil.getSelectionModel().getSelectedItem();

        if(nom.equals("") || tel.equals("") || email.equals("") || mdp.equals("") || p == null)
        {
            Utils.showMessage("VICH SHOP","MODIFICATION UTILISATEUR","Veuillez remplir les champs !!");
        }
        else
        {
            if(userId != 0)
            {
                try {
                    User u = Fabrique.getiUser().findUser(userId);
                    if(u != null) {
                        u.setNomComplet(nom);
                        u.setEmail(email);
                        u.setTel(tel);
                        u.setPassword(mdp);
                        u.setProfil(p);
                        Fabrique.getiUser().updateUser(u);
                        Utils.showMessage("VICH SHOP", "MODIFICATION UTILISATEUR", "modification avec succes !!!");
                        if(Utils.confirmMessage("VICH SHOP","Modification","Remodifier le meme utilisateur ? "))
                        {
                            initData(u);
                        }
                        else {
                            initialise();
                        }
                    }
                }
                catch (Exception e)
                {
                    Utils.showMessage("VICH SHOP","MODIFICATION UTILISATEUR","Echec !!!");
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
        btnModifier.setVisible(false);
        btnNewUser.setVisible(true);
        userId = 0;
        txtNom.setText("");
        txtMdp.setText("");
        txtEmail.setText("");
        txtNum.setText("");
        try {
            cbxProfil.getItems().clear();
            cbxProfil.setItems(FXCollections.observableArrayList(Fabrique.getiUser().getAllProfil()));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES PROFILS","ERREUR "+e);
        }
    }

    public void initData(User u)
    {
        txtNom.setText(u.getNomComplet());
        txtNum.setText(u.getTel());
        txtEmail.setText(u.getEmail());
        txtMdp.setText(u.getPassword());
        for (Profil p: cbxProfil.getItems()) {
            if(p.getId() == u.getProfil().getId())
            {
                cbxProfil.setValue(p);
            }
        }
        userId = u.getId();
        btnModifier.setVisible(true);
        btnNewUser.setVisible(false);
    }
}
