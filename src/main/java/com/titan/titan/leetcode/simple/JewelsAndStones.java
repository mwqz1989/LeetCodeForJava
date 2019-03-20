package com.titan.titan.leetcode.simple;

import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode Number : 771
 */
public class JewelsAndStones {
    public int numJewelsInStones(String J, String S) {
        char[] jChars = J.toCharArray();
        char[] sChars = S.toCharArray();
        int result = 0;
        Set<Character> jSet = new HashSet<Character>((int)Math.ceil(jChars.length/0.75));

        for(char c : jChars){
            jSet.add(new Character(c));
        }

        for(char s : sChars){
            if(jSet.contains(s)){
                result++;
            }
        }
        return result;
    }
}
