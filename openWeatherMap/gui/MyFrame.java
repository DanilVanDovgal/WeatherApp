package openWeatherMap.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import openWeatherMap.UrlConnection;
import openWeatherMap.UsbSaver;
import openWeatherMap.WeatherData;
import openWeatherMap.modelWeather.Json;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MyFrame extends JFrame {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private JPanel panel;
    private JFileChooser chooser;
    private JButton button;
    private JTextArea text;
    private MyDialogSave dialogSave;

    private WeatherData weatherData;
    private Logger logger = Logger.getLogger("openWeatherMap");

    public MyFrame(WeatherData weatherData) {

        logger.entering("MyFrame", "MyFrame", weatherData);

        this.weatherData = weatherData;

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception exc) {
            logger.log(Level.WARNING, "Can't set lookAndFell", exc);
        }

        setSize(WIDTH, HEIGHT);
        setLocationByPlatform(true);
        setJMenuBar(menu());

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        button = createButton();

        text = new JTextArea();
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
        text.setFont(new Font("Arial", Font.BOLD, 20));

        panel.add(button);
        panel.add(text);
        add(panel, BorderLayout.CENTER);
    }

    private JTextArea constructTextArea(String data) {
        text.replaceRange(data, 0, text.getSelectionEnd());
        return text;
    }

    private JButton createButton() {
        JButton myButton = new JButton("Get current weather");
        myButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        myButton.addActionListener(event -> {

            logger.entering("MyFrame", "createButton - ActionListener");

            try {
                String result = UrlConnection.performRequest();
                Gson gson = new GsonBuilder().create();
                Json json = gson.fromJson(result, Json.class);
                weatherData.add(json);
                text = constructTextArea(getResultString(json));
                panel.updateUI();

            } catch (Exception exc) {
                logger.log(Level.WARNING, "createButton ActionListener", exc);
            }
        });

        return myButton;
    }

    private JMenuBar menu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu about = new JMenu("About");
        menuBar.add(file);
        menuBar.add(about);

        JMenuItem pasteSave = new JMenuItem("Save");

        pasteSave.addActionListener(event -> {
            String path = UsbSaver.usbSearcher();
            if(path == null) {
                if(dialogSave == null) {
                    dialogSave = new MyDialogSave(MyFrame.this);
                    dialogSave.setVisible(true);
                }
            }
            UsbSaver.createProperties(path, weatherData.getLast());
            weatherData.saveData();
        });

        JMenuItem pasteSaveAs = new JMenuItem("Save as");

        pasteSaveAs.addActionListener(event -> {
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
            chooser.setDialogTitle("Choose a directory to save your file: ");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnValue = chooser.showSaveDialog(MyFrame.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                if (chooser.getSelectedFile().isDirectory()) {
                    UsbSaver.createProperties(chooser.getSelectedFile().getAbsolutePath(), weatherData.getLast());
                    weatherData.saveData();
                }
            }
        });

        file.add(pasteSave);
        file.add(pasteSaveAs);

        return menuBar;
    }

    private static String getResultString(Json obj) {
        return new StringBuilder()
                .append("Temperature = ").append(obj.main.temp).append(" \u00b0C\n")
                .append("Humidity = ").append(obj.main.humidity).append(" %\n")
                .append("Pressure = ").append((int) (obj.main.pressure * 0.750062)).append("\n")
                .append("Wind = ").append(obj.wind.speed).append(" m/s\n")
                .append("Wind deg = ").append(obj.wind.deg).append(" \u00b0\n").toString();
    }
}
