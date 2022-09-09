package breakout;

import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import javafx.scene.layout.Pane;

/**
 * Some game-initialization materials taken from A basic first example JavaFX program (Robert
 * Duvall).
 *
 * @author Jake Vigeant
 */
public class Main extends Application {

  // useful names for constant values used
  public static final String TITLE = "BreakOut Game";
  public static final int SIZE = 400;
  public static final int FRAMES_PER_SECOND = 70;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;


  private static Paddle gamePaddle;

  private Ball gameBall;

  private static Group root;
  private static Group brickArray;
  private static Text scoreText;
  private static Text livesText;

  private static ArrayList<Rectangle> brickAccess;
  //Game Element Colors
  public static final Paint BALL_COLOR = Color.YELLOWGREEN;
  public static final Paint BRICK_COLOR = Color.INDIANRED;
  public static final Paint PADDLE_COLOR = Color.CADETBLUE;
  public static final Paint BACKGROUND = Color.BLACK;
  //Game-wide constants
  public static final double BALL_RADIUS = 10;
  public static final int PADDLE_WIDTH = 150;
  public static final int BRICK_WIDTH = 50;
  public static final int BRICK_HEIGHT = 20;
  public static final int PADDLE_HEIGHT = 20;
  public static final int GRID_POS = 100;
  public static final int BRICK_ROWS = 5;
  private static final double PADDLE_VELOCITY = 3000;


  public static final int BlOCK_SCORE = 100;
  public static Integer score = 0;
  public static Integer lives = 3;
  public static int numBricks = 0;
  //Game-global variables that change
  public static final int INITIAL_LIVES = 3;
  public static int INITIAL_SCORE = 0;
  // things needed to remember during the game
  private Scene myScene;


  /**
   * Initialize what will be displayed and that it will be updated regularly.
   */
  @Override
  public void start(Stage stage) {
    // attach scene to the stage and display it
    myScene = setupGame(SIZE, SIZE, BACKGROUND);
    stage.setScene(myScene);
    stage.setTitle(TITLE);
    stage.show();
    // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY, root,myScene)));
    animation.play();
  }

  // Create the game's "scene": what shapes will be in the game and their starting properties
  public Scene setupGame(int width, int height, Paint background) {
    //Add ball to scene
    gameBall = new Ball(BALL_RADIUS, BALL_COLOR, new double[]{width / 2, height / 2});
    //Initialize Paddle
    gamePaddle = new Paddle(new double[]{SIZE/2-PADDLE_HEIGHT/2, SIZE-PADDLE_HEIGHT}, PADDLE_WIDTH,
        PADDLE_HEIGHT, PADDLE_VELOCITY, PADDLE_COLOR);
    // create one top level collection to organize the things in the scene
    // order added to the group is the order in which they are drawn
    root = new Group(gamePaddle.getShape());
    textInitialize(root);
    brickAccess = new ArrayList<>();
    brickArray = new Group();
    setBricks(root);
    root.getChildren().add(gameBall.getCircle());
    myScene = new Scene(root, width, height, background);
    // respond to mouse being moved

    return myScene;
  }

