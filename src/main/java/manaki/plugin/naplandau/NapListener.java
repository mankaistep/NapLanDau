package manaki.plugin.naplandau;

import me.manaki.plugin.shops.storage.ItemStorage;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import remvn.recard.event.PlayerCardChargingEvent;

public class NapListener implements Listener {

    @EventHandler
    public void onDonate(PlayerCardChargingEvent e) {
        if (!e.op.isOnline()) return;
        var p = e.op.getPlayer();
        var r = e.result;
        if (r.code == 0 && !Datas.has(p.getName())) {
            // Toggle
            Datas.toggle(p.getName(), true);

            // Give
            for (Reward rw : NapLanDau.get().getRewards()) {
                var is = ItemStorage.get(rw.getItemId());
                if (is != null) {
                    is.setAmount(rw.getAmount());
                    p.getInventory().addItem(is);
                }
            }

            // Message
            p.sendMessage("§aNhận quà Nạp lần đầu thành công");
            p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        RewardGUI.onClick(e);
    }

    @EventHandler
    public void onInvDrag(InventoryDragEvent e) {
        RewardGUI.onDrag(e);
    }

}
