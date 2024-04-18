package leetCode;

import javax.swing.text.Style;
import java.util.List;
import java.util.Set;

public class Tools {

    public static void printMatrix(int[][] matrix) {
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                System.out.printf("%4d", anInt);
            }
            System.out.println();
        }
    }


    public static void printArray(int[] array) {
        for (int num : array) {
            System.out.printf("%4d", num);
        }
        System.out.println();
    }

    public static void printArray(String pre, int[] array) {
        System.out.print(pre + " : ");
        for (int num : array) {
            System.out.printf("%4d", num);
        }
        System.out.println();
    }

    public static void printSet(Set<Integer> set) {
        for (int num : set) {
            System.out.printf("%4d", num);
        }
        System.out.println();
    }

    public static void printList(List<?> list) {
        for (Object item : list) {
            System.out.println(item);
        }
    }


}
