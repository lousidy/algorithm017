学习笔记

初级排序代码--

选择排序：

```java
public static void sort(int a[]){       
        int N = a.length;    
        //每个循环交换一个，既是每个位置只交换一次，共N次
        for(int i=0;i<N;i++){      
            int min = i;
            //将a[i]和a[i+1]到a[n]中最小的元素交换
            //每一次for循环，可以获得i+1到n中最小元素的下标
            for(int j=i+1;j<N;j++){      
                if(a[j]<a[min])
                    min=j;     
            }
            //当前序列最小的值，与i交换位置
            Example.exch(a, i, min);  
        }
```

插入排序


```java
    public static void sort(int a[]){
			int N = a.length;
     
    	for(int i=0;i<N;i++){
         
        //将a[i]插入到a[i-1],a[i-2]....a[0]里面合适的位置
        for(int j=i;j>0&&a[j]<a[j-1];j--)
            Example.exch(a, j, j-1);
         
    			}
      }
```



希尔排序

```java

public static void sort(int a[]){
         
        int N = a.length;
        int h = N/2;//增量，这里选择总元素的一半作为增量
         
        while(h>=1){          
            //原数组被分为h个数组，每相隔h个元素组成一个数组，对每个数组进行插入排序         
            //对每个数组的元素轮流遍历，可以避免三层循环
            for(int i=h;i<N;i++)
                for(int j=i;j>=h&&a[j]<a[j-h];j-=h)
                    Example.exch(a, j, j-h);           
            h=h/2;
        }     
    }
```

