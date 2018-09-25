package at.dalex.chunk5.commands;

import at.dalex.chunk5.Chunk5;
import at.dalex.chunk5.config.Config;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDS implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if ((sender instanceof Player)) {
            Player p = (Player) sender;

            if (args.length == 0) {
                for (int i = 0; i < 5; i++) {
                    p.sendMessage("");
                }
                p.sendMessage("§2------ §6Land §2------");
                p.sendMessage("");
                p.sendMessage("§8/§eLand §cinfo");
                p.sendMessage("§8/§eLand §elist");

                p.sendMessage("§8/§eLand §2buy");
                p.sendMessage("§8/§eLand §6sell");
                p.sendMessage("§8/§eLand §5add §8<§6Player§8>");
                p.sendMessage("§8/§eLand §5remove §8<§6Player§8>");
                p.sendMessage("§8/§eLand §4kick §8<§6Player§8>");
                p.sendMessage("");
                return true;
            }
            if (args[0].equalsIgnoreCase("info")) {
                CommandInfo.invoke(p);
            }
            else if (args[0].equalsIgnoreCase("setSpawn")) {
                if (p.hasPermission("chunk5.admin")) {
                    Config.spawn = p.getLocation();
                    p.sendMessage(Chunk5.prefix + "§aDu hast den Land-Welt-Spawn gesetzt!");
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                    Chunk5.getInstance().config.save();
                    return true;
                }
            }
            else if (args[0].equalsIgnoreCase("buy")) {
                CommandBuy.invoke(p);
            } else if (args[0].equalsIgnoreCase("add")) {
                CommandAddFriend.invoke(p, args);
            } else if (args[0].equalsIgnoreCase("remove")) {
                CommandRemoveFriend.invoke(p, args);
            } else if (args[0].equalsIgnoreCase("sell")) {
                p.sendMessage("§4In arbeit.");
            } else if (args[0].equalsIgnoreCase("kick")) {
                CommandKick.invoke(p, args);
            } else if (args[0].equalsIgnoreCase("reloadconfig")) {
                if (p.isOp()) {
                    Chunk5.getInstance().config.save();
                    Chunk5.getInstance().config.load();
                }
            }
            else if (args[0].equalsIgnoreCase("saveconfig") && p.isOp()) {
                Chunk5.getInstance().config.save();
            }
        }
        else
        {
            sender.sendMessage("§cYou need to be a player!");
        }
        return true;
    }
}
