package round2;

import java.util.Arrays;

/**
 * MahjongHandChecker - 麻将听牌/胡牌判定（简化规则）
 *
 * 题目（概要）：给定牌型数组 tiles（数字表示牌面），判断是否能组成合法牌型。规则：一对将 + 若干顺子/刻子。
 * 顺子为连续三张，刻子为相同三张。
 *
 * 算法原理：
 * - 枚举将牌：先排序，枚举每对相同牌作为将，移除后检查剩余能否全部组成顺子/刻子。
 * - 贪心分组：优先尝试刻子（三张同牌），再尝试顺子（连续三张），递归检验。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：排序 tiles；枚举相邻相同牌 (tiles[i]==tiles[i+1]) 作为将。
 * - 步骤 2：removePair 移除该对，得到 remainingTiles。
 * - 步骤 3：canBeGrouped 递归：空则 true；否则尝试刻子或顺子，递归剩余部分。
 *
 * 关键洞察：将牌唯一；顺子需连续三张（tiles[0]+1==tiles[1]&&tiles[1]+1==tiles[2]）。
 *
 * 时间复杂度：O(n²)（枚举将 × 分组尝试）
 * 空间复杂度：O(n)
 *
 * 示例：[1,2,3,4,4,5,6,7] 将 4-4，剩余可组顺子 → true
 */
public class MahjongHandChecker {

    public static boolean canFromValidHand(int[] tiles) {
        Arrays.sort(tiles);

        for (int i = 0; i < tiles.length - 1; i++) {
            if (tiles[i] == tiles[i + 1]) {

                int[] remainingTiles = removePair(tiles, i);

                if (canBeGrouped(remainingTiles)) {
                    return true;
                }
            }
        }

        return false;

    }

    private static int[] removePair(int[] tiles, int pairIndex) {
        int[] newTiles = new int[tiles.length - 2];
        int index = 0;
        for (int i = 0; i < tiles.length; i++) {
            if (i != pairIndex && i != pairIndex + 1) {
                newTiles[index++] = tiles[i];
            }
        }
        return newTiles;
    }

    private static boolean canBeGrouped(int[] tiles) {
        if (tiles.length == 0) {
            return true;
        }

        if (tiles.length >= 3 && tiles[0] == tiles[1] && tiles[1] == tiles[2]) {
            return canBeGrouped(Arrays.copyOfRange(tiles, 3, tiles.length));
        }

        if (tiles.length >= 3 && hasConsecutiveTriplet(tiles)) {
            return canBeGrouped(Arrays.copyOfRange(tiles, 3, tiles.length));
        }
        return false;
    }

    private static boolean hasConsecutiveTriplet(int[] tiles) {
        return tiles[0] + 1 == tiles[1] && tiles[1] + 1 == tiles[2];
    }

    public static void main(String[] args) {
        int[] tiles = { 1, 2, 3, 4, 4, 5, 6, 7};

        System.out.println(canFromValidHand(tiles));
    }
}
