package breakout;
import javafx.scene.shape.Rectangle;

import javafx.scene.text.*;
public class ScoreBoard {
     Rectangle container;
     Text score= new Text("Hi");

     Text lives = new Text("Hi");
    private Integer cLives;
    private Integer cScore;
    public ScoreBoard(int initLives, int initScore, int width, int height){
        container = new Rectangle(width, height);
        cLives = initLives;
        cScore = initScore;


    }
    //Position rectangle that will house scoreboard text
    public void setContainer(int upper, int lower, int width){
        container.setHeight(upper-lower);
        container.setY(upper);
        container.setX(0);
        container.setWidth(width);
        //score.setY(container.getY() + container.getHeight()/2);
        //lives.setY(container.getY() + container.getHeight()/2);
        score.setY(300);
        lives.setY(300);
        lives.setX(100);
        score.setX(100);
        score.setStyle("-fx-text-inner-color: white;");
        lives.setStyle("-fx-text-inner-color: white;");
    }
    //Add to score
    public void setScore(int amt){
        cScore += amt;
    }
    public void loseLife(){
        if(cLives == 0){
            //END GAME
        }
        cLives--;

    }
}
