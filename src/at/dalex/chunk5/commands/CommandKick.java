package at.dalex.chunk5.commands;

import at.dalex.chunk5.Chunk5;
import at.dalex.chunk5.config.Config;
import at.dalex.chunk5.manager.LandManager;
import at.dalex.chunk5.util.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class CommandKick {

    public static void invoke(Player p, String[] args) {
        if (args.length >= 2) {
            Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);

            if (targetPlayer.isOnline()) {
                LandManager lm = Chunk5.getInstance().config.landManager;
                Chunk chunk = targetPlayer.getLocation().getChunk();

                if ((lm.getLand(chunk) != null) && (lm.getLand(chunk).getData().landOwner.equals(p.getUniqueId()))) {
                    if (Config.spawn != null) {
                        if (targetPlayer != p) {

                            targetPlayer.teleport(Config.spawn);
                            targetPlayer.playSound(targetPlayer.getLocation(), Sound.BLOCK_WATERLILY_PLACE, 1.0F, 1.0F);
                            targetPlayer.sendMessage(Chunk5.prefix + "§7Du wurdest vom Grundst§ck geworfen.");
                            p.sendMessage(Chunk5.prefix + "§aDu hast den Spieler §e" + args[1] + "§a von deinem Grundstück geworden!");
                        }
                        else p.sendMessage(Chunk5.prefix + "§4ERROR: §cDu kannst dich nicht selbst kicken du Scherzkeks! ^^");
                    }
                }
                else Sounds.error(p, Chunk5.prefix + "§4ERROR: §cDer angegebene Spieler befindet sich nicht auf deinem Grundstück!");
            }
            else Sounds.error(p, Chunk5.prefix + "§4ERROR: §cDer angegebene Spieler ist nicht online.");
        }
        else Sounds.error(p, Chunk5.prefix + "§4ERROR: §cDu musst den Spieler angeben, den du von deinem Grundstück werfen m§chtest!");
    }
}
