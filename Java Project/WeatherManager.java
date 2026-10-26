import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class WeatherManager {
    private ArrayList<Weather> weatherList = new ArrayList<>();
    private List<WeatherObserver> observers = new ArrayList<>();

    public void addObserver(WeatherObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(WeatherObserver observer) {
        observers.remove(observer);
    }

    private void addWeather(Weather w) {
        weatherList.add(w);
        observers.forEach(o -> o.weatherStarted(w));
    }

    public boolean isActive(Weather w) {
        return w.getDuration() > 0;
        // if (w.getDuration() <= 0) {
        //     return false;
        // }

        // return true;
    }

    public void createWeather(String weather) {
        Weather w = null;

        switch (weather) {
            case "Rain":
                w = new Rain(8, 20, null);
                break;
            case "Heatwave":
                w = new Heatwave(2, 9, null);
                break;
            case "Lightning":
                w = new Lightning(10, 3, null, 3);
                break;
            default:
                break;
        }

        if (w != null) {
            addWeather(w);
        }
    }

    public void createWeather(String weather, String selectedDirection) {
        Weather w = null;
        int direction = 0;

        switch (selectedDirection) {
            case "East":
                direction = 1;
                break;
            case "South":
                direction = 2;
                break;
            case "West":
                direction = 3;
                break;
            default:
                break;
        }

        w = new Wind(20, 25, null, direction);

        if (w != null) {
            addWeather(w);
        }
    }

    public void advanceDecrement(Weather w) {
        w.decrementDuration();
    }

    public void update(ForestFireSimulation simulation) {
        weatherList.forEach(w -> advanceDecrement(w));

        List<Weather> expired = weatherList.stream()
            .filter(w -> !isActive(w))
            .collect(Collectors.toList());
        weatherList.removeAll(expired);
        expired.forEach(w -> observers.forEach(o -> o.weatherEnded(w)));

        weatherList.forEach(w -> w.affectSimulation(simulation));
    }

    public List<String> getActiveWeatherNames() {
        return weatherList.stream()
            .filter(this::isActive)
            .map(w -> w.getClass().getSimpleName())
            .distinct()
            .collect(Collectors.toList());
    }
}
