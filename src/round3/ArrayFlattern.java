package round3;

import java.util.ArrayList;
import java.util.Stack;

public class ArrayFlattern {

    public static Object[] flattern(Object[] array, ArrayList<Object> res) {

        for (Object element : array) {
            if (element instanceof Object[]) {
                flattern((Object[]) element, res);
            } else {
                res.add(element);
            }
        }
        return res.toArray(new Object[] {});

    }

    public static Object[] flatternNoRecusive(Object[] array) {

        Stack<Object> stack = new Stack<>();

        ArrayList<Object> res = new ArrayList<>();

        for (int i = array.length - 1; i >= 0; i--) {
            stack.push(array[i]);
        }

        while (!stack.isEmpty()) {
            Object current = stack.pop();

            if (current instanceof Object[]) {
                Object[] currentArr = (Object[]) current;

                for (int i = currentArr.length - 1; i >= 0; i--) {
                    stack.push(currentArr[i]);
                }

            } else {
                res.add(current);
            }
        }
        return res.toArray(new Object[] {});

    }

    public static void main(String[] args) {
        Object[] elements = { 1, 2, new Object[] { 3, 4 }, new Object[] { new Object[] { 5, 6 }, 7 } };

        ArrayList<Object> res = new ArrayList<>();
        // Object[] res1 = flattern(elements, res);
        Object[] res1 = flatternNoRecusive(elements);
        for (int i = 0; i < res1.length; i++) {
        System.out.print(res1[i] + ", ");

        }
    }

}
