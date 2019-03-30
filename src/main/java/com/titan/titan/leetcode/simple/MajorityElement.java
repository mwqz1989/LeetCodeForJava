package com.titan.titan.leetcode.simple;

import java.util.HashMap;

/**
 * LeetCode Number : 169
 */
public class MajorityElement {

    public int majorityElement(int[] nums) {
        HashMap<Integer,Integer> timesMap = new HashMap<>();
        int numsLenHelf = nums.length >> 1;
        for(int i : nums){
            Integer times = timesMap.get(Integer.valueOf(i));
            if(null == times){
                timesMap.put(i,times = 1);
                if(times > numsLenHelf)
                    return i;
            }else{
                timesMap.put(i,++times);
                if(times > numsLenHelf)
                    return i;
            }

        }
        return numsLenHelf;
    }
}
