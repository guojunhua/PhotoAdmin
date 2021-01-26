#镜像信息
FROM tomcat:8.5

#维护者
MAINTAINER 10000@qq.com

#将webapp下的全部删除
RUN rm -rf /usr/local/tomcat/webapps/*

#ROOT.war拷贝到/usr/local/tomcat/webapps/下
ADD ROOT.war /usr/local/tomcat/webapps/

#端口
EXPOSE 8080

#设置启动命令
ENTRYPOINT ["/usr/local/tomcat/bin/catalina.sh","run"]