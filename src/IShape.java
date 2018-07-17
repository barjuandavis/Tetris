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
        setColor(Paint.valueOf("Cyan"));
    }
}
