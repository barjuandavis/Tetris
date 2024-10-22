/**
 @project Tetris

 @author Barjuan Davis Penthalion    00000023971     BP3971@student.uph.edu
 @author Nadya Felim Bachtiar        00000019602     NB9602@student.uph.edu

 Informatics 2016
 Universitas Pelita Harapan
 */
package Shape;

import Main.Game;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Minos extends Rectangle {
    private final int size = 1;
    public Minos(int x, int y) {
        super(Game.boardWidth/ Game.boardWidth_r, Game.boardHeight/ Game.boardHeight_r, Paint.valueOf("red"));
        super.setStroke(Color.BLACK);
        move(x,y);
    }
    public void move(int x, int y) {setRelativeX(x+getRelativeX());setRelativeY(y+getRelativeY());}
    public void set(int x, int y) {setRelativeX(x);setRelativeY(y);}
    public void setRelativeX(int x) {setX(x*getWidth());}
    public void setRelativeY(int y) {setY(y*getHeight());}
    public int getRelativeX() {return (int)(getX()/(getWidth()));}
    public int getRelativeY() {return (int)(getY()/(getHeight()));}
    public static double getMinosHeight() {return Game.boardHeight/ Game.boardHeight_r;}
    public static double getMinosWidth() {return Game.boardWidth/ Game.boardWidth_r;}

}
