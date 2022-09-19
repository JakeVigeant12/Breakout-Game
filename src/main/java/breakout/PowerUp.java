package breakout;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public abstract class PowerUp <T>{

  public int effect;
  private Rectangle powerUp;
  private double[] velocity;
  private double[] position;
  public PowerUp(double[] coordinates){
    //Get a random number 1-3

    velocity = new double[]{100,400};
    position = coordinates;
    powerUp = new Rectangle(coordinates[0],coordinates[1],20,20);
    powerUp.setFill(Color.AZURE);
  }
  public Rectangle getShape(){
    return this.powerUp;
  }
  public void move(double time) {
    position[0] += time * velocity[0];
    position[1] += time * velocity[1];
    powerUp.setX(position[0]);
    powerUp.setY(position[1]);
  }
  public abstract Object affectGame();
}
