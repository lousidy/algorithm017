学习笔记

DFS和BFS都是每个节点访问一次，且仅访问一次

DFS：深度优先搜索

主要使用栈，模板如下：

```java
Set visited = new HashSet<>();

public void dfs(Node node, int target, Set visited) {
	if (visited.contains(node)
		return;
	if (node.val == target)
		return;
	visited.add(node);
	process(node);
	for (Node next : node.children) {
		dfs(next, target, visited);
	} 
}
```

BFS：广度优先搜索

主要是队列，或者双端队列，模板如下：

```java
public void bfs(Node node, int target, Set visited) {
	 Deque deque = new LinkedList<>();
	 stack.offer(node);
	 while (!stack.isEmpty()) {
		 Node cur = stack.poll();
		 visited.add(cur);
		 process(cur);
		 for (Node next : gen_relation_nodes(cur)) 
			 deque.offer(next);
	 }
```

贪心算法

每一步选择最优的方法，目的就是最终结果是最好的算法

平常中，很少看到

二分查找

单调性，存有边界，能够通过索引访问

查找的时候经常提起。貌似是常考题

模板：

```java
int left = 0， right = array.length() - 1;
    while (left <= right) {
		int mid = (right - left) /2 + left;
	    if (array[mid] == target) 
			break or return;
		else if (array[mid] < target) 
			left = mid + 1;
		else 
			right = mid - 1;	 	
	 }
```

