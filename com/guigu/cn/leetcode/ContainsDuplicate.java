package com.guigu.cn.leetcode;

import java.util.Arrays;

/**
 * ClassName:ContainsDuplicate
 * Package:com.guigu.cn.leetcode
 * description:存在重复元素
 *      给你一个整数数组 nums 。如果任一值在数组中出现 至少两次 ，返回 true ；
 *      如果数组中每个元素互不相同，返回 false
 * Author:crj
 * Create:2023/5/9 9:27
 */
public class ContainsDuplicate {

    public static void main(String[] args) {
        int[] nums ={2,14,18,22,22};
        System.out.println(containsDuplicate(nums));
    }
    public static boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i-1] == nums [i])
            {
                return true;
            }
        }
        return false;
    }
}
