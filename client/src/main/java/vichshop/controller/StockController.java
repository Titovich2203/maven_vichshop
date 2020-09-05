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
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import vichshop.controller.popup.NewProduitController;
import vichshop.model.Produit;
import vichshop.utils.Fabrique;
import vichshop.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StockController implements Initializable {

    @FXML
    private TextField txtLibelle;

    @FXML
    private TextField txtPu;

    @FXML
    private TextField txtPmin;

    @FXML
    private TextField txtQte;

    @FXML
    private Button btnRefresh;

    @FXML
    private TableView<Produit> tableProduit;

    @FXML
    private TableColumn<Produit, String> libelleCol;

    @FXML
    private TableColumn<Produit, Long> puCol;

    @FXML
    private TableColumn<Produit, Long> pminCol;

    @FXML
    private TableColumn<Produit, Integer> qteCol;

    @FXML
    private TableColumn<Produit, Void> editCol;

    @FXML
    private TableColumn<Produit, Void> deleteCol;

    @FXML
    private AnchorPane entreePane;

    @FXML
    void newProduit(ActionEvent event) throws IOException {
        Utils.showPopup("NOUVEAU Produit","Cliquez sur ok apres l'ajout","FormNewProduit.fxml");
        actualiserTab();
    }

    @FXML
    void onRefreshClick(ActionEvent event) {
        refresh();
    }

    @FXML
    void search(KeyEvent event) {
        String libelle = txtLibelle.getText().trim();
        Long pu = Utils.convertToLong(txtPu.getText().trim());
        Long pmin = Utils.convertToLong(txtPmin.getText().trim());
        int qte = Utils.convertToInt(txtQte.getText().trim());
        libelleCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLibelle()));
        editCol.setCellFactory(editProduitFactory);
        deleteCol.setCellFactory(deleteProduitFactory);

        try
        {
            tableProduit.setItems(FXCollections.observableArrayList(Fabrique.getiProduit().searchListProduit(libelle, pu, pmin,qte)));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES ProduitS","ERREUR "+e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
    }

    private void refresh() {
        try {
            AnchorPane pane1 = FXMLLoader.load(getClass().getResource("/vichshop/view/FormEntree.fxml"));
            entreePane.getChildren().setAll(pane1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        txtLibelle.setText("");
        actualiserTab();
    }


    private void actualiserTab()
    {
        libelleCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLibelle()));
        puCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrixU()));
        pminCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrixMin()));
        qteCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getQteStock()));
        editCol.setCellFactory(editProduitFactory);
        deleteCol.setCellFactory(deleteProduitFactory);

        try
        {
            tableProduit.setItems(FXCollections.observableArrayList(Fabrique.getiProduit().getAllProduit()));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES ProduitS","ERREUR "+e);
        }
    }


    Callback<TableColumn<Produit, Void>, TableCell<Produit, Void>> deleteProduitFactory =
            new Callback<TableColumn<Produit, Void>, TableCell<Produit, Void>>()
            {
                @Override
                public TableCell<Produit, Void> call( final TableColumn<Produit, Void> param)
                {
                    final TableCell<Produit, Void> cell = new TableCell<Produit, Void>()
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
                                    Produit u = getTableView().getItems().get(getIndex());
                                    // tableVille.getItems().clear();
                                    Boolean rep = Utils.confirmMessage("Gestion des Produits","SUPPRESSION","Etes vous sur ?");
                                    if (rep)
                                    {
                                        try {
                                            System.out.println(""+u.getId());
                                            Fabrique.getiProduit().deleteProduit(u);
                                            Utils.showMessage("Gestion des Produits","SUPPRESSION","SUCCES !!");
                                        }
                                        catch (Exception eX)
                                        {
                                            Utils.showMessage("Gestion des Produits","SUPPRESSION","ECHEC !!");
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

    Callback<TableColumn<Produit, Void>, TableCell<Produit, Void>> editProduitFactory =
            new Callback<TableColumn<Produit, Void>, TableCell<Produit, Void>>()
            {
                @Override
                public TableCell<Produit, Void> call( final TableColumn<Produit, Void> param)
                {
                    final TableCell<Produit, Void> cell = new TableCell<Produit, Void>()
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
                                    Produit u = getTableView().getItems().get(getIndex());
                                    try {
                                        FXMLLoader fl = Utils.getLoader("MODIFICATION UTILISATEUR","Cliquez sur ok apres la modification","FormNewProduit.fxml");
                                        NewProduitController ctrl = (NewProduitController)fl.getController();
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
