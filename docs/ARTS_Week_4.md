# ARTS Week 4

## Algorithm:

LeetCode number：20 Valid Parentheses [原题目戳这里](https://leetcode-cn.com/problems/valid-parentheses/)

[ValidParentheses - java](https://github.com/mwqz1989/LeetCodeForJava/blob/master/src/main/java/com/titan/titan/leetcode/simple/ValidParentheses.java)


## Review:

[Dependency Inject](https://medium.freecodecamp.org/a-quick-intro-to-dependency-injection-what-it-is-and-when-to-use-it-7578c84fa88f)

对依赖注入做了简单的介绍，以及常用的注入方式和优点，最后推荐了几个依赖注入的框架，例如spring等.

## Tip:

**慎用不熟悉的新技术**

这周在开发中前端框架选择了vue，部署用了webpack，但是这两样开发都不熟悉，同时为了做视频播放，引入了videojs（6.x），于是在做IE播放HLS流媒体时这个坑就出现了。

通过videojs播放HLS时在IE中并不兼容，需要flash的支持，但是恰巧选了videojs 6.x版本，这个版本刚好移除了内置flash的支持，于是乎有需要引入videojs-flash。

由于是公司内网，无法从videojs-flash默认的CDN获取到flash插件，需要通过webpack的install后重新指定flash的路径，在不熟悉webpack打包机制的情况下路径永远是404。

这个问题导致前台开发两天都在寻找解决方案，没有有效产出，最后不得已通过script src的方式才将flash引了进来，虽然实现方式很难看，但是总归功能实现了。

所以说在前期技术选型时，对不熟悉的框架一定要做好技术预研，否则不知道会引入多少坑。

## Share:

### 垃圾收集器与内存分配策略(2) 学习笔记

深入理解java虚拟机  --周志明

#### 常见的垃圾回收算法：

* 标记-清除算法（Mark-Sweep）：

基础算法，标记出所有需要回收的对象，标记完成之后统一回收。缺点是标记和清除效率都不高，并且会产生内存碎片。

* 复制算法（Copying）：

把内存分为相等大小的两块，每次只用其中一块。当一块用完之后把不需要回收的对象复制到另一块内存中，再把原来的那块内存清理掉。
优点是实现简单，运行高效，但是内存只有一半，利用率折半，当存活对象过多时需要进行大量的搬移操作。
虚拟机基本都是在新生代使用复制算法，将内存分为一块Eden和两块Survivor，比例为8:1:1，当Survivor不够用时还会向老年代借内存（分配担保）。

* 标记-整理算法（Mark-Compact）：

与标记清除算法类似，首先标记所有需要回收的对象，之后让存活的对象都移向一端，清理掉边界值以外的内存。一般老年代使用这种算法。

* 分代收集算法（Generational Collection）

根据不同的内存特点来选择不同的垃圾回收算法。

#### 内存分配与回收策略

* 对象优先在Eden分配

当新生代区域内存不足时首先会进行一次Minor GC,如果空间还不够的话只能通过分配担保机制提前转移至老年代。

* 大对象直接进入老年代

最直接的就是大数组及大字符串，避免出现创建生命周期短的大对象，会提前触发老年代的Major GC甚至是Full GC。

* 长期存活对象将进入老年代

虚拟机给每一个对象定义了Age，每经过一次垃圾回收Age++，当Age达到一定数值时（-XX:MaxTenuringThreshold，默认15），就会被移动到老年代。

* 动态对象年龄判断

虚拟机并不是等到对象年龄都达到阈值时才会移动至老年代，**如果在Survivor空间中相同年龄所有对象大小的总和大于Survivor空间的一半，
年龄大于等于该年龄的对象就会进入老年代**，无需等到阈值。

* 空间分配担保

在进行Minor GC前会检查老年代最大可用的连续空间是否大于新生代所有对象总空间，如果满足，GC是安全的；
如果不满足，就会查看是否允许担保，如果允许担保，会继续检查老年代最大可用的连续空间是否大于每次晋升到老年代对象的平均大小，
如果大于，会尝试进行Minor GC，此时GC是有风险的，因为担保失败会导致Full GC的发生；如果不允许会直接触发Full GC。

**这种担保机制是通过概率的手段来减少Full GC的次数。**

#### 垃圾收集器

* Serial 收集器

单线程收集器，新生代采用复制算法，暂停所有用户线程，老年代采用标记-整理算法，同样会暂停所有用户线程。

* ParNew 收集器

Serial 多线程版本，新生代为多线程。能与CMS配合工作

* Parallel Scavenge 收集器

新生代多线程收集器，关注点为吞吐量（运行用户代码时间/（运行用户代码时间 + 垃圾收集时间）），被称作“吞吐量优先”收集器。

* Serial Old 收集器

Serial 收集器的老年代版本。

* Parallel Old 收集器

Parallel Scavenge收集器老年代版本

* CMS 收集器

以最短停顿时间为目标，只有在初始标记时会STW。但是会存在内存碎片化等问题

* G1（Garbage-First） 收集器

java9默认收集器，停顿时间可控，优先回收最大的region，目前最优秀的垃圾回收器。