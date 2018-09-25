package at.dalex.chunk5.util;

import at.dalex.chunk5.Chunk5;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class ParticleWall {

    int count = 0;
    int countdown;

    public void drawChunkWall(final Player p, Chunk chunk, final EnumParticle particle) {
        Location corner1 = new Location(p.getWorld(), chunk.getX() * 16, p.getLocation().getY() - 50.0D, chunk.getZ() * 16);
        Location corner2 = new Location(p.getWorld(), chunk.getX() * 16 + 16, p.getLocation().getY() + 50.0D, chunk.getZ() * 16 + 16);

        final List<Location> result = new ArrayList();
        World world = corner1.getWorld();

        double minX = Math.min(corner1.getX(), corner2.getX());
        double minY = Math.min(corner1.getY(), corner2.getY());
        double minZ = Math.min(corner1.getZ(), corner2.getZ());
        double maxX = Math.max(corner1.getX(), corner2.getX());
        double maxY = Math.max(corner1.getY(), corner2.getY());
        double maxZ = Math.max(corner1.getZ(), corner2.getZ());

        for (double y = minY; y <= maxY; y += 2.0D) {
            for (double x = minX; x <= maxX; x += 1.0D) {
                result.add(new Location(world, x, y, minZ));
                result.add(new Location(world, x, y, maxZ));
            }
            for (double z = minZ; z <= maxZ; z += 1.0D) {
                result.add(new Location(world, minX, y, z));
                result.add(new Location(world, maxX, y, z));
            }
        }

        this.countdown = Bukkit.getScheduler().scheduleSyncRepeatingTask(Chunk5.getInstance(), new Runnable() {

            @Override
            public void run() {
                if (ParticleWall.this.count == 3) {
                    ParticleWall.this.count = 0;
                    Bukkit.getScheduler().cancelTask(ParticleWall.this.countdown);
                }
                else {
                    for (Location loc : result) {
                        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float)loc.getX(), (float)loc.getY(), (float)loc.getZ(), 0.0F, 0.0F, 0.0F, 0.0F, 1, new int[0]);
                        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
                    }
                    ParticleWall.this.count += 1;
                }
            }
        }, 0L, 20L);
    }
}
