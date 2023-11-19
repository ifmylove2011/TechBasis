package com.xter.algorithm.other;

/**
 * m行n列格子，从(0,0)起移动，坐标(x,y)数位和不能起过k，问总共可移动多少个格子
 */
public class RobotMove {
    public static void main(String[] args) {
        System.out.println(move(10,10,9));
    }

    private static int move(int m, int n, int k) {
        if (m <= 0 || n <= 0 || k < 0) {
            return 0;
        }
        boolean[] visited = new boolean[m * n];
        int count = moveCount(k, m, n, 0, 0, visited);
        return count;
    }

    /**
     * 尝试这一步是否能踏出，能踏出则+1，否则为0
     *
     * @param k
     * @param m
     * @param n
     * @param row
     * @param column
     * @param visited
     * @return
     */
    private static int moveCount(int k, int m, int n, int row, int column, boolean[] visited) {
        int count = 0;
        if (check(k, m, n, row, column, visited)) {
            visited[row * n + column] = true;
            count = 1 + moveCount(k, m, n, row - 1, column, visited)
                    + moveCount(k, m, n, row, column - 1, visited)
                    + moveCount(k, m, n, row + 1, column, visited)
                    + moveCount(k, m, n, row, column + 1, visited);
        }
        return count;
    }

    private static boolean check(int k, int m, int n, int row, int column, boolean[] visited) {
        if (row >= 0 && row < m && column >= 0 && column < n && digitSum(row) + digitSum(column) <= k && !visited[row * n + column]) {
            return true;
        }
        return false;
    }

    private static int digitSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num = num / 10;
        }
        return sum;
    }
}
