import java.awt.*;

public class Pixel {
    private Integer redValue = 0;
    private Integer greenValue = 0;
    private Integer blueValue = 0;
    //private Point location = new Point(0,0);

    public Pixel(Integer redValue, Integer greenValue, Integer blueValue) {
        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;
    }

    public Integer GetRedValue() {
        return this.redValue;
    }

    public Integer GetGreenValue() {
        return this.greenValue;
    }

    public Integer GetBlueValue() {
        return this.blueValue;
    }



}
