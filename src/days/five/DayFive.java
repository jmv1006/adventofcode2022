package src.days.five;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class DayFive {
    ArrayList<String> instructions = new ArrayList<>();
    ArrayList<Stack<String>> stacks = new ArrayList<>();
    void readInput() {
        //create stacks
        for(int i = 0; i < 9; i++) {
            Stack<String> stack = new Stack<>();
            stacks.add(stack);
        }

        try {
            File myObj = new File("src/days/five/input.txt");
            Scanner myReader = new Scanner(myObj);

            for(int i = 0; i < 8; i++) {
                String[] data = myReader.nextLine().split("");
                int l = 0;
                int r = 2;
                int stackNumber = 0;

                while(r <= data.length) {
                    String[] column = Arrays.copyOfRange(data, l, r + 1);
                    if(!(column[1].isBlank())) {
                        stacks.get(stackNumber).add(column[1]);
                    }
                    l += 4;
                    r += 4;
                    stackNumber += 1;
                }
            }

            //skipping two lines to get to instructions
            myReader.nextLine();
            myReader.nextLine();

            //adding instructions
            while(myReader.hasNextLine()) {
                String data = myReader.nextLine();
                instructions.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }

    String handleStackMoves(int beingMoved, int initial, int dest) {
        Stack<String> initialStack = stacks.get(initial - 1);
        Stack<String> destinationStack = stacks.get(dest - 1);
        return "M";
    }

    public static void main(String[] args) {
        DayFive day = new DayFive();
        day.readInput();
        System.out.println(day.stacks);
    }
}
