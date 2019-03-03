package RandomData;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class RandomDataGenerator {

    private static RandomDataGenerator instance;
    private RandomGenerationData data;

    public RandomGenerationData getData() {
        return data;
    }

    public void setData(RandomGenerationData data) {
        this.data = data;
    }

    public static RandomDataGenerator getInstance() {
        if (instance == null) {
            instance = new RandomDataGenerator();
        }
        return instance;
    }
    private RandomDataGenerator() {

    }
    public RandomDataGenerator(RandomGenerationData data) {
        this.data = data;
    }

    public void ConvertDataToJson() {
        Gson gson = new Gson();
        try {
            BufferedReader br = new BufferedReader(new FileReader("RandomGenerationData.json"));
             data = gson.fromJson(br, RandomGenerationData.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
