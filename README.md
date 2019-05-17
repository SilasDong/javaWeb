# 文档说明
## 1、 nginx配置

> 放在nginx的conf/conf.d文件夹下面，命名规则 域名_端口.conf(如：www.xinchidao.com_8812.conf)

```shell
server {
    listen 80;
    # listen 443 ssl;
    server_name demo.xinchidao.com;
    root /home/silas/www/demo;
    index index.html index.htm;
    # ssl_certificate /etc/letsencrypt/live/demo.xinchidao.com/fullchain.pem;
    # ssl_certificate_key /etc/letsencrypt/live/demo.xinchidao.com/privkey.pem;
    # ssl_trusted_certificate /etc/letsencrypt/live/demo.xinchidao.com/chain.pem;
    
    client_max_body_size 20m;

    # http转发到https
	# if ($scheme = http ) {
	# 	return 301 https://$host$request_uri;
	# }

    # 用来生成Let's Encrypt的http地址
    location ~ /.well-known
    {
        expires 1h;
        root /usr/local/nginx/html;

    }

    # vue history路由方式配置
    location / {
            index  index.html index.htm;
			try_files $uri $uri/ /index.html =404;
    }
	location /api{
		proxy_pass   http://localhost:8118;
		proxy_redirect    off;
		proxy_set_header Host $host;
		proxy_set_header X-Real-IP $remote_addr;
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
	}

    # 需要在uploadfiles下手动创建cache文件
    location ~* '/uploadfiles/(.+)/(.+)/(.+).(jpg|gif|png|jpeg)@([0-9]+)[w,h]' {
        root /;
        set $imageRoot /home/silas/www/demo/uploadfiles/$1/$2;
        set $extension $4;
        set $fileName $3;
        set $width $5;
        set $height $5;
        set $origin $imageRoot/${fileName}.$extension;
        set $file /home/silas/www/demo/uploadfiles/cache/${fileName}_${width}x${height}.jpg;
        if (!-f $file) {
                rewrite_by_lua '
                        local command = "convert -sample "..ngx.var.width.."x"..ngx.var.width.." "..ngx.var.origin.." "..ngx.var.file;
                os.execute(command);
                ';
        }
        rewrite ^ $file;
        break;
    }

    location /uploadfiles {
        root '/home/silas/www/demo/uploadfiles';
    }


	#禁止访问的文件或目录
    location ~ ^/(\.user.ini|\.htaccess|\.git|\.svn|\.project|LICENSE|README.md)
    {
        return 404;
    }
    
    location ~ .*\.(gif|jpg|jpeg|png|bmp|swf)$
    {
        expires      30d;
        access_log off; 
    }
    
    location ~ .*\.(js|css)?$
    {
        expires      12h;
        access_log off; 
    }
        error_page  403              /403.html;
        error_page  404              /404.html;

        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
}

```

## 2、springboot的启动脚本

> springboot.sh

```shell
#! /bin/bash
# springboot的jar放同级目录下即可，只能有一个jar文件
export PATH=$JAVA_HOME/bin:$PATH
CURRENT_PATH=$(cd "$(dirname "$0")"; pwd)
JAR=$(find $CURRENT_PATH -maxdepth 1 -name "*.jar")
JARNAME=${JAR##*/}
CURRETDATA=$(date +%Y-%m-%d)
PID=$(ps -ef | grep $JARNAME | grep -v grep | awk '{ print $2 }')

case "$1" in
    start)
        if [ ! -z "$PID" ]; then
            echo "$JAR 已经启动，进程号: $PID"
        else
            echo -n -e "启动 $JAR ... \n"
            cd $CURRENT_PATH
        nohup java -jar $JAR >>/dev/null 2>&1 &
            if [ "$?"="0" ]; then
                echo "启动完成，请查看日志确保成功"
                if [ ! -d "$CURRENT_PATH/logs/" ]; then
                   mkdir "$CURRENT_PATH/logs/"
                fi
                if [ ! -f "$CURRENT_PATH/logs/ctrlserver.log.$CURRETDATA.log" ]; then
                   touch "$CURRENT_PATH/logs/ctrlserver.log.$CURRETDATA.log"
                fi
                tail -f $CURRENT_PATH/logs/ctrlserver.log.$CURRETDATA.log
            else
                echo "启动失败"
            fi
        fi
        ;;
    stop)
        if [ -z "$PID" ]; then
            echo "$JAR 没有在运行，无需关闭"
        else
            echo "关闭 $JAR ..."
              kill -9 $PID
            if [ "$?"="0" ]; then
                echo "服务已关闭"
            else
                echo "服务关闭失败"
            fi
        fi
        ;;
    restart)
        sh ${0} stop
        sh ${0} start
        ;;
    # kill)
    #     echo "强制关闭 $JAR"
    #     killall $JARNAME
    #     if [ "$?"="0" ]; then
    #         echo "成功"
    #     else
    #         echo "失败"
    #     fi
    #     ;;
    status)
        if [ ! -z "$PID" ]; then
            echo "$JAR 正在运行"
        else
            echo "$JAR 未在运行"
        fi
        ;;
    log)
          tail -f $CURRENT_PATH/logs/ctrlserver.log.$CURRETDATA.log
          ;;
    *)
      echo "Usage: sh springboot.sh {start|stop|restart|status|log}" >&2
          exit 1
esac
```

## 3、springboot的application.yaml

> dbhost的地址是默认端口，不要跟上端口号，程序中定时备份的代码，会使用到其中的数据库配置

```yaml
logging:
    config: classpath:logback.xml
server:
    port: 8118
    tomcat:
        uri-encoding: UTF-8
    undertow:
        accesslog:
            dir: ./logs
            enabled: false
            pattern: common
            prefix: access_log
            rotate: true
            suffix: log
management:
    security:
        enabled: false
spring:
    datasource:
        driver-class-name: com.mysql.jdbc.Driver
        filters: stat,wall,log4j
        initialSize: 5
        logSlowSql: true
        maxActive: 20
        maxWait: 60000
        minEvictableIdleTimeMillis: 300000
        minIdle: 5
        testOnBorrow: false
        testOnReturn: false
        testWhileIdle: true
        timeBetweenEvictionRunsMillis: 60000
        type: com.alibaba.druid.pool.DruidDataSource
        dbhost: 127.0.0.1
        dbname: xcd_p_wxxk
        username: web
        password: xinchidao888
        url: jdbc:mysql://${spring.datasource.dbhost}/${spring.datasource.dbname}?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false
        validationQuery: SELECT 1 FROM DUAL
    http:
        encoding:
            charset: UTF-8
            enabled: true
            force: true
        multipart:
            max-file-size: 60MB
            max-request-size: 60MB
```