package com.lilingyan.deque;

import org.junit.Assert;
import org.junit.Test;
import java.util.LinkedList;
import java.util.Random;

/**
 * @Author: lilingyan
 * @Date 2019/3/4 16:55
 */
public class MyLinkedDequeTest {

    private static Random random = new Random();

    @Test
    public void putAndRemoveAndGetTest(){
        int max = 65535/256;
        MyLinkedDeque<String> myLinkedDeque = new MyLinkedDeque<>();
        LinkedList<String> linkedList = new LinkedList<>();
        for (int i = 0; i < max/2; i++) {
            int key = random.nextInt(max);
            myLinkedDeque.offer(String.valueOf(key));
            linkedList.offer(String.valueOf(key));
        }
        for (int i = 0; i < max/2; i++) {
            int key = random.nextInt(max);
            myLinkedDeque.offerLast(String.valueOf(key));
            linkedList.offerLast(String.valueOf(key));
        }
        Assert.assertTrue(myLinkedDeque.size() == linkedList.size());
        System.out.println(myLinkedDeque.size());

        for (int i = 0; i < max/2; i++) {
            Assert.assertTrue(myLinkedDeque.poll().equals(linkedList.poll()));
        }
        Assert.assertTrue(myLinkedDeque.peek().equals(linkedList.peek()));
        Assert.assertTrue(myLinkedDeque.size() == linkedList.size());
        System.out.println(myLinkedDeque.size());

        for (int i = 0; i < max/2; i++) {
            Assert.assertTrue(myLinkedDeque.remove().equals(linkedList.remove()));
        }
        Assert.assertTrue(myLinkedDeque.size() == linkedList.size());
        System.out.println(myLinkedDeque.size());

    }


}
