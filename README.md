# ClientTest
调用servicetest02的myservice，实现跨app调用service
Android5.0中service的intent必须要显性声明，否则会报错 IllegalArgumentException: Service Intent must be explicit
引用https://blog.csdn.net/shenzhonglaoxu/article/details/42675287中定义方法将隐性调用转换成显性调用
