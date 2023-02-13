package commands;

import config.Configs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import tools.ToolManager;
import toolsenhanced.toolsenhanced.CryoTools;

import java.io.IOException;
import java.util.*;

public class Commands implements TabCompleter, CommandExecutor {

    private final Map<String, Material> argMap = new HashMap<>();
    private final Configs configs = CryoTools.getConfigs();
    private final String incorrectCommandMessage = ChatColor.GOLD + "Incorrect command usage: /cryotools give <playername> <tooltype> <level>";
    private final String incorrectSetCommand = ChatColor.GOLD + "Incorrect command usage: /cryotools set <level>";
    private final String noPermissionsMessage = ChatColor.RED + "You do not have permissions to use this command.";

    public Commands(){
        argMap.put("hoe", Material.NETHERITE_HOE);
        argMap.put("pickaxe", Material.NETHERITE_PICKAXE);
        argMap.put("shovel", Material.NETHERITE_SHOVEL);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> arguments = new ArrayList<>();
        List<String> completions = new ArrayList<>();
        if(args.length == 1){
            arguments.add("give");
            arguments.add("set");
            arguments.add("help");
            arguments.add("info");
            arguments.add("skins");
            StringUtil.copyPartialMatches(args[0], arguments, completions);
        }
        else if(args.length == 2){
            if (args[0].equalsIgnoreCase("give")){
                Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                Bukkit.getServer().getOnlinePlayers().toArray(players);
                for(Player p : players) arguments.add(p.getName());
            }
            else if (args[0].equalsIgnoreCase("set")){
                arguments.add("1");
            }
            else if (args[0].equalsIgnoreCase("skins")){
                arguments.add("lock");
                arguments.add("unlock");
            }
            StringUtil.copyPartialMatches(args[1], arguments, completions);
        }
        else if(args.length == 3){
            if (args[0].equalsIgnoreCase("give")){
                arguments.addAll(argMap.keySet());
            }
            else if (args[0].equalsIgnoreCase("set")) arguments.add("1");

            else if(args[1].equalsIgnoreCase("unlock") || args[1].equalsIgnoreCase("lock")){
                arguments.add("*");
                Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                Bukkit.getServer().getOnlinePlayers().toArray(players);
                for(Player p : players) arguments.add(p.getName());
            }
            StringUtil.copyPartialMatches(args[2], arguments, completions);
        }
        else if(args.length == 4){
            if (args[0].equalsIgnoreCase("give")){
                arguments.add("1");
            }
            else if (args[0].equalsIgnoreCase("skins") && args[1].equalsIgnoreCase("unlock") || args[1].equalsIgnoreCase("lock")){
                arguments.addAll(argMap.keySet());
            }
            StringUtil.copyPartialMatches(args[3], arguments, completions);
        }
        else if(args.length == 5){
            if (args[0].equalsIgnoreCase("skins") && args[1].equalsIgnoreCase("unlock") || args[1].equalsIgnoreCase("lock")){
                arguments.add("*");
                for (int i = 0; i < configs.getModels().size(); i++){
                    if (configs.getModels().get(i).getToolType().equals(argMap.get(args[3]))){
                        for (Integer key : configs.getModels().get(i).getModelMap().keySet())
                            arguments.add(key.toString());
                        break;
                    }
                }
            }
            StringUtil.copyPartialMatches(args[4], arguments, completions);
        }

        Collections.sort(completions);
        return completions;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ToolManager toolManager = CryoTools.getToolManager();
        Player target;
        Player player = null;
        Bukkit.getServer().getOnlinePlayers().toArray();
        if (args.length == 0){
            if (sender instanceof Player) {
                player = (Player) sender;
                if (toolManager.isCryoTool(player.getInventory().getItemInMainHand()))
                    CryoTools.getMainMenu().openMenu(player.getInventory().getItemInMainHand(), player);
            }
        }
        if(args.length > 0){
            switch (args[0]){
                case "give":
                    if (sender instanceof Player){
                        player = (Player) sender;
                        if (!player.hasPermission("cryotools.give")){
                            player.sendMessage(noPermissionsMessage);
                            break;
                        }
                    }
                    try {
                        if (args.length > 4) sender.sendMessage(incorrectCommandMessage);
                        else {
                            target = Bukkit.getPlayerExact(args[1]);
                            if (argMap.containsKey(args[2])) {
                                if (target != null) {
                                    try {
                                        target.getInventory().addItem(toolManager.createItem(argMap.get(args[2].toLowerCase()), Integer.parseInt(args[3])));
                                        sender.sendMessage(ChatColor.GOLD + "Given tool to " + ChatColor.AQUA + target.getName());
                                    } catch (NumberFormatException exception) {
                                        sender.sendMessage(incorrectCommandMessage);
                                    }
                                } else sender.sendMessage(ChatColor.GOLD + "Player not found.");
                            } else sender.sendMessage(incorrectCommandMessage);
                        }
                    } catch (ArrayIndexOutOfBoundsException exception){
                        sender.sendMessage(incorrectCommandMessage);
                    }
                    break;

                case "set":
                    if (sender instanceof Player){
                        player = (Player) sender;
                        if (!player.hasPermission("cryotools.set")){
                            player.sendMessage(noPermissionsMessage);
                            break;
                        }
                        if (args.length != 2) player.sendMessage(incorrectSetCommand);
                        else{
                            try{
                                if (toolManager.isCryoTool(player.getInventory().getItemInMainHand()))
                                    toolManager.setItem(player.getInventory().getItemInMainHand(), Integer.parseInt(args[1]));
                                else sender.sendMessage(ChatColor.GOLD + "This tool cannot be altered!");
                            } catch (NumberFormatException exception){
                                player.sendMessage(incorrectSetCommand);
                            }
                        }
                    } else sender.sendMessage("Command can only be executed by players!");
                    break;

                case "help":
                    if (sender instanceof Player) {
                        player = (Player) sender;
                        if (!player.hasPermission("cryotools.help")) {
                            player.sendMessage(noPermissionsMessage);
                            break;
                        }
                    }
                    if (args.length > 1) sender.sendMessage(ChatColor.GOLD + "Incorrect command usage: /cryotools help");
                    else sender.sendMessage(ChatColor.GOLD + "The CryoTools Plugin implements custom tools with a levelling system and special abilities. " +
                            "These abilities can be unlocked by levelling up your tool through continuous use. Additionally, " +
                            "you may gain more experience and/or drops the higher your tool level is." +
                            "\nCommands:" + ChatColor.BLUE +
                            "\n/cryotools - Opens the GUI." +
                            "\n/cryotools give <player> <tooltype> <level> - Gives a player a tool with a given level." +
                            "\n/cryotools set <level> - Sets the tool in hand to a certain level." +
                            "\n/cryotools info - Shows general information about the tool in your hand.");
                    break;

                case "info":
                    if (sender instanceof Player) {
                        player = (Player) sender;
                        if (!player.hasPermission("cryotools.info")) {
                            player.sendMessage(noPermissionsMessage);
                            break;
                        }
                        if (args.length > 1)
                            sender.sendMessage(ChatColor.GOLD + "Incorrect command usage: /cryotools info");
                        else {
                            ItemStack item = player.getInventory().getItemInMainHand();
                            if (toolManager.isCryoTool(item)) {
                                player.sendMessage(item.getItemMeta().getDisplayName() +
                                        "\nLevel: " + toolManager.getLevel(item) +
                                        "\nCurrent EXP: " + toolManager.getCurrentExp(item) + "/" + toolManager.getTotalLevelExp(item) +
                                        "\nAutosell: " + toolManager.hasAutoSell(item) +
                                        "\nExtra Drops: " + toolManager.getExtraDrops(item));
                            }
                        }
                    } else sender.sendMessage("Command can only be executed by players!");
                    break;
                case "skins":
                    if (args.length == 1){
                        if (sender instanceof Player){
                            player = (Player) sender;
                            if(player.hasPermission("cryotools.skins")){
                                if (toolManager.isCryoTool(player.getInventory().getItemInMainHand()))
                                    CryoTools.getSkinsMenu().openGUI(player, player.getInventory().getItemInMainHand().getType());
                                else sender.sendMessage("You must be holding the correct tool to use this command.");
                            }
                            else
                                player.sendMessage(noPermissionsMessage);

                        }
                    }
                    else if (args.length == 5)
                        switch(args[1]){
                            case "unlock":
                                if(argMap.containsKey(args[3])){
                                    target = Bukkit.getPlayerExact(args[2]);
                                    try {
                                        if (target != null)
                                            CryoTools.getDataManager().getPlayerData().get(target.getUniqueId()).setData(Integer.parseInt(args[4]), argMap.get(args[3]));
                                        else
                                            sender.sendMessage("Player not found.");
                                        if (args[2].equalsIgnoreCase("*")){
                                            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                                            Bukkit.getServer().getOnlinePlayers().toArray(players);
                                            for (Player p : players){
                                                CryoTools.getDataManager().getPlayerData().get(p.getUniqueId()).setData(Integer.parseInt(args[4]), argMap.get(args[3]));
                                            }
                                        }

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else
                                    sender.sendMessage("There is no such tool!");

                                break;
                            case "lock":
                                // remove skin from player, remove from players table
                                break;
                            default:
                                sender.sendMessage(ChatColor.GOLD + "Incorrect command usage: /cryotools skins [unlock|lock]");
                        }

                    break;
                default:
                    sender.sendMessage("I DO NOTHING YET");
                    break;
            }
        }

        return true;
    }
}
