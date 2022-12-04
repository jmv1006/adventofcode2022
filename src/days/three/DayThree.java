package src.days.three;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class DayThree {
    HashMap<String, Integer> priorities = new HashMap<>();
    String[] sacks = new String[300];
    void readInput() {

        try {
            File myObj = new File("src/days/three/input.txt");
            Scanner myReader = new Scanner(myObj);
            int i = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                sacks[i] = data;
                i += 1;
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }

    void assignPriorities() {
        char c;
        int lower = 1;
        for(c ='a'; c <= 'z'; c++) {
            priorities.put(Character.toString(c), lower);
            lower += 1;
        }

        char C;
        int upper = 27;
        for(C = 'A'; C <= 'Z'; C++) {
            priorities.put(Character.toString(C), upper);
            upper += 1;
        }
    }

    int findCommonChar(String rucksack) {
        int mid = rucksack.length() / 2;
        String[] left = rucksack.substring(0, mid).split("");
        String[] right = rucksack.substring(mid).split("");

        HashSet<String> items = new HashSet<>();

        int sum = 0;

        for(String character : left) {
            items.add(character);
        }

        for(String character : right) {
            if(items.contains(character)) {
                sum += priorities.get(character);
                return sum;
            }
        }
        return sum;
    }

    int findCommonCharMultiple(String[] rucksacks) {
        HashSet<String> sack1 = new HashSet<>();
        HashSet<String> sack2 = new HashSet<>();
        HashSet<String> sack3 = new HashSet<>();

        int p = 1;
        for(String sack : rucksacks) {
            String[] sackArr = sack.split("");
            for(String character : sackArr) {
                switch (p) {
                    case 1:
                        sack1.add(character);
                        break;
                    case 2:
                        sack2.add(character);
                        break;
                    case 3:
                        sack3.add(character);
                        break;
                }
            }
            p += 1;
        }


        String sack = rucksacks[0];
        String[] sackArr = sack.split("");
        for(String character : sackArr) {
            if(sack2.contains(character) && sack3.contains(character)) {
                return priorities.get(character);
            }
        }

        return 0;
    }

    void getTotalSum() {
        int total = 0;
        for(String rucksack : sacks) {
            total += findCommonChar(rucksack);
        }
        System.out.println(total);
    }

    void getGroupSum() {
        int total = 0;
        int top = 0;
        int bottom = 3;

        while (bottom <= sacks.length) {
            String[] group = Arrays.copyOfRange(sacks, top, bottom);
            total += findCommonCharMultiple(group);
            top += 3;
            bottom += 3;
        }
        System.out.println(total);
    }

    public static void main(String[] args) {
        DayThree day = new DayThree();
        day.readInput();
        day.assignPriorities();
        day.getGroupSum();
    }
}
