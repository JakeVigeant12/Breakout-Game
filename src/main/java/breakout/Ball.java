package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball {

  private Circle ball;
  //vectors stored as arrays, 0 = x, 1 = y
  private double[] velocity;
  private double[] position;

  public Ball(double radius, Paint color, double[] startCoords) {
    ball = new Circle(radius, color);
    velocity = new double[]{100, 400};
    position = startCoords;
    ball.setCenterX(position[0]);
    ball.setCenterY(position[1]);
  }

  public Circle getCircle() {
    return this.ball;
  }

  public void move(double time) {
    position[0] += time * velocity[0];
    position[1] += time * velocity[1];
    ball.setCenterX(position[0]);
    ball.setCenterY(position[1]);
  }

  public void reset(double[] initialCoords) {
    position = initialCoords;
  }

  //Ball reverses direction in only one dimension at a time, so passing 0 or 1 to decide which works
  public void reverse(int direction) {
    velocity[direction] *= -1.005;
  }

  public void stopBall() {
    velocity[0] = 0;
    velocity[1] = 0;
  }

  public void paddleCollison(boolean right) {
    if (right) {
      velocity[0] = 1.005 * Math.abs(velocity[0]);
      return;
    }
    velocity[0] = -1.005 * Math.abs(velocity[0]);
  }

}
