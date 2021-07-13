package fr.gixy.practice.listeners;

import fr.gixy.practice.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherEvents implements Listener {
    public WeatherEvents(Main main) {
    }

    @EventHandler
    public void onRainEvent(WeatherChangeEvent event) {

        event.setCancelled(event.toWeatherState());

    }


}
