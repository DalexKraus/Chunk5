package at.dalex.chunk5.manager;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class BorderManager implements Runnable {

    private Plugin plugin;
    private Chunk chunk;
    private Material torchMaterial;
    private TorchPlaceWork work;

    public BorderManager(Plugin plugin, Chunk chunk, Material torchMaterial) {
        this.plugin = plugin;
        this.chunk = chunk;
        this.torchMaterial = torchMaterial;

        this.work = new TorchPlaceWork();
    }

    @Override
    public void run() {

        if (this.work.isWorkDone()) {
            return;
        }

        for (int count = 0; (count < 4) && (!this.work.isWorkDone()); count++) {
            Location block1 = this.chunk.getBlock(this.work.i, 0, 0).getLocation();
            block1.setY(getY(this.chunk.getWorld(), block1.getBlockX(), block1.getBlockZ()));
            this.chunk.getWorld().getBlockAt(block1).setType(this.torchMaterial);

            Location block2 = this.chunk.getBlock(15, 0, this.work.i).getLocation();
            block2.setY(getY(this.chunk.getWorld(), block2.getBlockX(), block2.getBlockZ()));
            this.chunk.getWorld().getBlockAt(block2).setType(this.torchMaterial);

            Location block3 = this.chunk.getBlock(15 - this.work.i, 0, 15).getLocation();
            block3.setY(getY(this.chunk.getWorld(), block3.getBlockX(), block3.getBlockZ()));
            this.chunk.getWorld().getBlockAt(block3).setType(this.torchMaterial);

            Location block4 = this.chunk.getBlock(0, 0, 15 - this.work.i).getLocation();
            block4.setY(getY(this.chunk.getWorld(), block4.getBlockX(), block4.getBlockZ()));
            this.chunk.getWorld().getBlockAt(block4).setType(this.torchMaterial);

            this.work.i += 1;
        }
        if (!this.work.isWorkDone()) {
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, this);
        }
    }

    public int getY(World world, int x, int z) {
        int y2 = 0;
        for (int y = 255; y > 0; y--) {
            if ((world.getBlockAt(x, y, z).getType() != Material.AIR) &&
                    (world.getBlockAt(x, y, z).getType() != Material.ENDER_PORTAL_FRAME) &&
                    (world.getBlockAt(x, y, z).getType() != Material.BEDROCK)) {

                if ((world.getBlockAt(x, y, z).getType() != Material.DEAD_BUSH) && (world.getBlockAt(x, y, z).getType() != Material.SNOW)) {
                    if (world.getBlockAt(x, y, z).getType() == Material.LONG_GRASS) {
                        y2 = y - 1;
                        break;
                    }
                    if (world.getBlockAt(x, y, z).getType() == Material.DOUBLE_PLANT) {
                        y2 = y - 2;
                        break;
                    }
                    y2 = y;
                    break;
                }
                y--;
                break;
            }
        }
        return y2 + 1;
    }

    public class TorchPlaceWork {
        public int i = 0;

        public TorchPlaceWork() {}

        public boolean isWorkDone() {
            return this.i == 15;
        }
    }
}
