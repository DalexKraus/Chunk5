package at.dalex.chunk5.listeners;

import at.dalex.chunk5.Chunk5;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Chunk5.getInstance().config.save();
        Chunk5.getInstance().config.load();
        e.getPlayer().sendMessage(Chunk5.prefix + "§7Successfully synchronized lands.");
    }
}
