package com.enderdevelopment.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public final class Main extends JavaPlugin implements Listener {

    private Inventory gui;
    private boolean isGuiOpen = false;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        gui = Bukkit.createInventory(null, 9, Component.text("GUI").color(NamedTextColor.GOLD));
        ItemStack button = new ItemStack(Material.STONE_BUTTON);
        ItemMeta meta = button.getItemMeta();
        meta.displayName(Component.text("Open GUI").color(NamedTextColor.GOLD));
        button.setItemMeta(meta);
        gui.setItem(4, button);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().addItem(new ItemStack(Material.STONE_BUTTON));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getItem().getType() == Material.STONE_BUTTON) {
            if (!isGuiOpen) {
                event.getPlayer().openInventory(gui);
                isGuiOpen = true;
            }
        }
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        if (event.getOffHandItem() != null && event.getOffHandItem().getType() == Material.STONE_BUTTON) {
            if (!isGuiOpen) {
                event.getPlayer().openInventory(gui);
                isGuiOpen = true;
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(gui)) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.STONE_BUTTON) {
                event.getWhoClicked().closeInventory();
                isGuiOpen = false;
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().equals(gui)) {
            isGuiOpen = false;
        }
    }
}