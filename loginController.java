

import java.util.ResourceBundle;
import java.util.Stack;

import com.mysql.cj.xdevapi.PreparableStatement;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.TextField;
import javafx.scene.Node;



public class loginController implements Initializable {
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private AnchorPane main_form;
    @FXML
    private Button loginBtn;
    @FXML
    private Button close;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void close(){
        System.exit(0);
    }

    @Override 
    public void initialize (URL url, ResourceBundle rb) {

    }
    public void loginAdmin(){
        String sql = "Select * FROM users WHERE username = ? and password = ?";

        connect = database.connectionDb();
        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, txtUsername.getText());
            prepare.setString(2, txtPassword.getText());

            result = prepare.executeQuery();

            Alert alert;

            if (txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fills all the blank fields");
                alert.showAndWait();

                
            } else {
                if (result.next()) {
                    //if correct password and username will be proceed to dashboard 
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();

                    loginBtn.getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setScene(scene);
                    stage.show();
                    
                }
                else{
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();

                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
