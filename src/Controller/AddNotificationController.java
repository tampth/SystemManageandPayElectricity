package Controller;

import Model.E_Notification;
import Repo.NotificationsRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.UUID;


    public class AddNotificationController implements Initializable {
        @FXML
        private TextField editFld;
        @FXML
        private DatePicker dcreateFld;
        @FXML
        private DatePicker dsentFld;
        @FXML
        private TextField ncontentFld;
        @FXML
        private Label titleLabel;
        @FXML
        private Label idLabel;
        private final NotificationsRepo notificationsRepo = new NotificationsRepo();

        @Override
        public void initialize(URL url, ResourceBundle rb) {
            // TODO

        }

        @FXML
        private void Save(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
            if (idLabel.getText().isEmpty()) {
                notificationsRepo.addNotification(UUID.randomUUID().toString().substring(0, 4), dcreateFld.getValue(),dsentFld.getValue(), editFld.getText(), ncontentFld.getText());
            }
            else{
                notificationsRepo.updateNotification(
                        new E_Notification(
                                idLabel.getText(),
                                dcreateFld.getValue(),
                                dsentFld.getValue(),
                                editFld.getText(),
                                ncontentFld.getText()));
            }

            onNavigateBack((Node) event.getSource());

        }
        public void setNotificationId(String id) {
            if (id == null) {
                titleLabel.setText("Add New Notification");
            } else {
                setFields(notificationsRepo.getNotificationById(id));
            }
        }
        private void setFields(E_Notification notification) {
            titleLabel.setText("Update " + notification.getID());
            idLabel.setText(notification.getID());
            dcreateFld.setValue(notification.getDateCreated());
            dsentFld.setValue(notification.getDateSent());
            editFld.setText(notification.getEditBy());
            ncontentFld.setText(notification.getDetail());
        }
        private void onNavigateBack(Node node) throws IOException {
            Scene currentScene = node.getScene();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("./View/addnotification.fxml")));
            currentScene.setRoot(root);
        }

        public void Back(ActionEvent mouseEvent) throws IOException  {
            onNavigateBack((Node) mouseEvent.getSource());
        }
    }

