package menus;

import config.Configs;
import data.ModelData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import toolsenhanced.toolsenhanced.CryoTools;

import java.util.*;

public class SkinsMenu {

    private final Configs configs = CryoTools.getConfigs();
    public void openGUI(Player player, Material material){

        Inventory skinsGUI = Bukkit.createInventory(new MenuHolder(), 54, "Skins Menu");

        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta barrierMeta = barrier.getItemMeta();
        barrierMeta.setDisplayName("Remove Skin");
        barrier.setItemMeta(barrierMeta);
        skinsGUI.addItem(barrier);
        for (ModelData m : configs.getModels()){
            if (m.getToolType().equals(material)){
                for (Integer i : configs.getModels().get(configs.getModels().indexOf(m)).getModelMap().keySet()){
                    ItemStack item = new ItemStack(material);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(configs.getModels().get(configs.getModels().indexOf(m)).getModelMap().get(i));
                    meta.setCustomModelData(i);
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    ArrayList<String> lore = new ArrayList<>();

                    Map<Integer, List<Material>> playerData = CryoTools.getDataManager().getPlayerData().get(player.getUniqueId()).getPlayerModelData();

                    if (playerData.containsKey(i) && playerData.get(i).contains(material)) lore.add(ChatColor.GOLD + "Unlocked");
                    else lore.add(ChatColor.GRAY + "Locked");

                    meta.setLore(lore);
                    item.setItemMeta(meta);
                    skinsGUI.addItem(item);
                }
            }
        }
        player.openInventory(skinsGUI);
    }
}
