package vichshop.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import vichshop.controller.popup.NewEntreeController;
import vichshop.model.Entree;
import vichshop.model.Produit;
import vichshop.model.User;
import vichshop.utils.Fabrique;
import vichshop.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class EntreeController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField txtQte;

    @FXML
    private ComboBox<Produit> cbxProduit;

    @FXML
    private ComboBox<User> cbxUser;

    @FXML
    private Button btnRefresh;

    @FXML
    private TableView<Entree> tableEntree;

    @FXML
    private TableColumn<Entree, String> dateCol;

    @FXML
    private TableColumn<Entree, Integer> qteCol;

    @FXML
    private TableColumn<Entree, String> produitCol;

    @FXML
    private TableColumn<Entree, String> userCol;

    @FXML
    private TableColumn<Entree, Void> editCol;

    @FXML
    private TableColumn<Entree, Void> deleteCol;


    ZoneId defaultZoneId = ZoneId.systemDefault();

    @FXML
    void newEntree(ActionEvent event) throws IOException {
        Utils.showPopup("NOUVEAU ENTREE","Cliquez sur ok apres l'ajout","FormNewEntree.fxml");
        actualiserTab();
    }

    @FXML
    void onRefreshClick(ActionEvent event) {
        refresh();
    }

    @FXML
    void search(KeyEvent event) {
        initCol();

        try
        {
            //tableEntree.setItems(FXCollections.observableArrayList(Fabrique.getiUser().searchListEntree(libelle)));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES ROLES","ERREUR "+e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
        refreshCbx();
    }

    private void refresh()
    {
        //txtLibelle.setText("");
        actualiserTab();

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


    private void actualiserTab()
    {
        initCol();

        try
        {
            tableEntree.setItems(FXCollections.observableArrayList(Fabrique.getiProduit().getAllEntree()));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES ROLES","ERREUR "+e);
        }
    }

    private void initCol() {
        dateCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(Utils.convertDateToString(cellData.getValue().getDate())));
        qteCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getQte()));
        userCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUser().toString()));
        produitCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getProduit().toString()));
        editCol.setCellFactory(editEntreeFactory);
        deleteCol.setCellFactory(deleteEntreeFactory);
    }

    @FXML
    void searchAction(ActionEvent event) {
        search();
    }

    @FXML
    void searchKeyEvent(KeyEvent event) {
        search();
    }


    void search()
    {
        int qte = Utils.convertToInt(txtQte.getText().trim());
        Date date;
        try {
            date = Date.from(datePicker.getValue().atStartOfDay(defaultZoneId).toInstant());
        }
        catch (Exception e)
        {
            date = null;
        }
        User u = cbxUser.getSelectionModel().getSelectedItem();
        Produit p = cbxProduit.getSelectionModel().getSelectedItem();
        initCol();
        System.out.println(date);
        try
        {
            tableEntree.setItems(FXCollections.observableArrayList(Fabrique.getiProduit().searchListEntree(qte,date,p,u)));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES ROLES","ERREUR "+e);
        }

    }

    Callback<TableColumn<Entree, Void>, TableCell<Entree, Void>> deleteEntreeFactory =
            new Callback<TableColumn<Entree, Void>, TableCell<Entree, Void>>()
            {
                @Override
                public TableCell<Entree, Void> call( final TableColumn<Entree, Void> param)
                {
                    final TableCell<Entree, Void> cell = new TableCell<Entree, Void>()
                    {
                        Image imgEdit = new Image(getClass().getResourceAsStream("/delete.png"));
                        final Button btnDel = new Button();

                        @Override
                        public void updateItem(Void check, boolean empty)
                        {
                            super.updateItem(check, empty);
                            if(empty)
                            {
                                setGraphic(null);
                                setText(null);
                            }
                            else{
                                btnDel.setOnAction(e ->{
                                    Entree u = getTableView().getItems().get(getIndex());
                                    // tableVille.getItems().clear();
                                    Boolean rep = Utils.confirmMessage("Gestion des Entrees","SUPPRESSION","Etes vous sur ?");
                                    if (rep)
                                    {
                                        try {
                                            System.out.println(""+u.getId());
                                            Fabrique.getiProduit().deleteEntree(u);
                                            Utils.showMessage("Gestion des Entrees","SUPPRESSION","SUCCES !!");
                                        }
                                        catch (Exception eX)
                                        {
                                            Utils.showMessage("Gestion des Entrees","SUPPRESSION","ECHEC !!");
                                        }

                                    }
                                    refresh();
                                });

                                btnDel.setStyle("-fx-background-color: transparent;");
                                ImageView iv = new ImageView();
                                iv.setImage(imgEdit);
                                iv.setPreserveRatio(true);
                                iv.setSmooth(true);
                                iv.setCache(true);
                                btnDel.setGraphic(iv);

                                setGraphic(btnDel);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }

                    };
                    return cell;
                }
            };

    Callback<TableColumn<Entree, Void>, TableCell<Entree, Void>> editEntreeFactory =
            new Callback<TableColumn<Entree, Void>, TableCell<Entree, Void>>()
            {
                @Override
                public TableCell<Entree, Void> call( final TableColumn<Entree, Void> param)
                {
                    final TableCell<Entree, Void> cell = new TableCell<Entree, Void>()
                    {
                        Image imgEdit = new Image(getClass().getResourceAsStream("/edit.png"));
                        final Button btnEdit = new Button();

                        @Override
                        public void updateItem(Void check, boolean empty)
                        {
                            super.updateItem(check, empty);
                            if(empty)
                            {
                                setGraphic(null);
                                setText(null);
                            }
                            else{
                                btnEdit.setOnAction(e ->{
                                    Entree u = getTableView().getItems().get(getIndex());
                                    try {
                                        FXMLLoader fl = Utils.getLoader("MODIFICATION ENTREE","Cliquez sur ok apres la modification","FormNewEntree.fxml");
                                        NewEntreeController ctrl = (NewEntreeController)fl.getController();
                                        ctrl.initData(u);
                                        Utils.showAlert();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                    actualiserTab();
                                    refresh();
                                });

                                btnEdit.setStyle("-fx-background-color: transparent;");
                                ImageView iv = new ImageView();
                                iv.setImage(imgEdit);
                                iv.setPreserveRatio(true);
                                iv.setSmooth(true);
                                iv.setCache(true);
                                btnEdit.setGraphic(iv);

                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }

                    };
                    return cell;
                }
            };

}
