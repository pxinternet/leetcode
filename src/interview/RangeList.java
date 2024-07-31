package interview;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.List;

import java.util.regex.Matcher;

public class RangeList {

    private List<int[]> ranges;

    public RangeList() {
        ranges = new ArrayList<>();
    }

    public void add(int start, int end) {

        List<int[]> newRanges = new ArrayList<>();
        boolean merged = false;

        for (int[] range : ranges) {
            if (range[1] < start) {
                newRanges.add(range);
            } else if (range[0] > end) {
                if (!merged) {
                    newRanges.add(new int[] {start, end});
                    merged = true;
                }
                newRanges.add(range);
            } else {
                start = Math.min(start, range[0]);
                end = Math.max(end, range[1]);
            }
        }

        if (!merged) {
            newRanges.add(new int[]{start, end});
        }

        ranges = newRanges;

    }

    public void remove(int start, int end) {
        List<int[]> newRanges = new ArrayList<>();

        for (int[] range : ranges) {
            if (range[1] < start || range[0] > end) {
                newRanges.add(range);
            } else {
                if (range[0] < start) {
                    newRanged.add(new int[] {range[0], start - 1});
                }
                if (range[1] > end) {
                    newRanges.add(new int[] {end + 1, range[1]});
                }
            }
        }
        ranges = newRanges;
    }

    public void print() {
        for (int[] range : ranges) {
            System.out.println("[" + range[0] + ", " + range[1] + "]");
        }
    }

}
