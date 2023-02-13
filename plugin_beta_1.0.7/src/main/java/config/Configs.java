package config;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import data.ModelData;
import toolsenhanced.toolsenhanced.CryoTools;
import java.util.ArrayList;

public class Configs {

    private String toolName;
    private int maxLevel;
    private int autoSellLevel;
    private boolean enableAutoSell;
    private boolean enableExtraDrops;
    private int levelsForExtraDrops;
    private double xValue;
    private double yValue;
    private boolean enableAutoReplant;
    private int autoReplantLevel;
    private static final FileConfiguration file = CryoTools.getPlugin().getConfig();
    private final ArrayList<ModelData> models = new ArrayList<>();

    public Configs(){

        toolName = ChatColor.translateAlternateColorCodes('&', file.getString("toolName", "Cryo Hoe"));
        maxLevel = file.getInt("maxLevel", 100);
        autoSellLevel = file.getInt("autoSellLevel", maxLevel/2);
        autoReplantLevel = file.getInt("autoReplantLevel", maxLevel/2);
        levelsForExtraDrops = file.getInt("levelsForExtraDrop", maxLevel/10);
        xValue = file.getDouble("xValue", 0.5);
        yValue = file.getDouble("yValue", 2.0);
        enableExtraDrops = file.getBoolean("enableExtraDrops", false);
        if (levelsForExtraDrops < 1 || levelsForExtraDrops > maxLevel) enableExtraDrops = false;
        enableAutoReplant = file.getBoolean("enableAutoReplant", false);
        if (autoReplantLevel < 1 || autoReplantLevel > maxLevel) enableAutoReplant = false;
        enableAutoSell = file.getBoolean("enableAutoSell", false);
        if (autoSellLevel < 1 || autoSellLevel > maxLevel) enableAutoSell = false;

        ConfigurationSection section = file.getConfigurationSection("models");
        if (section != null) {
            for(String i: section.getKeys(false)){
                try {
                    ModelData modelData = new ModelData(Material.valueOf(i));
                    ConfigurationSection innerSection = file.getConfigurationSection("models." + i);
                    for (String j : innerSection.getKeys(false)){
                        int num = innerSection.getInt(j + ".id");
                        String name = innerSection.getString(j + ".name");
                        name = ChatColor.translateAlternateColorCodes('&', name);
                        modelData.getModelMap().put(num, name);
                    }
                    models.add(modelData);

                } catch (Exception exception){ exception.printStackTrace();}
            }
        }
    }

    public String getToolName() {
        return toolName;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getAutoSellLevel() {
        return autoSellLevel;
    }

    public boolean isEnableAutoSell() {
        return enableAutoSell;
    }

    public boolean isEnableExtraDrops() {
        return enableExtraDrops;
    }

    public int getLevelsForExtraDrops() {
        return levelsForExtraDrops;
    }

    public double getXValue() {
        return xValue;
    }

    public double getYValue() {
        return yValue;
    }

    public boolean isEnableAutoReplant() {
        return enableAutoReplant;
    }

    public int getAutoReplantLevel() {
        return autoReplantLevel;
    }
    public ArrayList<ModelData> getModels() {
        return models;
    }
}
