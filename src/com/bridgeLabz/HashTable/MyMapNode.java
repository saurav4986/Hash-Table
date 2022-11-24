package com.bridgeLabz.HashTable;
/*(UC-2_Para-Frequency)
Ability to find frequency of words in a large paragraph phrase
“Paranoids are not paranoid because they are paranoid but because they
keep putting themselves deliberately into paranoid avoidable situations”
- Use hashcode to find index of the words in the para
- Create LinkedList for each index and store the words and its frequency
- Use LinkedList to do the Hash Table Operation
- To do this create MyMapNode with Key Value Pair and create LinkedList of MyMapNode*/

import java.util.ArrayList;
import java.util.Objects;

class HashNode<K,V> {
    K key;
    V value;

    final int hashcode;
    HashNode next;

    HashNode(K key, V value,int hashcode)
    {
        this.key = key;
        this.value = value;
        next = null;
        this.hashcode = hashcode;
    }

    HashNode(String key,Integer value,int hashcode)
    {
        this.key = (K) key;
        this.value = (V) value;
        next = null;
        this.hashcode = hashcode;
    }
}

public class MyMapNode<K,V> {
    private ArrayList<HashNode<K,V>> bucketList;

    private int numBuckets;

    private int size;

    MyMapNode() {
        bucketList = new ArrayList<>();
        numBuckets = 10;
        size = 0;

        for(int i=0;i<10;i++)
        {
            bucketList.add(null);
        }
    }

    public final int hashcode(K key)
    {
        return Objects.hashCode(key);
    }

    public int getBucketIndex(K key)
    {
        int index = hashcode(key) % numBuckets;
        index = index < 0 ? index * -1 : index;
        return index;
    }

    public void add(K key, V value)
    {

        HashNode<K,V> newnode = new HashNode(key, value, hashcode(key));
        int index = getBucketIndex(key);

        HashNode head = bucketList.get(index);

        if(head == null)
        {
            bucketList.set(index, newnode);
            size++;
            if(size >= numBuckets)
                expandList();
            return;
        }

        HashNode tempnode = head;

        while(tempnode != null)
        {
            if(tempnode.key.equals(key))
            {
                tempnode.value = ((Integer)tempnode.value) + (Integer)value;
                return;
            }
            tempnode = tempnode.next;
        }

        newnode.next = head;
        bucketList.set(index, newnode);
        size++;
        if(size >= numBuckets)
            expandList();
    }

    public void expandList()
    {
        for(int i=0;i<10;i++)
        {
            bucketList.add(null);
        }
        this.numBuckets = 20;
    }

    public void display()
    {
        for(HashNode<K,V> item : bucketList)
        {
            if(item == null)
                continue;
            else
            {
                while(item != null)
                {
                    System.out.println("word =>" + item.key + ",  frequency => "+item.value);
                    item = item.next;
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {

        MyMapNode<String, Integer> map = new MyMapNode();
        String s = "Paranoids are not paranoid because they are paranoid but because they keep putting themselves deliberately into paranoid avoidable situations";
        String arr[] = s.split("\\s");

        for(int i=0;i<arr.length;i++)
        {
            map.add(arr[i], 1);
        }

        System.out.println("Frequency of words is as follows");
        map.display();
        System.out.println("size is " + map.size+ " no of buckets is "+ map.numBuckets);
    }

}
