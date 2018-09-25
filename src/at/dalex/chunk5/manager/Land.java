package at.dalex.chunk5.manager;

import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.Chunk;

public class Land {

    private LandData landData;

    public Land(Chunk chunk, UUID owner) {
        this.landData = new LandData();
        this.landData.chunk = chunk;
        this.landData.landOwner = owner;
    }

    public boolean addFriend(UUID player) {
        if (this.landData.friends.contains((player))) {
            this.landData.friends.add(player);
            return true;
        }
        return false;
    }

    public boolean removeFriend(UUID player) {
        if (this.landData.friends.contains(player)) {
            this.landData.friends.remove(player);
            return true;
        }
        return false;
    }

    public boolean isSold() {
        return this.landData.sold;
    }

    public void setSold(boolean sold) {
        this.landData.sold = sold;
        this.landData.landOwner = UUID.fromString("000000000-0000-0000-0000-000000000000");
        this.landData.friends.clear();
    }

    public void setLastOwner(UUID player) {
        this.landData.lastOwner = player;
    }

    public UUID getLastOwner() {
        return this.landData.lastOwner;
    }

    public ArrayList<UUID> getFriends() {
        return this.landData.friends;
    }

    public LandData getData() {
        return this.landData;
    }
}
