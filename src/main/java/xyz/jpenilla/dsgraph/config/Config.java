package xyz.jpenilla.dsgraph.config;

import org.bukkit.inventory.meta.ItemMeta;
import xyz.jpenilla.dsgraph.DSGraph;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

@FieldNameConstants
public class Config {
    private final DSGraph plugin;

    @Getter
    private final ArrayList<StockConfig> files = new ArrayList<>();
    @Getter
    private boolean saveUnchangedData;
    @Getter
    private int deleteAfterDays;
    @Getter
    private int compressAfterHours;
    @Getter
    private int port;
    @Getter
    private boolean webServer;
    @Getter
    private boolean customHTML;

    public Config(DSGraph plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        plugin.saveConfig();
        load();
    }

    public void load() {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();

        files.clear();
        config.getConfigurationSection(Fields.files).getKeys(false).forEach(key -> {
            String shopName = config.getString(Fields.files + "." + key + "." + StockConfig.Fields.shopName);
            Material material = Material.getMaterial(config.getString(Fields.files + "." + key + "." + StockConfig.Fields.material));
            ItemMeta itemMeta = (ItemMeta) config.get(Fields.files + "." + key + ".itemStack");
            if (material != null) {
                files.add(new StockConfig(shopName, key, material, itemMeta));
            }
        });

        saveUnchangedData = config.getBoolean(Fields.saveUnchangedData);
        deleteAfterDays = config.getInt(Fields.deleteAfterDays);
        compressAfterHours = config.getInt(Fields.compressAfterHours);
        port = config.getInt(Fields.port);
        webServer = config.getBoolean(Fields.webServer);
        customHTML = config.getBoolean(Fields.customHTML);
    }
}
