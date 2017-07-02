package nz.motiondesign;

import nz.motiondesign.bean.PackingRequest;
import nz.motiondesign.module.Item;
import nz.motiondesign.module.Pack;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static nz.motiondesign.system.ANSIEscapeCodes.ANSI_RED;
import static nz.motiondesign.system.ANSIEscapeCodes.ANSI_RESET;
import static nz.motiondesign.utils.Validation.printErrorMessage;
import static nz.motiondesign.utils.Validation.validationForPackingRequest;

/**
 * Created by Renbin Fang on 17/7/2.
 */
public class Packer {

    /**
     * Packing process
     * 1. Validate user input
     * 2. Parse and convert user input
     * 3. Pack items as user requested
     * 4. Print result out for users
     *
     * @param lines
     * @param packer
     */
    public void pack(String[] lines, Packer packer) {
        /**
         * validation for packing request
         * 1. if less than three arguments
         * 2. if the first argument belongs to Pack.SortOrder type
         * 3. if the pieces per pack is a number, and above than 0
         * 4. if the max weight per pack is a number, and above than 0
         */
        List<String> errorMessages = validationForPackingRequest(lines);

        if (errorMessages.size() > 0) {
            errorMessages.forEach(message -> printErrorMessage(message));
        } else if (lines.length > 1) {
            PackingRequest packingRequest = packer.getPackingRequest(lines[0]);

            // getting items
            List<Item> parsedItems = packer.parseItemsData(lines);

            if (Pack.SortOrder.SHORT_TO_LONG.equals(packingRequest.getSortOrder())) {
                parsedItems.sort((item1, item2) -> item1.getLength() - item2.getLength());
            }

            if (Pack.SortOrder.LONG_TO_SHORT.equals(packingRequest.getSortOrder())) {
                parsedItems.sort((item1, item2) -> item2.getLength() - item1.getLength());
            }

            // packing items
            List<Item> itemList = packer.unboxItems(parsedItems);
            List<Pack> packs = packer.packItems(packingRequest, itemList);

            // printing results
            packer.sortPacks(packs);
            System.out.println("");
            System.out.println("We packed " + packs.size() + " packages for you. ");
            packs.forEach(pack -> {
                System.out.println(" ");
                System.out.println("Pack Number: " + pack.getPackId());
                pack.getItemList().forEach(item -> {
                    System.out.println(item.getItemId() + "," + item.getLength() + "," + item.getQuantity() + "," + item.getWeight());
                });

                System.out.printf("Pack Length: %.0fmm, Pack Weight: %.2fkg", pack.getPackLength(), pack.getPackWeight());
                System.out.println(" ");
            });
        }
    }

    /**
     * Unbox Items, and convert items maps into pieces list
     *
     * @param items
     * @return items list contains each item
     */
    public List<Item> unboxItems(List<Item> items) {
        List<Item> itemList = new ArrayList<>();
        items.forEach(item -> {
            int qty = item.getQuantity();
            for (int i = 0; i < qty; i++) {
                itemList.add(new Item(item.getItemId(), item.getLength(), 1, item.getWeight()));
            }
        });

        return itemList;
    }

    /**
     * Calculate total weight of giving pack
     *
     * @param itemsInPack items
     * @return total weight of giving pack
     */
    public double getPackWeight(List<Item> itemsInPack) {
        return itemsInPack.stream().mapToDouble(item -> item.getWeight()).sum();
    }

    /**
     * find longest item length among all items
     *
     * @param itemsInPack items pack
     * @return longest item length
     */
    public double getPackLength(List<Item> itemsInPack) {
        return itemsInPack.stream().map(item -> item.getLength()).max((length1, length2) -> Double.compare(length1, length2)).get();
    }

    /**
     * Sorting packs, the giving packs contain a list of packs, and each pack contains signal item as a list,
     * we group items and change quantity
     *
     * @param packs
     */
    public void sortPacks(List<Pack> packs) {
        packs.forEach(pack -> {
            Map<Long, List<Item>> map = pack.getItemList().stream().collect(Collectors.groupingBy(item -> item.getItemId()));
            List<Item> sortedItemList = new ArrayList<>(map.size());
            map.forEach((itemId, items) -> {
                Item item = items.get(0);
                item.setQuantity(items.size());

                sortedItemList.add(item);
            });

            sortedItemList.sort(Comparator.comparingLong(Item::getItemId));
            pack.setItemList(sortedItemList);
        });
    }

