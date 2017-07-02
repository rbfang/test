package nz.motiondesign.utils;

import nz.motiondesign.module.Pack;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static nz.motiondesign.system.ANSIEscapeCodes.ANSI_RED;
import static nz.motiondesign.system.ANSIEscapeCodes.ANSI_RESET;

/**
 * Created by Renbin Fang on 17/7/1.
 */
public class Validation {

    public static void printErrorMessage(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    /**
     * to see whether the sort order from user input matches one of Pack.SortOrder
     *
     * @param sortOrder
     * @return matches return true, does not match return false
     */
    public static boolean isCorrectSortOrder(String sortOrder) {
        return Arrays.stream(Pack.SortOrder.values()).anyMatch(order -> Objects.equals(order.toString(), sortOrder.toUpperCase()));
    }

    /**
     * Validation for user input
     *
     * @param lines number of lines that from user input
     * @return error messages list, if it passed validation, the size of list would be zero, otherwise the data is invalid
     */
    public static List<String> validationForPackingRequest(String[] lines) {
        List<String> errorMessages = new ArrayList<>();
        if (lines.length < 2) {
            errorMessages.add("Please input packing request and items need to pack");

            return errorMessages;
        }

        String packingRequestCommand = lines[0];
        String[] packingRequestParameters = packingRequestCommand.split(",");

        if (packingRequestParameters.length != 3) {
            errorMessages.add("Please input the valid packing request. It should be three parameters.");

            return errorMessages;
        }

        String sortOrder = packingRequestParameters[0];
        if (!isCorrectSortOrder(sortOrder)) {
            errorMessages.add("We only support three types of sort order, they are NATURAL, SHORT_TO_LONG, LONG_TO_SHORT;");

            return errorMessages;
        }

        if (!NumberUtils.isNumber(packingRequestParameters[1])) {
            errorMessages.add("Max pieces per pack should be a number");
        } else {
            int maxPcsPerPack = Integer.parseInt(packingRequestParameters[1]);
            if (maxPcsPerPack <= 0) {
                errorMessages.add("Max pieces per pack should be above 0");
            }
        }


        if (!NumberUtils.isNumber(packingRequestParameters[2])) {
            errorMessages.add("Max weight per pack should be a number");
        } else {
            double maxWeightPerPack = Integer.parseInt(packingRequestParameters[2]);
            if (maxWeightPerPack <= 0) {
                errorMessages.add("Max weight per pack should be above 0");
            }
        }

        return errorMessages;
    }
}
