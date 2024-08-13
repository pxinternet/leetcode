package dfs;

import java.util.List;
import java.util.Set;

public class LC749containVirus {

    private static final int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public int containVirus(int[][] isInfected) {

        int m = isInfected.length, n = isInfected[0].length;

        int result = 0;

        while (true) {
            List<Set<Integer>> regions = new ArrayList<>();
            List<Set<Integer>> frontiers = new ArrayList<>();
            List<Integer> walls = new ArrayList<>();

            boolean[][] visited = new boolean[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (isInfected[i][j] == 1 && !visited[i][j]) {
                        Set<Integer> region = new HashSet<>();
                        Set<Integer> frontier = new HashSet<>();
                        int[] wall = new int[1];
                        dfs(isInfected, visited, i, j, region, frontier, wall);
                        regions.add(region);
                        frontiers.add(frontier);
                        walls.add(wall[0]);
                    }
                }
            }

            if (regions.isEmpty()) break;

            int maxThreatIdx = 0;

            for (int i = 1; i < frontiers.size(); i++) {
                if (frontiers.get(i).size() > frontiers.get(maxThreatIdx).size()) {
                    maxThreatIdx = i;
                }
            }

            result += walls.get(maxThreatIdx);

            for (int idx : regions.get(maxThreatIdx)) {
                int x = idx / n, y = idx % n;
                isInfected[x][y] = -1;
            }

            for (int i = 0; i < regions.size(); i++) {
                if (i == maxThreatIdx) continue;
                for (int idx : frontiers.get(i)) {
                    int x = idx / n, y = idx % n;
                    isInfected[x][y] = 1;
                }
            }
        }

        return result;
    }

    private void dfs(int[][] isInfected, boolean[][] visited, int x, int y, Set<Integer> region, Set<Integer> frontier, int[] wall) {
        int m = isInfected.length, n = isInfected[0].length;

        visited[x][y] = true;
        region.add(x * n + y);

        for (int[] dir : dirs) {
            int nx = x + dir[0];
            int ny = y + dir[1];

            if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                if (isInfected[x][y] == 1 && !visited[x][y]) {
                    dfs(isInfected, visited, nx, ny, region, frontier, wall);
                } else if (grid[nx][ny] == 0) {
                    frontier.add(nx * n + ny);
                    wall[0]++;
                }
            }
        }
    }
}
