#!/bin/bash
SERVER_NAME='riskmonitoring-admin'
cd `dirname $0`
echo $0
echo `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`

#配置文件的目录
CONF_DIR=$DEPLOY_DIR/config
#cronolog日志切割工具的目录
CONOLOG_DIR=$DEPLOY_DIR/cronolog
#服务日志的目录
LOG_BASE=/app/logs/riskmonitoring-admin/serverlogs

#将CONF_DIR和lib添加到classpath下
#export的变量在当前shell和其子shell中有效。./xx.sh sh xx.sh都是开启了子shell也就是新的shell程序来执行shell脚本中的命令，脚本结束后变量回收
export CLASSPATH=$CLASSPATH:$CONF_DIR:$(ls $DEPLOY_DIR/lib/*.jar | tr '\n' :)
export JAVA_HOME=/app/jdk1.8.0_162

# SERVER_PORT=`sed '/server.port/!d;s/.*=//' config/application.properties | tr -d '\r'`
# 获取应用的端口号
SERVER_PORT=`sed -nr '/port: [0-9]+/ s/.*port: +([0-9]+).*/\1/p' $CONF_DIR/application.yml`
#查询该进程的进程号，awk列切割提取命令，先读取一行数据到内存，用默认的分隔符切割，然后将列赋值给$1-$n,然后执行动作。
PIDS=`ps -f | grep java | grep "$CONF_DIR" |awk '{print $2}'`
#$1是传给shell的第一个参数
if [ "$1" = "status" ]; then  
  if [ -n "$PIDS" ]; then
    echo "The $SERVER_NAME is running...!"
    echo "PID: $PIDS"
    exit 0
  else
    echo "The $SERVER_NAME is stopped"
    exit 0
  fi
fi
if [ -n "$PIDS" ]; then
  echo "ERROR: The $SERVER_NAME already started!"
  echo "PID: $PIDS"
  exit 1
fi
if [ -n "$SERVER_PORT" ]; then
  SERVER_PORT_COUNT=`netstat -tln | grep $SERVER_PORT | wc -l`
  if [ $SERVER_PORT_COUNT -gt 0 ]; then
    echo "ERROR: The $SERVER_NAME port $SERVER_PORT already used!"
    exit 1
  fi
fi
if [ ! -d $LOG_BASE ]; then
 mkdir -p $LOG_BASE
fi
JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "
JAVA_DEBUG_OPTS=""
if [ "$1" = "debug" ]; then
  JAVA_DEBUG_OPTS=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n "
fi
JAVA_JMX_OPTS=""
if [ "$1" = "jmx" ]; then
  JAVA_JMX_OPTS=" -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false "
fi
JAVA_MEM_OPTS=""
BITS=`java -version 2>&1 | grep -i 64-bit`
if [ -n "$BITS" ]; then
  JAVA_MEM_OPTS=" -server -Xmx2048m -Xms2048m -Xmn4096m -XX:PermSize=256m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
  echo "1====$JAVA_MEM_OPTS"
else
  JAVA_MEM_OPTS=" -server -Xms2048m -Xmx2048m -XX:PermSize=256m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
  echo "2====$JAVA_MEM_OPTS"
fi
#CONFIG_FILES=" -Dlogging.path=$LOGS_DIR -Dlogging.config=$CONF_DIR/logback-spring.xml -Dspring.config.location=$CONF_DIR/application.yml "
#CONFIG_FILES=" -Dspring.config.location=$CONF_DIR/application.yml "
#温馨提示(使用加载外部yml配置文件的坑):
#-Dspring.config.location=$CONF_DIR/application.yml这种方式是在启动springboot的时候指定外部的yml配置文件,那么springboot就会使用这个yml配置文件就不会从classpath下找application.yml了,
#但是如果你的application.yml中使用了spring.profiles.active或者spring.profiles.include来激活其他的classpath下的yml文件就会失效，因为你使用的是外部文件加载的方式,那么springboot就不会
#启用这个配置，它会都使用外部的配置文件，这样如果你有多个yml文件的话就需要你将所有的文件都引入才可以。
#如果非要使用外部加载的方式:可以这样配置,将配置全部添加进来 -Dspring.config.location=$CONF_DIR/application.yml,$CONF_DIR/application-platform.yml,$CONF_DIR/application-upload.yml

#加载yml配置的方式有两种
#方式一:外部引入多个需要的yml配置文件，此时yml中的spring.profiles.active和spring.profiles.include不起作用
#CONFIG_FILES =" -Dspring.config.location=$CONF_DIR/application.yml,$CONF_DIR/application-platform.yml,$CONF_DIR/application-upload.yml "

#方式二:使用默认的加载方式,springboot会默认从classpath下加载application.yml配置文件,如果yml中有spring.profiles.active和spring.profiles.include
#会从classpath下加载对应的yml配置，那么这里就不添加外部的yml的引入了
CONFIG_FILES=''
#配置cronolog
CRONOLOG_BIN_FILE=/usr/local/sbin/cronolog
if [ -f "$CRONOLOG_BIN_FILE" ]; then
	echo "系统已安装cronolog，使用系统的cronolog"
else
	echo "系统未安装cronolog,使用内置的"
	CRONOLOG_BIN_FILE=$CONOLOG_DIR/cronolog
fi

#在启动Java的时候，最终当前目录定格在BIN目录,properties中使用的相对目录，相对的是当前目录，这个就是当前目录。
cd $BIN_DIR

echo -e "Starting the $SERVER_NAME ..."
#nohup $JAVA_HOME/bin/java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS $CONFIG_FILES -jar $DEPLOY_DIR/lib/$JAR_NAME > $STDOUT_FILE 2>&1 &
#nohup $JAVA_HOME/bin/java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS $CONFIG_FILES -cp $CLASSPATH org.springframework.boot.loader.JarLauncher > $STDOUT_FILE 2>&1 &
nohup $JAVA_HOME/bin/java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS $CONFIG_FILES -cp $CLASSPATH org.springframework.boot.loader.JarLauncher 2>&1 | $CRONOLOG_BIN_FILE "$LOG_BASE/catalina.out.%Y-%m-%d" & 
COUNT=0
while [ $COUNT -lt 1 ]; do
  echo -e ".\c"
  sleep 1
  if [ -n "$SERVER_PORT" ]; then
    COUNT=`netstat -an | grep $SERVER_PORT | wc -l`
  else
   COUNT=`ps -f | grep java | grep "$DEPLOY_DIR" | awk '{print $2}' | wc -l`
  fi
  if [ $COUNT -gt 0 ]; then
    break
  fi
done
echo "OK!"
PIDS=`ps -f | grep java | grep "$DEPLOY_DIR" | awk '{print $2}'`
echo "PID: $PIDS"
#echo "STDOUT: $STDOUT_FILE"
curday=`date +%Y-%m-%d`;
tail -f $LOG_BASE/catalina.out.$curday