package at.dalex.chunk5.commands;

import at.dalex.chunk5.Chunk5;
import at.dalex.chunk5.config.Config;
import at.dalex.chunk5.util.ParticleWall;
import java.util.UUID;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class CommandInfo {

    public static void invoke(Player p) {

        if (p.getWorld().getName().equals(Config.spawn.getWorld().getName())) {
            Chunk chunk = p.getLocation().getChunk();

            p.sendMessage("§2------ §6Land §2------");
            p.sendMessage("");

            if (Chunk5.getInstance().config.landManager.isLand(p.getLocation().getChunk())) {
                if (Chunk5.getInstance().config.landManager.getLand(p.getLocation().getChunk()).isSold()) {
                    p.sendMessage("§6Dieses Land kann gekauft werden!");
                    p.sendMessage("§eLetzter Besitzer§7: §6" + Bukkit.getOfflinePlayer(Chunk5.getInstance().config.landManager.getLand(p.getLocation().getChunk()).getLastOwner()).getName());
                }
                else {
                    p.sendMessage("§7Dieses Land geh§rt§8: §e" + Bukkit.getOfflinePlayer(Chunk5.getInstance().config.landManager.getLand(p.getLocation().getChunk()).getData().landOwner).getName());
                    p.sendMessage("§6Freunde§7: ");

                    for (UUID uuid : Chunk5.getInstance().config.landManager.getLand(p.getLocation().getChunk()).getFriends()) {
                        if (Bukkit.getPlayer(uuid) != null) {
                            p.sendMessage("§8- §a" + Bukkit.getPlayer(uuid).getName());
                        } else p.sendMessage("§8- §c" + Bukkit.getOfflinePlayer(uuid).getName());
                    }
                }
            }
            else p.sendMessage("§7Dieses Land geh§rt§8: §aNiemandem! :)");

            new ParticleWall().drawChunkWall(p, chunk, EnumParticle.SPELL_WITCH);
        }
    }
}
