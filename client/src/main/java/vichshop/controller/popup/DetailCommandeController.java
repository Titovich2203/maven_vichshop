package vichshop.controller.popup;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import vichshop.model.*;
import vichshop.modeles.FacturePdf;
import vichshop.utils.Fabrique;
import vichshop.utils.Utils;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class DetailCommandeController implements Initializable {

    @FXML
    private DialogPane dialogPane;

    @FXML
    private DatePicker dateCom;

    @FXML
    private TextField txtMontant;

    @FXML
    private DatePicker dateLivraisonVoulue;

    @FXML
    private DatePicker dateLivraisonReelle;

    @FXML
    private TextField txtNomClient;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTypeClient;

    @FXML
    private TextField txtEtatBon;

    @FXML
    private TextField txtEtatP;

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
    private TextField txtPourcentage;

    @FXML
    private TextField txtTva;

    @FXML
    private TextField txtRemise;

    @FXML
    private TextField txtMntTtc;

    @FXML
    private Button btnValider;

    @FXML
    private Button btnRemise;

    BonCommande bon;

    double tva, pourcentage, mntTtc, remise, montantHt;


    @FXML
    void validerRemise(ActionEvent event) {
        String remiseText = txtRemise.getText().trim();
        if (remiseText.endsWith("%"))
            remiseText = remiseText.substring(0, remiseText.length() - 2);
        double valeur = Utils.convertToDouble(remiseText);
        if (valeur != Double.MIN_VALUE && valeur < 100)
            remise = valeur;
        else
            remise = 0;

        refreshValeur();
    }


    public void initData(BonCommande b) {
        bon = b;
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            if (b.getEtatBon().getLibelle().equals("VALIDE")) {
                btnValider.setVisible(false);
                btnRemise.setVisible(false);
            } else {
                btnValider.setVisible(true);
                btnRemise.setVisible(true);

            }
            //System.out.println(b.getDate());
            dateCom.setValue(LocalDate.parse(Utils.convertDateToString(b.getDate()), df));
            if (b.getDateLivrReel() != null)
                dateLivraisonReelle.setValue(LocalDate.parse(Utils.convertDateToString(b.getDateLivrReel()).toString(), df));
            dateLivraisonVoulue.setValue(LocalDate.parse(Utils.convertDateToString(b.getDateLivrVoulu()).toString(), df));
            txtEmail.setText(b.getClient().getEmail());
            txtNomClient.setText(b.getClient().getNom());
            txtTypeClient.setText(b.getClient().getTypeClient().getLibelle());
            txtEtatBon.setText(b.getEtatBon().getLibelle());
            txtEtatP.setText("Paye a " + b.getEtatPaiement().getPourcentage() + " %");
            txtMontant.setText(b.getMontant() + " FCFA");

            if (b.getClient().getTypeClient().getLibelle().equals("PARTICULIER"))
                pourcentage = 100;
            else
                pourcentage = 30;

            refreshValeur();

            tableProduit.getItems().clear();

            produitCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getProduit().getLibelle()));
            prixCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getProduit().getPrixU()));
            qteCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getQte()));
            mntCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getProduit().getPrixU() * cellData.getValue().getQte()));

            tableProduit.setItems(FXCollections.observableArrayList(b.getDetails()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void refreshValeur() {
        if (remise > 0)
            montantHt = (pourcentage / 100) * (bon.getMontant() - (bon.getMontant() * (remise / 100)));
        else {
            montantHt = (pourcentage / 100) * bon.getMontant();
        }
        tva = 0.18 * montantHt;
        mntTtc = montantHt + tva;
        txtPourcentage.setText(pourcentage + " %");
        txtTva.setText(tva + " Fcfa");
        txtMntTtc.setText(mntTtc + " Fcfa");
        txtRemise.setText(remise + " %");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /*
    @FXML
    void impression(ActionEvent event) {
        if(bon != null)
        {
            CommandePdf.imprimerPdf(bon);
        }
    }
    */


    @FXML
    void validerCommande(ActionEvent event) {
        if (bon != null) {
            if (bon.getEtatBon().getLibelle().equals("VALIDE")) {
                Utils.showMessage("VICH SHOP", "VALIDATION DE LA COMMANDE", "Commande deja validé");
            } else {
                try {
                    boolean ok = true;
                    for (DetailCommande d : bon.getDetails()) {
                        if (d.getQte() > d.getProduit().getQteStock()) {
                            Utils.showMessage("VICH SHOP", "VALIDATION DE LA COMMANDE", bon.getClient().getNom() + " veut commander " + d.getQte() + " " + d.getProduit().getLibelle() + " or il n'en reste plus que " + d.getProduit().getQteStock());
                            ok = false;
                        }
                    }
                    if (ok) {
                        for (DetailCommande d : bon.getDetails()) {
                            Produit p = d.getProduit();
                            p.setQteStock(p.getQteStock() - d.getQte());
                            Fabrique.getiProduit().updateProduit(p);
                        }
                        // BonCommande b = bon;
                        EtatBon etatBon = Fabrique.getiBon().findEtatBon("VALIDE");
                        EtatPaiement etatPaiement = Fabrique.getiBon().findEtatPaiement(pourcentage);
                        if (etatBon != null && etatPaiement != null) {
                            // etatBon.getBons().add(b);
                            // Fabrique.getiBon().updateEtatBon(etatBon);
                            // etatBon = Fabrique.getiBon().findEtatBon("VALIDE");
                            bon.setEtatBon(etatBon);
                            bon.setEtatPaiement(etatPaiement);
                            if (pourcentage == 100)
                                bon.setDateLivrReel(new Date());
                            Fabrique.getiBon().updateBonCommande(bon);
                            bon = Fabrique.getiBon().findBonCommande(bon.getNumero());
                            this.initData(bon);
                            Utils.showMessage("VICH SHOP", "VALIDATION DE LA COMMANDE", "Commande validé");


                            Facture f = new Facture();
                            f.setDate(new Date());
                            f.setMntHt(montantHt);
                            f.setMntTva(tva);
                            f.setMntTtc(mntTtc);
                            f.setBon(bon);
                            f.setPourcentage(pourcentage);
                            f.setRemise(remise);
                            f.setEtat("1");
                            if (pourcentage == 100)
                                f.setTypeFacture(Fabrique.getiFacture().findTypeFacture("SOLDE"));
                            else
                                f.setTypeFacture(Fabrique.getiFacture().findTypeFacture("ACCOMPTE"));

                            Fabrique.getiFacture().addFacture(f);
                            FacturePdf.imprimerPdf(f);
                        } else {
                            System.out.println("BON NULL");
                        }
                    } else {
                        Utils.showMessage("VICH SHOP", "VALIDATION DE LA COMMANDE", "Commande non validé");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
