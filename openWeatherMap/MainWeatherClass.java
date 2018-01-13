package openWeatherMap;

import openWeatherMap.gui.MyFrame;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.*;


public class MainWeatherClass {
    public static void main(String[] args) {

        Logger logger = Logger.getLogger("openWeatherMap");
        if (System.getProperty("java.util.logging.config.class") == null &&
                System.getProperty("java.util.logging.config.file") == null) {
            try {
                Logger.getLogger("openWeatherMap").setLevel(Level.ALL);
                final int LOG_ROTATION_COUNT = 10;
                Handler handler = new FileHandler("myLog.log", 0, LOG_ROTATION_COUNT);
                Logger.getLogger("openWeatherMap").addHandler(handler);
                handler.setFormatter(new SimpleFormatter());
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Can't create lof file handler", e);
            }
        }

        try {
            WeatherData weatherData = WeatherData.loadData();

            EventQueue.invokeLater(() -> {
                JFrame frame = new MyFrame(weatherData);
                frame.setTitle("Good Weather");
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            });

        } catch (Exception exc) {
            logger.log(Level.WARNING, "Main Exception", exc);
        }
    }
}
