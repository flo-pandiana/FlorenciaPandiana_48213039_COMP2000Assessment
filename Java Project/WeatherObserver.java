public interface WeatherObserver {
    void weatherStarted(Weather weather);

    void weatherEnded(Weather weather);
}
