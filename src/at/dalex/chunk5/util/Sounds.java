package at.dalex.chunk5.util;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Sounds {

    public static void success(Player p, String msg) {
        p.sendMessage(msg);
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
    }

    public static void error(Player p, String msg) {
        p.sendMessage(msg);
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 1.0F, 1.0F);
    }
}
