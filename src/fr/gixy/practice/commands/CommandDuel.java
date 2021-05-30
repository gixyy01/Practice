package fr.gixy.practice.commands;

import fr.gixy.practice.Main;
import fr.gixy.practice.State;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class CommandDuel implements CommandExecutor {

    private Main main;
    HashMap<UUID, UUID> request = new HashMap<>();


    public CommandDuel(Main main) {

        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player player = (Player) sender;


        Location spawn1 = new Location(Bukkit.getServer().getWorld("Map1"), 77.5, 13.5, 139.5, -88.5f, -2.5f);
        Location spawn2 = new Location(Bukkit.getServer().getWorld("Map1"), 176.5, 13.5, 139.5, 93.5f, -2.5f);


        if (sender instanceof Player) {


            // Argument help pour voir la liste des commandes

            if (args.length == 0 || (args[0].equalsIgnoreCase("help"))) {


                player.sendMessage(ChatColor.GOLD + "Voici les commandes que vous pouvez faire :");
                player.sendMessage("");
                player.sendMessage(ChatColor.YELLOW + "/duel accept » Pour accepter la demande de duel");
                player.sendMessage("");
                player.sendMessage(ChatColor.YELLOW + "/duel <pseudo> » Pour envoyer une demande de duel à un joueur");

                return true;

            }


            if (args[0].equalsIgnoreCase("accept")) {
                Player target = Bukkit.getPlayer((args[1]));


                if (args.length == 2) {

                    ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                    ItemMeta swordM = sword.getItemMeta();
                    swordM.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
                    sword.setItemMeta(swordM);

                    ItemStack bow = new ItemStack(Material.BOW);
                    ItemMeta bowM = bow.getItemMeta();
                    bowM.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
                    bow.setItemMeta(bowM);

                    ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
                    ItemMeta helmetM = helmet.getItemMeta();
                    helmetM.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
                    helmet.setItemMeta(helmetM);

                    ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                    ItemMeta chestplateM = chestplate.getItemMeta();
                    chestplateM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                    chestplate.setItemMeta(chestplateM);

                    ItemStack leggins = new ItemStack(Material.DIAMOND_LEGGINGS);
                    ItemMeta legginsM = leggins.getItemMeta();
                    legginsM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                    leggins.setItemMeta(legginsM);

                    ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                    ItemMeta bootsM = boots.getItemMeta();
                    bootsM.addEnchant(Enchantment.PROTECTION_PROJECTILE, 1, true);
                    boots.setItemMeta(bootsM);

                    ItemStack goldenHead = new ItemStack(Material.GOLDEN_APPLE, 3);
                    ItemMeta goldenHeadM = goldenHead.getItemMeta();
                    goldenHeadM.setDisplayName(ChatColor.GOLD + "Golden Head");
                    goldenHead.setItemMeta(goldenHeadM);

                    ItemStack rod = new ItemStack(Material.FISHING_ROD);
                    ItemStack lava = new ItemStack(Material.LAVA_BUCKET, 2);
                    ItemStack water = new ItemStack(Material.WATER_BUCKET, 2);
                    ItemStack food = new ItemStack(Material.COOKED_BEEF, 64);
                    ItemStack arrow = new ItemStack(Material.ARROW, 32);
                    ItemStack cobble = new ItemStack(Material.COBBLESTONE, 64);
                    ItemStack goldenApple = new ItemStack(Material.GOLDEN_APPLE, 6);


                    request.put(player.getUniqueId(), target.getUniqueId());
                    player.teleport(spawn1);
                    target.teleport(spawn2);
                    player.setGameMode(GameMode.SURVIVAL);
                    target.setGameMode(GameMode.SURVIVAL);

                    player.getInventory().setItem(0, sword);
                    player.getInventory().setItem(1, rod);
                    player.getInventory().setItem(2, bow);
                    player.getInventory().setItem(3, goldenApple);
                    player.getInventory().setItem(4, goldenHead);
                    player.getInventory().setItem(5, lava);
                    player.getInventory().addItem(cobble);
                    player.getInventory().addItem(cobble);
                    player.getInventory().setItem(7, food);
                    player.getInventory().setItem(8, water);

                    player.getInventory().addItem(arrow);
                    player.getInventory().setHelmet(helmet);
                    player.getInventory().setChestplate(chestplate);
                    player.getInventory().setLeggings(leggins);
                    player.getInventory().setBoots(boots);

                    target.getInventory().setItem(0, sword);
                    target.getInventory().setItem(1, rod);
                    target.getInventory().setItem(2, bow);
                    target.getInventory().setItem(3, goldenApple);
                    target.getInventory().setItem(4, goldenHead);
                    target.getInventory().setItem(5, lava);
                    target.getInventory().addItem(cobble);
                    target.getInventory().addItem(cobble);
                    target.getInventory().setItem(7, food);
                    target.getInventory().setItem(8, water);

                    target.getInventory().addItem(arrow);
                    target.getInventory().setHelmet(helmet);
                    target.getInventory().setChestplate(chestplate);
                    target.getInventory().setLeggings(leggins);
                    target.getInventory().setBoots(boots);
                    main.getPlayer().add(player);
                    main.getPlayer().add(target);


                    new BukkitRunnable() {


                        int timer = 6;


                        @Override
                        public void run() {

                            timer--;

                            main.setState(State.STARTING);


                            if (timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1) {

                                for (Player players : Bukkit.getOnlinePlayers()) {

                                    players.sendMessage(ChatColor.YELLOW + "Début du combat dans " + ChatColor.GOLD + timer + ChatColor.YELLOW + " secondes !");
                                }


                            }


                            if (timer == 0) {


                                main.setState(State.PLAYING);
                                cancel();
                                for (Player players : Bukkit.getOnlinePlayers()) {

                                    players.sendMessage(ChatColor.GRAY + "Début du combat !");
                                }
                            }


                        }
                    }.runTaskTimer(main, 20, 20);


                }

                return true;
            }

            Player target = Bukkit.getServer().getPlayer(args[0]);


            if (player == target) {

                player.sendMessage(ChatColor.RED + "Vous ne pouvez pas vous demander en duel vous-même !");
                player.playSound(player.getLocation(), Sound.VILLAGER_HAGGLE, 1f, 1f);

                return true;
            }

            if (request.containsKey(target.getUniqueId())) {

                player.sendMessage("Ce joueur est déjà en combat !");


                return true;

            }


            if (target == null) {

                player.sendMessage(ChatColor.RED + "Le joueur n'est pas en ligne");


            } else {


                TextComponent duel = new TextComponent(ChatColor.GOLD + player.getName() + ChatColor.YELLOW + " vous a envoyé une demande de duel, cliquez sur ce message pour accepter la demande");

                duel.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel accept " + player.getName()));
                duel.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.YELLOW + "Cliquez sur ce message pour accepter sa demande !").create()));
                target.spigot().sendMessage(duel);
                request.put(player.getUniqueId(), target.getUniqueId());
                target.playSound(target.getLocation(), Sound.NOTE_PLING, 1f, 1f);


            }


            return true;
        }


        return false;
    }
}
