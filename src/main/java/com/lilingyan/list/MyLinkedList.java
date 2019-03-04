package com.lilingyan.list;

/**
 * 双向链表
 * @Author: lilingyan
 * @Date 2019/3/4 10:10
 */
public class MyLinkedList<E> {

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
    public boolean add(E e) {
        linkLast(e);
        return true;
    }
    public void add(int index, E element) {
        checkPositionIndex(index);

        if (index == size)
            linkLast(element);
        else
            linkBefore(element, node(index));
    }
    /**
     * 在指定的后继节点前添加节点
     * @param e
     * @param succ
     */
    void linkBefore(E e, Node<E> succ) {
        // assert succ != null;
        //获取后继节点的前节点
        final Node<E> pred = succ.prev;
        final Node<E> newNode = new Node<>(pred, e, succ);
        //把后继节点的前指针指向新节点
        succ.prev = newNode;
        /**
         * 如果前节点部位空
         * 则把前节点后指针指向新节点
         *
         * 如果是空，说明添加的是头节点
         */
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
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
    public E remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
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

        //置空节点的引用(现在jvm不回有循环引用的问题，所以并没什么用)
        x.item = null;
        size--;
        return element;
    }
    /**
     * 支持删除null对象
     * @param o
     * @return
     */
    @SuppressWarnings("Duplicates")
    public boolean remove(Object o) {
        if (o == null) {
            /**
             * 如果要删除的对象是null
             * null没有equals方法，所以要特殊处理
             */
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            /**
             * 否则遍历整个链表
             * 找到一样的删除
             */
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }
    //=========================删除==========================

    //=========================查找==========================
    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }
    /**
     * 按链表下标查找节点
     * @param index
     * @return
     */
    Node<E> node(int index) {
        // assert isElementIndex(index);

        /**
         * 先中分一下
         * 然后判断在前半段还是后半段
         * 最后循环读出来
         */
        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }
    /**
     * 按节点引用对象查找节点下标
     * @param o
     * @return
     */
    @SuppressWarnings("Duplicates")
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            /**
             * null没有equals
             * 需要单独查询
             */
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null)
                    return index;
                index++;
            }
        } else {
            /**
             * 遍历获取下标
             */
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item))
                    return index;
                index++;
            }
        }
        return -1;
    }
    //=========================查找==========================

    //=========================一些常用方法封装==========================
    /**
     * 校验下标
     * @param index
     */
    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
    /**
     * 校验链表下标是否月结
     * @param index
     * @return
     */
    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }
    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }
    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }
    //=========================一些常用方法封装==========================

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
