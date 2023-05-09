package com.guigu.cn.leetcode;

/**
 * ClassName:MaxProfit
 * Package:com.guigu.cn.leetcode
 * description:
 * 买卖股票的最佳时机 II
 * 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
 *
 * 在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。你也可以先购买，然后在 同一天 出售。
 *
 * 返回 你能获得的 最大 利润 。
 *
 * 作者：LeetCode
 * 链接：https://leetcode.cn/leetbook/read/top-interview-questions-easy/x2zsx1/
 * Author:crj
 * Create:2023/5/7 7:27
 */
public class MaxProfit {

    public int maxProfit(int[] prices) {
        /**
         * 两两比较：
         *      只要后面的比前面大，则累加他们的差值
         */
        int sum = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i-1] < prices[i])
            {
                sum += prices[i] - prices[i -1];
            }
        }
        return sum;
    }
}
