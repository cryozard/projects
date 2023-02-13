package data;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import toolsenhanced.toolsenhanced.CryoTools;

import java.util.*;
import java.util.stream.Collectors;

public class BlockMap {

    private final Map<Material, Double> expMap = new HashMap<>();

    public BlockMap(String path){
        ConfigurationSection section = CryoTools.getPlugin().getConfig().getConfigurationSection(path);
        if (section != null) {
            for(String i: section.getKeys(false)){
                try {
                    Material block = Material.valueOf(section.getString(i + ".block"));
                    Double exp = section.getDouble(i + ".exp");
                    expMap.put(block, exp);
                } catch (Exception exception){ System.out.println("WARNING: Failed to fetch section number " + i + "of config path " + path + ". This item will not be loaded!");}
            }
        }
    }

    public List<ItemStack> getDrops(Block block){
        return block.getDrops().stream().distinct().collect(Collectors.toList());
    }
    public Map<Material, Double> getExpMap() {
        return expMap;
    }
}
