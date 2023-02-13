package tools;

import config.Configs;
import data.BlockMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import toolsenhanced.toolsenhanced.CryoTools;

public class HoeManager extends ToolManager{

    private final Configs configs = CryoTools.getConfigs();
    private final BlockMap blockMap = new BlockMap("cropList");;
    private final ToolManager toolManager = CryoTools.getToolManager();

    public void autoReplant(Block block, ItemStack heldItem, Material material) {
        if (hasAutoReplant(heldItem) && configs.isEnableAutoReplant())
            replantCrop(block.getLocation(), material, CryoTools.getPlugin());
    }
    public boolean isFullyGrown(Block block){
        Ageable ageable = (Ageable) block.getBlockData();
        return ageable.getAge() == ageable.getMaximumAge();
    }
    private void replantCrop(Location location, Material material, Plugin plugin){
        Bukkit.getScheduler().runTaskLater(plugin, () -> location.getBlock().setType(material), 20L);
    }
    private boolean hasAutoReplant(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = null;

        if(meta != null) data = meta.getPersistentDataContainer();
        if(data != null && data.has(toolManager.getLevelKey(), PersistentDataType.INTEGER)){
            return data.get(toolManager.getLevelKey(), PersistentDataType.INTEGER) >= configs.getAutoReplantLevel();
        }
        return false;
    }
    public BlockMap getBlockMap() {
        return blockMap;
    }

}
