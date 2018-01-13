package openWeatherMap.modelWeather;

import java.io.Serializable;

public class Main implements Serializable {
    private static final long serialVersionUID = 1L;
    public double temp;
    public int pressure;
    public int humidity;
    public int temp_min;
    public int temp_max;
}
