package tools;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import config.Configs;
import data.BlockMap;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import net.brcdev.shopgui.ShopGuiPlusApi;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import toolsenhanced.toolsenhanced.CryoTools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToolManager {

    private final NamespacedKey levelKey = new NamespacedKey(CryoTools.getPlugin(), "level");
    private final NamespacedKey currentExpKey = new NamespacedKey(CryoTools.getPlugin(), "currentExp");
    private final NamespacedKey totalLevelExpKey = new NamespacedKey(CryoTools.getPlugin(), "totalLevelExp");
    private final NamespacedKey maxLevelKey = new NamespacedKey(CryoTools.getPlugin(), "maxLevel");
    private final NamespacedKey extraDropsKey = new NamespacedKey(CryoTools.getPlugin(), "extraDrops");

    private final NamespacedKey autoSell = new NamespacedKey(CryoTools.getPlugin(), "autoSell");
    private final NamespacedKey autoReplant = new NamespacedKey(CryoTools.getPlugin(), "autoReplant");

    private final Map<Player, Boolean> taskBooleanMap = new HashMap<>();
    private final Map<Player, Double> taskExpMap = new HashMap<>();
    private final Map<Player, Double> taskSellMap = new HashMap<>();
    DecimalFormat df = new DecimalFormat("#.##");
    private final Configs configs = CryoTools.getConfigs();


    public boolean isCryoTool(ItemStack item){
        ItemMeta meta = null;
        if (item != null) meta = item.getItemMeta();
        PersistentDataContainer data = null;
        if(meta != null) data = meta.getPersistentDataContainer();
        return data != null && data.has(getLevelKey(), PersistentDataType.INTEGER);
    }
    public int calcExp(ItemStack item){
        if (isCryoTool(item)) return (int)Math.pow((getLevel(item)/ configs.getXValue()), configs.getYValue()) + configs.getMaxLevel();
        return 0;
    }

    public void updateItem(ItemStack item, BlockMap blockMap, Block block, Player player){
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        int level = getLevel(item);
        double currentExp = getCurrentExp(item);
        int totalLevelExp = getTotalLevelExp(item);
        if (level != configs.getMaxLevel()) {
            currentExp += blockMap.getExpMap().get(block.getType());
            dataContainer.set(getCurrentExpKey(), PersistentDataType.DOUBLE, currentExp);
            if (currentExp >= totalLevelExp) {
                currentExp -= totalLevelExp;
                dataContainer.set(getCurrentExpKey(), PersistentDataType.DOUBLE, currentExp);
                ++level;
                dataContainer.set(getLevelKey(), PersistentDataType.INTEGER, level);
                dataContainer.set(getTotalLevelExpKey(), PersistentDataType.INTEGER, calcExp(item));
                player.sendMessage(ChatColor.GREEN + "Your tool leveled up to " + level + "!");
                player.playSound(player.getLocation(), "entity.player.levelup", 1.0F, 1.0F);

                calcExtraDrops(item);
            }
            meta.setLore(getStrings(level, currentExp, totalLevelExp));
            item.setItemMeta(meta);
        }
        if (!(level >= configs.getMaxLevel() && !hasAutoSell(item) || !autoSellEnabled(item) || !configs.isEnableAutoSell()))
            periodicMessage(item, blockMap, block, player, level);
    }

    private List<String> getStrings(int level, double currentExp, int totalLevelExp) {
        List<String> lore = new ArrayList<>();
        if (level == configs.getMaxLevel()){
            lore.add(ChatColor.RED + "" + ChatColor.BOLD + ("Level: " + level));

        }else{
            lore.add(ChatColor.AQUA + ("Level: " + level));
            lore.add(ChatColor.AQUA +(currentExp + "/" + totalLevelExp + "XP"));
        }
        return lore;
    }

    public ItemStack createItem(Material material, int level){
        if (level < 1) level = 1;
        if (level > configs.getMaxLevel()) level = configs.getMaxLevel();
        ItemStack item = new ItemStack(material);
        setKey(item, getLevelKey(), PersistentDataType.INTEGER, level);
        setKey(item, getCurrentExpKey(), PersistentDataType.DOUBLE, 0.0);
        setKey(item, getTotalLevelExpKey(), PersistentDataType.INTEGER, calcExp(item));
        setKey(item, getMaxLevelKey(), PersistentDataType.INTEGER, configs.getMaxLevel());
        setKey(item, getExtraDropsKey(), PersistentDataType.INTEGER, 0);
        setKey(item, getAutoSellKey(), PersistentDataType.BYTE, (byte) 0);

        switch (material){
            case NETHERITE_HOE:
                setKey(item, getAutoReplantKey(), PersistentDataType.BYTE, (byte) 0);
        }

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(configs.getToolName());
        meta.setUnbreakable(true);

        meta.setLore(getStrings(level, getCurrentExp(item), getTotalLevelExp(item)));
        item.setItemMeta(meta);
        calcExtraDrops(item);
        return item;
    }

    public void setItem(ItemStack item, int level){
        if (level < 1) level = 1;
        if (level > configs.getMaxLevel()) level = configs.getMaxLevel();
        setKey(item, getLevelKey(), PersistentDataType.INTEGER, level);
        setKey(item, getCurrentExpKey(), PersistentDataType.DOUBLE, 0.0);
        setKey(item, getTotalLevelExpKey(), PersistentDataType.INTEGER, calcExp(item));
        ItemMeta meta = item.getItemMeta();

        meta.setLore(getStrings(level, getCurrentExp(item), getTotalLevelExp(item)));
        item.setItemMeta(meta);
        calcExtraDrops(item);
    }
    private void periodicMessage(ItemStack item, BlockMap blockMap, Block block, Player player, int level) {
        player.sendMessage("running scheduler");
        if (!taskBooleanMap.containsKey(player))
            taskBooleanMap.put(player, false);
        if (!taskExpMap.containsKey(player))
            taskExpMap.put(player, blockMap.getExpMap().get(block.getType()));
        else
            taskExpMap.put(player, taskExpMap.get(player) + blockMap.getExpMap().get(block.getType()));

        if (!taskBooleanMap.get(player)){
            taskBooleanMap.put(player, true);
            Bukkit.getScheduler().runTaskLater(CryoTools.getPlugin(), task ->{
                TextComponent periodicMessage = new TextComponent();
                if (level != configs.getMaxLevel()){
                    periodicMessage.setText(item.getItemMeta().getDisplayName() + ChatColor.BOLD + ChatColor.WHITE + " >> Gained " + ChatColor.GREEN + df.format(taskExpMap.get(player)) + "XP");
                    if (hasAutoSell(item) && configs.isEnableAutoSell() && autoSellEnabled(item) && taskSellMap.get(player) != null)
                        periodicMessage.addExtra(" | $" + df.format(taskSellMap.get(player)));
                }
                else {
                    if (taskSellMap.get(player) != null)
                        periodicMessage.setText(item.getItemMeta().getDisplayName() + ChatColor.BOLD + ChatColor.GREEN + " >> Gained: $" + df.format(taskSellMap.get(player)));
                }
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, periodicMessage);
                taskExpMap.remove(player);
                taskBooleanMap.remove(player);
                taskSellMap.remove(player);
            }, 50L);
        }
    }
    public boolean hasAutoSell(ItemStack item){
        if (CryoTools.getEconomy() == null) return false;
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = null;

        if(meta != null) data = meta.getPersistentDataContainer();
        if(data != null && data.has(getLevelKey(), PersistentDataType.INTEGER)){
            return getLevel(item) >= configs.getAutoSellLevel();
        }
        return false;
    }
    public void autoSell(BlockBreakEvent event, Player player, ItemStack heldItem, List<ItemStack> dropList) {
        if (CryoTools.getEconomy() != null){
            if (hasAutoSell(heldItem) && configs.isEnableAutoSell() && autoSellEnabled(heldItem)) {
                event.setDropItems(false);
                for (ItemStack d : dropList) {
                    if (CryoTools.usingShopGuiPlus()){
                        if (ShopGuiPlusApi.getItemStackShopItem(player, d) != null){
                            CryoTools.getEconomy().depositPlayer(player, ShopGuiPlusApi.getItemStackPriceSell(player, d));

                            if(!taskSellMap.containsKey(player))
                                taskSellMap.put(player, ShopGuiPlusApi.getItemStackPriceSell(player, d));
                            else taskSellMap.put(player, taskSellMap.get(player) + ShopGuiPlusApi.getItemStackPriceSell(player, d));
                        }
                    }

                }
            }
        }
    }

    public void calcExtraDrops(ItemStack item) {
        if (configs.isEnableExtraDrops()) {
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer data = null;

            if(meta != null) data = meta.getPersistentDataContainer();
            if(data != null && data.has(getLevelKey(), PersistentDataType.INTEGER)){
                int j = -1;
                for (int i = 0; i <= getLevel(item); i += configs.getLevelsForExtraDrops()){
                    j++;
                }
                data.set(getExtraDropsKey(), PersistentDataType.INTEGER, j);
                item.setItemMeta(meta);
            }
        }
    }
    public int getExtraDrops(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = null;

        if(meta != null) data = meta.getPersistentDataContainer();
        if(data != null && isCryoTool(item)){
            return data.get(getExtraDropsKey(), PersistentDataType.INTEGER);
        }
        return 0;
    }
    public List<ItemStack> getExtraDropsList(BlockMap blockMap, Block block, ItemStack tool){
        List<ItemStack> drops = new ArrayList<>();
        if(getExtraDrops(tool) > 0)
            for (int i = 0; i < getExtraDrops(tool); i++)
                drops.addAll(blockMap.getDrops(block));
        return drops;
    }
    public boolean hasGriefPreventionPermissions(BlockBreakEvent event, Player player, Block block){
        String noBuildReason = GriefPrevention.instance.allowBreak(player, block, block.getLocation(), event);
        if(noBuildReason == null) return true;
        GriefPrevention.sendMessage(player, ChatColor.RED, noBuildReason);
        event.setCancelled(true);
        return false;
    }
    public boolean hasWorldGuardPermissions(Player player) {
        Location loc = BukkitAdapter.adapt(player.getLocation());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(loc);
        for (ProtectedRegion region : set){
            if (!region.isMember((LocalPlayer) player)) return false;
        }
        return true;
    }


    public int getLevel(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = null;

        if(meta != null) data = meta.getPersistentDataContainer();
        if(data != null && isCryoTool(item)){
            return data.get(getLevelKey(), PersistentDataType.INTEGER);
        }
        return 0;
    }
    public double getCurrentExp(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = null;

        if(meta != null) data = meta.getPersistentDataContainer();
        if(data != null && isCryoTool(item)){
            return data.get(getCurrentExpKey(), PersistentDataType.DOUBLE);
        }
        return 0;
    }
    public int getTotalLevelExp(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = null;

        if(meta != null) data = meta.getPersistentDataContainer();
        if(data != null && isCryoTool(item)){
            return data.get(getTotalLevelExpKey(), PersistentDataType.INTEGER);
        }
        return 0;
    }
    public void setKey(ItemStack item, NamespacedKey key, PersistentDataType dataType, double value){
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(key, dataType, value);
        item.setItemMeta(meta);
    }
    public void setKey(ItemStack item, NamespacedKey key, PersistentDataType dataType, int value){
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(key, dataType, value);
        item.setItemMeta(meta);
    }
    public void setKey(ItemStack item, NamespacedKey key, PersistentDataType dataType, byte value){
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(key, dataType, value);
        item.setItemMeta(meta);
    }

    public NamespacedKey getMaxLevelKey() {
        return maxLevelKey;
    }
    public NamespacedKey getCurrentExpKey() {
        return currentExpKey;
    }
    public NamespacedKey getTotalLevelExpKey() {
        return totalLevelExpKey;
    }
    public NamespacedKey getLevelKey() {
        return levelKey;
    }
    public NamespacedKey getExtraDropsKey() {
        return extraDropsKey;
    }
    public NamespacedKey getAutoSellKey() {
        return autoSell;
    }

    public NamespacedKey getAutoReplantKey() {
        return autoReplant;
    }

    public void setAutoSell(ItemStack tool, boolean bool){
        if (isCryoTool(tool)){
            if (bool) setKey(tool, getAutoSellKey(), PersistentDataType.BYTE, (byte) 1);
            else setKey(tool, getAutoSellKey(), PersistentDataType.BYTE, (byte) 0);
        }

    }
    public boolean autoSellEnabled(ItemStack tool){
        ItemMeta meta = tool.getItemMeta();
        PersistentDataContainer data = null;

        if(meta != null) data = meta.getPersistentDataContainer();
        if(data != null && data.has(getLevelKey(), PersistentDataType.INTEGER)){
            return data.get(getAutoSellKey(), PersistentDataType.BYTE) == (byte) 1;
        }
        return false;
    }
}
