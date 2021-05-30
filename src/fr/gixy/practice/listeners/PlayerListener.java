package fr.gixy.practice.listeners;

import fr.gixy.practice.Main;
import fr.gixy.practice.State;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import javax.tools.Diagnostic;

public class PlayerListener implements Listener {

    private Main main;


    public PlayerListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        Location spawn = new Location(Bukkit.getWorld("Map1"), 120.5, 38.5, 152.5);


        player.setHealth(20);
        player.setFoodLevel(20);
        player.teleport(spawn);
        player.setExp(0);
        player.setLevel(0);
        player.getInventory().clear();
        player.getInventory().getArmorContents();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);

        if (!main.isState(State.WAITING)) {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(ChatColor.RED + "Un combat est déjà en cours !");

            return;


        }

        if (!main.getPlayer().contains(player)) main.getPlayer().add(player);
        player.setGameMode(GameMode.ADVENTURE);
        event.setJoinMessage(ChatColor.YELLOW + "[" + ChatColor.GREEN + "PRACTICE" + ChatColor.YELLOW + "] " + ChatColor.AQUA + player.getName() + ChatColor.AQUA + " a rejoint la partie !");


    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        event.setQuitMessage(ChatColor.YELLOW + "[" + ChatColor.GREEN + "PRACTICE" + ChatColor.YELLOW + "] " + ChatColor.AQUA + player.getName() + ChatColor.AQUA + " a quitté la partie !");

        main.getPlayer().remove(player);

        if (main.isState(State.STARTING) || main.isState(State.PLAYING)) {


            main.checkwin();
        }


    }

    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {

        Player player = (Player) event.getEntity();

        if (main.isState(State.WAITING)) {

            event.setCancelled(true);


        }


    }

    @EventHandler
    public void onPlayerDamageByEntityEvent(EntityDamageEvent event) {


        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        if (main.isState(State.PLAYING)) {

            event.setCancelled(false);
        }


        if (main.isState(State.WAITING) || main.isState(State.FINISH)) {

            event.setCancelled(true);

        }


    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {

        Player player = event.getEntity();
        Player killer = player.getKiller();


        event.setDeathMessage(null);
        event.getDrops().clear();

        if(killer != null) {

            if (killer instanceof Player) {


                for (Player players : main.getPlayer()) {

                    players.sendMessage(ChatColor.BLUE + "" + killer.getName() + ChatColor.DARK_GRAY + " a tué " + ChatColor.BLUE + "" + player.getName());


                }


            }

        }

        main.eliminate(player);
        main.checkwin();

    }

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event){

        Player player = event.getPlayer();
        Player killer = player.getKiller();

        player.teleport(killer.getLocation());



    }


}
