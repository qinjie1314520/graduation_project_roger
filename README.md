# graduation_project_roger
毕设项目
jwt密匙在网关处和鉴权中心均有保存

可通过jenkins中的maven项目直接构建镜像不用dockerfile文件，但是如果mvn构建就需要dockerfile文件

前端项目：https://gitee.com/yadong.zhang

mysql 180.102.132.35:10000 (root:root)

openfire 180.102.132.35:9090 (admin:admin)

nacos http://180.102.132.35:10001/nacos (nacos:nacos)

redis 180.102.132.35:10002 pass(redis)

graduation-project-roger-gateway 30004

graduation-project-roger-authority 30005  redis(1)

graduation-project-roger-notification 30006 redis(0)

graduation-project-roger-user-center 30007

graduation-project-roger-article 30008

graduation-project-roger-admin 30009 