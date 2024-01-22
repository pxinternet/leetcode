package leetCode;

public class LC134CanCompleteCircuit {

    public int canCompleteCircuit(int[] gas, int[] cost) {

        int n = gas.length;

        int sumGas = 0;
        for (int i = 0; i < n; i++) {
            sumGas += gas[i];
        }

        int sumCost = 0;
        for (int i = 0; i < n; i++) {
            sumCost += cost[i];
        }

        if (sumGas < sumCost) return -1;

        //如果sumGas >= sumCost 那么一定能找到

        int startIndex = -1;
        int gasTobeCost = 0;

        for (int i = 0; i < n; i++) {
            gasTobeCost += gas[i] - cost[i];

            if (gasTobeCost >= 0) {
                if (startIndex == -1) {
                    startIndex = i;
                }
            }
            if (gasTobeCost < 0) {
                startIndex = -1;
                gasTobeCost = 0;
            }
        }
        return startIndex;
    }


    public int canCompleteBetterCode(int[] gas, int[] cost) {
        int n = gas.length;

        int totalGus = 0;
        int currentGus = 0;
        int startStation = 0;

        for (int i = 0; i < n; i++) {
            totalGus += gas[i] - cost[i];
            currentGus += gas[i] - cost[i];

            if (currentGus < 0) {
                currentGus = 0;
                startStation = i + 1;
            }
        }
        return totalGus >= 0 ? startStation : -1;

    }

    public static void main(String[] args) {
        int[] gas = new int[]{1,2,3,4,5};
        int[] cost = new int[]{3,4,5,1,2};

        LC134CanCompleteCircuit lc134CanCompleteCircuit = new LC134CanCompleteCircuit();
        int res = lc134CanCompleteCircuit.canCompleteCircuit(gas, cost);

        System.out.println(res);


    }
}