  // Handle game "rules" for every "moment":
  // - movement: how do shapes move over time?
  // - collisions: did shapes intersect and, if so, what should happen?
  // - goals: did the game or level end?
  // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
  private void step(double elapsedTime, Group root, Scene scene) {
    //move the ball
    gameBall.move(elapsedTime);

    myScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        switch(event.getCode()){
          case LEFT: gamePaddle.moveLeft(SECOND_DELAY); break;
          case RIGHT: gamePaddle.moveRight(SECOND_DELAY, SIZE); break;
          case L: addLife(); break;
          case R: reset(); break;
        }
      }
    });
    // check for collision with paddle
    ballPaddleIntersection(gameBall.getCircle(), gamePaddle.getShape());
    //Check for wall collisions
    ballWallIntersection(gameBall.getCircle());
    //Check for brick collisions
    for (Rectangle brick : brickAccess) {
      ballBrickIntersection(gameBall.getCircle(), brick, brickArray);
    }

  }
  //Create gameBricks
  private void setBricks(Group root) {
    for (int j = 0; j < BRICK_ROWS; j++) {
      HBox row = new HBox();
      //Separate rows
      row.setLayoutY((BRICK_HEIGHT + 1) * j);

      //Set Spacing between Bricks in a row
      row.setSpacing(1);
      for (int i = 0; i < SIZE / BRICK_WIDTH; i++) {
        Brick cBrick = new Brick(BRICK_WIDTH, BRICK_HEIGHT, BRICK_COLOR);
        row.getChildren().add(cBrick.getRect());
        brickAccess.add(cBrick.getRect());
        numBricks++;
      }
      brickArray.getChildren().add(row);
    }
    root.getChildren().add(brickArray);
  }

  private void ballPaddleIntersection(Circle ball, Rectangle paddle) {
    Shape intersection = Shape.intersect(ball, paddle);
    if (intersection.getBoundsInLocal().getWidth() != -1) {
      gameBall.reverse(1);
      //Depending on position ball hits paddle, set velocity
      //left side of paddle, always redirects to the left
      gameBall.paddleCollison(ball.getCenterX() >= (paddle.getX() + PADDLE_WIDTH / 2));
    }
  }

  private void ballWallIntersection(Circle ball) {
    if (ball.getCenterX() >= SIZE || ball.getCenterX() <= 0) {
      gameBall.reverse(0);
    }
    if (ball.getCenterY() <= 0) {
      gameBall.reverse(1);
    }
    if (ball.getCenterY() >= SIZE) {
      gameBall.reset(new double[]{SIZE / 2, SIZE / 2});
      loseLife();
    }
  }

  private void ballBrickIntersection(Circle ball, Rectangle brick, Group bricks) {
    Shape intersection = Shape.intersect(ball, brick);
    if (intersection.getBoundsInLocal().getWidth() != -1) {
      brickAccess.remove(brick);
      brick.setFill(Color.BLACK);
      if (brickAccess.isEmpty()) {
        winGame();
      }
      gameBall.reverse(1);
      updateScore();
    }

  }

  //Initializes score and lives text
  private void textInitialize(Group root) {
    scoreText = new Text("Score:" + score.toString());
    livesText = new Text("Lives:" + lives.toString());
    scoreText.setFont(new Font("ARIAL", 30));
    scoreText.setStyle("-fx-font-weight: bold;");
    scoreText.relocate(0, GRID_POS);
    scoreText.setVisible(true);
    scoreText.setFill(Color.WHITE);
    livesText.setFont(new Font("ARIAL", 30));
    livesText.setStyle("-fx-font-weight: bold;");
    livesText.relocate(0, GRID_POS + 30);
    livesText.setVisible(true);
    livesText.setFill(Color.WHITE);
    root.getChildren().addAll(scoreText, livesText);
  }

  //lose a life
  private void loseLife() {
    if (lives == 0) {
      endGame();
      return;
    }
    lives--;
    livesText.setText("Lives:" + (lives.toString()));
  }
  private void addLife(){
    lives++;
    livesText.setText("Lives:" + (lives.toString()));
  }
  private void reset(){
    gameBall.reset(new double[]{SIZE/2,SIZE/2});
    gamePaddle.reset(new double[]{SIZE/2-PADDLE_HEIGHT/2, SIZE-PADDLE_HEIGHT});
  }

  private void updateScore() {
    score += BlOCK_SCORE;
    scoreText.setText("Score:" + score);
    if (score == numBricks * BlOCK_SCORE) {
      winGame();
    }
  }

  private void winGame() {
    gameBall.stopBall();
    livesText.setText("You Win!");
  }

  //End game
  private void endGame() {
    gameBall.stopBall();
    livesText.setText("Game Over");
  }

  /**
   * Start the program.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
