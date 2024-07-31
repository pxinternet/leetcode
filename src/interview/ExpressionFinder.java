package interview;

import java.util.ArrayList;
import java.util.List;

public class ExpressionFinder {

    public static List<String> findExpressions(int[] numbers, int target) {
        List<String> results = new ArrayList<>();
        backtrack(results, "", numbers, target, 0, 0, 0);
        return results;
    }

    private static void backtrack(List<String> results, String expr, int[] numbers, int target, int index, long currentValue, long prevOperand) {
        if (index == numbers.length) {
            if (currentValue == target) {
                results.add(expr);
            }
            return;
        }

        for (int i = index; i < numbers.length; i++) {
            if (i!= index && numbers[index] == 0) {
                break;
            }

            long currentNum = 0;

            for (int j = index; j <= i; j++) {
                currentNum = currentNum * 10 + numbers[j];
            }

            if (index == 0) {
                backtrack(results, expr + currentNum, numbers, target, i + 1, currentValue + currentNum, currentNum);
            } else {
                backtrack(results, expr + "+" + currentNum, numbers, target, i + 1, currentValue + currentNum, currentNum);
                backtrack(results, expr + "-" + currentNum, numbers, target, i + 1, currentValue - currentNum, currentNum);
            }
        }
    }

}
