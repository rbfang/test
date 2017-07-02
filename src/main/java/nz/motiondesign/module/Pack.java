package nz.motiondesign.module;

import java.util.List;

/**
 * Created by Renbin Fang on 17/6/27.
 */
public class Pack {
    private long packId;
    private long packNumber;
    private double packLength;
    private double packWeight;
    private List<Item> itemList;

    public enum SortOrder {
        NATURAL, SHORT_TO_LONG, LONG_TO_SHORT;
    }

    public Pack() {
    }

    public Pack(long packId, List<Item> itemList) {
        this.packId = packId;
        this.itemList = itemList;
    }

    public long getPackId() {
        return packId;
    }

    public void setPackId(long packId) {
        this.packId = packId;
    }

    public long getPackNumber() {
        return packNumber;
    }

    public void setPackNumber(long packNumber) {
        this.packNumber = packNumber;
    }

    public double getPackLength() {
        return packLength;
    }

    public void setPackLength(double packLength) {
        this.packLength = packLength;
    }

    public double getPackWeight() {
        return packWeight;
    }

    public void setPackWeight(double packWeight) {
        this.packWeight = packWeight;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "Pack{" +
                "packId=" + packId +
                ", packNumber=" + packNumber +
                ", packLength=" + packLength +
                ", packWeight=" + packWeight +
                ", itemList=" + itemList +
                '}';
    }
}
