package round2;

import java.util.Arrays;

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
