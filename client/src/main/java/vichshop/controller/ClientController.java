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
import vichshop.controller.popup.NewClientController;
import vichshop.model.Client;
import vichshop.model.Produit;
import vichshop.model.TypeClient;
import vichshop.model.User;
import vichshop.utils.Fabrique;
import vichshop.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    @FXML
    private TextField txtNom;

    @FXML
    private ComboBox<TypeClient> cbxTypeClient;

    @FXML
    private Button btnRefresh;

    @FXML
    private TableView<Client> tableClient;

    @FXML
    private TableColumn<Client, String> nomCol;

    @FXML
    private TableColumn<Client, String> emailCol;

    @FXML
    private TableColumn<Client, String> typeClientCol;

    @FXML
    private TableColumn<Client, Integer> nbComCol;

    @FXML
    private TableColumn<Client, Void> editCol;

    @FXML
    private TableColumn<Client, Void> deleteCol;

    @FXML
    void newClient(ActionEvent event) throws IOException {
        Utils.showPopup("NOUVEAU ENTREE","Cliquez sur ok apres l'ajout","FormNewClient.fxml");
        actualiserTab();
    }

    @FXML
    void onRefreshClick(ActionEvent event) {
        refresh();
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
            cbxTypeClient.getItems().clear();
            cbxTypeClient.setItems(FXCollections.observableArrayList(Fabrique.getiUser().getAllTypeClient()));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES TYPES CLIENTS","ERREUR "+e);
        }
    }


    private void actualiserTab()
    {
        initCol();

        try
        {
            tableClient.setItems(FXCollections.observableArrayList(Fabrique.getiUser().getAllClient()));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES ROLES","ERREUR "+e);
        }
    }

    private void initCol() {
        nomCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNom()));
        emailCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEmail()));
        typeClientCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTypeClient().getLibelle()));
        nbComCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBons().size()));
        editCol.setCellFactory(editClientFactory);
        deleteCol.setCellFactory(deleteClientFactory);
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
        String nom = txtNom.getText().trim();
        TypeClient typeClient = cbxTypeClient.getSelectionModel().getSelectedItem();
        initCol();
        try
        {
            tableClient.setItems(FXCollections.observableArrayList(Fabrique.getiUser().searchListClient(nom,typeClient)));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES ROLES","ERREUR "+e);
        }

    }

    Callback<TableColumn<Client, Void>, TableCell<Client, Void>> deleteClientFactory =
            new Callback<TableColumn<Client, Void>, TableCell<Client, Void>>()
            {
                @Override
                public TableCell<Client, Void> call( final TableColumn<Client, Void> param)
                {
                    final TableCell<Client, Void> cell = new TableCell<Client, Void>()
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
                                    Client u = getTableView().getItems().get(getIndex());
                                    // tableVille.getItems().clear();
                                    Boolean rep = Utils.confirmMessage("Gestion des Clients","SUPPRESSION","Etes vous sur ?");
                                    if (rep)
                                    {
                                        try {
                                            System.out.println(""+u.getId());
                                            Fabrique.getiUser().deleteClient(u);
                                            Utils.showMessage("Gestion des Clients","SUPPRESSION","SUCCES !!");
                                        }
                                        catch (Exception eX)
                                        {
                                            Utils.showMessage("Gestion des Clients","SUPPRESSION","ECHEC !!");
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

    Callback<TableColumn<Client, Void>, TableCell<Client, Void>> editClientFactory =
            new Callback<TableColumn<Client, Void>, TableCell<Client, Void>>()
            {
                @Override
                public TableCell<Client, Void> call( final TableColumn<Client, Void> param)
                {
                    final TableCell<Client, Void> cell = new TableCell<Client, Void>()
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
                                    Client u = getTableView().getItems().get(getIndex());
                                    try {
                                        FXMLLoader fl = Utils.getLoader("MODIFICATION ENTREE","Cliquez sur ok apres la modification","FormNewClient.fxml");
                                        NewClientController ctrl = (NewClientController)fl.getController();
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
