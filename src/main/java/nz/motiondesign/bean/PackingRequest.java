package nz.motiondesign.bean;

import nz.motiondesign.module.Pack;

/**
 * Created by renbin fang on 17/7/1.
 */
public class PackingRequest {
    private Pack.SortOrder sortOrder;
    private int maxPcsPerPack;
    private int maxWeightPerPack;

    public PackingRequest() {
    }

    public PackingRequest(Pack.SortOrder sortOrder, int maxPcsPerPack, int maxWeightPerPack) {
        this.sortOrder = sortOrder;
        this.maxPcsPerPack = maxPcsPerPack;
        this.maxWeightPerPack = maxWeightPerPack;
    }

    public Pack.SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Pack.SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getMaxPcsPerPack() {
        return maxPcsPerPack;
    }

    public void setMaxPcsPerPack(int maxPcsPerPack) {
        this.maxPcsPerPack = maxPcsPerPack;
    }

    public int getMaxWeightPerPack() {
        return maxWeightPerPack;
    }

    public void setMaxWeightPerPack(int maxWeightPerPack) {
        this.maxWeightPerPack = maxWeightPerPack;
    }

    @Override
    public String toString() {
        return "PackingRequest{" +
                "sortOrder=" + sortOrder +
                ", maxPcsPerPack=" + maxPcsPerPack +
                ", maxWeightPerPack=" + maxWeightPerPack +
                '}';
    }
}
