package vichshop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import vichshop.modeles.UserSession;
import vichshop.utils.LoadView;
import vichshop.utils.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private TextField txtSearch;

    @FXML
    private AnchorPane containerPane;

    @FXML
    private HBox userBox;

    @FXML
    private HBox stockBox;

    @FXML
    private HBox commandeBox;

    @FXML
    private HBox factureBox;

    @FXML
    private void gotoUserBoard()
    {
        changeView("FormUser");
    }

    @FXML
    private void gotoStatisticBoard()
    {
        changeView("FormStatistic");
    }

    @FXML
    private void gotoStockBoard()
    {
        changeView("FormStock");
    }

    @FXML
    private void gotoCommandBoard()
    {
        changeView("FormCommandeY");
    }

    @FXML
    private void gotoFactuteBoard()
    {
        changeView("FormFacture");
    }

    private void changeView(String view)
    {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/vichshop/view/"+view+".fxml"));
            containerPane.getChildren().setAll(pane);
        }
        catch (Exception e)
        {
            Utils.showMessage("HUMM","HUMM",""+e);
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //txtSearch.deselect();
        //txtSearch.setEditable(false);
        //txtSearch.setText("Recherche");
     //   txtSearch.setEditable(true);
      //  changeView("FormStatistic");
        String profil = UserSession.getInstace().getUser().getProfil().getLibelle();
        if(profil.equals("CAISSIER"))
        {
            //userBox.setDisable(false);
            userBox.setVisible(false);
            stockBox.setVisible(false);
        }
        if(profil.equals("MAGASINIER"))
        {
            //userBox.setDisable(false);
            userBox.setVisible(false);
            commandeBox.setVisible(false);
            factureBox.setVisible(false);
            //stockBox.setVisible(false);
        }
        changeView("FormStatistic");
    }


    @FXML
    void deconnect(MouseEvent event) {
        UserSession.getInstace().cleanUserSession();
        LoadView.showView("VICH SHOP","FormLogin.fxml",1);
    }

    @FXML
    void deconnexion(MouseEvent event) {
        UserSession.getInstace().cleanUserSession();
        LoadView.showView("VICH SHOP","FormLogin.fxml",1);
    }
}
