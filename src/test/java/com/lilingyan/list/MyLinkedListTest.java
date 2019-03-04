package com.lilingyan.list;

import org.junit.Assert;
import org.junit.Test;
import java.util.LinkedList;
import java.util.Random;

/**
 * @Author: lilingyan
 * @Date 2019/3/4 14:56
 */
public class MyLinkedListTest {

    private static Random random = new Random();

    @Test
    public void putAndRemoveAndGetTest(){
        int max = 65535/256;
        MyLinkedList<String> myLinkedList = new MyLinkedList<>();
        LinkedList<String> linkedList = new LinkedList<>();
        for (int i = 0; i < max/2; i++) {
            int key = random.nextInt(max);
            myLinkedList.add(String.valueOf(key));
            linkedList.add(String.valueOf(key));
        }
        for (int i = 0; i < max/2; i++) {
            int key = random.nextInt(max);
            if(key<=myLinkedList.size()){
                myLinkedList.add(key,String.valueOf(key));
                linkedList.add(key,String.valueOf(key));
            }
        }
        Assert.assertTrue(myLinkedList.size() == linkedList.size());
        System.out.println(myLinkedList.size());
        for (int i = 0; i < max/2; i++) {
            int key = random.nextInt(max);
            if(myLinkedList.contains(String.valueOf(key))){
                Assert.assertTrue(myLinkedList.remove(String.valueOf(key))&&(linkedList.remove(String.valueOf(key))));
            }else{
                Assert.assertTrue(!myLinkedList.remove(String.valueOf(key))&&!linkedList.remove(String.valueOf(key)));
            }
        }
        for (int i = 0; i < max/2; i++) {
            int key = random.nextInt(max);
            if(key<myLinkedList.size()){
                Assert.assertTrue(myLinkedList.remove(key).equals(linkedList.remove(key)));
            }
        }
        Assert.assertTrue(myLinkedList.size() == linkedList.size());
        for (int i = 0; i < max; i++) {
            int key = random.nextInt(max);
            if(key<myLinkedList.size()){
                Assert.assertTrue(myLinkedList.get(key).equals(linkedList.get(key)));
            }
        }
    }

}
