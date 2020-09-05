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
import vichshop.controller.popup.NewUserController;
import vichshop.model.Profil;
import vichshop.model.User;
import vichshop.utils.Fabrique;
import vichshop.utils.LoadPopup;
import vichshop.utils.LoadView;
import vichshop.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNumero;

    @FXML
    private ChoiceBox<Profil> cbxProfil;

    @FXML
    private Button btnRefresh;

    @FXML
    private TableView<User> tableUser;

    @FXML
    private TableColumn<User, String> nomCol;

    @FXML
    private TableColumn<User, String> emailCol;

    @FXML
    private TableColumn<User, String> mdpCol;

    @FXML
    private TableColumn<User, String> telCol;

    @FXML
    private TableColumn<User, String> profilCol;

    @FXML
    private TableColumn<User, Void> editCol;

    @FXML
    private TableColumn<User, Void> deleteCol;

    @FXML
    private AnchorPane rolePane;

    @FXML
    private AnchorPane profilPane;

    @FXML
    private AnchorPane clientPane;

    @FXML
    void newUser(ActionEvent event) throws IOException {
        //LoadView.hide();
        //LoadPopup.showView("NOUVEL UTILISATEUR", "FormNewUser.fxml", 1);
        Utils.showPopup("NOUVEL UTILISATEUR","Cliquez sur ok apres l'ajout","FormNewUser.fxml");
        actualiserTab();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initialise();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialise() throws IOException {
        txtNom.setText("");
        txtNumero.setText("");
        txtEmail.setText("");
        actualisercbxProfil();
        actualiserTab();

        //AnchorPane pane1 = FXMLLoader.load(getClass().getResource("/vichshop/view/FormRole.fxml"));
        //rolePane.getChildren().setAll(pane1);
        AnchorPane pane2 = FXMLLoader.load(getClass().getResource("/vichshop/view/FormProfil.fxml"));
        profilPane.getChildren().setAll(pane2);
        AnchorPane pane3 = FXMLLoader.load(getClass().getResource("/vichshop/view/FormClient.fxml"));
        clientPane.getChildren().setAll(pane3);
    }

    @FXML
    void onRefreshClick(ActionEvent event) throws IOException {
        initialise();
    }

    @FXML
    void search(KeyEvent event) {
        refresh();
    }
    void refresh() {
        String nom = txtNom.getText().trim();
        String tel = txtNumero.getText().trim();
        String email = txtEmail.getText().trim();
        Profil p = cbxProfil.getSelectionModel().getSelectedItem();

        setColProperties();

        try
        {
            tableUser.setItems(FXCollections.observableArrayList(Fabrique.getiUser().searchListUser(nom,email,tel,p)));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES USERS","ERREUR "+e);
        }
    }

    private void setColProperties() {
        nomCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNomComplet()));
        emailCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEmail()));
        mdpCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(Utils.crypterString(cellData.getValue().getPassword())));
        telCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTel()));
        profilCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getProfil().getLibelle()));
        editCol.setCellFactory(editUserFactory);
        deleteCol.setCellFactory(deleteUserFactory);
    }

    public void actualiserTab()
    {
        setColProperties();

        try
        {
            tableUser.setItems(FXCollections.observableArrayList(Fabrique.getiUser().getAllUser()));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES USERS","ERREUR "+e);
        }
    }

    public void actualisercbxProfil()
    {
        try {
            cbxProfil.getItems().clear();
            cbxProfil.setItems(FXCollections.observableArrayList(Fabrique.getiUser().getAllProfil()));
        }
        catch (Exception e)
        {
            Utils.showMessage("Gestion des utilisateurs","LISTE DES PROFILS","ERREUR "+e);
        }
    }

    Callback<TableColumn<User, Void>, TableCell<User, Void>> deleteUserFactory =
            new Callback<TableColumn<User, Void>, TableCell<User, Void>>()
            {
                @Override
                public TableCell<User, Void> call( final TableColumn<User, Void> param)
                {
                    final TableCell<User, Void> cell = new TableCell<User, Void>()
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
                                    User u = getTableView().getItems().get(getIndex());
                                    // tableVille.getItems().clear();
                                    Boolean rep = Utils.confirmMessage("Gestion des Users","SUPPRESSION","Etes vous sur ?");
                                    if (rep)
                                    {
                                        try {
                                            System.out.println(""+u.getId());
                                            Fabrique.getiUser().deleteUser(u);
                                            Utils.showMessage("Gestion des Users","SUPPRESSION","SUCCES !!");
                                        }
                                        catch (Exception eX)
                                        {
                                            Utils.showMessage("Gestion des Users","SUPPRESSION","ECHEC !!");
                                        }

                                    }
                                    try {
                                        initialise();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
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

    Callback<TableColumn<User, Void>, TableCell<User, Void>> editUserFactory =
            new Callback<TableColumn<User, Void>, TableCell<User, Void>>()
            {
                @Override
                public TableCell<User, Void> call( final TableColumn<User, Void> param)
                {
                    final TableCell<User, Void> cell = new TableCell<User, Void>()
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
                                    User u = getTableView().getItems().get(getIndex());
                                    try {
                                        FXMLLoader fl = Utils.getLoader("MODIFICATION UTILISATEUR","Cliquez sur ok apres la modification","FormNewUser.fxml");
                                        NewUserController ctrl = (NewUserController)fl.getController();
                                        ctrl.initData(u);
                                        Utils.showAlert();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                    actualiserTab();
                                    try {
                                        initialise();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
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