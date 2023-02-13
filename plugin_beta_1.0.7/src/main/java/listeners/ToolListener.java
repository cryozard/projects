package listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareGrindstoneEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import tools.ToolManager;
import toolsenhanced.toolsenhanced.CryoTools;

public class ToolListener implements Listener {

    public ToolListener(CryoTools plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    private final static ToolManager toolManager = CryoTools.getToolManager();


    @EventHandler
    public void preventEnchant(PrepareItemEnchantEvent event){
        if (toolManager.isCryoTool(event.getItem()))
            event.setCancelled(true);
    }
    @EventHandler
    public void preventRepair(PrepareAnvilEvent event){
        if (toolManager.isCryoTool(event.getInventory().getItem(0)) || toolManager.isCryoTool(event.getInventory().getItem(1)))
            event.getInventory().setRepairCost(Integer.MAX_VALUE);
    }
    @EventHandler
    public void preventSmithing(PrepareSmithingEvent event){
        if (toolManager.isCryoTool(event.getInventory().getItem(0)) || toolManager.isCryoTool(event.getInventory().getItem(1)))
            event.setResult(null);
    }
    @EventHandler
    public void preventGrindstone(PrepareGrindstoneEvent event){
        if (toolManager.isCryoTool(event.getInventory().getItem(0)) || toolManager.isCryoTool(event.getInventory().getItem(1)))
            event.setResult(null);
    }
    @EventHandler
    public void preventItemCraft(PrepareItemCraftEvent event){
        if (toolManager.isCryoTool(event.getInventory().getItem(0)) || toolManager.isCryoTool(event.getInventory().getItem(1)))
            event.getInventory().setResult(null);
    }
}
