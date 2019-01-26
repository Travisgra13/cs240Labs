import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PPMFileWriter {
    public void WriteFile(ArrayList<Integer> myModifiedInts, String fileName, Integer width, Integer height, Integer maxColor) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            StringBuilder OSS = new StringBuilder();
            OSS.append("P3\n");
            OSS.append(width + " " + height + "\n");
            OSS.append(maxColor + "\n");
            //PrintWriter printWriter = new PrintWriter(fileWriter);
            //printWriter.println("P3");
            //printWriter.println(width + " " + height);
            //printWriter.println(maxColor);
            for (Integer i = 0; i < myModifiedInts.size(); i++) {
                if (i % width == 0) {
                    OSS.append(myModifiedInts.get(i) + " \n" );
                } else {
                    OSS.append(myModifiedInts.get(i) + " ");
                }
            }
            fileWriter.write(OSS.toString());
            fileWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
