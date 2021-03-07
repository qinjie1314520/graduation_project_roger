pipeline {
    agent any
    stages {
        stage('从notification分支检出项目并打包') {
            when{
                branch 'notification'
            }
            steps {
                echo '从GitHub下载项目分支notification源码'
                checkout([$class: 'GitSCM', branches: [[name: '*/notification']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'db7a710a-dcdb-416a-a00f-4eb579a64a45', url: 'https://github.com/qinjie1314520/graduation_project_roger.git']]])
                echo '检出完毕'
                dir('notification') {
                      echo '转换目录完毕'
                      echo '开始编译构建'
                      sh 'mvn clean package docker:build'
                      echo '构建完毕'
                      echo '开始推送'
                      sh 'docker push registry.cn-chengdu.aliyuncs.com/qinjie/graduation_project_roger_notification:latest'
                      echo '推送完毕'
                      echo '开始删除镜像，清理项目'
                      sh 'mvn clean & docker rmi registry.cn-chengdu.aliyuncs.com/qinjie/graduation_project_roger_notification:latest'
                      echo '完成'
                }
            }
        }
        stage('从gateway分支检出项目并打包') {
            when{
                branch 'gateway'
            }
            steps {
                echo '从GitHub下载项目分支gateway源码'
                checkout([$class: 'GitSCM', branches: [[name: '*/gateway']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'db7a710a-dcdb-416a-a00f-4eb579a64a45', url: 'https://github.com/qinjie1314520/graduation_project_roger.git']]])
                echo '检出完毕'
                dir('gateway') {
                      echo '转换目录完毕'
                      echo '开始编译构建'
                      sh 'mvn clean package docker:build'
                      echo '构建完毕'
                      echo '开始推送'
                      sh 'docker push registry.cn-chengdu.aliyuncs.com/qinjie/graduation_project_roger_gateway:latest'
                      echo '推送完毕'
                      echo '开始删除镜像，清理项目'
                      sh 'mvn clean & docker rmi registry.cn-chengdu.aliyuncs.com/qinjie/graduation_project_roger_gateway:latest'
                      echo '完成'
                }
            }
        }
        stage('从article分支检出项目并打包') {
            when{
                branch 'article'
            }
            steps {
                echo '从GitHub下载项目分支article源码'
                checkout([$class: 'GitSCM', branches: [[name: '*/article']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'db7a710a-dcdb-416a-a00f-4eb579a64a45', url: 'https://github.com/qinjie1314520/graduation_project_roger.git']]])
                echo '检出完毕'
                dir('article') {
                      echo '转换目录完毕'
                      echo '开始编译构建'
                      sh 'mvn clean package docker:build'
                      echo '构建完毕'
                      echo '开始推送'
                      sh 'docker push registry.cn-chengdu.aliyuncs.com/qinjie/graduation_project_roger_article:latest'
                      echo '推送完毕'
                      echo '开始删除镜像，清理项目'
                      sh 'mvn clean & docker rmi registry.cn-chengdu.aliyuncs.com/qinjie/graduation_project_roger_article:latest'
                      echo '完成'
                }
            }
        }


        stage('从authority分支检出项目并打包') {
                    when{
                        branch 'authority'
                    }
                    steps {
                        echo '从GitHub下载项目分支authority源码'
                        checkout([$class: 'GitSCM', branches: [[name: '*/authority']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'db7a710a-dcdb-416a-a00f-4eb579a64a45', url: 'https://github.com/qinjie1314520/graduation_project_roger.git']]])
                        echo '检出完毕'
                        dir('authority') {
                              echo '转换目录完毕'
                              echo '开始编译构建'
                              sh 'mvn clean package docker:build'
                              echo '构建完毕'
                              echo '开始推送'
                              sh 'docker push registry.cn-chengdu.aliyuncs.com/qinjie/graduation_project_roger_authority:latest'
                              echo '推送完毕'
                              echo '开始删除镜像，清理项目'
                              sh 'mvn clean & docker rmi registry.cn-chengdu.aliyuncs.com/qinjie/graduation_project_roger_authority:latest'
                              echo '完成'
                        }
                    }
                }
    }
}