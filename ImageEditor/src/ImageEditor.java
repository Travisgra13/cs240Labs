import java.io.FileWriter;
import java.util.*;

public class ImageEditor {
    public static void main(String[] args) {
        ArrayList<Integer> finishedPixels = new ArrayList<>();
        PPMFileReader myReader = new PPMFileReader();
        ArrayList<String> myArrayList = myReader.ParsedFile(args[0]);
        PPMImage myImageEditor = new PPMImage(myReader.GetWidth(), myReader.GetHeight(), myReader.GetMaxColorVal());

        if (args[2].equals("invert")) {
            finishedPixels = myImageEditor.Invert(myArrayList);
            PPMFileWriter fileWriter = new PPMFileWriter();
            fileWriter.WriteFile(finishedPixels, args[1], myReader.GetWidth(), myReader.GetHeight(), myReader.GetMaxColorVal());
        }
        else if (args[2].equals("grayscale")) {
            finishedPixels = myImageEditor.GrayScale(myArrayList);
            PPMFileWriter fileWriter = new PPMFileWriter();
            fileWriter.WriteFile(finishedPixels, args[1], myReader.GetWidth(), myReader.GetHeight(), myReader.GetMaxColorVal());
        }

        else if (args[2].equals("emboss")) {
            finishedPixels = myImageEditor.Emboss(myArrayList);
            PPMFileWriter fileWriter = new PPMFileWriter();
            fileWriter.WriteFile(finishedPixels, args[1], myReader.GetWidth(), myReader.GetHeight(), myReader.GetMaxColorVal());
        }

        else if (args[2].equals("motionblur")) {
            finishedPixels = myImageEditor.MotionBlur(myArrayList, args[3]);
            PPMFileWriter fileWriter = new PPMFileWriter();
            fileWriter.WriteFile(finishedPixels, args[1], myReader.GetWidth(), myReader.GetHeight(), myReader.GetMaxColorVal());
        }

    }
}



