package leetCode;

import java.util.*;

public class LC433minMutation {

    public int minMutation(String startGene, String endGene, String[] bank) {
        Set<String> bankSet = new HashSet<>(Arrays.asList(bank));

        if (!bankSet.contains(endGene)) return -1;

        Set<String> visisted = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        char[] charSet = new char[] {'A', 'C', 'G', 'T'};

        queue.offer(startGene);
        visisted.add(startGene);
        int level = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                String curr = queue.poll();
                if (curr.equals(endGene)) return level;

                char[] currArray = curr.toCharArray();

                for (int i = 0; i < currArray.length; i++) {
                    char old = currArray[i];
                    for (char c : charSet) {
                        currArray[i] = c;
                        String next = new  String(currArray);
                        if (!visisted.contains(next) && bankSet.contains(next)) {
                            queue.offer(next);
                            visisted.add(next);
                        }
                    }

                    currArray[i] = old;

                }
            }
            level++;
        }

        return -1;
    }
}
