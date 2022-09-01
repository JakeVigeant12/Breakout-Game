package breakout;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author YOUR NAME HERE
 */
public class Main extends Application {
    // useful names for constant values used
    public static final String TITLE = "Example JavaFX Animation";
    public static final int SIZE = 400;
    // many resources may be in the same shared folder
    // note, leading slash means automatically start in "src/main/resources" folder
    // note, Java always uses forward slash, "/", (even for Windows)
    public static final String RESOURCE_PATH = "/breakout/";
    public static final String BALL_IMAGE = RESOURCE_PATH + "ball.gif";


    /**
     * Initialize what will be displayed.
     */
    @Override
    public void start (Stage stage) {
        ImageView ball = new ImageView(new Image(BALL_IMAGE));
        ball.setFitWidth(SIZE);
        ball.setFitHeight(SIZE);
        ball.setX(SIZE / 2 - ball.getBoundsInLocal().getWidth() / 2);
        ball.setY(SIZE / 2 - ball.getBoundsInLocal().getHeight() / 2);

        Group root = new Group(ball);
        Scene scene = new Scene(root, SIZE, SIZE, Color.DARKBLUE);
        stage.setScene(scene);

        stage.setTitle(TITLE);
        stage.show();
    }


    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
