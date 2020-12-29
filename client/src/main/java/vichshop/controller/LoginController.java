package vichshop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import vichshop.model.User;
import vichshop.modeles.UserSession;
import vichshop.utils.Fabrique;
import vichshop.utils.LoadView;
import vichshop.utils.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    void login(ActionEvent event) {
        String email = txtUsername.getText().trim();
        String mdp = txtPassword.getText().trim();
        if(email.equals("") || mdp.equals(""))
        {
            Utils.showMessage("VICH SHOP","AUTHENTIFICATION ERROR","Veuillez remplir tous les champs !!");
        }
        else
        {
            try {
                User u = Fabrique.getiUser().findUser(email,mdp);
                if(u != null)
                {
                    UserSession.setSession(u);
                    LoadView.showView("VICH SHOP","FormDashboard.fxml",1);
                }
                else
                {
                    Utils.showMessage("VICH SHOP","AUTHENTIFICATION ERROR","Erreur de connexion au serveur !!!");
                    txtUsername.setText("");
                    txtPassword.setText("");
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtUsername.setText("tito@codify-sn.com");
        txtPassword.setText("passer");
    }
}
