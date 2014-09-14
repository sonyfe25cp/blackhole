##备忘

1. 拷贝静态资源

2. 开启nginx，指定文件夹
	全局参数设置：
	user  nobody;                               ＃＃使用普通帐号nobody启动服务＃＃
	worker_processes  8;                   ＃＃工作进程设置＃＃
	error_log  logs/error.log;
	pid        logs/nginx.pid;
	events { 
	    worker_connections  5000;        ＃＃每worker_processes进程的最大连接数＃＃
	    use epoll;                                  ＃＃使用epoll＃＃
	}
	http{ }项参数设置:
	server_names_hash_bucket_size 64;  ＃＃当设置多个虚拟主机sever时，需增大此参数，默认32＃
	server_tokens	off;                         ＃＃禁止显示nginx软件的版本号＃＃
	sendfile	     on;
	tcp_nodelay       on;
	keepalive_timeout  30;
	server {
        listen       80;
        server_name  monitor.com ;
        charset utf-8;
        root   /opt/webmonitor ;
        index  index.html index.htm;
       }
3. 测试静态文件生成


##逻辑

1. dns 日志按照小时切割

2. 定时解析对应小时的dns日志，然后生成相应的html

3. 添加当天的记录到列表页

##产品

1. 网站使用图 -- 饼图

2. 用户图 -- 饼图

3. 时间序列，上网次数图 -- 折线图

4. 时间序列，上网人数图 -- 折线图

##任务

0. 不存在的网址返回mm页面 

1. dns日志切割 √

2. dns日志解析 √

3. dns垃圾数据过滤

4. 各网页对应页面 √

5. 自动打包脚本

6. nginx配置mm页面

7. nginx配置劫持页面

8. 劫持页面本地化

9. 定期回传数据