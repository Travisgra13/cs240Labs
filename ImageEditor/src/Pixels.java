import java.awt.*;
import java.util.ArrayList;

public class Pixels {
    private ArrayList<Pixel> pixels = new ArrayList<>();
    private ArrayList<String> myArrayList = new ArrayList<>();
    private Integer widthMax = 0;
    private Integer heightMax = 0;
    private Integer maxColor = 0;

    public Pixels(ArrayList<String> origArrayList, Integer widthMax, Integer heightMax, Integer maxColor) {
        this.myArrayList = origArrayList;
        this.widthMax = widthMax;
        this.heightMax = heightMax;
        this.maxColor = maxColor;
    }
    public Pixel[][] ParsePixels() {
        Pixel[][] myPixels = new Pixel[heightMax][widthMax];
        Integer rowCounter = 0;
        Integer columnCounter = 0;
        for (Integer i = 0; i < myArrayList.size(); i++) {
           String redValue =  myArrayList.get(i);
           String greenValue = myArrayList.get(i + 1);
           String blueValue = myArrayList.get(i + 2);
           Pixel currPixel = new Pixel(Integer.parseInt(redValue), Integer.parseInt(greenValue), Integer.parseInt(blueValue));
            myPixels[rowCounter][columnCounter] = (currPixel);
            if (columnCounter.equals(widthMax - 1)) {
               columnCounter = 0;
               rowCounter++;
            }
           else {
               columnCounter++;
            }
            i += 2;
        }
        return myPixels;
    }


}
