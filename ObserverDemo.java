

import java.util.*;

interface Observer {
    void update(double temp, double humidity);
}

interface Subject {
    void register(Observer o);
    void unregister(Observer o);
    void notifyObservers();
}

class WeatherStation implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    private double temp;
    private double humidity;

    public void register(Observer o) { observers.add(o); }
    public void unregister(Observer o) { observers.remove(o); }
    public void setMeasurements(double t, double h) { this.temp = t; this.humidity = h; notifyObservers(); }

    public void notifyObservers() {
        for (Observer o : new ArrayList<>(observers)) o.update(temp, humidity);
    }
}

class ConsoleDisplay implements Observer {
    private final String name;
    public ConsoleDisplay(String name) { this.name = name; }
    public void update(double temp, double humidity) {
        System.out.printf("%s -> Temp: %.2f, Humidity: %.2f%n", name, temp, humidity);
    }
}

// Demo
class ObserverDemo {
    public static void main(String[] args) {
        WeatherStation station = new WeatherStation();
        ConsoleDisplay d1 = new ConsoleDisplay("Display-1");
        ConsoleDisplay d2 = new ConsoleDisplay("Display-2");
        station.register(d1); station.register(d2);
        station.setMeasurements(30.5, 70.0);
        station.setMeasurements(28.2, 75.5);
    }
}

