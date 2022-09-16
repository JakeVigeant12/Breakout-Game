package breakout;

import java.awt.Paint;
import javafx.scene.Group;
import javafx.scene.Node;

public class BallPowerUp extends PowerUp{

  public BallPowerUp(double[] coordinates) {
    super(coordinates);
  }
  @ Override
  public Node affectGame(Group root) {
    Ball spawnedBall = new Ball();
    activeBalls.add(spawnedBall);
    root.getChildren().add(spawnedBall.getCircle());
    return null;
  }
}
