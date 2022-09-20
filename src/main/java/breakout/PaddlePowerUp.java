package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class PaddlePowerUp extends PowerUp{
  private double initialPaddleWidth;
  public int type = 2;

  public PaddlePowerUp(double[] coordinates, double width) {
    super(coordinates);
    initialPaddleWidth = width;
  }

  public Paddle affectGame() {
    Paddle resultPaddle = Main.lengthenPaddle();
    return resultPaddle;
  }
}
