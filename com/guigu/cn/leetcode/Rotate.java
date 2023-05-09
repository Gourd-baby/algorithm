package com.guigu.cn.leetcode;

/**
 * ClassName:Rotate
 * Package:com.guigu.cn.leetcode
 * description:旋转数组
 * 给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
 *
 * 示例
 * 输入: nums = [1,2,3,4,5,6,7], k = 3
 * 输出: [5,6,7,1,2,3,4]
 * 解释:
 * 向右轮转 1 步: [7,1,2,3,4,5,6]
 * 向右轮转 2 步: [6,7,1,2,3,4,5]
 * 向右轮转 3 步: [5,6,7,1,2,3,4]

 * Author:crj
 * Create:2023/5/7 8:24
 */
public class Rotate {
    public static void main(String[] args) {
        int[] nums = {1,2};
//        int[] nums = {-1,-100,3,99};
        int k = 3;
        rotate(nums,3);
    }


    public static void rotate(int[] nums, int k)
    {
        int[] array = new int[nums.length];
        int begin = (nums.length - k)>0?nums.length-k:k-nums.length;
        int auto_incre = -1;
        for (int i = begin; i < nums.length ; i++) {
            array[++auto_incre] = nums[i];
        }
        for (int i = 0; i <= begin -1; i++) {
            array[++auto_incre]=nums[i];
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = array[i];
        }
    }
}
//        int length = nums.length;
//        int temp[] = new int[length];
//        for (int i = 0; i < length; i++) {
//        temp[i] = nums[i];
//        }
//        //然后在把临时数组的值重新放到原数组，并且往右移动k位
//        for (int i = 0; i < length; i++) {
//        nums[(i + k) % length] = temp[i];
//        }
