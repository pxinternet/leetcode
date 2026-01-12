package peleet;

import java.util.*;

/**
 * 车牌摇号系统
 * 
 * 实现基于积分的加权随机摇号算法（轮盘赌算法）：
 * - 每个人的积分越高，获得车牌的概率越大
 * - 算法公平透明，基于数学概率
 * 
 * 算法原理：
 * 1. 计算所有参与者的积分总和
 * 2. 为每个人分配一个区间 [start, end)，区间大小等于其积分
 * 3. 随机生成一个 [0, totalScore) 之间的数
 * 4. 找到该数落在哪个区间，对应的人中签
 * 
 * 时间复杂度：O(n) 其中 n 是参与者数量
 * 空间复杂度：O(n)
 */
public class LicensePlateLottery {
    private final List<Participant> participants;
    private final Random random;
    private long totalScore;

    /**
     * 构造函数
     * @param participants 参与者列表
     */
    public LicensePlateLottery(List<Participant> participants) {
        if (participants == null || participants.isEmpty()) {
            throw new IllegalArgumentException("参与者列表不能为空");
        }
        this.participants = new ArrayList<>(participants);
        this.random = new Random();
        this.totalScore = calculateTotalScore();
    }

    /**
     * 使用指定的随机数生成器（便于测试）
     */
    public LicensePlateLottery(List<Participant> participants, Random random) {
        if (participants == null || participants.isEmpty()) {
            throw new IllegalArgumentException("参与者列表不能为空");
        }
        this.participants = new ArrayList<>(participants);
        this.random = random;
        this.totalScore = calculateTotalScore();
    }

    /**
     * 计算所有参与者的积分总和
     */
    private long calculateTotalScore() {
        long sum = 0;
        for (Participant p : participants) {
            sum += p.getScore();
        }
        if (sum == 0) {
            throw new IllegalArgumentException("所有参与者的积分总和不能为0");
        }
        return sum;
    }

    /**
     * 执行一次摇号，返回中签的参与者
     * 
     * 使用轮盘赌算法（Roulette Wheel Selection）：
     * - 根据积分分配权重，积分越高，分配的区间越大
     * - 随机选择一个中签者
     * 
     * 公平性保证：
     * - 如果多人积分相同，他们分配的区间大小相同，中签概率也相同
     * - 例如：张三100分，李四100分，总积分200
     *   张三区间：[0, 100)，李四区间：[100, 200)
     *   随机数在[0, 200)均匀分布，每人50%概率
     * - 列表顺序不影响公平性，因为区间大小只取决于积分
     * 
     * @return 中签的参与者
     */
    public Participant draw() {
        if (totalScore == 0) {
            throw new IllegalStateException("总积分为0，无法进行摇号");
        }

        // 生成 [0, totalScore) 之间的随机数（均匀分布）
        // 注意：使用 long 类型避免精度问题
        long randomValue = (long) (random.nextDouble() * totalScore);

        // 使用累积区间查找中签者
        // 每个人分配的区间大小为：其积分值
        // 例如：积分100的人，区间大小为100；积分200的人，区间大小为200
        long cumulativeScore = 0;
        for (Participant participant : participants) {
            cumulativeScore += participant.getScore();
            // 如果随机数落在当前参与者的区间内，该参与者中签
            // 注意：区间是左闭右开 [start, end)
            if (randomValue < cumulativeScore) {
                return participant;
            }
        }

        // 理论上不应该到达这里（因为randomValue < totalScore），但为了安全起见返回最后一个
        return participants.get(participants.size() - 1);
    }

    /**
     * 执行多次摇号（不重复），返回中签者列表
     * 
     * @param count 摇号次数
     * @return 中签者列表
     */
    public List<Participant> drawMultiple(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("摇号次数必须大于0");
        }
        if (count > participants.size()) {
            throw new IllegalArgumentException("摇号次数不能超过参与者数量");
        }

        // 创建参与者副本和权重列表
        List<Participant> remaining = new ArrayList<>(participants);
        List<Integer> scores = new ArrayList<>();
        for (Participant p : remaining) {
            scores.add(p.getScore());
        }

        List<Participant> winners = new ArrayList<>();
        long currentTotalScore = totalScore;

        for (int i = 0; i < count; i++) {
            // 生成随机数
            long randomValue = (long) (random.nextDouble() * currentTotalScore);

            // 找到中签者
            long cumulativeScore = 0;
            int selectedIndex = -1;
            for (int j = 0; j < remaining.size(); j++) {
                cumulativeScore += scores.get(j);
                if (randomValue < cumulativeScore) {
                    selectedIndex = j;
                    break;
                }
            }

            if (selectedIndex == -1) {
                selectedIndex = remaining.size() - 1;
            }

            // 添加中签者并从列表中移除
            Participant winner = remaining.get(selectedIndex);
            winners.add(winner);
            currentTotalScore -= scores.get(selectedIndex);
            remaining.remove(selectedIndex);
            scores.remove(selectedIndex);
        }

        return winners;
    }

    /**
     * 计算每个参与者的理论中签概率
     * 
     * @return Map<参与者姓名, 中签概率>
     */
    public Map<String, Double> getProbability() {
        Map<String, Double> probabilityMap = new LinkedHashMap<>();
        for (Participant p : participants) {
            double probability = (double) p.getScore() / totalScore;
            probabilityMap.put(p.getName(), probability);
        }
        return probabilityMap;
    }

    /**
     * 获取所有参与者
     */
    public List<Participant> getParticipants() {
        return new ArrayList<>(participants);
    }

    /**
     * 获取总积分
     */
    public long getTotalScore() {
        return totalScore;
    }

    /**
     * 添加参与者（动态添加）
     */
    public void addParticipant(Participant participant) {
        if (participant == null) {
            throw new IllegalArgumentException("参与者不能为null");
        }
        participants.add(participant);
        totalScore += participant.getScore();
    }

    /**
     * 移除参与者
     */
    public boolean removeParticipant(Participant participant) {
        if (participants.remove(participant)) {
            totalScore = calculateTotalScore();
            return true;
        }
        return false;
    }
}
