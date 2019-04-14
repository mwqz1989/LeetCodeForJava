package com.titan.titan.leetcode.simple;

import java.util.Stack;

/**
 * LeetCode number：20
 */
public class ValidParentheses {

    public static boolean isValid(String s) {

        if (null == s || s.isEmpty())
            return true;

        Stack<Character> stack = new Stack<Character>();
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if ('[' == c || '{' == c || '(' == c) {
                stack.push(c);
            } else {
                if (stack.isEmpty())
                    return false;
                if (stack.peek().charValue() == (c - 1) || stack.peek().charValue() == (c - 2)) {
                    stack.pop();
                }else{
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

}
