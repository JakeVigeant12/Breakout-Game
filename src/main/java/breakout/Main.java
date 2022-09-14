package breakout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.*;
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
  private static final int PERCENT_OF_BRICKS_WITH_POWERUP = 10;


  private static Paddle gamePaddle;

  private static Group root;
  private static Group brickArray;
  private static Text scoreText;
  private static Text livesText;
  private static Text topScore;
  private static Text levelText;

  private static ArrayList<Integer> highScores;
  private static ArrayList<Brick> brickAccess;
  private static ArrayList<PowerUp> activePowerUps;
  private static ArrayList<Ball> activeBalls;
  //Game Element Colors
  public static final Paint BALL_COLOR = Color.YELLOWGREEN;
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
  public static int currLevel = 1;
  //Game-global variables that change
  public static final int INITIAL_LIVES = 3;
  public static int INITIAL_SCORE = 0;
  // things needed to remember during the game
  private Scene myScene;


  /**
   * Initialize what will be displayed and that it will be updated regularly.
   */
  @Override
  public void start(Stage stage) throws FileNotFoundException {
    // attach scene to the stage and display it
    myScene = setupGame(SIZE, SIZE, BACKGROUND);
    activePowerUps = new ArrayList<>();
    stage.setScene(myScene);
    stage.setTitle(TITLE);
    stage.show();
    // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames()
        .add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> {
          try {
            step(SECOND_DELAY, root, myScene);
          } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
          }
        }));
    animation.play();
  }

  // Create the game's "scene": what shapes will be in the game and their starting properties
  public Scene setupGame(int width, int height, Paint background) throws FileNotFoundException {
    initializeScores();
    activeBalls = new ArrayList<>();
    //Add ball to scene
    Ball firstBall = new Ball(BALL_RADIUS, BALL_COLOR, new double[]{width / 2, height / 2});
    activeBalls.add(firstBall);
    //Initialize Paddle
    gamePaddle = new Paddle(new double[]{SIZE / 2 - PADDLE_HEIGHT / 2, SIZE - PADDLE_HEIGHT},
        PADDLE_WIDTH,
        PADDLE_HEIGHT, PADDLE_VELOCITY, PADDLE_COLOR);
    // create one top level collection to organize the things in the scene
    // order added to the group is the order in which they are drawn
    root = new Group(gamePaddle.getShape());
    textInitialize(root);
    brickAccess = new ArrayList<>();
    brickArray = new Group();
    setBricks(root, currLevel);
    root.getChildren().add(firstBall.getCircle());
    myScene = new Scene(root, width, height, background);
    return myScene;
  }

  private void initializeScores() throws FileNotFoundException {
    String path = "src/main/resources/Scores/highScores.txt";
    Scanner scanner = new Scanner(new File(path));
    String scoreLine = scanner.nextLine();
    String[] scores = scoreLine.split(" ");
    highScores = new ArrayList<>();
    for (String score : scores) {
      highScores.add(Integer.parseInt(score));
    }

  }

  // Handle game "rules" for every "moment":
  // - movement: how do shapes move over time?
  // - collisions: did shapes intersect and, if so, what should happen?
  // - goals: did the game or level end?
  // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
  private void step(double elapsedTime, Group root, Scene scene) throws FileNotFoundException {
    //move the ball
    for(Ball currBall : activeBalls){
      currBall.move(elapsedTime);
    }
    if (!activePowerUps.isEmpty()) {
      for(PowerUp currentPowerUp : activePowerUps) {
        currentPowerUp.move(elapsedTime);
      }
    }

    myScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        switch (event.getCode()) {
          case LEFT:
            gamePaddle.moveLeft(SECOND_DELAY);
            break;
          case RIGHT:
            gamePaddle.moveRight(SECOND_DELAY, SIZE);
            break;
          case L:
            addLife();
            break;
          case R:
            reset();
            break;
          case DIGIT1:
            currLevel = 1;
            try {
              clearLevel();
            } catch (FileNotFoundException e) {
              throw new RuntimeException(e);
            }
            break;
          case DIGIT2:
            currLevel = 2;
            try {
              clearLevel();
            } catch (FileNotFoundException e) {
              throw new RuntimeException(e);
            }
            break;
          case DIGIT3:
            currLevel = 3;
            try {
              clearLevel();
            } catch (FileNotFoundException e) {
              throw new RuntimeException(e);
            }
            break;
          case DIGIT4:
            currLevel = 3;
            try {
              clearLevel();
            } catch (FileNotFoundException e) {
              throw new RuntimeException(e);
            }
            break;
          case DIGIT5:
            currLevel = 3;
            try {
              clearLevel();
            } catch (FileNotFoundException e) {
              throw new RuntimeException(e);
            }
            break;
          case DIGIT6:
            currLevel = 3;
            try {
              clearLevel();
            } catch (FileNotFoundException e) {
              throw new RuntimeException(e);
            }
            break;
          case DIGIT7:
            currLevel = 3;
            try {
              clearLevel();
            } catch (FileNotFoundException e) {
              throw new RuntimeException(e);
            }
            break;
          case DIGIT8:
            currLevel = 3;
            try {
              clearLevel();
            } catch (FileNotFoundException e) {
              throw new RuntimeException(e);
            }
            break;
          case DIGIT9:
            currLevel = 3;
            try {
              clearLevel();
            } catch (FileNotFoundException e) {
              throw new RuntimeException(e);
            }
            break;
        }
      }
    });
    // check for collision with paddle and wall on each ball
    for(Ball currBall : activeBalls){
      ballPaddleIntersection(currBall, gamePaddle.getShape());
      ballWallIntersection(currBall, root);
    }
    if (!activePowerUps.isEmpty()) {
      for(PowerUp currentPowerUp : activePowerUps) {
        paddlePowerUpIntersection(currentPowerUp, gamePaddle.getShape(), root);
      }
    }
    //Check for brick collisions
    for (Brick brick : brickAccess) {
      for(Ball currBall : activeBalls) {
        ballBrickIntersection(currBall, brick, brickArray, root);
      }
    }

  }

  //Create gameBricks
  private void setBricks(Group root, int displayLevel) throws FileNotFoundException {
    int currLevel = 1;
    String basePath = "src/main/resources/Levels/";
    String path = basePath + "level" + displayLevel + ".txt";
    Scanner scanner = new Scanner(new File(path));
    int lineCount = 0;
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      lineCount++;
      String[] bricks = line.split(" ");
      HBox row = new HBox();
      row.setLayoutY((BRICK_HEIGHT + 1) * lineCount);
      row.setSpacing(1);
      for (String num : bricks) {
        Brick cBrick;
        if (new Random().nextInt(100 - 1 + 1) + 1 <= PERCENT_OF_BRICKS_WITH_POWERUP) {
          cBrick = new Brick(BRICK_WIDTH, BRICK_HEIGHT, Integer.parseInt(num), true);
        } else {
          cBrick = new Brick(BRICK_WIDTH, BRICK_HEIGHT, Integer.parseInt(num), false);
        }
        row.getChildren().add(cBrick.getRect());
        brickAccess.add(cBrick);
      }
      brickArray.getChildren().add(row);
    }
    root.getChildren().add(brickArray);
  }

  private void paddlePowerUpIntersection(PowerUp powerUp, Rectangle paddle, Group root)
      throws FileNotFoundException {
    Shape intersection = Shape.intersect(powerUp.getShape(), paddle);
    if (intersection.getBoundsInLocal().getWidth() != -1) {
      switch (powerUp.effect) {
        case 1:
          lengthenPaddle();
          break;
        case 2:
          currLevel++;
          clearLevel();
          break;
        case 3:
          addBall(root);
          break;
      }
      root.getChildren().remove(powerUp.getShape());
      activePowerUps.remove(powerUp);
    }
  }

  private void addBall(Group root) {
    Ball spawnedBall = new Ball(BALL_RADIUS,BALL_COLOR,new double[]{SIZE/2,SIZE/2});
    activeBalls.add(spawnedBall);
    root.getChildren().add(spawnedBall.getCircle());
  }

  private void lengthenPaddle() {
    double currentPaddleWidth = gamePaddle.getShape().getWidth();
  if(currentPaddleWidth > PADDLE_WIDTH){
    gamePaddle.getShape().setWidth(gamePaddle.getShape().getWidth() * 0.95);
    return;
  }
    gamePaddle.getShape().setWidth(gamePaddle.getShape().getWidth() * 1.05);
  }

  private void ballPaddleIntersection(Ball ball, Rectangle paddle) {
    Shape intersection = Shape.intersect(ball.getCircle(), paddle);
    if (intersection.getBoundsInLocal().getWidth() != -1) {
      ball.reverse(1);
      //Depending on position ball hits paddle, set velocity
      //left side of paddle, always redirects to the left
      ball.paddleCollison(ball.getCircle().getCenterX() >= (paddle.getX() + PADDLE_WIDTH / 2));
    }
  }

  private void ballWallIntersection(Ball ball, Group root) throws FileNotFoundException {
    if (ball.getCircle().getCenterX() >= SIZE || ball.getCircle().getCenterX() <= 0) {
      ball.reverse(0);
    }
    if (ball.getCircle().getCenterY() <= 0) {
      ball.reverse(1);
    }
    if (ball.getCircle().getCenterY() >= SIZE) {
      root.getChildren().remove(ball.getCircle());
      activeBalls.remove(ball);
      loseLife(root);
    }
  }

  private void ballBrickIntersection(Ball ball, Brick brick, Group bricks, Group root)
      throws FileNotFoundException {
    Shape intersection = Shape.intersect(ball.getCircle(), brick.getRect());
    if (intersection.getBoundsInLocal().getWidth() != -1) {
      boolean isDead = brick.subLife();
      if (isDead) {
        if (brick.holdsPowerUp) {
          PowerUp currentPowerUp = new PowerUp(new double[]{brick.getRect().getX(), brick.getRect().getY()});
          root.getChildren().add(currentPowerUp.getShape());
          activePowerUps.add(currentPowerUp);
        }
        brickAccess.remove(brick);
      }
      if (brickAccess.isEmpty()) {
        currLevel++;
        clearLevel();
      }
      ball.reverse(1);
      updateScore();
    }

  }

  //Initializes score and lives text
  private void textInitialize(Group root) {
    topScore = new Text("Top Score:" + highScores.get(9).toString());
    levelText = new Text("Level: " + currLevel);
    scoreText = new Text("Score:" + score.toString());
    livesText = new Text("Lives:" + lives.toString());
    scoreText.setFont(new Font("ARIAL", 30));
    scoreText.setStyle("-fx-font-weight: bold;");
    scoreText.relocate(0, GRID_POS);
    scoreText.setVisible(true);
    scoreText.setFill(Color.WHITE);
    livesText.setFont(new Font("ARIAL", 30));
    livesText.setStyle("-fx-font-weight: bold;");
    livesText.relocate(0, GRID_POS + 55);
    livesText.setVisible(true);
    livesText.setFill(Color.WHITE);
    topScore.setFill(Color.WHITE);
    topScore.setFont(new Font("ARIAL", 30));
    topScore.setStyle("-fx-font-weight: bold;");
    topScore.relocate(0, GRID_POS + 25);
    topScore.setVisible(true);
    levelText.setFill(Color.WHITE);
    levelText.setFont(new Font("ARIAL", 30));
    levelText.setStyle("-fx-font-weight: bold;");
    levelText.relocate(0, GRID_POS + 85);
    levelText.setVisible(true);
    root.getChildren().addAll(scoreText, livesText, topScore, levelText);
  }

  //lose a life
  private void loseLife(Group root) throws FileNotFoundException {
    if (lives == 0) {
      endGame();
      return;
    }
    if(activeBalls.isEmpty()) {
      lives--;
      livesText.setText("Lives:" + (lives.toString()));
      Ball replacement = new Ball(BALL_RADIUS, BALL_COLOR,new double[]{SIZE/2, SIZE/2});
      activeBalls.add(replacement);
      root.getChildren().add(replacement.getCircle());
    }
  }

  private void addLife() {
    lives++;
    livesText.setText("Lives:" + (lives.toString()));
  }

  private void reset() {
    for (Ball currBall : activeBalls) {
      currBall.reset(new double[]{SIZE / 2, SIZE / 2});
      currBall.reset(new double[]{SIZE / 2 - PADDLE_HEIGHT / 2, SIZE - PADDLE_HEIGHT});
    }
  }
  private void updateScore() {
    score += BlOCK_SCORE;
    scoreText.setText("Score:" + score);
  }

  private void clearLevel() throws FileNotFoundException {
    if (currLevel == 4) {
      livesText.setText("You Win!");
      for(Ball currBall : activeBalls){
        currBall.stopBall();
      }
      return;
    }
    levelText.setText("Level: " + currLevel);
    brickAccess = new ArrayList<>();
    for(Ball currBall : activeBalls){
      currBall.reset(new double[]{SIZE / 2, SIZE / 2});
    }
    setBricks(root, currLevel);

  }

  //End game
  private void endGame() throws FileNotFoundException {
    highScores.add(score);
    Collections.sort(highScores);
    //Remove the last score as it will not be included.\
    highScores.remove(0);
    try (Writer writer = new BufferedWriter(new OutputStreamWriter(
        new FileOutputStream("src/main/resources/Scores/highScores.txt"), "utf-8"))) {
      String output = "";
      for (Integer score : highScores) {
        output += score;
        output += " ";
      }
      writer.write(output);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    for (Ball currBall : activeBalls) {
      currBall.stopBall();
      livesText.setText("Game Over");
    }
  }

  /**
   * Start the program.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
