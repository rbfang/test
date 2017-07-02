package nz.motiondesign;

import nz.motiondesign.bean.PackingRequest;
import nz.motiondesign.module.Item;
import nz.motiondesign.module.Pack;
import nz.motiondesign.utils.Helper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static nz.motiondesign.utils.Validation.printErrorMessage;
import static nz.motiondesign.utils.Validation.validationForPackingRequest;

/**
 * Created by Renbin Fang on 17/6/27.
 */
public class App {

    public static void main(String[] args) {
        Packer packer = new Packer();
        Helper helper = new Helper();
        helper.printInstructions();

        Scanner sc = new Scanner(System.in);
        int enterCounter = 0;
        StringBuffer stringBuffer = new StringBuffer();
        while (sc.hasNextLine()) {
            String inputLine = sc.nextLine();

            if (!inputLine.isEmpty()) {
                if (StringUtils.equals(inputLine.toUpperCase(), "Q")) {
                    System.exit(0);
                }
                stringBuffer.append(inputLine + "|");
            } else {
                enterCounter++;
            }

            // two enters means user finish input
            if (enterCounter == 1) {
                String input = stringBuffer.toString().replaceAll("\\s+", "");
                String[] lines = input.split("\\|");

                if (lines.length == 1) {
                    printErrorMessage("You should input some items you need to pack.");
                    System.out.println(" ");
                }

                if (lines.length > 1) {
                    packer.pack(lines, packer);
                }

                // reset counter and clean string buffer
                enterCounter = 0;
                stringBuffer.setLength(0);
            }
        }
    }

}
