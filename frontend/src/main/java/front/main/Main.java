package front.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Layout layout = new Layout();
        layout.run();

        Scene scene = new Scene(layout, 1280, 720);
        stage.setTitle("TaskView");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
