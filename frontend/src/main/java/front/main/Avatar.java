package front.main;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Avatar extends StackPane implements Runnable {
    private double size;
    private String text;

    public Avatar(double size, String text) {
        this.size = size;
        this.text = text;
    }

    @Override
    public void run() {
        final Circle circle = new Circle(size, Color.CYAN);
        final Text textNode = new Text(text);
        getChildren().addAll(circle, textNode);
    }

}
