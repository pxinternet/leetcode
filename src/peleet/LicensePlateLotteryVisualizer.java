package peleet;

import java.util.*;

/**
 * 摇号系统可视化工具
 * 用于展示区间分配，帮助理解公平性
 */
public class LicensePlateLotteryVisualizer {

    /**
     * 可视化区间分配
     */
    public static void visualizeIntervals(LicensePlateLottery lottery) {
        List<Participant> participants = lottery.getParticipants();
        long totalScore = lottery.getTotalScore();

        System.out.println("========== 区间分配可视化 ==========");
        System.out.printf("总积分: %d\n\n", totalScore);

        long cumulativeScore = 0;
        for (int i = 0; i < participants.size(); i++) {
            Participant p = participants.get(i);
            long start = cumulativeScore;
            long end = cumulativeScore + p.getScore();
            double probability = (double) p.getScore() / totalScore * 100;

            System.out.printf("参与者 %d: %s (积分: %d)\n", i + 1, p.getName(), p.getScore());
            System.out.printf("  区间: [%d, %d)\n", start, end);
            System.out.printf("  区间大小: %d\n", p.getScore());
            System.out.printf("  中签概率: %.2f%%\n", probability);
            
            // 可视化区间（用ASCII艺术）
            int barLength = 50;
            int startPos = (int) ((double) start / totalScore * barLength);
            int endPos = (int) ((double) end / totalScore * barLength);
            StringBuilder bar = new StringBuilder();
            bar.append("  [");
            for (int j = 0; j < barLength; j++) {
                if (j >= startPos && j < endPos) {
                    bar.append("=");
                } else {
                    bar.append(" ");
                }
            }
            bar.append("]");
            System.out.println(bar.toString());
            System.out.println();

            cumulativeScore = end;
        }

        System.out.println("说明：");
        System.out.println("- 每个参与者的区间大小 = 其积分值");
        System.out.println("- 积分相同的人，区间大小相同，中签概率相同");
        System.out.println("- 随机数在 [0, 总积分) 之间均匀分布");
        System.out.println("- 随机数落在哪个区间，对应的人中签");
    }

    /**
     * 演示积分相同时的情况
     */
    public static void demonstrateSameScore() {
        System.out.println("\n========== 演示：两个人积分相同 ==========\n");
        
        List<Participant> participants = Arrays.asList(
            new Participant("张三", 100),
            new Participant("李四", 100)
        );

        LicensePlateLottery lottery = new LicensePlateLottery(participants);
        visualizeIntervals(lottery);

        System.out.println("公平性分析：");
        System.out.println("✓ 张三和李四积分相同（都是100），区间大小相同（都是100）");
        System.out.println("✓ 张三区间：[0, 100)，李四区间：[100, 200)");
        System.out.println("✓ 随机数在[0, 200)均匀分布，每人50%概率");
        System.out.println("✓ 无论列表顺序如何，结果都是公平的");
    }

    /**
     * 演示不同积分的情况
     */
    public static void demonstrateDifferentScores() {
        System.out.println("\n========== 演示：不同积分 ==========\n");
        
        List<Participant> participants = Arrays.asList(
            new Participant("低积分", 50),
            new Participant("中积分", 100),
            new Participant("高积分", 200)
        );

        LicensePlateLottery lottery = new LicensePlateLottery(participants);
        visualizeIntervals(lottery);

        System.out.println("公平性分析：");
        System.out.println("✓ 积分越高，分配的区间越大，中签概率越大");
        System.out.println("✓ 低积分：50/350 ≈ 14.29%");
        System.out.println("✓ 中积分：100/350 ≈ 28.57%");
        System.out.println("✓ 高积分：200/350 ≈ 57.14%");
    }

    public static void main(String[] args) {
        demonstrateSameScore();
        demonstrateDifferentScores();
    }
}
