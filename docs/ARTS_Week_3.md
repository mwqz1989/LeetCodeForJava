# ARTS Week 3

## Algorithm:

LeetCode number：141 Linked List Cycle [原题目戳这里](https://leetcode-cn.com/problems/linked-list-cycle/)

[Linked List Cycle - java](https://github.com/mwqz1989/LeetCodeForJava/blob/master/src/main/java/com/titan/titan/leetcode/simple/LinkedListCycle.java)

## Review:

[3 Tips to Improve Development Workflow](https://medium.com/@lucaspenzeymoog/3-tips-to-improve-development-workflow-384310948fa1)

主要讲了保证高效开发的三点建议：

1、自动化测试用例的重要性，保证每次修改或者重构代码时自己不会花费大量时间在测试上面。

2、工作中遇到的问题记录下来，推荐了evernote的chrome插件，在google搜索时可以同时搜索笔记及google中的东西，防止重复查找。

3、高可读性的代码，保证统一的格式，干净的内容，防止几个月后再看自己写的代码就像读别人写的糟糕代码一样

## Tip:

最近项目中引入了vue，在第一次使用vue编写组件时，使用ES6箭头函数，在mounted中初始化了dom节点的属性，但是总是说属性undefined，去官网完整的看了一遍文档才找到官方提醒：
**不要在选项属性或回调上使用箭头函数，因为箭头函数并没有 `this`，`this` 会作为变量一直向上级词法作用域查找，直至找到位置，经常导致
`Uncaught TypeError: Cannot read property of undefined` 
或 
`Uncaught TypeError: this.myMethod is not a function` 
之类的错误。**

```
new Vue({
  data: {
    a: 1
  },
  // 此处属性使用了ES6的箭头函数，this有可能会undefined
  mounted: () => {
    console.log('a is: ' + this.a)
  }
})
```

## Share:

**垃圾收集器与内存分配策略(1) 学习笔记**

深入理解java虚拟机  --周志明

* 对象的存活判定

引用计数器算法：每个对象被引用计数器就加一，引用失效计数器就减一。这种算法无法通知GC回收循环引用的对象。

可达性分析（JVM）：通过GC ROOT对象来寻找一个对象是否被引用。
可以作为GC ROOT对象有4中，分别为：

> 虚拟机栈中引用的对象

> 方法区中类静态属性引用的对象

> 方法区中常量引用的对象

> 本地方法栈中JNI引用的对象（native方法）

* 引用类型

强引用（StrongReference）：类似`Object a = new Object();`这种引用即为强引用。

软引用（SoftReference）：指一些还有用但是非必须的对象。软引用关联的对象在即将要内存溢出时会进行回收。

弱引用（WeakReference）：与软引用类似，也只一些有用但是非必须的对象，但是比软引用更弱一些，在下次垃圾回收时无论内存是否足够都会被回收。

虚引用（PhantomReference）：也称幽灵引用或者幻影引用，是最弱的引用，存不存在虚引用均会被回收。
