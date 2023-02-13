package data;

import org.bukkit.Material;
import java.util.HashMap;
import java.util.Map;

public class ModelData {

    private final Material toolType;
    private final Map<Integer, String> modelMap = new HashMap<>();
    public ModelData(Material toolType){
        this.toolType = toolType;
    }
    public Material getToolType() {
        return toolType;
    }
    public Map<Integer, String> getModelMap() {
        return modelMap;
    }
}
