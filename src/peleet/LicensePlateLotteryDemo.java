package peleet;

import java.util.*;

/**
 * 车牌摇号系统演示类
 */
public class LicensePlateLotteryDemo {

    public static void main(String[] args) {
        // 示例1：基本摇号演示
        System.out.println("========== 示例1：基本摇号演示 ==========");
        basicDemo();

        // 示例2：多次摇号演示
        System.out.println("\n========== 示例2：多次摇号演示 ==========");
        multipleDrawDemo();

        // 示例3：概率统计演示
        System.out.println("\n========== 示例3：概率统计演示 ==========");
        probabilityDemo();

        // 示例4：公平性验证
        System.out.println("\n========== 示例4：公平性验证 ==========");
        fairnessDemo();
    }

    /**
     * 基本摇号演示
     */
    private static void basicDemo() {
        List<Participant> participants = Arrays.asList(
            new Participant("张三", 100),
            new Participant("李四", 200),
            new Participant("王五", 300),
            new Participant("赵六", 150),
            new Participant("钱七", 250)
        );

        LicensePlateLottery lottery = new LicensePlateLottery(participants);

        // 显示每个参与者的中签概率
        System.out.println("参与者信息及理论中签概率：");
        Map<String, Double> probabilities = lottery.getProbability();
        for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
            System.out.printf("  %s: %.2f%%\n", entry.getKey(), entry.getValue() * 100);
        }

        // 执行10次摇号
        System.out.println("\n执行10次摇号结果：");
        for (int i = 1; i <= 10; i++) {
            Participant winner = lottery.draw();
            System.out.printf("第%d次摇号: %s (积分: %d)\n", i, winner.getName(), winner.getScore());
        }
    }

    /**
     * 多次摇号演示（不重复）
     */
    private static void multipleDrawDemo() {
        List<Participant> participants = Arrays.asList(
            new Participant("张三", 100),
            new Participant("李四", 200),
            new Participant("王五", 300),
            new Participant("赵六", 150),
            new Participant("钱七", 250)
        );

        LicensePlateLottery lottery = new LicensePlateLottery(participants);

        System.out.println("从5个人中摇出3个中签者：");
        List<Participant> winners = lottery.drawMultiple(3);
        for (int i = 0; i < winners.size(); i++) {
            System.out.printf("  第%d个中签者: %s (积分: %d)\n", 
                i + 1, winners.get(i).getName(), winners.get(i).getScore());
        }
    }

    /**
     * 概率统计演示
     */
    private static void probabilityDemo() {
        List<Participant> participants = Arrays.asList(
            new Participant("张三", 10),
            new Participant("李四", 20),
            new Participant("王五", 30),
            new Participant("赵六", 40)
        );

        LicensePlateLottery lottery = new LicensePlateLottery(participants);

        System.out.println("理论概率：");
        Map<String, Double> probabilities = lottery.getProbability();
        for (Map.Entry<String, Double> entry : probabilities.entrySet()) {
            System.out.printf("  %s: %.2f%%\n", entry.getKey(), entry.getValue() * 100);
        }

        // 执行10000次摇号，统计实际中签次数
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
    }

    /**
     * 公平性验证：验证积分越高，中签概率越大
     */
    private static void fairnessDemo() {
        // 创建积分差异明显的参与者
        List<Participant> participants = Arrays.asList(
            new Participant("低积分者", 10),
            new Participant("中积分者", 50),
            new Participant("高积分者", 100)
        );

        LicensePlateLottery lottery = new LicensePlateLottery(participants);

        System.out.println("参与者积分及理论中签概率：");
        Map<String, Double> probabilities = lottery.getProbability();
        for (Participant p : participants) {
            double prob = probabilities.get(p.getName());
            System.out.printf("  %s (积分: %d): %.2f%%\n", 
                p.getName(), p.getScore(), prob * 100);
        }

        // 验证：高积分者的中签概率应该大于低积分者
        double lowProb = probabilities.get("低积分者");
        double midProb = probabilities.get("中积分者");
        double highProb = probabilities.get("高积分者");

        System.out.println("\n公平性验证：");
        System.out.printf("  低积分者概率 < 中积分者概率: %s (%.2f%% < %.2f%%)\n", 
            lowProb < midProb, lowProb * 100, midProb * 100);
        System.out.printf("  中积分者概率 < 高积分者概率: %s (%.2f%% < %.2f%%)\n", 
            midProb < highProb, midProb * 100, highProb * 100);
        System.out.printf("  高积分者概率是低积分者的 %.2f 倍\n", highProb / lowProb);
    }
}
