import java.io.File;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.*;

public class PPMFileReader {
    private ArrayList<String> arrayList = new ArrayList<>();
    private String header = "";
    private Integer width = 0;
    private Integer height = 0;
    private Integer maxColorVal = 0;


    public ArrayList<String> ParsedFile(String fileName) {
        try {
        Scanner in = new Scanner(new File(fileName));
        header = in.next();
        in.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
        width = Integer.parseInt(in.next());
        in.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
        height = Integer.parseInt(in.next());
        in.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
        maxColorVal = Integer.parseInt(in.next());

            while (in.hasNext()) {
            in.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
            String num = in.next();
            arrayList.add(num);
        }
        in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return arrayList;
    }
    public Integer GetWidth() {
        return width;
    }

    public Integer GetHeight() {
        return height;
    }

    public Integer GetMaxColorVal() {
        return maxColorVal;
    }

}
