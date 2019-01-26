import java.awt.*;
import java.util.ArrayList;

public class PPMImage {
    final Integer maxPixels = 255;
    private ArrayList<Integer> modifiedArrayList = new ArrayList<>();
    private Integer widthMax = 0;
    private Integer heightMax = 0;
    private Integer maxColor = 0;


    public PPMImage(Integer widthMax, Integer heightMax, Integer maxColor) {
        this.widthMax = widthMax;
        this.heightMax = heightMax;
        this.maxColor = maxColor;
    }
    public ArrayList<Integer> Invert(ArrayList<String> myArrayList) {
        for (Integer i = 0; i < myArrayList.size(); i++) {
            modifiedArrayList.add(Invert(myArrayList.get(i)));
        }
        return modifiedArrayList;
    }

    public ArrayList<Integer> GrayScale(ArrayList<String> myArrayList) {
        for (Integer i = 0; i < (myArrayList.size()); i++) {
            Integer numToBeAdded = 0;
            numToBeAdded = (GrayScale(myArrayList.get(i), myArrayList.get(i + 1), myArrayList.get(i + 2)));
            modifiedArrayList.add(numToBeAdded);
            modifiedArrayList.add(numToBeAdded);
            modifiedArrayList.add(numToBeAdded);
            i = i + 2;
        }
        return modifiedArrayList;
    }
    public Integer Invert(String num) {
        Integer myInteger = Integer.parseInt(num);
        myInteger = maxPixels - myInteger;
        return myInteger;
    }

    public Integer GrayScale(String num1, String num2, String num3) {
        Integer myFinalInteger = 0;
        Integer myInteger1 = Integer.parseInt(num1);
        Integer myInteger2 = Integer.parseInt(num2);
        Integer myInteger3 = Integer.parseInt(num3);
        myFinalInteger = ((myInteger1 + myInteger2 + myInteger3) / 3);
        return myFinalInteger;
    }

    public ArrayList<Integer> Emboss(ArrayList<String> myArrayList) {
        Pixels myPixels = new Pixels(myArrayList, widthMax, heightMax, maxColor);
        Pixel[][] myNewPixels = myPixels.ParsePixels();
        Integer v = 0;
        for (Integer i = 0; i < heightMax; i++) {
            for (Integer j = 0; j < widthMax; j++) {
                Pixel currPixel = myNewPixels[i][j];
                Integer currXLoc = j;
                Integer currYLoc = i;
                //System.out.println("New location x is (" + currXLoc + "," + currYLoc + ")");
                Integer targetXLoc = currXLoc - 1;
                Integer targetYLoc = currYLoc - 1;
                if (targetXLoc < 0 || targetYLoc < 0) {
                    v = 128;
                } else {
                    Pixel targetPixel = myNewPixels[targetYLoc][targetXLoc];
                    Integer redDiff = currPixel.GetRedValue() - targetPixel.GetRedValue();
                    Integer greenDiff = currPixel.GetGreenValue() - targetPixel.GetGreenValue();
                    Integer blueDiff = currPixel.GetBlueValue() - targetPixel.GetBlueValue();
                    Integer maxDiff = FindMaxDifference(redDiff, greenDiff, blueDiff);
                    v = 128 + maxDiff;
                    if (v < 0) {
                        v = 0;
                    } else if (v > 255) {
                        v = 255;
                    }
                }

                //Pixel myNewPixel = new Pixel(v, v, v);
                //myNewPixel.SetLocation(new Point(currXLoc, currYLoc));
                modifiedArrayList.add(v);
                modifiedArrayList.add(v);
                modifiedArrayList.add(v);
            }
        }
       return modifiedArrayList;
    }
    public ArrayList<Integer> MotionBlur(ArrayList<String> myArrayList, String myMotBlurNum) {
        Pixels myPixels = new Pixels(myArrayList, widthMax, heightMax, maxColor);
        Pixel[][] myNewPixels = myPixels.ParsePixels();
        Integer motBlurVal = Integer.parseInt(myMotBlurNum);
        for (Integer i = 0; i < heightMax; i++) {
            for (Integer j = 0; j < widthMax; j++) {
                Pixel currPixel = myNewPixels[i][j];
                Integer currXLoc = j;
                Integer currYLoc = i;
                Integer rightVal = widthMax - currXLoc - 1;
                Integer redAverage = 0;
                Integer greenAverage = 0;
                Integer blueAverage = 0;
                Integer counter = 1;
                for (Integer k = 0; k < motBlurVal; k++) {
                    if (k + currXLoc > widthMax - 1) {
                        break;
                    }
                    Pixel myTargetPixel = myNewPixels[currYLoc][k + currXLoc];
                    redAverage += myTargetPixel.GetRedValue();
                    greenAverage += myTargetPixel.GetGreenValue();
                    blueAverage += myTargetPixel.GetBlueValue();
                    counter++;
                }

                redAverage = (redAverage / (counter - 1));
                greenAverage = (greenAverage / (counter - 1));
                blueAverage = (blueAverage / (counter - 1));
                modifiedArrayList.add(redAverage);
                modifiedArrayList.add(greenAverage);
                modifiedArrayList.add(blueAverage);

            }
        }
        return modifiedArrayList;
    }

    public Integer FindMaxDifference (Integer redDiff, Integer greenDiff, Integer blueDiff) {
        Integer newRedDiff = Math.abs(redDiff);
        Integer newGreenDiff = Math.abs(greenDiff);
        Integer newBlueDiff = Math.abs(blueDiff);
        Integer maxDiff = 0;
        if (newRedDiff > newGreenDiff) {
            maxDiff = redDiff;
        } else if (newRedDiff < newGreenDiff) {
            maxDiff = greenDiff;
        } else if (newRedDiff.equals(newGreenDiff)) {
            maxDiff = redDiff;
        }
        Integer newMaxDiff = Math.abs(maxDiff);
        if (newMaxDiff > newBlueDiff) {
            //maxDiff is the same
        } else if (newMaxDiff < newBlueDiff) {
            maxDiff = blueDiff;
        } else if (newMaxDiff.equals(newBlueDiff)) {
            //maxDiff is the same
        }
        return maxDiff;
    }

}
