package src.days.four;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class DayFour {
    String[][] readInput() {
        String[][] rangesArr = new String[1000][2];
        int i = 0;
        try {
            File myObj = new File("src/days/four/input.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] ranges = data.split(",");
                rangesArr[i] = ranges;
                i += 1;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return rangesArr;
    }

    boolean determineOverlap(int[] pair1, int[] pair2) {
        if(pair1[0] <= pair2[0] && pair1[1] >= pair2[1]) {
            return true;
        } else if (pair2[0] <= pair1[0] && pair2[1] >= pair1[1]) {
            return true;
        }
        return false;
    }

    boolean determineOverlapPartTwo(int[] pair1, int[] pair2) {
        HashSet<Integer> numbersInPair1 = new HashSet<>();

        for(int i = pair1[0]; i <= pair1[1]; i++) {
            numbersInPair1.add(i);
        }

        for(int i = pair2[0]; i <= pair2[1]; i++) {
            if(numbersInPair1.contains(i)) return true;
        }
        return false;
    }

    void countOverlaps(String[][] ranges) {
        int overlaps = 0;
        for(String[] range : ranges) {
            String[] pair1String = range[0].split("-");
            String[] pair2String = range[1].split("-");

            int[] pair1 = new int[2];
            int[] pair2 = new int[2];

            for(int i = 0; i < 2; i++) {
                pair1[i] = Integer.parseInt(pair1String[i]);
                pair2[i] = Integer.parseInt(pair2String[i]);
            }

            boolean isOverlap = determineOverlapPartTwo(pair1, pair2);
            if(isOverlap) overlaps += 1;
        }
        System.out.println(overlaps);
    }

    public static void main(String[] args) {
        DayFour day = new DayFour();
        String[][] ranges = day.readInput();
        day.countOverlaps(ranges);
    }
}
