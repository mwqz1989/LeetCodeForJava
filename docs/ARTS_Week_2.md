Algorithm:
LeetCode number：41   First Missing Positive

`com.titan.titan.leetcode.simple.FirstMissingPositive`

```java
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
```


Review:

Tip:

Share: