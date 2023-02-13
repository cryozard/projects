package listeners;

import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import tools.HoeManager;
import toolsenhanced.toolsenhanced.CryoTools;
import java.util.List;
import java.util.Objects;

public class HoeListener implements Listener {

    private final HoeManager hoeManager = CryoTools.getHoeManager();

    public HoeListener(CryoTools plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        // Handle player placed blocks
        Block block = event.getBlock();
        if (hoeManager.getBlockMap().getExpMap().containsKey(block.getType())){
            PersistentDataContainer customBlockData = new CustomBlockData(block, CryoTools.getPlugin());
            try{
                Ageable ageable = (Ageable) block.getBlockData();
            } catch (ClassCastException e){
                customBlockData.set(new NamespacedKey(CryoTools.getPlugin(), "playerPlacedBlock"), PersistentDataType.INTEGER, 0);
            }
        }
    }

    @EventHandler
    public void onCropBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        List<ItemStack> drops = (List<ItemStack>) block.getDrops();
        PersistentDataContainer customBlockData = new CustomBlockData(block, CryoTools.getPlugin());

        // Is this block in the list
        if (hoeManager.getBlockMap().getExpMap().containsKey(block.getType())){
            ItemStack heldItem =  player.getInventory().getItemInMainHand();


            // Is player holding correct tool
            if (hoeManager.isCryoTool(heldItem) && heldItem.getType().equals(Material.NETHERITE_HOE)) {

                // Check permissions
                if (CryoTools.usingWorldGuard() && !hoeManager.hasWorldGuardPermissions(player)) event.setCancelled(true);
                if (CryoTools.usingGriefPrevention() && !hoeManager.hasGriefPreventionPermissions(event, player, block)) return;
                // Is crop fully grown
                if (!customBlockData.has(new NamespacedKey(CryoTools.getPlugin(), "playerPlacedBlock"), PersistentDataType.INTEGER)) {
                    try {
                        if (hoeManager.isFullyGrown(block)) hoeManager.autoReplant(block, heldItem, block.getType());
                    }
                    catch (ClassCastException ignored){}

                    if(!hoeManager.hasAutoSell(heldItem) || !hoeManager.autoSellEnabled(heldItem) && !hoeManager.getExtraDropsList(hoeManager.getBlockMap(), block, heldItem).isEmpty())
                        for (ItemStack drop : hoeManager.getExtraDropsList(hoeManager.getBlockMap(), block, heldItem))
                            Objects.requireNonNull(block.getLocation().getWorld()).dropItemNaturally(block.getLocation(), drop);
                    else drops.addAll(hoeManager.getExtraDropsList(hoeManager.getBlockMap(), block, heldItem));

                    hoeManager.autoSell(event, player, heldItem, drops);
                    hoeManager.updateItem(heldItem, hoeManager.getBlockMap(), block, player);
                }
            }
        }
        if(customBlockData.has(new NamespacedKey(CryoTools.getPlugin(), "playerPlacedBlock"), PersistentDataType.INTEGER))
            customBlockData.remove(new NamespacedKey(CryoTools.getPlugin(), "playerPlacedBlock"));
    }
}
