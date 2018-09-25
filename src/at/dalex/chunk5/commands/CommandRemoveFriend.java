package at.dalex.chunk5.commands;

import at.dalex.chunk5.Chunk5;
import at.dalex.chunk5.manager.LandManager;
import at.dalex.chunk5.util.Sounds;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandRemoveFriend {

    public static void invoke(Player p, String[] args) {
        if (args.length == 1) {
            Sounds.error(p, Chunk5.prefix + "§4ERROR: §cDu musst einen Spieler angeben.");
            return;
        }

        LandManager lm = Chunk5.getInstance().config.landManager;
        if (lm.isBoughtByPlayer(p.getLocation().getChunk(), p)) {
            UUID target = Bukkit.getServer().getPlayer(args[1]).getUniqueId();
            if (lm.getLand(p.getLocation().getChunk()).getFriends().contains(target)) {
                lm.getLand(p.getLocation().getChunk()).removeFriend(target);
                Sounds.success(p, Chunk5.prefix + "§aDu hast den Spieler §e" + args[1] + " §avon deinem Grundstück entfernt!");
            } else Sounds.error(p, Chunk5.prefix + "§4ERROR: §cDieser Spieler hat keine Rechte auf deinem Grundstück!");
        } else Sounds.error(p, Chunk5.prefix + "§4ERROR: §cDas Land auf dem du stehst, gehört dir nicht!");
    }
}
