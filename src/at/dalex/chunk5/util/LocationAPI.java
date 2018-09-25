package at.dalex.chunk5.util;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class LocationAPI {

    public void saveConfiguration(Location loc, File f, String path) {
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);

        c.set(path + ".X", Double.valueOf(loc.getX()));
        c.set(path + ".Y", Double.valueOf(loc.getY()));
        c.set(path + ".Z", Double.valueOf(loc.getZ()));
        c.set(path + ".WORLD", loc.getWorld().getName());
        c.set(path + ".YAW", Float.valueOf(loc.getYaw()));
        c.set(path + ".PITCH", Float.valueOf(loc.getPitch()));

        try {
            c.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Location loadLocation(File f, String path)
    {
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);

        double x = c.getDouble(path + ".X") + 0.5D;
        double y = c.getDouble(path + ".Y");
        double z = c.getDouble(path + ".Z") + 0.5D;
        World w = Bukkit.getServer().getWorld(c.getString(path + ".WORLD"));

        double yaw = c.getDouble(path + ".YAW");
        double pitch = c.getDouble(path + ".PITCH");

        return new Location(w, x, y, z, (float)yaw, (float)pitch);
    }
}
