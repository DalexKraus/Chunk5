package at.dalex.chunk5;

import at.dalex.chunk5.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class Chunk5 extends JavaPlugin {

    public static String prefix = "§8[§2Land§8] §7";
    private static Chunk5 instance;
    public Config config;

    @Override
    public void onEnable() {
        instance = this;

        this.config = new Config("plugins/Chunk5/config.yml");
        this.config.load();

        regCMDS();
        regListeners();
    }

    @Override
    public void onDisable() {
        this.config.save();
    }

    private void regCMDS() {
        getCommand("land").setExecutor(new CMDS());
    }

    private void regListeners() {
        new ProtectionListener();
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    public static Chunk5 getInstance() {
        return instance;
    }
}
