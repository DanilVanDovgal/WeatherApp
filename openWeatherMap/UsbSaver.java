package openWeatherMap;

import openWeatherMap.modelWeather.Json;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsbSaver {

    public static String usbSearcher() {
        FileSystemView fsv = FileSystemView.getFileSystemView();

        for (File f : File.listRoots()) {
            String partition = fsv.getSystemTypeDescription(f);
            if(partition.toLowerCase().contains("usb")) {
                partition = f.getAbsolutePath();
                return partition;
            }
        }

        return null;
    }

    public static void createProperties(String path, Json obj) {

        Logger.getLogger("openWeatherMap").entering("UsbSaver.class", "createProperties", path);

        try {
            LocalDateTime dateTime = LocalDateTime.now();
            String form = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm"));

            try (PrintWriter pw = new PrintWriter(path + form + "weather.properties")) {
                pw.println("Temperature = " + obj.main.temp);
                pw.println("Humidity = " + obj.main.humidity);
                pw.println("Pressure = " + (int) (obj.main.pressure * 0.750062));
                pw.println("Wind = " + obj.wind.speed);
                pw.print("Wind deg = " + obj.wind.deg);
            }
        } catch (Exception e) {
            Logger.getLogger("openWeatherMap").log(Level.WARNING, "Exception createProperties", e);
        }
    }


}
