package Controller;

import Repo.NotificationsRepo;
import Model.E_Notification;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class NotificationController implements Initializable {

    private NotificationsRepo notificationsRepo = new NotificationsRepo();
    private final FXMLLoader addNotificationLoader = new FXMLLoader(getClass().getResource("View/addnotification.fxml"));

    @FXML
    private TableView<E_Notification> notiTable;
    @FXML
    private TableColumn<E_Notification,String> idCol;
    @FXML
    private TableColumn<E_Notification, String>  DCreateCol;
    @FXML
    private TableColumn<E_Notification, String> DSentCol;
    @FXML
    private TableColumn<E_Notification, String> EditByCol;
    @FXML
    private TableColumn<E_Notification, String> DetailCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        DCreateCol.setCellValueFactory(new PropertyValueFactory<>("DateCreated"));
        DSentCol.setCellValueFactory(new PropertyValueFactory<>("DateSent"));
        EditByCol.setCellValueFactory(new PropertyValueFactory<>("EditBy"));
        DetailCol.setCellValueFactory(new PropertyValueFactory<>("Detail"));

        notiTable.setRowFactory(providerTableView -> {
            TableRow<E_Notification> row = new TableRow<>();

            MenuItem editMenuItem = new MenuItem("Modify");
            editMenuItem.setOnAction(event -> {

                try {
                    Parent root = addNotificationLoader.load();

                    AddNotificationController controller = addNotificationLoader.getController();
                    controller.setNotificationId(row.getItem().getID());

                    Scene currentScene = notiTable.getScene();
                    currentScene.setRoot(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            MenuItem deleteMenuItem = new MenuItem("Delete");
            deleteMenuItem.setOnAction(event -> {
                try {
                    notificationsRepo.deleteNotification(row.getItem().getID());
                } catch (SQLException | ClassNotFoundException throwable) {
                    throwable.printStackTrace();
                }
            });

            ContextMenu secondaryContextMenu = new ContextMenu();
            secondaryContextMenu.getItems().addAll(editMenuItem, deleteMenuItem);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(secondaryContextMenu));
            return row;
        });

        notiTable.setItems(notificationsRepo.getNotification());

    }
    @FXML
    private void btnAdd(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./View/addnotification.fxml"));
        Parent root = loader.load();

        AddNotificationController notification = loader.getController();
        notification.setNotificationId(null);

        Scene currentScene = ((Node) event.getSource()).getScene();
        currentScene.setRoot(root);
    }

}

