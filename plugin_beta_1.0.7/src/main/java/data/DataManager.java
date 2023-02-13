package data;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import toolsenhanced.toolsenhanced.CryoTools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataManager implements Listener {


    public Map<UUID, PlayerData> getPlayerData() {
        return playerData;
    }

    private final Map<UUID, PlayerData> playerData = new HashMap<>();

    public DataManager(CryoTools plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();
        try {
            playerData.put(player.getUniqueId(), new PlayerData(player.getUniqueId()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        playerData.remove(player.getUniqueId());
    }


}
