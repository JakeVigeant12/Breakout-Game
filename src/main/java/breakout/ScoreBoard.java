package breakout;
import javafx.scene.shape.Rectangle;
public class ScoreBoard {
    private Rectangle container;
    private int lives;
    private int score;
    public ScoreBoard(int initLives, int initScore, int width, int height){
        container = new Rectangle(width, height);
        lives = initLives;
        score = initScore;
    }
}
