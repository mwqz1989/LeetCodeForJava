package com.titan.titan.leetcode.simple;

/**
 * LeetCode Number : 41
 */
public class FirstMissingPositive {

    public int firstMissingPositive(int[] nums) {
        int length = nums.length;
        if (length == 0)
            return 1;
        int minPositiveNum = 0;
        for (int i = 0; i < length; i++){
            if (nums[i] > length || nums[i] <= 0)
                continue;
            if (i != nums[i] - 1){
                int temp = nums[nums[i] - 1];
                if (temp == nums[i])
                    continue;
                nums[nums[i] -1] = nums[i];
                nums[i] = temp;
                i--;
            }
        }
        boolean noMathed = false;
        for (int i = 0; i < length; i++){
            if (i != nums[i] - 1){
                minPositiveNum = i + 1;
                break;
            }else{
                if (i == length -1)
                    noMathed = true;
            }
        }
        if(noMathed)
            minPositiveNum = length + 1;
        return minPositiveNum;
    }

    public static void main(String[] args) {
        int[] nums = {1,3,1,4,3,4};
        int result = new FirstMissingPositive().firstMissingPositive(nums);
        System.out.println(result);
    }
}
