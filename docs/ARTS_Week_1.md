# ARTS Week One

##Algorith:

第一周分享先尝试了LeetCode简单题目，发现难度尚可，第二周开始可以逐步去尝试中等题目。

LeetCode Number：771  宝石与石头 [原题目戳这里](https://leetcode-cn.com/problems/first-missing-positive/)

[Jewels And Stones- java](https://github.com/mwqz1989/LeetCodeForJava/blob/master/src/main/java/com/titan/titan/leetcode/simple/JewelsAndStones.java)

##Review：

第一次读英文文档，花了大概两天时间才把jdk里面的说明和wiki词条读明白。对着代码和解释一起看，基本能了解个大概。看完后觉得设计算法真是一项伟大的工程，不看说明根本不知道写的是啥。

[Tim Sort](https://en.wikipedia.org/wiki/Timsort)

##Tips：

阅读了java中Collections.sort实现方式。一直以为java中数组的排序使用归并排序来实现的，从源码来看，并没有想得那么简单，毕竟工业级的容器需要仔细推敲。
Collections.sort方法在jdk1.7之前用的还是归并排序，1.7开始用了一种混合排序算法：TimSort。jdk文档中声明了该算法是借鉴了Python中list排序算法的实现。
```
* <p>The implementation was adapted from Tim Peters's list sort for Python
* (<a href="http://svn.python.org/projects/python/trunk/Objects/listsort.txt">
* TimSort</a>).  It uses techniques from Peter McIlroy's "Optimistic
* Sorting and Information Theoretic Complexity", in Proceedings of the
* Fourth Annual ACM-SIAM Symposium on Discrete Algorithms, pp 467-474,
* January 1993.
```

再回头看源码，Collections.sort调用的还是Arrays.sort方法，如下：

```
public static <T> void sort(T[] a, Comparator<? super T> c) {
    if (c == null) {
        sort(a);
    } else {
        if (LegacyMergeSort.userRequested)
            legacyMergeSort(a, c);// 原来的归并排序算法
        else
            TimSort.sort(a, 0, a.length, c, null, 0, 0);// 新引入的TimSort
    } 
```

当比较器为空时，调用sort方法，如下：

```
public static void sort(Object[] a) {
    if (LegacyMergeSort.userRequested)
        legacyMergeSort(a);
    else
        ComparableTimSort.sort(a, 0, a.length, null, 0, 0);
}
```

两个归根结底调用的都是同一个TimSort，只是一个传入了比较器，一个用默认自然顺序比较。同时里面还保留着原来的归并排序（legacyMergeSort），并用`LegacyMergeSort.userRequested`这个开关来控制使用哪种排序，默认是关闭。
同时也理解了耗子哥为什么说读懂英文文档是多么的重要。Timsort算法在合并时为了减少有序序列的重复排序问题，引入了Run和Galloping Mode的概念，这个解释只有全英文的wiki百科和各种英文paper，只能硬着头皮去读了。

##Share：

既然看到了`Collections.sort`源码了解了TimSort，就要追根问底，刚开始读源码时觉得非常难懂，看完之后感慨发明者算法的哥们真机智。
TimSort是一个稳定的混合型排序，同时用到了归并排序和插入排序，平均时间复杂度达到O(n*lgn)，最优时间复杂度能达到O(n)。在待排序序列有序度很高时效率非常高，不必对有序序列做重复排序。
sort方法的实现首先判断待排序序列是否小于阈值MIN_MERGE（32），小于阈值的话就用了插入排序。同时还做了一个很巧妙地处理，利用TimSort里查找有序序列的方法，查找从头开始有序序列的长度，减少插入排序的次数。

```
if (nRemaining < MIN_MERGE) {
    //从开头寻找有序序列的长度，如果是倒序就反转
    int initRunLen = countRunAndMakeAscending(a, lo, hi, c);
    binarySort(a, lo, hi, lo + initRunLen, c);
    return;
}
```

接下来就是初始化TimSort，初始化了一个数组work，在合并run时会用到。workBase和workLen是临时数组存放的起始index和长度，初始值默认为0。

`TimSort<T> ts = new TimSort<>(a, c, work, workBase, workLen); `

查找最小的run执行长度，小于此值子序列就用折半插入排序（binarySort），否则就用run合并。

`int minRun = minRunLength(nRemaining); `

下面是查找每一个run和合并的过程。

Line3查找run的长度；

Line6判断run长度是否小于之前计算的最小长度，如果小于的话对剩余的部分做插入排序；

Line13将分割的run下标放入TimSort初始化的栈中；

Line14对相邻的栈进行合并，需要满足`runLen[i - 3] > runLen[i - 2] + runLen[i - 1]和runLen[i - 2] > runLen[i - 1];`

Line17-20将剩余集合长度减去run长度，直到剩余长度为0退出。

```
do {
    // Identify next run
    int runLen = countRunAndMakeAscending(a, lo, hi, c);

    // If run is short, extend to min(minRun, nRemaining)
    if (runLen < minRun) {
        int force = nRemaining <= minRun ? nRemaining : minRun;
        binarySort(a, lo, lo + force, lo + runLen, c);
        runLen = force;
    }

    // Push run onto pending-run stack, and maybe merge
    ts.pushRun(lo, runLen);
    ts.mergeCollapse();

    // Advance to find next run
    lo += runLen;
    nRemaining -= runLen;
} while (nRemaining != 0);
```

当run分割合并完成后就是最后的合并了

`ts.mergeForceCollapse(); `

两个mergeCollapse()和mergeForceCollapse()方法都是调用了mergeAt();贴出的代码删除了注释和断言方法。这个里面最关键的就是gallopRight和gallopLeft以及mergeLo和mergeHi；

```
private void mergeAt(int i) {
    int base1 = runBase[i];
    int len1 = runLen[i];
    int base2 = runBase[i + 1];
    int len2 = runLen[i + 1];

    runLen[i] = len1 + len2;
    if (i == stackSize - 3) {
        runBase[i + 1] = runBase[i + 2];
        runLen[i + 1] = runLen[i + 2];
    }
    stackSize--;

    int k = gallopRight(a[base2], a, base1, len1, 0, c);
    base1 += k;
    len1 -= k;
    if (len1 == 0)
        return;
        
    len2 = gallopLeft(a[base1 + len1 - 1], a, base2, len2, len2 - 1, c);
    if (len2 == 0)
        return;
        
    if (len1 <= len2)
        mergeLo(base1, len1, base2, len2);
    else
        mergeHi(base1, len1, base2, len2);
}
```
gallopRight和gallopLeft是分别寻找需要合并两个run中的第一个元素在另一个中的位置，mergeLo和mergeHi就是将小的run合并入大的run中。
整个TimSort主要逻辑就结束了，期中细节的地方很多，需要细细品味，而且实现寻找和合并的思路都很有意思，值得借鉴。当然，第一次看这种复杂的算法，还需要多看几遍来帮助理解。看到最后大概意思是明白了，但是细节地方还有比较模糊的，需要再多读几遍。