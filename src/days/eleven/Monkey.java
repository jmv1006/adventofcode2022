package src.days.eleven;

import java.util.ArrayDeque;
import java.util.Queue;

public class Monkey {
    Queue<Long> items = new ArrayDeque<>();
    int id;
    String operator;
    int operationAmount;

    int divisibleAmount;

    int trueTarget;

    int falseTarget;

    public Monkey(int initialId, Queue<Long> initialItems) {
        id = initialId;
        items = initialItems;
    }

    Queue<Long> addItem(long item) {
        items.add(item);
        return items;
    }

    void popItem() {
        items.remove();
    }

    void setOperationMethod(String[] operationString) {
        operator = operationString[1];
        if(operationString[2].equals("old")) return;
        operationAmount = Integer.parseInt(operationString[2]);
    }

    void setTestDivisibleAmount(int amount) {
        divisibleAmount = amount;
    }

    Long operate(Long oldAmount) {
        if(operationAmount == 0) return oldAmount * oldAmount;
        return switch (operator) {
            case "*" -> oldAmount * operationAmount;
            case "+" -> oldAmount + operationAmount;
            default -> 0L;
        };
    }

    boolean test(Long num) {
        return num % divisibleAmount == 0;
    }



}
