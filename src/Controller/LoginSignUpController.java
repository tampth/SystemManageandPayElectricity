package Controller;

import Database.Repo.LoginRepo;
import Database.Repo.SignUpRepo;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class LoginSignUpController implements Initializable {
    ObservableList<String> genderlist=FXCollections.observableArrayList("Nu","Nam");
    ObservableList<String> regionlist=FXCollections.observableArrayList("Mien Bac","Mien Nam","Mien Tay","Mien Trung");
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tabLogin;
    @FXML
    private Tab tabRegister;
    @FXML
    private Pane slidingPane;
    @FXML
    private Label lblStatus;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label LoginMessText;
    @FXML
    private TextField fldFullname;
    @FXML
    private PasswordField fldPassword;
    @FXML
    private TextField fldUsername;
    @FXML
    private TextField fldCusID;
    @FXML
    private TextField fldPhone;
    @FXML
    private TextField fldAddress;
    @FXML
    private TextField fldNationId;
    @FXML
    private ChoiceBox cbGender;
    @FXML
    private DatePicker dpBirthday;
    @FXML
    private ChoiceBox cbRegion;
    @FXML
            private Label RegisterMessTest;
    private LoginRepo loginRepo = new LoginRepo();
    private SignUpRepo signup= new SignUpRepo();

    public LoginSignUpController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //TODO
        cbGender.setItems(genderlist);
        cbRegion.setItems(regionlist);

    }

    //Login//
    @FXML
    public void LoginButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
            String userName=txtUsername.getText();
            String password=txtPassword.getText();
            boolean bool = LoginRepo.Login(userName, password);
            if(userName.isBlank()==true  || password.isBlank()==true) {
                LoginMessText.setText("Please enter username and password!");
            }
            else
            {
                //AnchorPane mainMenu = FXMLLoader.load(getClass().getResource((".fxml")));
                //AnchorPane mainMenuAdmin = FXMLLoader.load(getClass().getResource((".fxml")));
                if (bool) {

                    boolean type = loginRepo.AccountRole(userName);
                    if (type) {
                        //rootLogin.getChildren().setAll(mainMenuAdmin);
                        LoginMessText.setText("You are Admin");
                    } else {
                        //rootLogin.getChildren().setAll(mainMenu);
                        LoginMessText.setText("You are Customer");
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Error!");
                    alert.setHeaderText("Login Failed!");
                    alert.setContentText("The username or password is incorrect ");

                    alert.showAndWait();
                    System.out.println("Wrong login details");
                }
            }

    }

    //Register
    public void btnRegisterOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException{
        String id = fldCusID.getText();
        String fullname = fldFullname.getText();
        String username = fldUsername.getText();
        String pass = fldPassword.getText();
        String phone = fldPhone.getText();
        String address = fldAddress.getText();
        String nationid=fldNationId.getText();
        Object gender=cbGender.getSelectionModel().getSelectedItem();
        String rg= (String) cbRegion.getSelectionModel().getSelectedItem();
        String birth=dpBirthday.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if(signup.CheckUsername(username)) {
            if (signup.addNewCustomer(id, fullname, username, pass, phone, address, nationid, (String) gender, birth, rg)) {
                RegisterMessTest.setText("Sign up Success");
           }
            System.out.println("Insert success");
        }


    }



    //Change Tab
    public void openRegisterTab(MouseEvent mouseEvent) {
        TranslateTransition toRightAnimation= new TranslateTransition(new Duration(500), lblStatus);
        toRightAnimation.setToX(slidingPane.getTranslateX()+(slidingPane.getPrefWidth()-lblStatus.getPrefWidth()));
        toRightAnimation.play();
        toRightAnimation.setOnFinished(event -> {
            lblStatus.setText("REGISTER");
        });
        tabPane.getSelectionModel().select(tabRegister);
    }

    public void openLoginTab(MouseEvent mouseEvent) {
        TranslateTransition toLeftTransition = new TranslateTransition(new Duration(500),lblStatus);
        toLeftTransition.setToX(slidingPane.getTranslateX());
        toLeftTransition.play();
        toLeftTransition.setOnFinished((ActionEvent event2)->{
            lblStatus.setText("LOGIN");
        });
        tabPane.getSelectionModel().select(tabLogin);
    }

}
