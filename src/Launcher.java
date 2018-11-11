import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        RedBlackTree redBlackTree = new RedBlackTree();
        BPlusTree bPlusTree = new BPlusTree(6);
        HBox[] leftHBoxes = new HBox[3];
        for (int i = 0; i < 3; i++)
            leftHBoxes[i] = new HBox(10);
        HBox[] rightHBoxes = new HBox[3];
        for (int i = 0; i < 3; i++)
            rightHBoxes[i] = new HBox(10);

        TextField path = new TextField();
        path.setPrefWidth(180);
        path.setEditable(false);
        Button buttonBrowse = new Button("Browse");
        Button buttonSubmit = new Button("Submit");
        Button buttonAdd = new Button("Add");
        Button buttonDelete = new Button("Delete");

        leftHBoxes[0].getChildren().addAll(buttonBrowse, buttonSubmit);
        TextField addEnglish = new TextField();
        TextField addChinese = new TextField();
        addChinese.setPrefWidth(100);
        addEnglish.setPrefWidth(100);
        leftHBoxes[1].getChildren().addAll(new Label("English:"), addEnglish, new Label("Chinese:"), addChinese);
        leftHBoxes[2].getChildren().addAll(buttonAdd, buttonDelete);


        RadioButton rbRedBlack = new RadioButton("Red-black tree");
        RadioButton rbBTree = new RadioButton("B+ tree");
        ToggleGroup toggleGroup = new ToggleGroup();
        rbRedBlack.setToggleGroup(toggleGroup);
        rbBTree.setToggleGroup(toggleGroup);
        rbRedBlack.setSelected(true);
        rbRedBlack.setFont(Font.font("Lucida console", FontWeight.BOLD, 12));
        rbBTree.setFont(Font.font("Lucida console", FontWeight.BOLD, 12));

        TextField tfLookUp = new TextField();
        tfLookUp.setPrefWidth(200);
        Button button = new Button("Translate");
        TextField tfSearch1 = new TextField();
        TextField tfSearch2 = new TextField();
        tfSearch1.setPrefWidth(80);
        tfSearch2.setPrefWidth(80);
        Button btSubmit = new Button("Submit");

        rightHBoxes[0].getChildren().addAll(rbRedBlack, rbBTree);
        rightHBoxes[1].getChildren().addAll(tfLookUp, button);
        rightHBoxes[2].getChildren().addAll(new Label("Search from"), tfSearch1,
                new Label("to"), tfSearch2, btSubmit);
        Label label = new Label("Here  show the result");
        label.setFont(Font.font("Calibri", FontWeight.BOLD, 12));
        label.setTextFill(Paint.valueOf("RED"));
        label.setLabelFor(rightHBoxes[2]);
        Label result = new Label("\n");

        VBox vBox1 = new VBox(10);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setPrefWidth(350);
        vBox1.setFillWidth(false);
        vBox1.getChildren().addAll(path, leftHBoxes[0], leftHBoxes[1], leftHBoxes[2]);

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(rightHBoxes[1], rightHBoxes[2], label, result);
        Label lbLookUp = new Label("LOOK-UP", vBox);
        lbLookUp.setContentDisplay(ContentDisplay.BOTTOM);
        lbLookUp.setStyle("-fx-border-color: gray");
        lbLookUp.setAlignment(Pos.CENTER);
        lbLookUp.setPadding(new Insets(5));

        Label lbManagement = new Label("MANAGEMENT", vBox1);
        lbManagement.setContentDisplay(ContentDisplay.BOTTOM);
        lbManagement.setStyle("-fx-border-color: gray");
        lbManagement.setAlignment(Pos.CENTER);
        lbManagement.setPadding(new Insets(5));
        lbManagement.setFont(Font.font("Verdana", FontWeight.BLACK, 14));

        VBox vBox2 = new VBox(10);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.setPrefWidth(350);
        vBox2.getChildren().addAll(rightHBoxes[0], lbLookUp);
        lbLookUp.setAlignment(Pos.CENTER_LEFT);
        lbLookUp.setFont(Font.font("Verdana", FontWeight.BLACK, 14));

        BorderPane pane = new BorderPane();
        pane.setLeft(lbManagement);
        pane.setRight(vBox2);
        BorderPane.setAlignment(lbManagement, Pos.CENTER);
        pane.setPadding(new Insets(5));


        buttonBrowse.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Please select the importing data files:");
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(
                    "Plain text data file", "txt"));
            File defaultPath = new File("D:\\Documents\\Data Structures" +
                    " and Algorithms\\Project\\sample files");
            if (defaultPath.exists())
                fileChooser.setInitialDirectory(defaultPath);
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            path.setText(selectedFile == null ? null : selectedFile.getAbsolutePath());
        });

        buttonSubmit.setOnAction(event -> {
            File file = new File(path.getText());
            if (file.canRead())
                if (rbRedBlack.isSelected())
                    redBlackTree.importData(file);
                else bPlusTree.importData(file);
        });

        button.setOnAction(event -> {
            String key = tfLookUp.getText();
            String resultString;
            if (rbRedBlack.isSelected())
                resultString = redBlackTree.get(key);
            else resultString = bPlusTree.get(key);
            bPlusTree.preOrderPrint(0, bPlusTree.root);
            result.setText(resultString);
        });

        buttonAdd.setOnAction(event -> {
            if (rbRedBlack.isSelected())
                redBlackTree.put(addEnglish.getText(), addChinese.getText());
            else bPlusTree.put(addEnglish.getText(), addChinese.getText());
        });

        buttonDelete.setOnAction(event -> {
            if (rbRedBlack.isSelected())
                redBlackTree.remove(addEnglish.getText());
            else bPlusTree.remove(addEnglish.getText());
        });

        btSubmit.setOnAction(event -> {
            if (rbRedBlack.isSelected())
                redBlackTree.searchScope(tfSearch1.getText(), tfSearch2.getText(), redBlackTree.getRoot());
            else bPlusTree.searchScope(tfSearch1.getText(), tfSearch2.getText());
        });

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 720, 200);
        primaryStage.setTitle("English-Chinese Dictionary");
        // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }
}