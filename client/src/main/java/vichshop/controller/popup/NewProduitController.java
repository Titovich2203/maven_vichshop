package vichshop.controller.popup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import vichshop.model.Produit;
import vichshop.utils.Fabrique;
import vichshop.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewProduitController implements Initializable {

    @FXML
    private DialogPane dialogPane;

    @FXML
    private TextField txtLibelle;

    @FXML
    private TextField txtPu;

    @FXML
    private TextField txtPmin;

    @FXML
    private TextField txtQte;

    @FXML
    private Button btnNewProduit;

    @FXML
    private Button btnModifier;

    private long ProduitId;

    @FXML
    void NewProduit(ActionEvent event) throws IOException {
        String libelle = txtLibelle.getText().trim();
        String puString = txtPu.getText().trim();
        String pminString = txtPmin.getText().trim();
        String qteString = txtQte.getText().trim();

        if (libelle.equals("") || puString.equals("") || pminString.equals("") || qteString.equals("")) {
            Utils.showMessage("VICH SHOP", "NOUVEAU PRODUIT", "Veuillez remplir les champs !!");
        } else {
            long pu = Utils.convertToLong(puString);
            long pmin = Utils.convertToLong(pminString);
            int qte = Utils.convertToInt(qteString);
            if (qte == Integer.MIN_VALUE || pu == Long.MIN_VALUE || pmin == Long.MIN_VALUE || pu < pmin) {
                Utils.showMessage("VICH SHOP", "NOUVEAU PRODUIT", "Prix U. ou Prix Min ou Qte invalide");
            } else {
                Produit u = new Produit();

                u.setLibelle(libelle);
                u.setQteStock(qte);
                u.setPrixMin(pmin);
                u.setPrixU(pu);

                try {
                   // u.setUser(Fabrique.getiUser().findUser(UserSession.getInstace().getUser().getId()));
                    u.setUser(Fabrique.getiUser().findUser(1));
                    Fabrique.getiProduit().addProduit(u);
                    Utils.showMessage("VICH SHOP", "NOUVEL PRODUIT", "Ajout avec succes !!!");
                    initialise();

                } catch (Exception e) {
                    e.printStackTrace();
                    Utils.showMessage("VICH SHOP", "NOUVEL PRODUIT", "Echec d'jout !!!");
                }
            }
        }
    }

    @FXML
    void updateProduit(ActionEvent event) {
        String libelle = txtLibelle.getText().trim();
        String puString = txtPu.getText().trim();
        String pminString = txtPmin.getText().trim();
        String qteString = txtQte.getText().trim();

        if (libelle.equals("") || puString.equals("") || pminString.equals("") || qteString.equals("")) {
            Utils.showMessage("VICH SHOP", "MODIFICATION UTILISATEUR", "Veuillez remplir les champs !!");
        } else {
            long pu = Utils.convertToLong(puString);
            long pmin = Utils.convertToLong(pminString);
            int qte = Utils.convertToInt(qteString);
            if (qte == Integer.MIN_VALUE || pu == Long.MIN_VALUE || pmin == Long.MIN_VALUE) {
                Utils.showMessage("VICH SHOP", "NOUVEAU PRODUIT", "Prix U. ou Prix Min ou Qte invalide");
            } else {
                if (ProduitId != 0) {
                    try {
                        Produit u = Fabrique.getiProduit().findProduit(ProduitId);
                        if (u != null) {
                            u.setLibelle(libelle);
                            u.setQteStock(qte);
                            u.setPrixMin(pmin);
                            u.setPrixU(pu);
                            //u.setUser(Fabrique.getiUser().findUser(UserSession.getInstace().getUser().getId()));
                            u.setUser(Fabrique.getiUser().findUser(1));
                            Fabrique.getiProduit().updateProduit(u);
                            Utils.showMessage("VICH SHOP", "MODIFICATION UTILISATEUR", "modification avec succes !!!");
                            if (Utils.confirmMessage("VICH SHOP", "Modification", "Remodifier le meme utilisateur ? ")) {
                                initData(u);
                            } else {
                                initialise();
                            }
                        }
                    } catch (Exception e) {
                        Utils.showMessage("VICH SHOP", "MODIFICATION UTILISATEUR", "Echec !!!");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialise();
    }

    void initialise() {
        btnModifier.setVisible(false);
        btnNewProduit.setVisible(true);
        ProduitId = 0;
        txtLibelle.setText("");
        txtQte.setText("");
        txtPmin.setText("");
        txtPu.setText("");
        txtQte.setDisable(false);
    }

    public void initData(Produit u) {
        txtLibelle.setText(u.getLibelle());
        txtPu.setText(u.getPrixU()+"");
        txtPmin.setText(u.getPrixMin()+"");
        txtQte.setText(u.getQteStock()+"");
        btnModifier.setVisible(true);
        btnNewProduit.setVisible(false);
        txtQte.setDisable(true);
        ProduitId = u.getId();
    }
}
