package Shape;

import javafx.scene.paint.Paint;

public class OShape extends Shape{
    public OShape() {
        super();
    }
    @Override
    public void initShape() {
        getMinosArray()[0].move(1,0);
        getMinosArray()[1].move(2,0);
        getMinosArray()[2].move(1,1);
        getMinosArray()[3].move(2,1);
        setRotator(-1);
        setColor(Paint.valueOf("Magenta"));
    }
}
