package data;


import org.bukkit.Material;
import toolsenhanced.toolsenhanced.CryoTools;

import java.io.*;
import java.util.*;

public class PlayerData {

    private final Map<Integer, List<Material>> modelData = new Hashtable<>();
    private final File playerData;

    public PlayerData(UUID playerUUID) throws IOException{
        playerData = new File(CryoTools.getPlugin().getDataFolder() + "/PlayerData/", playerUUID.toString());

        if (!playerData.exists())
            playerData.createNewFile();
        else{
            BufferedReader br = new BufferedReader(new FileReader(playerData));
            br.lines().forEach(line ->{
                String[] split = line.split("-");
                int key = Integer.parseInt(split[0]);
                Material toolType = Material.valueOf(split[1]);
                if (!modelData.containsKey(key)) {
                    modelData.put(key, new ArrayList<>());
                }
                modelData.get(key).add(toolType);
            });
            br.close();
        }
    }
    public Map<Integer, List<Material>> getPlayerModelData(){
        return modelData;
    }
    public void setData(Integer data, Material toolType) throws IOException {
        if (!modelData.containsKey(data))
            modelData.put(data, new ArrayList<>());

        List<Material> toolTypes = modelData.get(data);
        if(!toolTypes.contains(toolType)){
            toolTypes.add(toolType);
            BufferedWriter bw = new BufferedWriter(new FileWriter(playerData));
            bw.write(data+"-"+toolType+"\n");
            bw.close();
        }
    }
}
