package manaki.plugin.naplandau;

import mk.plugin.playerdata.storage.PlayerDataAPI;

public class Datas {

    private static final String HOOK = "naplandau";
    private static final String KEY = "toggle";

    public static boolean has(String player) {
        var pd = PlayerDataAPI.get(player, HOOK);
        return pd.hasData(KEY) && Boolean.parseBoolean(pd.getValue(KEY));
    }

    public static void toggle(String player, boolean value) {
        var pd = PlayerDataAPI.get(player, HOOK);
        pd.set(KEY, value + "");
        pd.save();
        NapLanDau.get().getLogger().info(player + " toggle " + value);
    }

}
