package at.dalex.chunk5.commands;

import at.dalex.chunk5.Chunk5;
import at.dalex.chunk5.manager.LandManager;
import at.dalex.chunk5.util.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandAddFriend {
    
    public static void invoke(Player p, String[] args) {
        if (args.length == 1) {
            Sounds.error(p, Chunk5.prefix + "§4ERROR: §cDu musst einen Spieler angeben.");
            return;
        }
        
        if (Bukkit.getPlayer(args[1]) != null) {
            if (!args[1].equals(p.getName())) {
                LandManager lm = Chunk5.getInstance().config.landManager;
                if (lm.isBoughtByPlayer(p.getLocation().getChunk(), p)) {
                    if (!lm.getLand(p.getLocation().getChunk()).getFriends().contains(p.getUniqueId())) {
                        lm.getLand(p.getLocation().getChunk()).addFriend(Bukkit.getPlayer(args[1]).getUniqueId());
                        Sounds.success(p, Chunk5.prefix + "§aDu hast den Spieler §e" + args[1] + " §azu deinem Grundstück hinzugefügt!");
                    }
                    else Sounds.error(p, Chunk5.prefix + "§4ERROR: §cDieser Spieler hat bereits Rechte auf deinem Grundstück!");

                }
                else Sounds.error(p, Chunk5.prefix + "§4ERROR: §cDas Land auf dem du stehst, geh§rt dir nicht!");
            }
            else Sounds.error(p, Chunk5.prefix + "§4ERROR: §cDu kannst dich nicht selbst auf deinem Grundstück hinzufügen du Scherzkeks ^^");
        }
        else Sounds.error(p, Chunk5.prefix + "§4ERROR: §cDieser Spieler ist nicht online!");
    }
}
