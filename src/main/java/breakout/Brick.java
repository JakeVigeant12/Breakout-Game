package breakout;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
public class Brick {
  private Rectangle brick;
  private int numLives;
public Brick(double width, double height, int lives){
  this.numLives = lives;
  Color[] colorMapping = new Color[4];
  colorMapping[0] = Color.BLACK;
  colorMapping[1] = Color.RED;
  colorMapping[2] = Color.YELLOW;
  colorMapping[3] = Color.GREEN;
  brick = new Rectangle(width,height,colorMapping[numLives]);
}
public Rectangle getRect(){
  return this.brick;
}

//Boolean return indicates if a block should be removed or not. True when block is eliminated
public boolean subLife(){
  this.numLives-=1;
  switch(numLives){
    case 3:
      this.brick.setFill(Color.GREEN);
      break;
    case 2:
      this.brick.setFill(Color.YELLOW);
      break;
    case 1:
      this.brick.setFill(Color.RED);
      break;
    case 0:
      this.brick.setFill(Color.BLACK);
      break;
  }
  if(this.numLives == 0){
    return true;
  }
  return false;
}

}
