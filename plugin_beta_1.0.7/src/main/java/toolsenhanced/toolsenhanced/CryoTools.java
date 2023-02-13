package toolsenhanced.toolsenhanced;

import commands.Commands;
import com.jeff_media.customblockdata.CustomBlockData;
import config.Configs;
import data.DataManager;
import data.PlayerData;
import listeners.HoeListener;
import listeners.ToolListener;
import menus.MainMenu;
import menus.MenuListener;
import menus.SkinsMenu;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import tools.HoeManager;
import tools.ToolManager;

import java.io.File;
import java.io.IOException;

public final class CryoTools extends JavaPlugin implements Listener {

    private static Economy econ;
    private static CryoTools instance;
    private static SkinsMenu skinsMenu;
    private static MainMenu mainMenu;
    private static DataManager dataManager;
    private static Configs configs;

    public static ToolManager getToolManager() {
        return toolManager;
    }

    private static ToolManager toolManager;

    public static HoeManager getHoeManager() {
        return hoeManager;
    }

    private static HoeManager hoeManager;

    @Override
    public void onEnable() {
        instance = this;
        CustomBlockData.registerListener(instance);

        File file = new File(getDataFolder(), "PlayerData");
        if (!file.exists()) file.mkdirs();

        saveDefaultConfig();
        getConfig();
        if (!setupEconomy()) this.getLogger().severe("No Vault dependency found! Autosell will not function!");

        if (usingShopGuiPlus()) this.getLogger().info("Using ShopGUIPlus.");
        configs = new Configs();
        dataManager = new DataManager(this);
        this.getCommand("cryotools").setTabCompleter(new Commands());
        this.getCommand("cryotools").setExecutor(new Commands());

        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);
        for (Player player : players){
            try {
                dataManager.getPlayerData().put(player.getUniqueId(), new PlayerData(player.getUniqueId()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        toolManager = new ToolManager();
        hoeManager = new HoeManager();
        mainMenu = new MainMenu();
        skinsMenu = new SkinsMenu();
        new ToolListener(this);
        new HoeListener(this);
        new MenuListener(this);

        System.out.println("Plugin setup complete.");
    }

    @Override
    public void onDisable() {
        System.out.println("Disabling plugin...");
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }
    public static CryoTools getPlugin(){
        return instance;
    }
    public static boolean usingShopGuiPlus(){
        return Bukkit.getPluginManager().getPlugin("ShopGUIPlus") != null;
    }
    public static boolean usingWorldGuard(){
        return Bukkit.getPluginManager().getPlugin("WorldGuard") != null;
    }
        public static boolean usingGriefPrevention(){
        return Bukkit.getPluginManager().getPlugin("GriefPrevention") != null;
    }
    public static SkinsMenu getSkinsMenu() {
        return skinsMenu;
    }
    public static MainMenu getMainMenu() {
        return mainMenu;
    }
    public static DataManager getDataManager() {
        return dataManager;
    }
    public static Configs getConfigs(){
        return configs;
    }
}
