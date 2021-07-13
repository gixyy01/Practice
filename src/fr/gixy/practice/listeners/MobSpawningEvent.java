package fr.gixy.practice.listeners;

import fr.gixy.practice.Main;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class MobSpawningEvent implements Listener {
    public MobSpawningEvent(Main main) {
    }

    @EventHandler
    public void onMobSpawningEvent(CreatureSpawnEvent event) {
        event.setCancelled(event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL);

    }


}
