package toolsenhanced.toolsenhanced;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentRandomizer {


    public void randomizer(Material toolType, Player player){
        ItemStack tool = new ItemStack(toolType);
        ItemMeta toolMeta = tool.getItemMeta();
        //tool.addEnchantments(possibleEnchantments);

    }

    public Map<Enchantment, Integer> getPossibleEnchantments(Material toolType){
        Map<Enchantment, Integer> possibleEnchantments = new HashMap<>();;

        switch (toolType){
            case NETHERITE_HOE:
                possibleEnchantments.put(Enchantment.DIG_SPEED, 1);
        }

        return new HashMap<>();
    }
}
