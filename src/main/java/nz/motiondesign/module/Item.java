package nz.motiondesign.module;

/**
 * Created by Renbin Fang on 17/6/27.
 */
public class Item {
    private long itemId;
    private int length;
    private int quantity;
    private double weight;

    public Item() {
    }

    public Item(long itemId, int length, int quantity, double weight) {
        this.itemId = itemId;
        this.length = length;
        this.quantity = quantity;
        this.weight = weight;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", length=" + length +
                ", quantity=" + quantity +
                ", weight=" + weight +
                '}';
    }
}
