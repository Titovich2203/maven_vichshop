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
import vichshop.controller.popup.NewProfilController;
import vichshop.model.Profil;
import vichshop.utils.Fabrique;
import vichshop.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfilController implements Initializable {

    @FXML
    private TextField txtLibelle;

    @FXML
    private Button btnRefresh;

    @FXML
    private TableView<Profil> tableProfil;

    @FXML
    private TableColumn<Profil, String> libelleCol;

    @FXML
    private TableColumn<Profil, Void> editCol;

    @FXML
    private TableColumn<Profil, Void> deleteCol;

    @FXML
    void newProfil(ActionEvent event) throws IOException {
        Utils.showPopup("NOUVEAU Profil","Cliquez sur ok apres l'ajout","FormNewProfil.fxml");
        actualiserTab();
    }

    @FXML
    void onRefreshClick(ActionEvent event) {
        refresh();
    }

    @FXML
    void search(KeyEvent event) {
        String libelle = txtLibelle.getText().trim();
        libelleCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLibelle()));
        editCol.setCellFactory(editProfilFactory);
        deleteCol.setCellFactory(deleteProfilFactory);

        try
        {
            tableProfil.setItems(FXCollections.observableArrayList(Fabrique.getiUser().searchListProfil(libelle)));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES ProfilS","ERREUR "+e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
    }

    private void refresh()
    {
        txtLibelle.setText("");
        actualiserTab();
    }


    private void actualiserTab()
    {
        libelleCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLibelle()));
        editCol.setCellFactory(editProfilFactory);
        deleteCol.setCellFactory(deleteProfilFactory);

        try
        {
            tableProfil.setItems(FXCollections.observableArrayList(Fabrique.getiUser().getAllProfil()));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES ProfilS","ERREUR "+e);
        }
    }


    Callback<TableColumn<Profil, Void>, TableCell<Profil, Void>> deleteProfilFactory =
            new Callback<TableColumn<Profil, Void>, TableCell<Profil, Void>>()
            {
                @Override
                public TableCell<Profil, Void> call( final TableColumn<Profil, Void> param)
                {
                    final TableCell<Profil, Void> cell = new TableCell<Profil, Void>()
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
                                    Profil u = getTableView().getItems().get(getIndex());
                                    // tableVille.getItems().clear();
                                    Boolean rep = Utils.confirmMessage("Gestion des Profils","SUPPRESSION","Etes vous sur ?");
                                    if (rep)
                                    {
                                        try {
                                            System.out.println(""+u.getId());
                                            Fabrique.getiUser().deleteProfil(u);
                                            Utils.showMessage("Gestion des Profils","SUPPRESSION","SUCCES !!");
                                        }
                                        catch (Exception eX)
                                        {
                                            Utils.showMessage("Gestion des Profils","SUPPRESSION","ECHEC !!");
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

    Callback<TableColumn<Profil, Void>, TableCell<Profil, Void>> editProfilFactory =
            new Callback<TableColumn<Profil, Void>, TableCell<Profil, Void>>()
            {
                @Override
                public TableCell<Profil, Void> call( final TableColumn<Profil, Void> param)
                {
                    final TableCell<Profil, Void> cell = new TableCell<Profil, Void>()
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
                                    Profil u = getTableView().getItems().get(getIndex());
                                    try {
                                        FXMLLoader fl = Utils.getLoader("MODIFICATION UTILISATEUR","Cliquez sur ok apres la modification","FormNewProfil.fxml");
                                        NewProfilController ctrl = (NewProfilController)fl.getController();
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
