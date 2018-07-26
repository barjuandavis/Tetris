/**
 @project Tetris

 @author Barjuan Davis Penthalion    00000023971     BP3971@student.uph.edu
 @author Nadya Felim Bachtiar        00000019602     NB9602@student.uph.edu

 Informatics 2016
 Universitas Pelita Harapan
 */
package Shape;

import javafx.scene.paint.Paint;
public class IShape extends Shape {
    public IShape() {super();}
    @Override
    public void initShape() {
        getMinosArray()[0].move(0,0);
        getMinosArray()[1].move(1,0);
        getMinosArray()[2].move(2,0);
        getMinosArray()[3].move(3,0);
        setRotator(1);
        setColor(Paint.valueOf("Blue"));
    }
}
