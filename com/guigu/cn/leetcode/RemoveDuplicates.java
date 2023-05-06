package com.guigu.cn.leetcode;

/**
 * ClassName:RemoveDuplicates
 * Package:com.guigu.cn.leetcode
 * description: 删除排序数组的重复项
 * Author:crj
 * Create:2023/5/6 7:17
 */
public class RemoveDuplicates {
    /**
     * 给你一个 升序排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，
     * 返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。然后返回 nums 中唯一元素的个数
     *
     * 作者：LeetCode
     * 链接：https://leetcode.cn/leetbook/read/top-interview-questions-easy/x2gy9m/
     */
    public static void main(String[] args) {
        int[] array = new int[]{0,1,2,2,3};
        System.out.println(removeDuplicates(array));
    }
    //双指针解决
    /**
     *     右指针往右边移动
     *          如果右指针指向的值等于左指针指向的值，左指针不动。
     *          如果右指针指向的值不等于左指针指向的值，那么左指针往右移一步，然后再把右指针指向的值赋给左指针。
     */
    public static int removeDuplicates(int[] nums) {
        if (nums == null && nums.length ==0){
            return 0;
        }
        int left = 0;
        for (int right = 1;right < nums.length;right++)
        {
            if (nums[left] != nums[right] )
            {
                nums[++left] = nums[right];
            }
        }
        return ++left;
    }

}
