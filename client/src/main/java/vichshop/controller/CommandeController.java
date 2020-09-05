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
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import vichshop.model.BonCommande;
import vichshop.model.Client;
import vichshop.model.DetailCommande;
import vichshop.model.Produit;
import vichshop.modeles.CommandePdf;
import vichshop.utils.Fabrique;
import vichshop.utils.Utils;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CommandeController implements Initializable {

    @FXML
    private DatePicker dateCom;

    @FXML
    private TextField txtMontant;

    @FXML
    private DatePicker dateLivraisonVoulue;

    @FXML
    private ComboBox<Client> cbxClient;

    @FXML
    private TextField txtQte;

    @FXML
    private ComboBox<Produit> cbxProduit;

    @FXML
    private TableView<DetailCommande> tableProduit;

    @FXML
    private TableColumn<DetailCommande, String> produitCol;

    @FXML
    private TableColumn<DetailCommande, Long> prixCol;

    @FXML
    private TableColumn<DetailCommande, Integer> qteCol;

    @FXML
    private TableColumn<DetailCommande, Long> mntCol;

    @FXML
    private TableColumn<DetailCommande, Void> deleteCol;

    @FXML
    private AnchorPane listeComPane;

    List<DetailCommande> details;

    double montantTotal = 0;

    ZoneId defaultZoneId = ZoneId.systemDefault();

    @FXML
    void addCommande(ActionEvent event) {
        Date dateC;
        try {
            dateC = Date.from(dateCom.getValue().atStartOfDay(defaultZoneId).toInstant());
        } catch (Exception e) {
            dateC = null;
        }
        Date dateL;
        try {
            dateL = Date.from(dateLivraisonVoulue.getValue().atStartOfDay(defaultZoneId).toInstant());
        } catch (Exception e) {
            dateL = null;
        }
        Client cl = cbxClient.getSelectionModel().getSelectedItem();
        if (dateC == null || dateL == null || cl == null || montantTotal == 0 || details.size() == 0) {
            Utils.showMessage("VICHSHOP", "Enregistrement d'une commande", "Veuillez remplir les champs !!");
        } else {
            try {
                String num = "NUM_COM" + Fabrique.getiBon().lastBonID();
                // String num = "NUM_COM1";
                BonCommande bon = new BonCommande();
                bon.setNumero(num);
                bon.setDate(dateC);
                bon.setDateLivrVoulu(dateL);
                bon.setClient(cl);
                bon.setMontant(montantTotal);
                bon.setEtatBon(Fabrique.getiBon().findEtatBon("NON_VALIDE"));
                bon.setEtatPaiement(Fabrique.getiBon().findEtatPaiement(0.0));
                bon.setUser(Fabrique.getiUser().findUser(1));
                Fabrique.getiBon().addBonCommande(bon);

                bon = Fabrique.getiBon().findBonCommande(num);
                for (DetailCommande d : details) {
                    d.setBon(bon);
                    Fabrique.getiBon().addDetailCommande(d);
                }
                bon.setDetails(details);
                CommandePdf.imprimerPdf(bon);
                Utils.showMessage("VICHSHOP", "Enregistrement d'une commande", "Commande ajoutee avec succes !!");
                refresh();
            } catch (Exception e) {
                Utils.showMessage("VICHSHOP", "Enregistrement d'une commande", "Echec !!");
                e.printStackTrace();
            }
            //bon.setDateLivrReel();
        }
    }


    @FXML
    void addProduit(ActionEvent event) {
        int qte = Utils.convertToInt(txtQte.getText().trim());
        Produit p = cbxProduit.getSelectionModel().getSelectedItem();

        if (qte == Integer.MIN_VALUE || qte <= 0 || p == null) {
            Utils.showMessage("VICHSHOP", "Gestion des produits", "Veuillez remplir les champs !!");
        } else {
            boolean ok = false;
            int index = 0;
            for (DetailCommande d : details) {
                if (d.getProduit().getLibelle().equals(p.getLibelle())) {
                    index = details.indexOf(d);
                    ok = true;
                }
            }
            if (ok) {
                DetailCommande d = details.get(index);
                //System.out.println(d.getQte());
                d.setQte(d.getQte() + qte);
                //System.out.println(d.getQte());
                details.set(index, d);
            } else {
                DetailCommande detail = new DetailCommande();
                detail.setQte(qte);
                detail.setProduit(p);
                details.add(detail);
            }
            actualiserTab();


        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
    }

    void refresh() {
        details = new ArrayList<>();
        details.clear();
        montantTotal = 0;
        dateLivraisonVoulue.setValue(null);
        txtQte.setText("");
        dateCom.setValue(LocalDate.now());
        actualiserTab();
        actualiserCbx();
        try {
            AnchorPane pane3 = FXMLLoader.load(getClass().getResource("/vichshop/view/FormListeCommande.fxml"));
            listeComPane.getChildren().setAll(pane3);
        } catch (Exception e) {

        }

    }

    void actualiserCbx() {
        try {
            cbxClient.getItems().clear();
            cbxClient.setItems(FXCollections.observableArrayList(Fabrique.getiUser().getAllClient()));
            cbxProduit.getItems().clear();
            cbxProduit.setItems(FXCollections.observableArrayList(Fabrique.getiProduit().getAllProduit()));
        } catch (Exception e) {
            Utils.showMessage("Gestion des utilisateurs", "LISTE DES PRODUITS ET USERS", "ERREUR " + e);
        }
    }

    public void actualiserTab() {

        tableProduit.getItems().clear();

        produitCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getProduit().getLibelle()));
        prixCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getProduit().getPrixU()));
        qteCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getQte()));
        mntCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getProduit().getPrixU() * cellData.getValue().getQte()));
        deleteCol.setCellFactory(deleteDetailCommandeFactory);


        try {
            montantTotal = 0;
            for (DetailCommande d : details) {
                montantTotal += (d.getQte() * d.getProduit().getPrixU());
            }
            txtMontant.setText(montantTotal + " FCFA");
            tableProduit.setItems(FXCollections.observableArrayList(details));
        } catch (Exception e) {

        }
    }

    Callback<TableColumn<DetailCommande, Void>, TableCell<DetailCommande, Void>> deleteDetailCommandeFactory =
            new Callback<TableColumn<DetailCommande, Void>, TableCell<DetailCommande, Void>>() {
                @Override
                public TableCell<DetailCommande, Void> call(final TableColumn<DetailCommande, Void> param) {
                    final TableCell<DetailCommande, Void> cell = new TableCell<DetailCommande, Void>() {
                        Image imgEdit = new Image(getClass().getResourceAsStream("/delete.png"));
                        final Button btnDel = new Button();

                        @Override
                        public void updateItem(Void check, boolean empty) {
                            super.updateItem(check, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btnDel.setOnAction(e -> {
                                    DetailCommande detail = getTableView().getItems().get(getIndex());
                                    // tableVille.getItems().clear();
                                    Boolean rep = Utils.confirmMessage("Gestion des DetailCommandes", "SUPPRESSION", "Etes vous sur ?");
                                    if (rep) {
                                        try {
                                            for (DetailCommande d : details) {
                                                if (d.getProduit().getLibelle().equals(detail.getProduit().getLibelle())) {
                                                    details.remove(d);
                                                }
                                            }
                                            actualiserTab();
                                        } catch (Exception eX) {
                                            Utils.showMessage("Gestion des DetailCommandes", "SUPPRESSION", "ECHEC !!");
                                        }

                                    }
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

}
