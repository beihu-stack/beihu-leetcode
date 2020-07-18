package ltd.beihu.leetcode.interview;

import ltd.beihu.leetcode.utils.GsonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 40个算法题
 *
 * @author Adam
 * @date 2020/7/11
 */
public class FortyAlgorithm {

    /**
     * 206. 反转链表
     *
     * 定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
     * 示例:
     * 输入: 1->2->3->4->5->NULL
     * 输出: 5->4->3->2->1->NULL
     * <p>
     * 限制：
     * 0 <= 节点个数 <= 5000
     */
    public static ListNode reverseList1(ListNode head) {

        ListNode pre = null;
        ListNode current = head;

        while (current != null) {
            ListNode temp = current.next;
            current.next = pre;
            pre = current;
            current = temp;
        }
        return pre;
    }
    public static ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode a = reverseList2(head.next);
        head.next.next = head;  // 5的next 换成 4∂
        head.next = null;  // 4的next 换成 null
        return a;  // 返回当前节点 5
    }

    /**
     * 24. 两两交换链表中的节点
     *
     * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
     * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     * 示例:
     * 给定 1->2->3->4, 你应该返回 2->1->4->3.
     *
     * 复杂度分析
     * 时间复杂度：O(N)，其中 N 指的是链表的节点数量。
     * 空间复杂度：O(N)，递归过程使用的堆栈空间。
     */
    public static ListNode swapPairs1(ListNode head) {

        // 假节点 核心 - 所有转换指针都会修改dummy的地址
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode preNode = dummy;
        while (head != null && head.next != null) {
            ListNode firstNode = head;
            ListNode secondNode = head.next;

            // 交换两个节点：
            // 1. 把3放到1的next 得到  1->3->4
            // 2. 把1->3挂到2的next
            // 3. 将交换后的节点 放到后续节点的头部
            firstNode.next = secondNode.next;
            secondNode.next = firstNode;
            preNode.next = secondNode;

            // 下一次迭代 准备
            preNode = firstNode;  // preNode 只是用来记录处理节点的前一个node
            head = firstNode.next;
        }

        return dummy.next;
    }
    public static ListNode swapPairs2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode firstNode = head;  // 第一个节点
        ListNode secondNod = head.next;  // 第二个节点
        firstNode.next = swapPairs2(secondNod.next);   // 递归 交换两个节点指针 将交换后的链表给到 第一个节点的next
        secondNod.next = firstNode;   // 将第二个节点的next 设置为第一个节点
        return secondNod;  // 交换后 返回第二个节点
    }

    /**
     * 141. 环形链表
     *
     * 给定一个链表，判断链表中是否有环。
     * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
     */
    public boolean hasCycle(ListNode head) {
        // 方式1：hashSet存储<ListNode>
        // 方式2：快慢指针（龟兔赛跑）:两个指针同时跑，一个每次跑一步，一个每次跑两步，当他们相遇时，说明有环，当有next为null时，说明无环
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode faster = head.next;

        while (faster != null && faster.next != null){
            if (faster == slow) {
                return true;
            }
            faster = faster.next.next;
            slow = slow.next;
        }
        return false;
    }

    /**
     * 20. 有效的括号
     *
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
     * 有效字符串需满足：
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     * 注意空字符串可被认为是有效字符串。
     */
    public boolean isValid(String s) {
        Map<Character, Character> mapping = new HashMap<>();
        mapping.put(')', '(');
        mapping.put('}', '{');
        mapping.put(']', '[');
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!mapping.containsKey(c)) {
                stack.push(c);
            } else {
                if (stack.empty()) {
                    return false;
                }
                if (stack.pop().charValue() != mapping.get(c).charValue()) {
                    return false;
                }
            }
        }
        return stack.empty();
    }



    // main

    public static void main(String[] args) {
        // testReverseList1();
        // testReverseList2();
        // testswapPairs1();
        // testswapPairs2();

        testMyQueue();
    }

    // test reverseList

    public static ListNode buildListNode() {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        return head;
    }

    public static void testReverseList1() {
        ListNode listNode = reverseList1(buildListNode());
        System.out.println(GsonUtils.gson.toJson(listNode));
    }
    public static void testReverseList2() {
        ListNode listNode = reverseList2(buildListNode());
        System.out.println(GsonUtils.gson.toJson(listNode));
    }

    public static void testswapPairs1() {
        ListNode listNode = swapPairs1(buildListNode());
        System.out.println(GsonUtils.gson.toJson(listNode));
    }
    public static void testswapPairs2() {
        ListNode listNode = swapPairs2(buildListNode());
        System.out.println(GsonUtils.gson.toJson(listNode));
    }

    public static void testMyQueue() {
        MyQueue myQueue = new MyQueue();
        myQueue.push(1);
        myQueue.push(2);
        myQueue.push(3);
        System.out.println(myQueue.pop());
        myQueue.push(4);
        System.out.println(myQueue.peek());
        System.out.println(myQueue.pop());
        System.out.println(myQueue.empty());

        while (!myQueue.empty()) {
            System.out.println(myQueue.pop());
        }
    }

}

/**
 * 伪链表
 */
class ListNode {

    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

}

/**
 * 232. 用栈实现队列
 *      官方实现不需要考虑多线程操作
 */
class MyQueue {

    private static Stack<Integer> inStack;
    private static Stack<Integer> outStack;
    private static ReentrantLock lock;

    /** Initialize your data structure here. */
    public MyQueue() {
        inStack = new Stack<>();
        outStack = new Stack<>();
        lock = new ReentrantLock();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        lock.lock();
        try {
            inStack.push(x);
        } finally {
            lock.unlock();
        }
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        lock.lock();
        try {
            if (outStack.isEmpty()) {
                while (!inStack.isEmpty()) {
                    outStack.push(inStack.pop());
                }
            }
            return outStack.pop();
        } finally {
            lock.unlock();
        }
    }

    /** Get the front element. */
    public int peek() {
        lock.lock();
        try {
            if (outStack.isEmpty()) {
                while (!inStack.isEmpty()) {
                    outStack.push(inStack.pop());
                }
            }
            return outStack.peek();
        } finally {
            lock.unlock();
        }
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        if (inStack.isEmpty() && outStack.isEmpty()) {
            return true;
        }
        return false;
    }
}
