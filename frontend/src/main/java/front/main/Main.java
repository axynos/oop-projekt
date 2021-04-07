package front.main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Text text = new Text("Hello World");
        root.getChildren().add(new VBox(text));

        Scene scene = new Scene(root, 1200, 800);
        stage.setTitle("TaskView");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
