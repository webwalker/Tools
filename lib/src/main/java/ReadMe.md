两种方式格式化埋点数据：

1、Excel插入一新列，格式化为大写，然后赋值到RegexBuddy中，用以下表达式替换即可。

(\w+)\s+(.+)
public static final String $1 = $1;//$2

2、采用本项目直接运行

(1)在项目的执行目录（相对路径是lib\build\libs）放置umeng.txt，内容为Excel直接复制的内容

(2)运行项目,success后从执行目录打开结果文件

Android中运行Java项目可参考：http://blog.csdn.net/asmcvc/article/details/47006275

3、导出为Jar包
   java -jar lib.jar