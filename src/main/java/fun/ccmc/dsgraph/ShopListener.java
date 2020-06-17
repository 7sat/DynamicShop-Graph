package fun.ccmc.dsgraph;

import me.sat7.dynamicshop.events.ShopBuySellEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ShopListener implements Listener {
    @EventHandler
    public void onShop(ShopBuySellEvent e) {
        DSGraph.getInstance().getLogger().info(e.toString());
        if (DSGraph.getInstance().getRecordDataTask() != null) {
            DSGraph.getInstance().getCfg().getFiles().forEach(stockConfig -> {
                if (e.getMerchandise().equals(stockConfig.getMaterial()) && e.getShopName().equals(stockConfig.getShopName())) {
                    DSGraph.getInstance().getRecordDataTask().queue(stockConfig);
                }
            });
        }
    }
}