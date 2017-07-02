package nz.motiondesign.test;

import nz.motiondesign.module.Item;
import nz.motiondesign.module.Pack;

import java.util.*;
import java.util.stream.Collectors;

import static nz.motiondesign.system.ANSIEscapeCodes.ANSI_RED;
import static nz.motiondesign.system.ANSIEscapeCodes.ANSI_RESET;

/**
 * Created by renbin fang on 17/7/1.
 */
public class AppTest {
    public static void main(String[] args) {
        System.out.println();
        System.out.println(ANSI_RED + "This text is red!" + ANSI_RESET);
    }
}
