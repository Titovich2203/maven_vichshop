package vichshop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import vichshop.utils.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private TextField txtSearch;

    @FXML
    private AnchorPane containerPane;

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
        changeView("FormCommandeY");
    }
}
