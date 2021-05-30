package fr.gixy.practice.commands;

import fr.gixy.practice.Main;
import javafx.scene.control.Tab;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CommandSondage implements CommandExecutor {

    HashMap<UUID, Boolean> vote = new HashMap<>();
    Boolean runningcommand = false;
    private Main main;

    public CommandSondage(Main main) {

        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;


        if (sender instanceof Player) {

            if (args.length == 0) {

                player.sendMessage(ChatColor.RED + "Vous devez faire /sondage avec le titre de votre sondage ");

                return true;

            }
            if (args[0].equalsIgnoreCase("oui")) {
                if (runningcommand) {
                    vote.put(player.getUniqueId(), true);
                    player.sendMessage("§aVous avez voté oui");

                } else {

                }
                return true;
            }
            if (args[0].equalsIgnoreCase("non")) {
                if (runningcommand) {
                    vote.put(player.getUniqueId(), false);
                    player.sendMessage("§aVous avez voté non");

                } else {

                }
                return true;
            }
            if (runningcommand) {
                player.sendMessage("§4Un sondage est déjà en cours !");
                player.playSound(player.getLocation(), Sound.VILLAGER_HAGGLE, 1f, 1f);
                return true;
            }
            runningcommand = true;
            String sondage = String.join(" ", args);
            TextComponent yes = new TextComponent("[" + "§6OUI" + "]");
            TextComponent no = new TextComponent("[" + "§6NON" + "]");

            for (Player players : Bukkit.getOnlinePlayers()) {

                players.sendMessage("[" + ChatColor.GOLD + "Sondage" + ChatColor.WHITE + "] " + ChatColor.GREEN + sondage);
                players.sendMessage("");
                players.spigot().sendMessage(yes, no);
            }
            yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sondage oui"));
            no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sondage non"));

            new BukkitRunnable() {

                @Override
                public void run() {

                    int countYes = 0;
                    int countNo = 0;


                    for (Boolean result : vote.values()) {
                        if (result) {
                            countYes++;

                        } else {
                            countNo++;
                        }
                    }
                    Bukkit.broadcastMessage("§eNombre de oui : " + countYes);
                    Bukkit.broadcastMessage("§eNombre de non : " + countNo);
                    runningcommand = false;
                }
            }.runTaskLater(main, 45 * 20);

        }

        return false;
    }
}
