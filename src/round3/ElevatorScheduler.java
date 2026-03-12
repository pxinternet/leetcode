package round3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ElevatorScheduler - 电梯调度（同向优先）
 *
 * 题目（概要）：电梯当前楼层与方向（上/下），给定请求楼层列表，按同向优先策略返回访问顺序。
 * 先处理同向请求，再反向。
 *
 * 算法原理：
 * - 同向优先：movingUp 时先上后下；movingDown 时先下后上。
 * - 排序后按 currentFloor 分界：同向楼层在 index 一侧，反向在另一侧。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：请求排序；找 index 使 floorsToVisit[index]>=currentFloor 且 index 最小。
 * - 步骤 2：movingUp：先遍历 index..n-1（同向），再 index-1..0（反向）。
 * - 步骤 3：movingDown：先 index-1..0，再 index..n-1。
 *
 * 关键洞察：简化调度，不考虑中途新请求；同向完再反向。
 *
 * 时间复杂度：O(n log n)
 * 空间复杂度：O(n)
 *
 * 示例：currentFloor=5, movingUp=false, requests=[1,3,7,9,4,8] → 先 [4,3,1] 再 [7,8,9]
 */
public class ElevatorScheduler {

    private int currentFloor;

    private boolean movingUp;

    public ElevatorScheduler(int initFloor, boolean movingUp) {
        this.currentFloor = initFloor;
        this.movingUp = movingUp;
    }


    public List<Integer> schedule(List<Integer> requests) {

        List<Integer> floorsToVisit = new ArrayList<>(requests);

        Collections.sort(floorsToVisit);

        List<Integer> result = new ArrayList<>();

        int index = 0;

        if (movingUp) {
            while (index < floorsToVisit.size() && floorsToVisit.get(index) < currentFloor) {
                index++;
            }

            for (int i = index; i < floorsToVisit.size(); i++) {
                result.add(floorsToVisit.get(i));
            }

            for (int i = index - 1; i >= 0; i--) {
                result.add(floorsToVisit.get(i));
            }
        } else {

            while (index < floorsToVisit.size() && floorsToVisit.get(index) < currentFloor) {
                index++;
            }

            for (int i = index - 1; i >= 0; i--) {
                result.add(floorsToVisit.get(i));
            }


            for (int i = index; i < floorsToVisit.size(); i++) {
                result.add(floorsToVisit.get(i));
            }
        }

        return result;
    }

    public static void main(String[] args) {
        ElevatorScheduler scheduler = new ElevatorScheduler(5, false);
        List<Integer> requests = List.of(1, 3, 7, 9, 4, 8);

        List<Integer> scheduledFloors = scheduler.schedule(requests);

        System.out.print(scheduledFloors);
    }

}
