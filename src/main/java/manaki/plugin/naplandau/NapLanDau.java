package manaki.plugin.naplandau;

import com.google.common.collect.Lists;
import me.manaki.plugin.shops.storage.ItemStorage;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class NapLanDau extends JavaPlugin {

    private List<Reward> rewards;

    @Override
    public void onEnable() {
        this.reloadConfig();

        this.getCommand("naplandau").setExecutor(this);
        this.getCommand("naplandauadmin").setExecutor(this);

        Bukkit.getPluginManager().registerEvents(new NapListener(), this);
    }

    @Override
    public void onDisable() {
        RewardGUI.onReload();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("naplandau")) {
            var p = (Player) sender;
            RewardGUI.open(p);
        }
        else if (command.getName().equals("naplandauadmin")) {
            if (args.length == 0) {
                sender.sendMessage("§a/naplandauadmin reload: §fLệnh này đéo hiểu thì ăn cứt mẹ mày đi thằng lồn");
                sender.sendMessage("§a/naplandauadmin toggle <true/false> <player>: §fKích hoạt trạng trái nạp lần đầu");
                sender.sendMessage("§a/naplandauadmin has <player>: §fKiểm tra player đã nạp lần đầu chưa");
                sender.sendMessage("§a/naplandauadmin give <player>: §fGive quà Nạp lần đầu");
            }

            else if (args[0].equals("reload")) {
                this.reloadConfig();
                sender.sendMessage("§aThật tuyệt vời, reload thành công không một vết xước địch mẹ mày");
            }

            else if (args[0].equals("toggle")) {
                var value = Boolean.parseBoolean(args[1]);
                var name = args[2];
                Datas.toggle(name, value);
                sender.sendMessage("§aRồi ok, set nạp lần đầu của " + name + " thành " + value);
            }

            else if (args[0].equals("has")) {
                var name = args[1];
                if (Datas.has(name)) {
                    sender.sendMessage("§aCó nha, thằng lồn này có nạp lần đầu");
                }
                else sender.sendMessage("§cĐéo có");
            }

            else if (args[0].equals("give")) {
                var p = Bukkit.getPlayer(args[1]);
                if (p == null) {
                    sender.sendMessage("§cVãi lồn nhập gì thế, đéo tìm thấy player địch cụ nhà mày nữa");
                    return false;
                }
                for (Reward rw : NapLanDau.get().getRewards()) {
                    var is = ItemStorage.get(rw.getItemId());
                    if (is != null) {
                        is.setAmount(rw.getAmount());
                        p.getInventory().addItem(is);
                    }
                }
                p.sendMessage("§aNhận quà Nạp lần đầu thành công");
                p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
            }
        }

        return false;
    }

    public void reloadConfig() {
        this.saveDefaultConfig();
        this.rewards = Lists.newArrayList();
        var config = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "config.yml"));
        for (String s : config.getStringList("rewards")) {
            var id = s.split(" ")[0];
            var a = Integer.parseInt(s.split(" ")[1]);
            this.rewards.add(new Reward(id, a));
        }
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public static NapLanDau get() {
        return JavaPlugin.getPlugin(NapLanDau.class);
    }
}
