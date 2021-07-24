package manaki.plugin.naplandau;

public class Reward {

    private final String itemId;
    private final int amount;

    public Reward(String itemId, int amount) {
        this.itemId = itemId;
        this.amount = amount;
    }

    public String getItemId() {
        return itemId;
    }

    public int getAmount() {
        return amount;
    }
}
