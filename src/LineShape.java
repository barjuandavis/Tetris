import javafx.scene.paint.Paint;
public class LineShape extends Shape {
    public LineShape() {
        super();
    }
    @Override
    public void initShape() {
        minosArray[0].move(0,0);
        minosArray[1].move(1,0);
        minosArray[2].move(2,0);
        minosArray[3].move(3,0);
        setRotator(1);
        setColor(Paint.valueOf("Cyan"));
    }
}
