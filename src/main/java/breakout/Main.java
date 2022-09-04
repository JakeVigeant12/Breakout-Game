package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    private static Circle ball;
    //Game Element Colors
    public static final Paint BALL_COLOR = Color.WHITE;
    public static final Paint BRICK_COLOR = Color.WHITE;
    public static final Paint PADDLE_COLOR = Color.WHITE;
    public static final Paint BACKGROUND = Color.BLACK;
    //Game-wide constants
    public static final int BLOCK_SIZE = 10;
    public static final int PADDLE_WIDTH = 150;
    public static final int BRICK_WIDTH = 50;
    public static final int BRICK_HEIGHT = 20;
    public static final int PADDLE_HEIGHT = 20;
    public static final int GRID_POS = 100;
    public static final double BALL_RADIUS = 10;
    public static final int PADDLE_SPEED = 10;
    public static int ballSpeedX = 50;
    public static int ballSpeedY = 200;
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
        //TODO: Call Method to loop through and add blocks to the game's dimensions

        //Add ball to scene
        ball = new Circle(BALL_RADIUS, BALL_COLOR);
        //Initialize ball position
        ball.setCenterX(width/2);
        ball.setCenterY(height/2);
        //Initialize Paddle
        paddle = new Rectangle(SIZE-PADDLE_WIDTH/2,SIZE-PADDLE_HEIGHT/2,PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle.setFill(PADDLE_COLOR);
        Text test = new Text();
        test.setFont(new Font("ARIAL", 30));
        test.setStyle("-fx-font-weight: bold;");
        test.relocate(100, 100);
        test.setVisible(true);
        test.setFill(Color.WHITE);
        //Initialize ScoreBoard
        ScoreBoard scoreBoard = new ScoreBoard(INITIAL_LIVES,INITIAL_SCORE, SIZE, SIZE);
        // create one top level collection to organize the things in the scene
        // order added to the group is the order in which they are drawn
        scoreBoard.setContainer(GRID_POS,PADDLE_HEIGHT, SIZE);
        Group root = new Group(ball, paddle,test);
        setBricks(root);
        myScene = new Scene(root, width, height, background);
        // respond to mouse being moved
        myScene.setOnMouseMoved(e -> handleMouseMoved(e.getX()));
        return myScene;
    }

    // Handle game "rules" for every "moment":
    // - movement: how do shapes move over time?
    // - collisions: did shapes intersect and, if so, what should happen?
    // - goals: did the game or level end?
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
    private void step (double elapsedTime) {
        //move the ball and check if its hit a brick
        ball.setCenterX(elapsedTime * ballSpeedX +  ball.getCenterX());
        ball.setCenterY(elapsedTime * ballSpeedY +  ball.getCenterY());

        // check for collision with paddle
       Shape intersection = Shape.intersect(ball, paddle);
        if (intersection.getBoundsInLocal().getWidth() != -1) {
            ballSpeedY = -1 * ballSpeedY;
            ballSpeedX = -1 * ballSpeedX;
        }
        //Check for wall collisions
        if(ball.getCenterX() >= SIZE || ball.getCenterX() <= 0){
            ballSpeedX = -1 * ballSpeedX;
        }
        if(ball.getCenterY() <= 0){
            ballSpeedY = -1 * ballSpeedY;
        }
        if(ball.getCenterY() >= SIZE){
            //Need to lose a life
            //TODO Call Life Lose Method
            ball.setCenterX(SIZE/2);
            ball.setCenterY(SIZE/2);
        }
    }
    //Move paddle to current mouse location
    private void handleMouseMoved(double x){
        paddle.setX(x);
    }
    //Create gameBricks and position them along gridPane
    private void setBricks(Group root){
        for(int j = 0; j < 5; j++) {
            HBox row = new HBox();
            //Separate rows
            row.setLayoutY((BRICK_HEIGHT+1)*j);

            //Set Spacing between Bricks in a row
            row.setSpacing(1);
            for (int i = 0; i < SIZE/BRICK_WIDTH; i++) {
                Rectangle cBrick = new Rectangle(BRICK_WIDTH, BRICK_HEIGHT, BRICK_COLOR);
                row.getChildren().add(cBrick);
            }
            root.getChildren().add(row);
        }
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
