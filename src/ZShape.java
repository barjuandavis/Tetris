import javafx.scene.paint.Paint;

public class ZShape extends Shape{
    public ZShape() {
        super();
    }
    @Override
    public void initShape() {
        getMinosArray()[0].move(0,0);
        getMinosArray()[1].move(1,0);
        getMinosArray()[2].move(1,1);
        getMinosArray()[3].move(2,1);
        setRotator(1);
        setColor(Paint.valueOf("#ff1a1a"));
    }
}
