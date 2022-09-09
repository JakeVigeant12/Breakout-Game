package breakout;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;
public class Paddle {
  private double[] position;
  private double velocity;
  private Rectangle paddle;
  public Paddle(double[] startingCoords, double width, double height, double velo, Paint color){
    position = startingCoords;
    velocity = velo;
    paddle = new Rectangle(position[0],position[1], width,  height);
    paddle.setFill(color);
  }
  public Rectangle getShape(){
    return this.paddle;
  }

  public void moveRight(double time) {
    this.position[0] += velocity * time;
    paddle.setX(position[0]);
  }

  public void moveLeft(double time) {
    this.position[0] -= velocity * time;
    paddle.setX(position[0]);
  }
}
