//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {


    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(this.getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Music Player ");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.fullScreenExitHintProperty();
//        primaryStage.close();
    }

    @Override
    public void stop() {
       System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

