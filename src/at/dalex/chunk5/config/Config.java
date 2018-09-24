package at.dalex.chunk5.config;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Config {

    private File file;
    private FileConfiguration config;
    public static Location spawn;
    public LandManager landManager = new LandManager();

    public Config(String path) {
        this.file = new File(path);
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Parse the last state of the Plugin using the config file.
     */
    public void load() {
        spawn = null;
        this.landManager.reset();
        if (this.config.get("spawn") != null) {
            double x = this.config.getDouble("spawn.X") + 0.5D;
            double y = this.config.getDouble("spawn.Y") + 0.5D;
            double z = this.config.getDouble("spawn.Z") + 0.5D;
            World w = Bukkit.getServer().getWorld(this.config.getString("spawn.WORLD"));
            float yaw = (float)this.config.getDouble("spawn.YAW");
            float pitch = (float)this.config.getDouble("spawn.PITCH");

            spawn = new Location(w, x, y, z, yaw, pitch);
        }
        if (spawn != null) {
            if (this.config.get("lands") != null) {
                for (String rawLandId : this.config.getConfigurationSection("lands").getKeys(false)) {
                    UUID landId = UUID.fromString(rawLandId);
                    String landLocation = this.config.getString("lands." + landId + ".coordinate");

                    String[] loc = landLocation.split(";");

                    int x = Integer.parseInt(loc[0]);
                    int y = Integer.parseInt(loc[1]);

                    Chunk chunk = spawn.getWorld().getChunkAt(x, y);

                    UUID owner = UUID.fromString(this.config.getString("lands." + landId + ".owner"));
                    Land plot = new Land(chunk, owner);

                    List<String> rawFriends = this.config.getStringList("lands." + landId + ".friends");
                    for (String friend : rawFriends) {
                        plot.addFriend(UUID.fromString(friend));
                    }
                    if (this.config.get("lands." + landId + ".lastOwner") != null) {
                        plot.setLastOwner(UUID.fromString(this.config.getString("lands." + landId + ".lastOwner")));
                    }
                    this.landManager.addLand(plot);
                }
            }
        }
        else Bukkit.getServer().getConsoleSender().sendMessage("[Chunk5] §4ERROR: §cWorld is not set! §7(Spawn)");

        System.out.println("[Chunk5] Config loaded!");
    }

    /**
     * Save the current state of the Plugin into the config file.
     */
    public void save() {
        this.file.delete();
        this.config.set("lands", null);
        if ((spawn != null) && (this.landManager != null))
        {
            for (Land land : this.landManager.getRegisteredLands())
            {
                this.config.set("lands." + land.getData().landId + ".coordinate", land.getData().chunk.getX() + ";" + land.getData().chunk.getZ());
                this.config.set("lands." + land.getData().landId + ".owner", land.getData().landOwner.toString());

                List<String> list = new ArrayList();
                for (UUID friend : land.getData().friends) {
                    list.add(friend.toString());
                }
                this.config.set("lands." + land.getData().landId + ".friends", list);
            }
            this.config.set("spawn.X", Double.valueOf(spawn.getX() - 0.5D));
            this.config.set("spawn.Y", Double.valueOf(spawn.getY() - 0.5D));
            this.config.set("spawn.Z", Double.valueOf(spawn.getZ() - 0.5D));
            this.config.set("spawn.WORLD", spawn.getWorld().getName());
            this.config.set("spawn.YAW", Float.valueOf(spawn.getYaw()));
            this.config.set("spawn.PITCH", Float.valueOf(spawn.getPitch()));
        }
        try
        {
            this.config.save(this.file);
        }
        catch (IOException e)
        {
            System.out.println("§c[Chunk5] §4Config could not be saved! (Unexpected error)");
            e.printStackTrace();
        }
        System.out.println("[Chunk5] §aConig saved.");
    }

    public String getPath() {
        return this.file.getPath();
    }

    public File getFile() {
        return this.file;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }
}
