package at.dalex.chunk5.land;

import at.dalex.chunk5.Chunk5;
import org.bukkit.Chunk;

import java.util.ArrayList;
import java.util.UUID;

public class LandData {

    public UUID landId;
    public Chunk chunk;
    public UUID landOwner;
    public UUID lastOwner;
    public ArrayList<UUID> friends;
    public boolean sold;

    public LandData() {
        this.friends = new ArrayList();
        this.landId = Chunk5.getInstance().config.landManager.genLandId();
    }
}
