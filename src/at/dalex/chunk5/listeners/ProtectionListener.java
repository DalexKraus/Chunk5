package at.dalex.chunk5.listeners;

import at.dalex.chunk5.Chunk5;
import at.dalex.chunk5.util.Sounds;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ProtectionListener implements Listener {

    public ProtectionListener() {
        Chunk5.getInstance().getServer().getPluginManager().registerEvents(this, Chunk5.getInstance());
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!Chunk5.getInstance().config.landManager.hasRightOn(e.getBlock().getLocation().getChunk(), p)) {
            e.setCancelled(true);
            Sounds.error(p, Chunk5.prefix + "§cDu hast keine Rechte auf dieses Land.");
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (!Chunk5.getInstance().config.landManager.hasRightOn(e.getBlock().getLocation().getChunk(), p)) {
            e.setCancelled(true);
            Sounds.error(p, Chunk5.prefix + "§cDu hast keine Rechte auf dieses Land.");
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if ((e.getClickedBlock() != null) &&
                (!Chunk5.getInstance().config.landManager.hasRightOn(e.getClickedBlock().getLocation().getChunk(), p))) {

            e.setCancelled(true);
            Sounds.error(p, Chunk5.prefix + "§cDu hast keine Rechte auf dieses Land.");
        }
    }
}
