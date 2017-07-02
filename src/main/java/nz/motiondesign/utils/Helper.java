package nz.motiondesign.utils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by renbin fang on 17/7/1.
 */
public class Helper {

    private static final String LOGO_FILE = "logo.txt";

    public void printInstructions() {
        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(LOGO_FILE).getFile());

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}
