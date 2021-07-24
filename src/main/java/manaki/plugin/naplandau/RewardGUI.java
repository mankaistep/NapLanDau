package manaki.plugin.naplandau;

import me.manaki.plugin.shops.storage.ItemStorage;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class RewardGUI {

    public static void open(Player player) {
        var rewards = NapLanDau.get().getRewards();
        int size = rewards.size();
        size = size % 9 == 0 ? size : (size / 9 + 1) * 9;
        var inv = Bukkit.createInventory(new RewardGUIHolder(), size, "§0§lQUÀ NẠP LẦN ĐẦU");
        player.openInventory(inv);
        player.sendMessage("§c§lLưu ý: §fNhớ để trống kho đồ, tránh tình trạng mất đồ!");

        Bukkit.getScheduler().runTaskAsynchronously(NapLanDau.get(), () -> {
            for (int i = 0; i < rewards.size(); i++) {
                var r = rewards.get(i);
                var is = ItemStorage.get(r.getItemId());
                if (is == null) return;

                var meta = is.getItemMeta();
                for (NamespacedKey key : meta.getPersistentDataContainer().getKeys()) {
                    meta.getPersistentDataContainer().remove(key);
                }
                is.setItemMeta(meta);
                is.setAmount(r.getAmount());

                inv.setItem(i, is);
            }
        });
    }

    public static void onClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof RewardGUIHolder) e.setCancelled(true);
    }

    public static void onDrag(InventoryDragEvent e) {
        if (e.getInventory().getHolder() instanceof RewardGUIHolder) e.setCancelled(true);
    }

    public static void onReload() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.getOpenInventory();
            p.getOpenInventory().getTopInventory();
            if (p.getOpenInventory().getTopInventory().getHolder() instanceof RewardGUIHolder) p.closeInventory();
        }
    }

}

class RewardGUIHolder implements InventoryHolder {

    @Override
    public Inventory getInventory() {
        return null;
    }
}
