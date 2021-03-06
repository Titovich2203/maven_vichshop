package vichshop.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import vichshop.model.Client;
import vichshop.model.Facture;
import vichshop.modeles.FacturePdf;
import vichshop.utils.Fabrique;
import vichshop.utils.Utils;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ListeFactureController implements Initializable {

    @FXML
    private TextField txtNum;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<Client> cbxClient;

    @FXML
    private TextField txtMnt;

    @FXML
    private Button btnRefresh;

    @FXML
    private TableView<Facture> tableFacture;

    @FXML
    private TableColumn<Facture, String> dateCol;

    @FXML
    private TableColumn<Facture, String> mntCol;

    @FXML
    private TableColumn<Facture, String> pourcentageCol;

    @FXML
    private TableColumn<Facture, String> numCol;

    @FXML
    private TableColumn<Facture, String> clientCol;

    @FXML
    private TableColumn<Facture, Void> printCol;

    @FXML
    void onRefreshClick(ActionEvent event) {
        refresh();
    }

    @FXML
    void searchAction(ActionEvent event) {
        search();
    }

    @FXML
    void searchKeyEvent(KeyEvent event) {
        search();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
    }

    void search()
    {
        String numero = txtNum.getText().trim();
        Date date;
        try {
            date = Date.from(datePicker.getValue().atStartOfDay(Utils.defaultZoneId).toInstant());
        }
        catch (Exception e)
        {
            date = null;
        }
        Client cl = cbxClient.getSelectionModel().getSelectedItem();
        Double mnt = Utils.convertToDouble(txtMnt.getText().trim());

        dateCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(Utils.convertDateToString(cellData.getValue().getDate())));
        pourcentageCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPourcentage()+" %"));
        mntCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getMntTtc()+" FCFA"));
        numCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBon().getNumero()));
        clientCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBon().getClient().getNom()));
        printCol.setCellFactory(printFactureFactory);
        try {
            tableFacture.setItems(FXCollections.observableArrayList(Fabrique.getiFacture().searchListeFacture(numero,date, cl, mnt)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void refresh()
    {
        txtNum.setText("");
        txtMnt.setText("");
        actualiserCbxClient();
        datePicker.setValue(null);
        actualiserTab();
    }

    void actualiserCbxClient()
    {
        try {
            cbxClient.getItems().clear();
            cbxClient.setItems(FXCollections.observableArrayList(Fabrique.getiUser().getAllClient()));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES CLIENTS","ERREUR "+e);
        }
    }

    void actualiserTab()
    {
        try
        {
            dateCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(Utils.convertDateToString(cellData.getValue().getDate())));
            pourcentageCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPourcentage()+" %"));
            mntCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getMntTtc()+" FCFA"));
            numCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBon().getNumero()));
            clientCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBon().getClient().getNom()));
            printCol.setCellFactory(printFactureFactory);
            tableFacture.setItems(FXCollections.observableArrayList(Fabrique.getiFacture().getAllFacture()));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES FACTURES","ERREUR "+e);
        }
    }

    Callback<TableColumn<Facture, Void>, TableCell<Facture, Void>> printFactureFactory =
            new Callback<TableColumn<Facture, Void>, TableCell<Facture, Void>>()
            {
                @Override
                public TableCell<Facture, Void> call( final TableColumn<Facture, Void> param)
                {
                    final TableCell<Facture, Void> cell = new TableCell<Facture, Void>()
                    {
                        Image imgEdit = new Image(getClass().getResourceAsStream("/images/print.png"));
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
                                    Facture f = getTableView().getItems().get(getIndex());
                                    FacturePdf.imprimerPdf(f);
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

/*
package vichshop.controller;


import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import vichshop.model.BonCommande;
import vichshop.model.Client;
import vichshop.modeles.CommandePdf;
import vichshop.utils.Fabrique;
import vichshop.utils.Utils;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ListeFactureController implements Initializable {

    @FXML
    private TextField txtNum;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<Client> cbxClient;

    @FXML
    private TextField txtMnt;

    @FXML
    private Button btnRefresh;

    @FXML
    private TableView<BonCommande> tableCom;

    @FXML
    private TableColumn<BonCommande, String> numCol;

    @FXML
    private TableColumn<BonCommande, String> dateCol;

    @FXML
    private TableColumn<BonCommande, String> clientCol;

    @FXML
    private TableColumn<BonCommande, Double> mntCol;

    @FXML
    private TableColumn<BonCommande, Void> printCol;

    @FXML
    private TableColumn<BonCommande, Void> deleteCol;

    @FXML
    private TableView<BonCommande> tableCom1;

    @FXML
    private TableColumn<BonCommande, String> numCol1;

    @FXML
    private TableColumn<BonCommande, String> dateCol1;

    @FXML
    private TableColumn<BonCommande, String> clientCol1;

    @FXML
    private TableColumn<BonCommande, Double> mntCol1;

    @FXML
    private TableColumn<BonCommande, Void> printCol1;


    @FXML
    void onRefreshClick(ActionEvent event) {
        refresh();
    }

    @FXML
    void searchAction(ActionEvent event) {
        search();
    }

    @FXML
    void searchKeyEvent(KeyEvent event) {
        search();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
    }

    void refresh()
    {
        actualiserCbxClient();
        actuliserTab();
        datePicker.setValue(null);
        txtNum.setText("");
        txtMnt.setText("");
    }

    void actualiserCbxClient()
    {
        try {
            cbxClient.getItems().clear();
            cbxClient.setItems(FXCollections.observableArrayList(Fabrique.getiUser().getAllClient()));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES PRODUITS ET USERS","ERREUR "+e);
        }
    }
    void initCol()
    {
        initIdemCol(numCol, dateCol, clientCol, mntCol, printCol);
        deleteCol.setCellFactory(deleteBonCommandeFactory);

        initIdemCol(numCol1, dateCol1, clientCol1, mntCol1, printCol1);
    }

    private void initIdemCol(TableColumn<BonCommande, String> numCol1, TableColumn<BonCommande, String> dateCol1, TableColumn<BonCommande, String> clientCol1, TableColumn<BonCommande, Double> mntCol1, TableColumn<BonCommande, Void> printCol1) {
        numCol1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNumero()));
        dateCol1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(Utils.convertDateToString(cellData.getValue().getDate())));
        clientCol1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getClient().getNom()));
        mntCol1.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getMontant()));
        printCol1.setCellFactory(printBonCommandeFactory);
    }

    void actuliserTab()
    {
        initCol();
        try
        {
            tableCom.setItems(FXCollections.observableArrayList(Fabrique.getiBon().getAllBonCommandeNonValide()));
            tableCom1.setItems(FXCollections.observableArrayList(Fabrique.getiBon().getAllBonCommandeValide()));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES BONS","ERREUR "+e);
        }
    }

    void search()
    {
        initCol();
        String numero = txtNum.getText().trim();
        Date date;
        try {
            date = Date.from(datePicker.getValue().atStartOfDay(Utils.defaultZoneId).toInstant());
        }
        catch (Exception e)
        {
            date = null;
        }
        Client cl = cbxClient.getSelectionModel().getSelectedItem();
        Double mnt = Utils.convertToDouble(txtMnt.getText().trim());
        try
        {
            tableCom.setItems(FXCollections.observableArrayList(Fabrique.getiBon().searchListBonCommandeNonValide(numero,date,cl,mnt)));
            tableCom1.setItems(FXCollections.observableArrayList(Fabrique.getiBon().searchListBonCommandeValide(numero,date,cl,mnt)));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des commandes","LISTE DES BONS","ERREUR "+e);
        }
    }

    Callback<TableColumn<BonCommande, Void>, TableCell<BonCommande, Void>> deleteBonCommandeFactory =
            new Callback<TableColumn<BonCommande, Void>, TableCell<BonCommande, Void>>()
            {
                @Override
                public TableCell<BonCommande, Void> call( final TableColumn<BonCommande, Void> param)
                {
                    final TableCell<BonCommande, Void> cell = new TableCell<BonCommande, Void>()
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
                                    BonCommande u = getTableView().getItems().get(getIndex());
                                    // tableVille.getItems().clear();
                                    Boolean rep = Utils.confirmMessage("Gestion des BonCommandes","SUPPRESSION","Etes vous sur ?");
                                    if (rep)
                                    {
                                        try {
                                            System.out.println(""+u.getId());
                                            Fabrique.getiBon().deleteBonCommande(u);
                                            Utils.showMessage("Gestion des BonCommandes","SUPPRESSION","SUCCES !!");
                                        }
                                        catch (Exception eX)
                                        {
                                            Utils.showMessage("Gestion des BonCommandes","SUPPRESSION","ECHEC !!");
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

    Callback<TableColumn<BonCommande, Void>, TableCell<BonCommande, Void>> printBonCommandeFactory =
            new Callback<TableColumn<BonCommande, Void>, TableCell<BonCommande, Void>>()
            {
                @Override
                public TableCell<BonCommande, Void> call( final TableColumn<BonCommande, Void> param)
                {
                    final TableCell<BonCommande, Void> cell = new TableCell<BonCommande, Void>()
                    {
                        Image imgEdit = new Image(getClass().getResourceAsStream("/images/print.png"));
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
                                    BonCommande u = getTableView().getItems().get(getIndex());
                                    CommandePdf.imprimerPdf(u);
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
*/