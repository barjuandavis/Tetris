package Shape;

import javafx.scene.paint.Paint;

public class JShape extends Shape {
    public JShape() {
        super();
    }
    @Override
    public void initShape() {
        getMinosArray()[0].move(0,0);
        getMinosArray()[1].move(1,0);
        getMinosArray()[2].move(2,0);
        getMinosArray()[3].move(2,1);
        setRotator(1);
        setColor(Paint.valueOf("#0066ff"));
    }
}
