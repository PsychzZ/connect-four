package com.encoway.viergewinnt.utils;

import java.util.Scanner;

public class Utils {

    public static int getColumnFromPlayer() {
        return new Scanner(System.in).nextInt();
    }

    public static Boolean getInputForPlayingAnotherGame() {
        return new Scanner(System.in).nextBoolean();
    }
}
