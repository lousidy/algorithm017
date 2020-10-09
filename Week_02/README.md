学习笔记

1，hashmap的总结

hashmap主体是数组进行存储，当数组处的节点产生碰撞后，会向下眼神，生成一条链表，当链表长度超过8，会转为红黑树

通过Key的hashCode方法计算出值，再通过某种算法计算出数组中存储数据的空间的索引值，如果没有数据则存储。

- HashTable的创建

> jdk8以前：在创建的时候就会有一个Entry[] table来存储
>
> jdk8以后：会在第一次put方法被调用的时候创建Entry[] 数组

计算索引的方式：key的hashCode方法计算出hash值，再用hash值与数组长度进行无符号右移(>>>)，按位异或（^）、按位与（&）计算出索引

源码查看：

#### put方法（重点）**hash值计算**

```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    if ((tab = table) == null || (n = tab.length) == 0)
        // 在第一次put的时候会创建Node数组
        n = (tab = resize()).length;
    // 这里执行了hash计算的操作。没有出现碰撞
    // p已经在这里实现了赋值，就是hash计算后的节点
    if ((p = tab[i = (n - 1) & hash]) == null)
        // 如果没有碰撞，则直接put
        // 这里的null代表的是拉链法 链表 的下一个结点暂时为null（还没有碰撞产生）
        tab[i] = newNode(hash, key, value, null);
    else {
        Node<K,V> e; K k;
        // 判断key是否相同
        if (p.hash == hash &&
            // 如果key相同或者key与k相等
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        // 判断是否是树节点
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {
            // 链表遍历
            for (int binCount = 0; ; ++binCount) {
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                    // 如果结点大于8，那么链表成树
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    break;
                }
                // 链表上出现相同的key
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }
        // 如果当前位置存在结点
        if (e != null) { // existing mapping for key
            // 只修改值
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }
    // 记录map的修改次数
    ++modCount;
	// 插入成功，size+1， 当大于阈值，执行扩容    
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}
123456789101112131415161718192021222324252627282930313233343536373839404142434445464748495051525354555657
```

#### 红黑树转换

```java
final void treeifyBin(Node<K,V>[] tab, int hash) {
    int n, index; Node<K,V> e;
    // 如果数组长度小于64，会执行扩容，而不是建树
    if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
        resize(); // 倍增扩容
    // 判断当前桶中的节点是否为null
    else if ((e = tab[index = (n - 1) & hash]) != null) {
        // hd -- 头结点，tl -- 尾结点
        TreeNode<K,V> hd = null, tl = null;
        // 将链表里的每一个节点替换成TreeNode
        do {
            // 将Node转换为TreeNode
            TreeNode<K,V> p = replacementTreeNode(e, null);
            // p作为树的根节点
            if (tl == null)
                hd = p;
            else {
                // 
                p.prev = tl;
                tl.next = p;
            }
            tl = p;
        } while ((e = e.next) != null);
        // 如果根节点不为空，执行建树
        if ((tab[index] = hd) != null)
            hd.treeify(tab);
    }
}
12345678910111213141516171819202122232425262728
```

#### 扩容方法 - resize

```java
final Node<K,V>[] resize() {
    Node<K,V>[] oldTab = table;
    int oldCap = (oldTab == null) ? 0 : oldTab.length;
    int oldThr = threshold;
    // 新的容量和阈值
    int newCap, newThr = 0;
    if (oldCap > 0) {
        // 进行扩容。如果超过最大容量，那么不变，阈值变为最大
        if (oldCap >= MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return oldTab;
        }
        // 如果扩容后满足条件，那么新阈值等于旧阈值翻倍
        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                 oldCap >= DEFAULT_INITIAL_CAPACITY)
            newThr = oldThr << 1; // double threshold
    }
    // 通过阈值的方式来创建HashMap
    else if (oldThr > 0) // initial capacity was placed in threshold
        newCap = oldThr;
    else {               // zero initial threshold signifies using defaults
        //阈值为零，那么使用默认值
        newCap = DEFAULT_INITIAL_CAPACITY;
        newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
    }
    // 扩容超界或者旧容量小于默认容量
    if (newThr == 0) {
        float ft = (float)newCap * loadFactor;
        newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                  (int)ft : Integer.MAX_VALUE);
    }
    threshold = newThr;
    @SuppressWarnings({"rawtypes","unchecked"})
    // 重新创建数组
    Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
    table = newTab;
    if (oldTab != null) {
        // 将旧数组的数组放到新数组里
        for (int j = 0; j < oldCap; ++j) {
            Node<K,V> e;
            if ((e = oldTab[j]) != null) {
                oldTab[j] = null;
                if (e.next == null)
                    newTab[e.hash & (newCap - 1)] = e;
                else if (e instanceof TreeNode)
                    // 树节点建树
                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                else { // preserve order
                    // 链表复制
                    Node<K,V> loHead = null, loTail = null;
                    Node<K,V> hiHead = null, hiTail = null;
                    Node<K,V> next;
                    do {
                        next = e.next;
                        // 变化的高位为0
                        if ((e.hash & oldCap) == 0) {
                            if (loTail == null)
                                loHead = e;
                            else
                                loTail.next = e;
                            loTail = e;
                        }
                        else {
                            // 高位变为1
                            if (hiTail == null)
                                hiHead = e;
                            else
                                hiTail.next = e;
                            hiTail = e;
                        }
                    } while ((e = next) != null);
                    if (loTail != null) {
                        loTail.next = null;
                        // 将结点放在原位置
                        newTab[j] = loHead;
                    }
                    if (hiTail != null) {
                        hiTail.next = null;
                        // 将节点放在原位置+旧容量的地方
                        newTab[j + oldCap] = hiHead;
                    }
                }
            }
        }
    }
    return newTab;
}
```