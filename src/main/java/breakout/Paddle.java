package breakout;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;
public class Paddle extends Node {
  private double[] position;
  private double velocity;
  private Rectangle paddle;
  public Paddle(double[] startingCoords, double width, double height, double velocity, Paint color){
    position = startingCoords;
    this.velocity = velocity;
    paddle = new Rectangle(position[0],position[1], width,  height);
    paddle.setFill(color);
  }
  public Rectangle getShape(){
    return this.paddle;
  }

  public void moveRight(double time, double size) {
    this.position[0] += velocity * time;
    if(position[0] >= size - paddle.getWidth()){
      this.position[0] -= velocity * time;
      return;
    }
    paddle.setX(position[0]);
  }

  public void reset(double[] initialCoords) {
    position = initialCoords;
    paddle.setX(position[0]);
    paddle.setY(position[1]);
  }
  public void moveLeft(double time) {
    this.position[0] -= velocity * time;
    if(position[0] <= 0){
      this.position[0] += velocity * time;
      return;
    }
    paddle.setX(position[0]);
  }
}
