package at.dalex.chunk5.commands;

import at.dalex.bank.Bank;
import at.dalex.bank.TransactionStatus;
import at.dalex.chunk5.Chunk5;
import at.dalex.chunk5.manager.BorderManager;
import at.dalex.chunk5.manager.Land;
import at.dalex.chunk5.util.ParticleWall;
import at.dalex.chunk5.util.Sounds;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CommandBuy {

    public static void invoke(Player p) {
        if (Chunk5.getInstance().config.landManager.isLand(p.getLocation().getChunk())) {
            if (Chunk5.getInstance().config.landManager.isBoughtByPlayer(p.getLocation().getChunk(), p)) {
                Sounds.error(p, Chunk5.prefix + "§4ERROR: §cDieses Land gehört bereits dir!");
                new ParticleWall().drawChunkWall(p, p.getLocation().getChunk(), EnumParticle.FLAME);
                return;
            }

            Sounds.error(p, Chunk5.prefix + "§4ERROR: §cDieses Land ist bereits verkauft!");
            new ParticleWall().drawChunkWall(p, p.getLocation().getChunk(), EnumParticle.FLAME);
        }
        
        else {
            if (Bank.removeBalance(p.getUniqueId(), 2000) == TransactionStatus.TRANSACTION_SUCCESSFUL) {
                Chunk5.getInstance().config.landManager.addLand(new Land(p.getLocation().getChunk(), p.getUniqueId()));
                Sounds.success(p, Chunk5.prefix + "§aDu hast dieses Land erfolgreich gekauft!");
                Bukkit.getScheduler().scheduleSyncDelayedTask(Chunk5.getInstance(), new BorderManager(Chunk5.getInstance(), p.getLocation().getChunk(), Material.TORCH));
                new ParticleWall().drawChunkWall(p, p.getLocation().getChunk(), EnumParticle.FIREWORKS_SPARK);
            }
            else p.sendMessage("§4ERROR: §cDu hast nicht genügend Geld!");

            return;
        }
    }
}
