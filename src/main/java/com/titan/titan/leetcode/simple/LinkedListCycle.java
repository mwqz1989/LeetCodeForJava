package com.titan.titan.leetcode.simple;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * LeetCode Number : 141
 */
public class LinkedListCycle {

    public boolean hasCycle(ListNode head) {
        Set<ListNode> linkVal = new HashSet<ListNode>();

        if (null == head)
            return false;

        while (null != head.next){
            linkVal.add(head);
            head = head.next;
            if (linkVal.contains(head))
                return true;
        }
        Collections.sort();
        return false;
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }
}