    /**
     * Making packs from given items by packing request
     *
     * @param packingRequest
     * @param itemList
     * @return a list of packs
     */
    public List<Pack> packItems(PackingRequest packingRequest, List<Item> itemList) {
        int maxPcs = packingRequest.getMaxPcsPerPack();
        int maxWeight = packingRequest.getMaxWeightPerPack();
        Pack.SortOrder sortOrder = packingRequest.getSortOrder();

        int currentQty = 0;
        double totalWeight = 0;

        List<Pack> packs = new ArrayList<>();
        List<Item> itemsForPacking = new ArrayList<>();

        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);

            // if current item weight over maximum weight, this item cannot be packed.
            if (item.getWeight() > maxWeight) {
                printErrorMessage("Item cannot be packed: " + item.toString());
                continue;
            }

            currentQty++;
            totalWeight += item.getWeight();

            if (currentQty == maxPcs && totalWeight < maxWeight) {
                itemsForPacking.add(item);
                List<Item> itemsInPack = new ArrayList<>();
                itemsInPack.addAll(itemsForPacking);

                Pack pack = new Pack();
                pack.setPackId(packs.size() + 1);
                pack.setItemList(itemsInPack);
                pack.setPackLength(getPackLength(itemsInPack));
                pack.setPackWeight(getPackWeight(itemsInPack));
                packs.add(pack);

                // clean up
                currentQty = 0;
                totalWeight = 0;
                itemsForPacking.clear();
            } else if (currentQty < maxPcs && totalWeight > maxWeight) {
                List<Item> itemsInPack = new ArrayList<>();
                itemsInPack.addAll(itemsForPacking);

                Pack pack = new Pack();
                pack.setPackId(packs.size() + 1);
                pack.setItemList(itemsInPack);
                pack.setPackLength(getPackLength(itemsInPack));
                pack.setPackWeight(getPackWeight(itemsInPack));
                packs.add(pack);

                // clean up
                itemsForPacking.clear();

                // add current item for looping
                currentQty = 1;
                totalWeight = item.getWeight();

                itemsForPacking.add(item);
            } else {
                itemsForPacking.add(item);
            }
        }

        if (itemsForPacking.size() > 0) {
            Pack pack = new Pack();
            pack.setPackId(packs.size() + 1);
            pack.setItemList(itemsForPacking);
            pack.setPackLength(getPackLength(itemsForPacking));
            pack.setPackWeight(getPackWeight(itemsForPacking));
            packs.add(pack);
        }

        return packs;
    }


    /**
     * Get packing request from user input.
     * Packing request includes Sort Order, Maximum pieces and maximum weight for each pack
     *
     * @param line first line of user input
     * @return PackingRequest bean
     */
    public PackingRequest getPackingRequest(String line) {
        String[] parameters = line.split(",");

        PackingRequest packingRequest = new PackingRequest();
        packingRequest.setSortOrder(Pack.SortOrder.valueOf(parameters[0]));
        packingRequest.setMaxPcsPerPack(Integer.parseInt(parameters[1]));
        packingRequest.setMaxWeightPerPack(Integer.parseInt(parameters[2]));

        return packingRequest;
    }


    /**
     * Parsing user input to a list contains items for each line
     *
     * @param lines items line
     * @return item list
     */
    public List<Item> parseItemsData(String[] lines) {
        return Stream.of(lines).map(line -> {
            String[] parameters = line.split(",");

            if (parameters.length != 4) {
                return null;
            }

            String itemId = parameters[0];
            String length = parameters[1];
            String quantity = parameters[2];
            String weight = parameters[3];

            if (!NumberUtils.isNumber(itemId) || !NumberUtils.isNumber(length) ||
                    !NumberUtils.isNumber(quantity) || !NumberUtils.isNumber(weight)) {

                printErrorMessage("Cannot be parsed line: " + line);

                return null;
            }

            Item item = new Item();
            item.setItemId(Long.valueOf(itemId));
            item.setLength(Integer.valueOf(length));
            item.setQuantity(Integer.valueOf(quantity));
            item.setWeight(Double.valueOf(weight));

            return item;
        }).filter(item -> item != null).collect(Collectors.toList());
    }
}
