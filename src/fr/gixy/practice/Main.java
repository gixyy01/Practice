package fr.gixy.practice;

import fr.gixy.practice.commands.CommandDuel;
import fr.gixy.practice.commands.CommandSondage;
import fr.gixy.practice.listeners.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    private State state;
    private List<Player> players = new ArrayList<>();
    private List<Location> spawn = new ArrayList<>();


    @Override
    public void onEnable() {


        setState(State.WAITING);
        PluginManager pm = getServer().getPluginManager();


        pm.registerEvents(new PlayerListener(this), this);
        getCommand("duel").setExecutor(new CommandDuel(this));
        //pm.registerEvents(new DamageListener(this), this);
        pm.registerEvents(new GoldenHeadEvent(this), this);
        pm.registerEvents(new MobSpawningEvent(this), this);
        pm.registerEvents(new WeatherEvents(this), this);
        getCommand("sondage").setExecutor(new CommandSondage(this));


    }

    public void setState(State state) {

        this.state = state;

    }

    public boolean isState(State state) {

        return this.state == state;
    }

    public List<Player> getPlayer() {

        return players;

    }



    public void eliminate(Player player) {


        if (players.contains(player)) {

            players.remove(player);
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(ChatColor.GREEN + "Vous êtes morts !");

        }


    }

    public void checkwin() {

        if (players.size() == 1) {

            Player winner = players.get(0);
            setState(State.FINISH);

            for (Player players : Bukkit.getOnlinePlayers()) {

                players.sendMessage(ChatColor.YELLOW + "[" + ChatColor.GREEN + "PRACTICE" + ChatColor.YELLOW + "] " + ChatColor.GOLD + winner.getName() + ChatColor.AQUA + " gagne le match");


            }


        }


    }
}
