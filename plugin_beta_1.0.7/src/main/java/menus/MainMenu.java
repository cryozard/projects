package menus;

import config.Configs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tools.ToolManager;
import toolsenhanced.toolsenhanced.CryoTools;

public class MainMenu {

    private final Configs configs = CryoTools.getConfigs();
    private final ToolManager toolManager = CryoTools.getToolManager();

    public void openMenu(ItemStack tool, Player player){

        Inventory menu = Bukkit.createInventory(new MenuHolder(), 9, "Tool Menu");
        for (int i = 0; i < menu.getSize(); i++){
            menu.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        }






        menu.setItem(3, setAutoSellItem(tool));
        menu.setItem(1, tool);

        player.openInventory(menu);
    }

    public ItemStack setAutoSellItem(ItemStack tool){
        String autoSell;
        if (!configs.isEnableAutoSell())
            autoSell = ChatColor.GRAY + "Disabled";
        else if (toolManager.hasAutoSell(tool)){
            if (toolManager.autoSellEnabled(tool))
                autoSell = "Enabled";
            else
                autoSell = "Disabled";
        }
        else
            autoSell = ChatColor.GRAY + "Locked";
        ItemStack autoSellIcon = new ItemStack(Material.GOLD_INGOT);
        autoSellIcon.addUnsafeEnchantment(Enchantment.DIG_SPEED, 1);
        ItemMeta autoSellIconMeta = autoSellIcon.getItemMeta();
        autoSellIconMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        autoSellIconMeta.setDisplayName(ChatColor.GOLD + "Autosell: " + autoSell);
        autoSellIcon.setItemMeta(autoSellIconMeta);
        return autoSellIcon;
    }
}
