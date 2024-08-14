

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
 


public class login extends Application {
   


    @Override
    public void start(Stage primaryStage) throws IOException {
       try{
           
       
          Parent parentRoot = FXMLLoader.load(getClass().getResource("login.fxml"));
           
           primaryStage.initStyle(StageStyle.TRANSPARENT);
           Scene scene = new Scene(parentRoot);

           primaryStage.initStyle(StageStyle.TRANSPARENT);
           primaryStage.setScene(scene);
           primaryStage.show();
           
       }catch(Exception ex){
           System.err.println(ex.getMessage());
           ex.printStackTrace();
           System.exit(0);
           
       }
       
    }
    
    public static void main(String[]args){
        launch(args);
    }
    
}

