package src.days.one;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DayOne {
    int readInput() {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

        try {
            File myObj = new File("src/days/one/input.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                int currSum = 0;
                String data = myReader.nextLine();

                while(data != "") {
                    currSum += Integer.parseInt(data);
                    if(!myReader.hasNextLine()) break;
                    data = myReader.nextLine();
                }
                maxHeap.add(currSum);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }

        int total = 0;
        for(int i = 0; i < 3; i++) {
            total += maxHeap.remove();
        }
        return total;
    }

    public static void main(String[] args) {
        DayOne day = new DayOne();
        int total = day.readInput();
        System.out.println(total);
    }
}
