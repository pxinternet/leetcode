package peleet;

import java.util.*;

/**
 * 公平性测试：验证积分相同时的公平性
 */
public class FairnessTest {

    public static void main(String[] args) {
        System.out.println("========== 测试：两个人积分相同的情况 ==========");
        testSameScore();

        System.out.println("\n========== 测试：列表顺序是否影响结果 ==========");
        testOrderIndependence();

        System.out.println("\n========== 测试：多个相同积分的人 ==========");
        testMultipleSameScore();
    }

    /**
     * 测试两个人积分相同的情况
     */
    private static void testSameScore() {
        List<Participant> participants = Arrays.asList(
            new Participant("张三", 100),
            new Participant("李四", 100)
        );

        LicensePlateLottery lottery = new LicensePlateLottery(participants);

        System.out.println("参与者信息：");
        for (Participant p : participants) {
            System.out.printf("  %s: 积分 %d\n", p.getName(), p.getScore());
        }

        System.out.println("\n理论中签概率：");
        Map<String, Double> probabilities = lottery.getProbability();
        for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
            System.out.printf("  %s: %.2f%%\n", entry.getKey(), entry.getValue() * 100);
        }

        // 执行10000次摇号，统计中签次数
        System.out.println("\n执行10000次摇号，统计实际中签次数：");
        Map<String, Integer> countMap = new HashMap<>();
        for (Participant p : participants) {
            countMap.put(p.getName(), 0);
        }

        int totalDraws = 10000;
        for (int i = 0; i < totalDraws; i++) {
            Participant winner = lottery.draw();
            countMap.put(winner.getName(), countMap.get(winner.getName()) + 1);
        }

        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            String name = entry.getKey();
            int count = entry.getValue();
            double actualRate = (double) count / totalDraws * 100;
            double theoreticalRate = probabilities.get(name) * 100;
            System.out.printf("  %s: 实际 %.2f%% (理论 %.2f%%, 差异 %.2f%%)\n", 
                name, actualRate, theoreticalRate, Math.abs(actualRate - theoreticalRate));
        }

        // 验证是否接近50:50
        int count1 = countMap.get("张三");
        int count2 = countMap.get("李四");
        double ratio = (double) Math.max(count1, count2) / Math.min(count1, count2);
        System.out.printf("\n中签次数比例: %.2f:1 (理想情况应该是 1:1)\n", ratio);
        System.out.printf("结论: %s\n", ratio < 1.1 ? "✓ 公平，两人中签概率基本相同" : "✗ 可能存在偏差");
    }

    /**
     * 测试列表顺序是否影响结果
     */
    private static void testOrderIndependence() {
        // 测试1：张三在前，李四在后
        List<Participant> order1 = Arrays.asList(
            new Participant("张三", 100),
            new Participant("李四", 100)
        );
        LicensePlateLottery lottery1 = new LicensePlateLottery(order1);

        // 测试2：李四在前，张三在后
        List<Participant> order2 = Arrays.asList(
            new Participant("李四", 100),
            new Participant("张三", 100)
        );
        LicensePlateLottery lottery2 = new LicensePlateLottery(order2);

        System.out.println("测试场景1：张三在前，李四在后");
        Map<String, Integer> count1 = runTest(lottery1, 10000);
        System.out.printf("  张三: %d次, 李四: %d次\n", count1.get("张三"), count1.get("李四"));

        System.out.println("\n测试场景2：李四在前，张三在后");
        Map<String, Integer> count2 = runTest(lottery2, 10000);
        System.out.printf("  张三: %d次, 李四: %d次\n", count2.get("张三"), count2.get("李四"));

        // 比较两种顺序的结果
        double ratio1 = (double) count1.get("张三") / count1.get("李四");
        double ratio2 = (double) count2.get("张三") / count2.get("李四");
        
        System.out.println("\n分析：");
        System.out.printf("  场景1中张三/李四比例: %.4f\n", ratio1);
        System.out.printf("  场景2中张三/李四比例: %.4f\n", ratio2);
        System.out.printf("  比例差异: %.4f\n", Math.abs(ratio1 - ratio2));
        
        // 理论上，无论顺序如何，比例都应该接近1.0
        if (Math.abs(ratio1 - 1.0) < 0.1 && Math.abs(ratio2 - 1.0) < 0.1) {
            System.out.println("  结论: ✓ 列表顺序不影响公平性，算法是公平的");
        } else {
            System.out.println("  结论: ✗ 可能存在顺序依赖问题");
        }
    }

    /**
     * 测试多个相同积分的人
     */
    private static void testMultipleSameScore() {
        List<Participant> participants = Arrays.asList(
            new Participant("张三", 100),
            new Participant("李四", 100),
            new Participant("王五", 100),
            new Participant("赵六", 100)
        );

        LicensePlateLottery lottery = new LicensePlateLottery(participants);

        System.out.println("4个人积分都是100，理论上每人25%中签概率");
        System.out.println("\n执行10000次摇号，统计实际中签次数：");

        Map<String, Integer> countMap = new HashMap<>();
        for (Participant p : participants) {
            countMap.put(p.getName(), 0);
        }

        int totalDraws = 10000;
        for (int i = 0; i < totalDraws; i++) {
            Participant winner = lottery.draw();
            countMap.put(winner.getName(), countMap.get(winner.getName()) + 1);
        }

        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            String name = entry.getKey();
            int count = entry.getValue();
            double actualRate = (double) count / totalDraws * 100;
            System.out.printf("  %s: %d次 (%.2f%%)\n", name, count, actualRate);
        }

        // 计算方差，验证是否均匀分布
        double expected = 2500; // 期望值：10000 / 4
        double variance = 0;
        for (int count : countMap.values()) {
            variance += Math.pow(count - expected, 2);
        }
        variance /= countMap.size();
        double stdDev = Math.sqrt(variance);

        System.out.printf("\n统计信息：");
        System.out.printf("\n  期望中签次数: %.0f", expected);
        System.out.printf("\n  标准差: %.2f", stdDev);
        System.out.printf("\n  变异系数: %.2f%%\n", (stdDev / expected) * 100);
        
        if (stdDev / expected < 0.05) { // 变异系数小于5%认为很均匀
            System.out.println("  结论: ✓ 分布均匀，算法公平");
        } else {
            System.out.println("  结论: ⚠ 分布可能存在偏差");
        }
    }

    private static Map<String, Integer> runTest(LicensePlateLottery lottery, int times) {
        Map<String, Integer> countMap = new HashMap<>();
        List<Participant> participants = lottery.getParticipants();
        for (Participant p : participants) {
            countMap.put(p.getName(), 0);
        }

        for (int i = 0; i < times; i++) {
            Participant winner = lottery.draw();
            countMap.put(winner.getName(), countMap.get(winner.getName()) + 1);
        }

        return countMap;
    }
}
