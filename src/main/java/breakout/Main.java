package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.GridPane;


/**
 * Some game-initialization materials taken from A basic first example JavaFX program (Robert Duvall).
 *
 * @author Jake Vigeant
 */
public class Main extends Application {
    // useful names for constant values used
    public static final String TITLE = "BreakOut Game";
    public static final int SIZE = 400;
    public static final int FRAMES_PER_SECOND = 70;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private static Rectangle paddle;
    //Game Element Colors
    public static final Paint BALL_COLOR = Color.WHITE;
    public static final Paint BRICK_COLOR = Color.WHITE;
    public static final Paint PADDLE_COLOR = Color.WHITE;
    public static final Paint BACKGROUND = Color.BLACK;
    //Game-wide constants
    public static final int BLOCK_SIZE = 10;
    public static final int PADDLE_WIDTH = 10;
    public static final int PADDLE_HEIGHT = 10;
    public static final double BALL_RADIUS = 10;
    public static final int PADDLE_SPEED = 10;
    public static final int BALL_SPEED = 10;
    public static final int BLOCK_SPEED = 10;
    //Game-global variables that change
    public static final int INITIAL_LIVES = 3;
    public static int INITIAL_SCORE = 0;
    // things needed to remember during the game
    private Scene myScene;


    /**
     * Initialize what will be displayed and that it will be updated regularly.
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        myScene = setupGame(SIZE, SIZE, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY)));
        animation.play();
    }

    // Create the game's "scene": what shapes will be in the game and their starting properties
    public Scene setupGame (int width, int height, Paint background) {
        // Construct a gridPane of blocks
        GridPane gridPane = new GridPane();
        setBricks(gridPane);
        //TODO: Call Method to loop through and add blocks to the game's dimensions

        //Add ball to scene
        Circle ball = new Circle(BALL_RADIUS, BALL_COLOR);
        //Initialize ball position
        ball.setCenterX(width/2);
        ball.setCenterY(height/2);
        //Initialize Paddle
        paddle = new Rectangle(width / 2 - PADDLE_WIDTH / 2, height / 2 - 100, PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle.setFill(PADDLE_COLOR);
        //Initialize ScoreBoard
        ScoreBoard scoreBoard = new ScoreBoard(INITIAL_LIVES,INITIAL_SCORE);
        // create one top level collection to organize the things in the scene
        // order added to the group is the order in which they are drawn
        Group root = new Group(gridPane, ball, paddle, scoreBoard);
        // could also add them dynamically later
        //root.getChildren().add(myMover);
        //root.getChildren().add(myGrower);
        // create a place to see the shapes
        myScene = new Scene(root, width, height, background);
        // respond to input
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return myScene;
    }

    // Handle game "rules" for every "moment":
    // - movement: how do shapes move over time?
    // - collisions: did shapes intersect and, if so, what should happen?
    // - goals: did the game or level end?
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
    private void step (double elapsedTime) {
        // update "actors" attributes
        myMover.setRotate(myMover.getRotate() - ROTATION_RATE * elapsedTime);
        myGrower.setRotate(myGrower.getRotate() + ROTATION_RATE / 2 * elapsedTime);

        // check for collisions and choose response
        Shape intersection = Shape.intersect(myMover, myGrower);
        // with shapes, can check precisely based on their geometry
        if (intersection.getBoundsInLocal().getWidth() != -1) {
            myMover.setFill(HIGHLIGHT);
        }
        else {
            myMover.setFill(MOVER_COLOR);
        }
        // with images can only check bounding box (as it is calculated in container with other objects)
        if (isIntersecting(myBouncer, myGrower)) {
            myGrower.setFill(HIGHLIGHT);
        }
        else {
            myGrower.setFill(GROWER_COLOR);
        }
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        // NOTE new Java syntax that some prefer (but watch out for the many special cases!)
        //   https://blog.jetbrains.com/idea/2019/02/java-12-and-intellij-idea/
        switch (code) {
            case RIGHT -> myMover.setX(myMover.getX() + MOVER_SPEED);
            case LEFT -> myMover.setX(myMover.getX() - MOVER_SPEED);
            case UP -> myMover.setY(myMover.getY() - MOVER_SPEED);
            case DOWN -> myMover.setY(myMover.getY() + MOVER_SPEED);
        }
        // TYPICAL way to do it, definitely more readable for longer actions
//        if (code == KeyCode.RIGHT) {
//            myMover.setX(myMover.getX() + MOVER_SPEED);
//        }
//        else if (code == KeyCode.LEFT) {
//            myMover.setX(myMover.getX() - MOVER_SPEED);
//        }
//        else if (code == KeyCode.UP) {
//            myMover.setY(myMover.getY() - MOVER_SPEED);
//        }
//        else if (code == KeyCode.DOWN) {
//            myMover.setY(myMover.getY() + MOVER_SPEED);
//        }
    }

    // What to do each time a mouse button is clicked
    private void handleMouseInput (double x, double y) {
        if (myGrower.contains(x, y)) {
            myGrower.setScaleX(myGrower.getScaleX() * GROWER_RATE);
            myGrower.setScaleY(myGrower.getScaleY() * GROWER_RATE);
        }
    }
    //Create gameBricks and position them along gridPane
    private void setBricks(GridPane myGridPane){

    }
    // Name for a potentially complex comparison to make code more readable
    private boolean isIntersecting (ImageView a, Rectangle b) {
        // with images can only check bounding box (as it is calculated in container with other objects)
//        return b.getBoundsInParent().intersects(a.getBoundsInParent());
        // with shapes, can check precisely (in this case, it is easy because the image is circular)
        Shape bouncerBounds = new Circle(a.getX() + a.getFitWidth() / 2,
                a.getY() + a.getFitHeight() / 2,
                a.getFitWidth() / 2 - a.getFitWidth() / 20);
        return ! Shape.intersect(bouncerBounds, b).getBoundsInLocal().isEmpty();
    }
    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
