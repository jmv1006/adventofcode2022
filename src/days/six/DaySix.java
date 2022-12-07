package src.days.six;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class DaySix {
    String[] readInput() {
        try {
            File myObj = new File("src/days/six/input.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] splitData = data.split("");
                return splitData;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return new String[1];
    }

    boolean characterWindowUnique(String[] window) {
        HashSet<String> used = new HashSet<>();
        for(String character : window) {
            if(used.contains(character)) return false;
            used.add(character);
        }
        return true;
    }

    int startSlidingWindow(String[] datastream) {
        int l = 0;
        int r = 3;

        while(r < datastream.length) {
            boolean allUnique = characterWindowUnique(Arrays.copyOfRange(datastream, l, r));
            if (allUnique) return r;
            l += 1;
            r += 1;
        }
        return r;
    }
    public static void main(String[] args) {
        DaySix day = new DaySix();
        String[] datastream = day.readInput();
        int firstMarker = day.startSlidingWindow(datastream);
        System.out.println(firstMarker);
    }
}
