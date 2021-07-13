package fr.gixy.practice.listeners;

import fr.gixy.practice.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class GoldenHeadEvent implements Listener {

    private Main main;

    public GoldenHeadEvent(Main main) {

        this.main = main;
    }

    @EventHandler
    public void onGoldenHeadEvent(PlayerItemConsumeEvent event) {

        ItemStack item = event.getItem();
        Player player = event.getPlayer();

        if (event.getItem().getType() == Material.GOLDEN_APPLE) {
            if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Golden Head")) {

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.removePotionEffect(PotionEffectType.REGENERATION);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 8, 0));

                    }
                }.runTaskLater(main, 3);
            }
        }
    }
}