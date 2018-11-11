import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        HBox[] leftHBoxes = new HBox[3];
        for (int i = 0; i < 3; i++)
            leftHBoxes[i] = new HBox(10);
        HBox[] rightHBoxes = new HBox[3];
        for (int i = 0; i < 3; i++)
            rightHBoxes[i] = new HBox(10);


        Label lbLookUp = new Label("LOOK-UP");
        TextField path = new TextField();
        path.setPrefWidth(180);
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


        RadioButton rbRedBlack = new RadioButton("red-black tree");
        RadioButton rbBTree = new RadioButton("B+ tree");
        ToggleGroup toggleGroup = new ToggleGroup();
        rbRedBlack.setToggleGroup(toggleGroup);
        rbBTree.setToggleGroup(toggleGroup);

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
        label.setLabelFor(rightHBoxes[2]);
        VBox vBox1 = new VBox(10);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setPrefWidth(350);
        vBox1.setFillWidth(false);
        vBox1.getChildren().addAll(path, leftHBoxes[0], leftHBoxes[1], leftHBoxes[2]);
        Label lbManagement = new Label("MANAGEMENT", vBox1);
        lbManagement.setContentDisplay(ContentDisplay.BOTTOM);
        lbManagement.setStyle("-fx-border-color: gray");
        VBox vBox2 = new VBox(10);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.setPrefWidth(350);
        vBox2.getChildren().addAll(rightHBoxes[0], lbLookUp, rightHBoxes[1], rightHBoxes[2], label);
        lbLookUp.setAlignment(Pos.BOTTOM_LEFT);
        lbManagement.setAlignment(Pos.CENTER);
        lbManagement.setPadding(new Insets(5));
        BorderPane pane = new BorderPane();
        pane.setLeft(lbManagement);
        pane.setRight(vBox2);
        BorderPane.setAlignment(lbManagement, Pos.CENTER);
        pane.setPadding(new Insets(5));
        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 720, 300);
        primaryStage.setTitle("English-Chinese Dictionary");
        // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

    }
}
