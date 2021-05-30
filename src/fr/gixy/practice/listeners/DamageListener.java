package fr.gixy.practice.listeners;

import fr.gixy.practice.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    private Main main;

    public DamageListener(Main main) {

        this.main = main;

    }

    @EventHandler
    public void onDamageEvent(EntityDamageEvent event) {

        Entity victim = event.getEntity();

        if (victim instanceof Player) {

            Player player = (Player) victim;

            if (player.getHealth() <= event.getDamage()) {

                event.setDamage(0);
                main.eliminate(player);


            }
        }


    }

    @EventHandler
    public void onPvp(EntityDamageByEntityEvent event) {


        Entity victim = event.getEntity();

        if (!(event.getEntityType().equals(EntityType.PLAYER))) {


            return;


        }

        if (victim instanceof Player) {

            Player player = (Player) victim;
            Player damager = (Player) event.getDamager();
            Player killer = null;

            if (player.getHealth() <= event.getDamage()) {


                if (event.getEntity() instanceof Player) {


                    if (damager instanceof Player) killer = (Player) damager;


                    if (damager instanceof Arrow) {

                        Arrow arrow = (Arrow) damager;
                        if (arrow.getShooter() instanceof Player) {


                            killer = (Player) arrow.getShooter();
                        }


                    }
                    killer.sendMessage(ChatColor.GOLD + "Vous avez tué " + ChatColor.GOLD + player.getName());
                    event.setDamage(0);
                    main.eliminate(player);


                }

            }


        }

    }
}


