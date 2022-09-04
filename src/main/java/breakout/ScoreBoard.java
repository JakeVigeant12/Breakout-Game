package breakout;
import javafx.scene.shape.Rectangle;

import javafx.scene.text.*;
public class ScoreBoard {
     Rectangle container;
     Text score= new Text();

     Text lives = new Text();
    private Integer cLives;
    private Integer cScore;
    public ScoreBoard(int initLives, int initScore, int width, int height){
        container = new Rectangle(width, height);
        cLives = initLives;
        cScore = initScore;
        score.setText(cScore.toString());
        lives.setText(cLives.toString());
    }
    //Position rectangle that will house scoreboard text
    public void setContainer(int upper, int lower, int width){
        container.setHeight(upper-lower);
        container.setY(upper);
        container.setX(0);
        container.setWidth(width);
        score.setY(container.getY() + container.getHeight()/2);
        lives.setY(container.getY() + container.getHeight()/2);
        lives.setX(0);
        score.setX(container.getWidth()/2);
        score.setStyle("-fx-text-inner-color: white;");
        lives.setStyle("-fx-text-inner-color: white;");
    }
    //Add to score
    public void setScore(int amt){
        cScore += amt;
    }
}
