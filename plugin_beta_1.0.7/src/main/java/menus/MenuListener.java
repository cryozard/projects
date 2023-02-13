package menus;

import config.Configs;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tools.ToolManager;
import toolsenhanced.toolsenhanced.CryoTools;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MenuListener implements Listener {

    private final Configs configs = CryoTools.getConfigs();
    private final ToolManager toolManager = CryoTools.getToolManager();
    private final MainMenu mainMenu = CryoTools.getMainMenu();

    public MenuListener(CryoTools plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onClickItemEvent(InventoryClickEvent event){
        if (event.getInventory().getHolder() instanceof MenuHolder){

            if (event.getWhoClicked() instanceof Player){
                Player player = (Player) event.getWhoClicked();
                ItemStack heldItem = player.getInventory().getItemInMainHand();

                if (event.getView().getTitle().equals("Skins Menu")){
                    ItemStack skin = event.getCurrentItem();
                    ItemMeta itemMeta = heldItem.getItemMeta();
                    if (skin == null){
                        event.setCancelled(true);
                        return;
                    }
                    if (CryoTools.getToolManager().isCryoTool(heldItem)){
                        if (skin.getType().equals(Material.BARRIER)){
                            if (itemMeta.hasCustomModelData()){
                                if (itemMeta.getCustomModelData() != 0){
                                    itemMeta.setCustomModelData(0);
                                    itemMeta.setDisplayName(configs.getToolName());
                                    heldItem.setItemMeta(itemMeta);
                                    player.sendMessage("Removed skin from this tool.");
                                }
                                else player.sendMessage("This tool has no skin.");
                            }
                            else player.sendMessage("This tool has no skin.");
                        }

                        if(skin.hasItemMeta() && !skin.getType().equals(Material.BARRIER)){
                            ItemMeta skinMeta = skin.getItemMeta();
                            Map<Integer, List<Material>> playerData = CryoTools.getDataManager().getPlayerData().get(player.getUniqueId()).getPlayerModelData();

                            if (playerData.containsKey(skinMeta.getCustomModelData()) && playerData.get(skinMeta.getCustomModelData()).contains(skin.getType())){


                                if (itemMeta != null && (!itemMeta.hasCustomModelData() || skinMeta.getCustomModelData() != itemMeta.getCustomModelData())) {
                                    itemMeta.setDisplayName(skinMeta.getDisplayName());
                                    itemMeta.setCustomModelData(skinMeta.getCustomModelData());
                                    heldItem.setItemMeta(itemMeta);
                                    player.sendMessage("Set tool skin to " + skinMeta.getDisplayName());
                                }
                                else
                                    player.sendMessage("This skin is already set.");
                            }
                            else player.sendMessage("This skin is locked.");
                        }

                    }
                }
                else if (event.getView().getTitle().equals("Tool Menu")){
                    if(event.getClickedInventory() != null){
                        if (Objects.equals(event.getCurrentItem(), event.getClickedInventory().getItem(3))){
                            if (toolManager.hasAutoSell(heldItem)){
                                if (toolManager.autoSellEnabled(heldItem)){
                                    toolManager.setAutoSell(heldItem, false);
                                    event.setCurrentItem(mainMenu.setAutoSellItem(heldItem));
                                    player.sendMessage(ChatColor.GREEN + "Disabled Auto-Sell for " + heldItem.getItemMeta().getDisplayName());
                                }
                                else {
                                    toolManager.setAutoSell(heldItem, true);
                                    event.setCurrentItem(mainMenu.setAutoSellItem(heldItem));
                                    player.sendMessage(ChatColor.GREEN + "Enabled Auto-Sell for " + heldItem.getItemMeta().getDisplayName());
                                }
                            }
                        }
                    }

                }
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMoveItemEvent(InventoryMoveItemEvent event){
        if (event.getInitiator().getHolder() instanceof MenuHolder) event.setCancelled(true);
    }
}
