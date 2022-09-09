package breakout;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;
public class Brick {
  private Rectangle brick;
public Brick(double width, double height, Paint color){
  brick = new Rectangle(width,height,color);
}
public Rectangle getRect(){
  return this.brick;
}

}
