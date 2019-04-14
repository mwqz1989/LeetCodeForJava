# ARTS Week 2

## Algorithm:

LeetCode number：41 First Missing Positive [原题目戳这里](https://leetcode-cn.com/problems/first-missing-positive/)

[First Missing Positive - java](https://github.com/mwqz1989/LeetCodeForJava/blob/master/src/main/java/com/titan/titan/leetcode/simple/FirstMissingPositive.java)

## Review:
最近项目中在向微服务发展，引入了spring cloud，阅读了netflix官方说明文档。

[Spring Cloud netflix](https://spring.io/projects/spring-cloud-netflix#overview)

了解了netflix原来是一套完整的微服务解决方案，包含了服务发现（Eureka）、服务熔断（Hystrix）、智能路由（Zuul）以及负载均衡（Ribbon）。

## Tip:

学习了MarkDown语法。一直好奇别人的GitHub项目里面炫酷的首页Readme是怎么做的，学习了一下发现确实简单实用。
以后的打卡就可以用md写在GitHub上了。

常用的md语法其实很简单，和HTML非常类似，相当于标注了文本的结构，只需要知道以下几种就可以开开心心的写文档了：

* 标题 #:通过#的个数来控制标题级数，越多表示级数越低，可以理解为HTML中的`<h></h>`标签,#等同于`<h1></h1>`

* 链接 \[] () 可以理解为HTML中的`<a href = 'xxx'></a>`方括号内就相当于a标签的内容，圆括号内就相当于href中的链接

* 列表 列表可以用*、+、-后面跟空格加上文字，相当于HTML中的ul、li

* 代码框 用\``来包裹代码，没有想到很好的类比。

其他的基本上可以用到了再去查一下，很方便。

## Share:

**JVM类加载器ClassLoader学习笔记**

深入理解java虚拟机  --周志明

* 类的加载过程

JVM在加载类时，主要分为三步：加载（Loading） -> 链接（Link） -> 初始化（Initialize）

* 加载

加载是指JVM通过类的完全限定名来查找该类的字节码文件（.class），并用字节码创建Class对象。
这一过程JVM需要完成三件事：

1、通过完全限定名来获取二进制字节流

2、将这个字节流所代表的静态存储结构转化为方法区的运行时数据结构

3、在内存中生成一个代表这个类的java.lang.Class对象，作为方法区这个类的各种数据结构的访问入口

CLass文件的来源可以是zip包中，比如常见的jar、war包。
或者从网络中获取，比如Applet。
或者在运行时生成，比如java中的反射机制。
或者由其他文件生成，比如jsp。

* 链接

链接又分为验证、准备和解析三个阶段。

1、验证

验证阶段总的来说就是验证Class文件的正确性，其中包含：

文件格式验证：是否以魔术0xCAFFBABE开头、版本号校验等等；

元数据验证：对Class文件的语义进行校验，比如final类型的类被继承这种不符合语义的类；

字节码验证：确定程序语义及逻辑的合法性；

符号引用验证：将符号引用转化为直接引用。

2、准备

为静态域分配空间（不包含实例），初始化静态变量，比如`static int i=5;`这里只将i初始化为0，至于5的值将在初始化时赋值。

3、解析

解析这个类创建的对其他类的引用，不一定会有。

* 初始化

类加载的最后一步，到了初始化阶段才真正执行java类中的代码，即字节码。如果有父类，对其初始化，执行静态初始化器，初始化静态变量。

**.class方式只会触发类的第一步加载（即只生成对应的字节码），Class.forName会触发类的整个加载过程**

* 双亲委派模型

JVM中类加载器的层级关系如下：

BootStrap ClassLoader（启动类加载器）：由C++实现，是虚拟机的一部分。
负责加载 <JAVA_HOME>/lib 下核心类库，比如java.xxx、javax.xxx、sun.xxx

Extension ClassLoader（扩展类加载器）：负责加载<JAVA_HOME>/lib/ext目录下的类。

Application ClassLoader（应用类加载器）：负责加载classpath下用户指定的类。

双亲委派模型要求除了BootStrap ClassLoader以外都要有自己的父类加载器，同时在加载类时首先委托自己的父类加载器加载，直到父类加载器加载不到，才由自己来加载类。
保证了类不会重复被加载，同时还能保护核心类库不会被恶意覆盖。





