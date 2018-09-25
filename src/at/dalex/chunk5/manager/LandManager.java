package at.dalex.chunk5.manager;

import at.dalex.chunk5.config.Config;
import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class LandManager {
    private ArrayList<Land> lands = new ArrayList();
    private ArrayList<UUID> landIds = new ArrayList();

    public boolean addLand(Land land) {
        if (!this.lands.contains(land)) {
            this.lands.add(land);
            return true;
        }
        return false;
    }

    public boolean removeLand(Land land) {
        if (this.lands.contains(land)) {
            this.lands.remove(land);
            return true;
        }
        return false;
    }

    public boolean isLand(Chunk chunk) {
        for (Land land : this.lands) {
            if (land.getData().chunk.equals(chunk)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRightOn(Chunk chunk, Player p) {
        if (!p.getWorld().getName().equals(Config.spawn.getWorld().getName())) {
            return true;
        }
        if (isBoughtByPlayer(chunk, p)) {
            return true;
        }
        if ((getLand(chunk) != null) && (getLand(chunk).getFriends() != null) && (getLand(chunk).getFriends().contains(p.getUniqueId()))) {
            return true;
        }
        return false;
    }

    public boolean isBoughtByPlayer(Chunk chunk, Player p) {
        for (Land land : this.lands) {
            if (land.getData().chunk.equals(chunk))
            {
                if (land.getData().landOwner.equals(p.getUniqueId())) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public Land getLand(Chunk chunk) {
        for (Land land : this.lands) {
            if (land.getData().chunk.equals(chunk)) {
                return land;
            }
        }
        return null;
    }

    public ArrayList<Land> getRegisteredLands() {
        return this.lands;
    }

    public ArrayList<UUID> getRegisteredUUIDs() {
        ArrayList<UUID> list = new ArrayList();
        for (Land land : this.lands) {
            list.add(land.getData().landOwner);
        }
        return list;
    }

    public UUID genLandId() {
        UUID landId = null;
        boolean done = false;
        while (!done)
        {
            landId = UUID.randomUUID();
            if (!this.landIds.contains(landId)) {
                done = true;
            }
        }
        return landId;
    }

    public void reset() {
        this.lands.clear();
        this.landIds.clear();
    }
}
