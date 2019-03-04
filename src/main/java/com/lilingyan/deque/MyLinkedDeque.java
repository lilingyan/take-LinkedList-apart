package com.lilingyan.deque;

import java.util.NoSuchElementException;

/**
 * 双向队列
 * @Author: lilingyan
 * @Date 2019/3/4 16:16
 */
public class MyLinkedDeque<E> {

    /**
     * 链表总长度
     */
    transient int size = 0;
    /**
     * 链表头节点
     */
    transient Node<E> first;
    /**
     * 链表尾节点
     */
    transient Node<E> last;

    //=========================添加==========================
    public void addFirst(E e) {
        linkFirst(e);
    }
    public void addLast(E e) {
        linkLast(e);
    }
    /**
     * 添加一个节点
     * 返回成功与否
     * 这里是不做线程安全，不做容量判断的添加
     * 所以都是成功
     * @param e
     * @return
     */
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }
    /**
     * 添加一个节点
     * 永远成功
     * @param e
     * @return
     */
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }
    /**
     * 插入并返回成功与否
     * @param e
     * @return
     */
    public boolean offer(E e) {
        return add(e);
    }
    /**
     * 因为是简单的双向链表
     * 没做容量等判断
     * 所以引用正确
     * @param e
     * @return
     */
    public boolean add(E e) {
        linkLast(e);
        return true;
    }
    /**
     * 添加头节点
     * @param e
     */
    private void linkFirst(E e) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(null, e, f);
        //把新加节点设置为头
        first = newNode;
        if (f == null)
            //如果是第一个节点，则初始化一下尾节点
            last = newNode;
        else
            //在原本头节点前挂载新节点
            f.prev = newNode;
        size++;
    }
    /**
     * 添加尾节点
     * @param e
     */
    void linkLast(E e) {
        final Node<E> l = last;
        //前指针是尾节点的节点
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            //如果是第一个元素，则设置下头指针
            first = newNode;
        else
            //把尾节点指针指向新节点
            l.next = newNode;
        size++;
    }
    //=========================添加==========================

    //=========================删除==========================
    public E removeFirst() {
        final Node<E> f = first;
        if (f == null)
            //如果是空队列 抛出错误
            throw new NoSuchElementException();
        return unlinkFirst(f);
    }
    public E removeLast() {
        final Node<E> l = last;
        if (l == null)
            throw new NoSuchElementException();
        return unlinkLast(l);
    }
    /**
     * 返回第一个节点并删除
     * 如果是空
     * 则返回null
     * @return
     */
    public E pollFirst() {
        final Node<E> f = first;
        return (f == null) ? null : unlinkFirst(f);
    }
    /**
     * 与pollFirst()同理(反向)
     * @return
     */
    public E pollLast() {
        final Node<E> l = last;
        return (l == null) ? null : unlinkLast(l);
    }
    /**
     * 删除第一个碰到的
     * 就是普通从头到尾的遍历
     * @param o
     * @return
     */
    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }
    /**
     * 删除最后一次碰到的节点
     * 其实就是倒着差链表，第一次碰到的
     * @param o
     * @return
     */
    public boolean removeLastOccurrence(Object o) {
        if (o == null) {
            /**
             * null不能equals
             * 特殊对待
             */
            for (Node<E> x = last; x != null; x = x.prev) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            /**
             * 倒查的第一次
             * 就是正查的最后一次
             */
            for (Node<E> x = last; x != null; x = x.prev) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 与pollFirst()一模一样
     * @return
     */
    public E poll() {
        final Node<E> f = first;
        return (f == null) ? null : unlinkFirst(f);
    }
    /**
     * 与removeFirst()一样
     * @return
     */
    public E remove() {
        return removeFirst();
    }
    /**
     * 就是把第二个节点直接挂到头指针上
     * @param f
     * @return
     */
    private E unlinkFirst(Node<E> f) {
        // assert f == first && f != null;
        final E element = f.item;
        final Node<E> next = f.next;
        f.item = null;
        f.next = null; // help GC
        first = next;
        if (next == null)
            //如果删的是最后一个节点，则把尾指针也置空
            last = null;
        else
            next.prev = null;
        size--;
        return element;
    }
    /**
     * 只是把倒数第二个节点后指针置空
     * @param l
     * @return
     */
    private E unlinkLast(Node<E> l) {
        // assert l == last && l != null;
        final E element = l.item;
        final Node<E> prev = l.prev;
        l.item = null;
        l.prev = null; // help GC
        last = prev;
        if (prev == null)
            first = null;
        else
            //倒数第二个节点后指针置空
            prev.next = null;
        size--;
        return element;
    }
    @SuppressWarnings("Duplicates")
    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }
    @SuppressWarnings("Duplicates")
    E unlink(Node<E> x) {
        // assert x != null;
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) {
            /**
             * 如果要删除的是第一个节点
             * 直接把它挂载到头指针上
             */
            first = next;
        } else {
            /**
             * 如果不是头节点
             * 则把前节点的后指针指向当前节点的后节点(跳过当前节点)
             */
            prev.next = next;
            x.prev = null;
        }

        /**
         * 与上同理(反向)
         */
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        return element;
    }
    //=========================删除==========================

    //=========================查找==========================
    /**
     * 返回第一个节点
     * 如果是空队列
     * 则抛错
     * @return
     */
    public E getFirst() {
        final Node<E> f = first;
        if (f == null)
            throw new NoSuchElementException();
        return f.item;
    }
    /**
     * getFirst()同理(反向)
     * @return
     */
    public E getLast() {
        final Node<E> l = last;
        if (l == null)
            throw new NoSuchElementException();
        return l.item;
    }
    /**
     * 查看第一个元素
     * @return
     */
    public E peekFirst() {
        final Node<E> f = first;
        return (f == null) ? null : f.item;
    }
    /**
     * 查看最后一个元素
     * @return
     */
    public E peekLast() {
        final Node<E> l = last;
        return (l == null) ? null : l.item;
    }
    /**
     * 就是getFirst()
     * @return
     */
    public E element() {
        return getFirst();
    }
    /**
     * 与peekFirst()一样
     * @return
     */
    public E peek() {
        final Node<E> f = first;
        return (f == null) ? null : f.item;
    }
    //=========================查找==========================

    /**
     * 标准的双向链表节点(前后指针)
     * @param <E>
     */
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;
        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public int size() {
        return size;
    }

}
