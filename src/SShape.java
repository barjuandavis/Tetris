import javafx.scene.paint.Paint;

public class SShape extends Shape{
    public SShape() {
        super();
    }
    @Override
    public void initShape() {
        getMinosArray()[0].move(0,1);
        getMinosArray()[1].move(1,1);
        getMinosArray()[2].move(1,0);
        getMinosArray()[3].move(2,0);
        setRotator(1);
        setColor(Paint.valueOf("Yellow"));
    }
}
