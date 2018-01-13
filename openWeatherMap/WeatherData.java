package openWeatherMap;

import openWeatherMap.modelWeather.Json;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeatherData implements Serializable{

    private static final long serialVersionUID = 1L;
    private static final String path = "data.out";
    private List<Json> dataWeatherList = new ArrayList<>();
    private static Logger logger = Logger.getLogger("openWeatherMap");


    public void add(Json obj) {
        dataWeatherList.add(obj);
    }

    private int size() {
        return dataWeatherList.size();
    }

    public List<Json> getDataWeatherList() {
        return Collections.unmodifiableList(dataWeatherList);
    }

    public Json getLast() {
        return dataWeatherList.get(size() - 1);
    }

    public void saveData() {
        logger.entering("WeatherData.class", "saveData");
        try {
            try (FileOutputStream fos = new FileOutputStream(path);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(this);
            }
        } catch (Exception exc) {
            logger.log(Level.WARNING, "Exception saveData", exc);
        }
    }

    public static WeatherData loadData() {

        logger.entering("WeatherData", "loadData");

        File file = new File(path);

        if (file.exists()) {
            WeatherData weatherData = null;
            try {
                try (FileInputStream fis = new FileInputStream(file);
                     ObjectInputStream ois = new ObjectInputStream(fis)) {
                    weatherData = (WeatherData) ois.readObject();
                }
            } catch (Exception exc) {
                logger.log(Level.WARNING, "Exception loadData", exc);
            }
            return weatherData;
        }

        return new WeatherData();
    }
}
