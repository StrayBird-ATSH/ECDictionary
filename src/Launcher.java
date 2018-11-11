import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Image[] images = new Image[1];
        images[0] = new Image("./IMG_0899.jpg");
        Button btGenerateC = new Button("Generate testing C Source files");
        Button buttonSo = new Button("Generate Dynamic Link Library files");
        Button buttonData = new Button("Test performance data");
        btGenerateC.setOnAction(e -> {
            final Runtime runtime = Runtime.getRuntime();
            Process process1 = null;
            try {
                process1 = runtime.exec("./generateC");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        buttonSo.setOnAction(e -> {
            final Runtime runtime = Runtime.getRuntime();
            Process process2 = null;
            try {
                process2 = runtime.exec("./generateSo");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        buttonData.setOnAction(e -> {
            final Runtime runtime = Runtime.getRuntime();
            Process process3 = null;
            try {
                process3 = runtime.exec("./linkRun");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        HBox[] leftHBoxes = new HBox[3];
        for (HBox hBox : leftHBoxes) {
            hBox = new HBox(10);
        }
        TextField path = new TextField();
        Button buttonBrowse = new Button("Browse");
        Button buttonSubmit = new Button("Submit");
        Button buttonAdd = new Button("Add");
        Button buttonDelete = new Button("Delete");

        leftHBoxes[0].getChildren().addAll(buttonBrowse, buttonSubmit);
        TextField addEnglish = new TextField();
        TextField addChinese = new TextField();
        leftHBoxes[1].getChildren().addAll(new Label("English:"), addEnglish, new Label("Chinese:"), addChinese);
        leftHBoxes[2].getChildren().addAll(buttonAdd, buttonDelete);


        RadioButton rbRedBlack = new RadioButton("red-black tree");
        RadioButton rbBTree = new RadioButton("B+ tree");
        ToggleGroup toggleGroup = new ToggleGroup();
        rbBTree.setToggleGroup(toggleGroup);
        rbBTree.setToggleGroup(toggleGroup);



        VBox vBox1 = new VBox(10);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.getChildren().addAll(new Label("MANAGEMENT"), path, leftHBoxes[0], leftHBoxes[1], leftHBoxes[2]);
        VBox vBox2 = new VBox(10);
        vBox2.setAlignment(Pos.CENTER);
        BorderPane pane = new BorderPane();
        pane.setLeft(vBox1);
        pane.setRight(vBox2);
        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 550, 430);
        primaryStage.setTitle("English-Chinese Dictionary");
        // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

    }
}
