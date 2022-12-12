package src.days.eleven;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DayEleven {
    HashMap<Integer, Monkey> readInput() {
        HashMap<Integer, Monkey> map = new HashMap<>();

        try {
            File myObj = new File("src/days/eleven/input.txt");
            Scanner myReader = new Scanner(myObj);

            int idCounter = 0;

            while(myReader.hasNextLine()) {
                String monkeyLine = myReader.nextLine();
                if(monkeyLine.equals("")) continue;

                Monkey newMonkey = new Monkey(idCounter, new ArrayDeque<>());
                map.put(idCounter, newMonkey);
                idCounter += 1;

                String itemsLine = myReader.nextLine().substring(18);
                String[] items = itemsLine.split(",");

                for(String item : items) {
                    int itemInt = Integer.parseInt(item.replaceAll("\\s",""));
                    newMonkey.addItem(itemInt);
                }

                String operation = myReader.nextLine().substring(19);
                String[] operationArr = operation.split(" ");
                newMonkey.setOperationMethod(operationArr);

                String[] test = myReader.nextLine().split(" ");
                newMonkey.setTestDivisibleAmount( Integer.parseInt(test[test.length - 1]));

                String[] trueCase = myReader.nextLine().split(" ");
                newMonkey.trueTarget = Integer.parseInt(trueCase[trueCase.length - 1]);

                String[] falseCase = myReader.nextLine().split(" ");
                newMonkey.falseTarget = Integer.parseInt(falseCase[falseCase.length - 1]);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return map;
    }

    void startRounds(HashMap<Integer, Monkey> monkeyMap) {
        /*
        int round = 1;

        HashMap<Integer, Integer> inspections = new HashMap<>();

        while(round < 21) {
            for(int key : monkeyMap.keySet()) {
                Monkey currentMonkey = monkeyMap.get(key);
                Queue<Integer> items = currentMonkey.items;

                for(int item : items) {
                    int worryLevel = currentMonkey.operate(item);
                    int decreasedWorryLevel = (int)Math.floor(worryLevel / 3);
                    boolean passesTest = currentMonkey.test(decreasedWorryLevel);

                    int targetId;

                    if(passesTest) {
                        targetId = currentMonkey.trueTarget;
                    } else {
                        targetId = currentMonkey.falseTarget;
                    }

                    //throwing item to target
                    Monkey targetMonkey = monkeyMap.get(targetId);
                    targetMonkey.addItem(decreasedWorryLevel);
                    currentMonkey.popItem();
                    if(!inspections.containsKey(currentMonkey.id)) inspections.put(currentMonkey.id, 0);
                    inspections.put(currentMonkey.id, inspections.get(currentMonkey.id) + 1);
                }
            }
            round += 1;
        }

         */
    }

    void partTwo(HashMap<Integer, Monkey> monkeyMap) {
        int round = 1;
        HashMap<Integer, Long> inspections = new HashMap<>();

        long product = 1;
        for(int key : monkeyMap.keySet()) {
            product *= monkeyMap.get(key).divisibleAmount;
        }

        while(round < 10001) {
            for(int key : monkeyMap.keySet()) {
                Monkey currentMonkey = monkeyMap.get(key);
                Queue<Long> items = currentMonkey.items;
                if(items.size() < 1) continue;


                for(Long item : items) {
                    //inspecting...
                    long worryLevel = currentMonkey.operate(item);
                    boolean passesTest = currentMonkey.test(worryLevel);

                    int targetId;

                    if(passesTest) {
                        targetId = currentMonkey.trueTarget;
                    } else {
                        targetId = currentMonkey.falseTarget;
                    }

                    //throwing item to target
                    Monkey targetMonkey = monkeyMap.get(targetId);
                    targetMonkey.addItem(worryLevel % product);
                    currentMonkey.popItem();
                    if(!inspections.containsKey(currentMonkey.id)) inspections.put(currentMonkey.id, 0L);
                    inspections.put(currentMonkey.id, inspections.get(currentMonkey.id) + 1);
                }
            }
            round += 1;
        }
        System.out.println(inspections);
    }


    public static void main(String[] args) {
        DayEleven day = new DayEleven();
        HashMap<Integer, Monkey> map = day.readInput();
        day.partTwo(map);
        long one = 1;
        long two = 5;
    }
}
