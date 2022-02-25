package homework;

import java.util.Scanner;

public class NumbersCheck {

    public static boolean isInt(String str) {

        if (new Scanner(str).hasNextInt()) {return true;}
        else {return false;}

    }
}
