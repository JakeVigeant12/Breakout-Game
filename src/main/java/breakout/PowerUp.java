package breakout;
import java.util.Random;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
public class PowerUp {
  private int effect;
  private Rectangle powerUp;
  private double[] velocity;
  private double[] position;
  public PowerUp(double[] coordinates){
    //Get a random number 1-3
    effect = new Random().nextInt(3 - 1 + 1) + 1;
    velocity = new double[]{100,400};
    position = coordinates;
    powerUp = new Rectangle(coordinates[0],coordinates[1],20,20);
    powerUp.setFill(Color.AZURE);
  }
  public Shape getShape(){
    return this.powerUp;
  }
  public void move(double time) {
    position[0] += time * velocity[0];
    position[1] += time * velocity[1];
    powerUp.setX(position[0]);
    powerUp.setY(position[1]);
  }

}
