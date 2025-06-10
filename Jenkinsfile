
pipeline {
    
    agent any

 
    tools {
        maven 'Maven3' 
        jdk 'Java21'   
    }

   
    environment {
       
        SONARQUBE_ENV = 'MySonarQube'

       
        NEXUS_CREDENTIALS_ID = 'nexus-deploy-credentials'
        TOMCAT_CREDENTIALS_ID = 'tomcat-manager-credentials'

        
        SLACK_CREDENTIALS_ID = 'slack-token' 
    }

    
    stages {
       
        stage('Checkout') {
            steps {
               
                git url: 'https://github.com/manikiran7/simple.git', branch: 'main'
            }
        }
        stage('Verify POM') { 
    steps {
        sh 'cat pom.xml'
    }
}       
        stage('Build') {
            steps {
                
               
                sh 'mvn clean package -DskipTests'
            }
        }

        
        stage('SonarQube Analysis') {
            steps {
                
              
                withSonarQubeEnv("${SONARQUBE_ENV}") {
                   
                   
                    sh 'mvn sonar:sonar -Dsonar.projectKey=SimpleCustomerApp'
                }
            }
        }

      
        stage('Upload to Nexus') {
            steps {
                script {
                   
                    withCredentials([usernamePassword(credentialsId: NEXUS_CREDENTIALS_ID, passwordVariable: 'NEXUS_PASSWORD', usernameVariable: 'NEXUS_USERNAME')]) {
                      
                        
                        def nexusSettingsContent = """<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.1.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.1.0 http://maven.apache.org/xsd/settings-1.1.0.xsd">

  <servers>
    <server>
      <!-- The ID 'nexus' must match the distributionManagement/repository ID in your project's pom.xml -->
      <id>nexus</id>
      <username>${NEXUS_USERNAME}</username>
      <password>${NEXUS_PASSWORD}</password>
    </server>
  </servers>

  <mirrors>
    <mirror>
      <id>nexus-all-repos</id>
      <name>Nexus Public Repository All</name>
      <!-- This URL should point to your Nexus public group or release repository -->
      <url>http://34.233.134.210:8081/repository/simpleservlet/</url>
      <mirrorOf>*</mirrorOf> <!-- Mirrors all repositories, ensuring all dependencies come from Nexus -->
    </mirror>
  </mirrors>

  <profiles>
    <profile>
      <id>nexus</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <repositories>
        <repository>
          <id>central</id>
          <url>http://34.233.134.210:8081/repository/simpleservlet/</url>
          <releases><enabled>true</enabled></releases>
          <snapshots><enabled>true</enabled></snapshots>
        </repository>
      </repositories>
      <pluginRepositories>
        <pluginRepository>
          <id>central</id>
          <url>http://34.233.134.210:8081/repository/simpleservlet/</url>
          <releases><enabled>true</enabled></releases>
          <snapshots><enabled>true</enabled></snapshots>
        </pluginRepository>
      </pluginRepositories>
    </profile>
  </profiles>
</settings>
"""
               
                        writeFile(file: 'nexus-settings.xml', text: nexusSettingsContent)
                        
                       
                        
                        sh "mvn deploy -DskipTests -s nexus-settings.xml"
                    }
                }
            }
        }

        // Stage: Deploy the WAR file to Tomcat
        stage('Deploy to Tomcat') {
            steps {
                script {
                  
                    withCredentials([usernamePassword(credentialsId: TOMCAT_CREDENTIALS_ID, passwordVariable: 'TOMCAT_PASSWORD', usernameVariable: 'TOMCAT_USERNAME')]) {
                   
                        def tomcatSettingsContent = """<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.1.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.1.0 http://maven.apache.org/xsd/settings-1.1.0.xsd">

  <servers>
    <server>
      <!-- The ID 'tomcat-server' must match the <id> configured in your project's pom.xml
           for the tomcat7-maven-plugin or similar deployment plugin. -->
      <id>tomcat-server</id>
      <username>${TOMCAT_USERNAME}</username>
      <password>${TOMCAT_PASSWORD}</password>
    </server>
  </servers>
</settings>
"""
                     
                        writeFile(file: 'tomcat-settings.xml', text: tomcatSettingsContent)

                       
                 
                        sh "mvn tomcat7:redeploy -s tomcat-settings.xml"
                    }
                }
            }
        }
    }

  
    post {
       
        always {
            cleanWs() 
            echo 'Pipeline cleanup complete.'
           
            slackSend (
                channel: '#team',
                color: '#CCCC00', 
                message: "Project *${env.JOB_NAME}* - Build #${env.BUILD_NUMBER} has finished with status: *${currentBuild.currentResult}* (<${env.BUILD_URL}|Open in Jenkins>)"
            )
        }
      
        success {
            echo 'Pipeline finished successfully!'
         
            slackSend (
                channel: '#team', 
                color: 'good',    
                message: "SUCCESS: Project *${env.JOB_NAME}* - Build #${env.BUILD_NUMBER} deployed successfully! (<${env.BUILD_URL}|Open in Jenkins>)"
            )
        }
        
        failure {
            echo 'Pipeline failed!'
         
            slackSend (
                channel: '#team', 
                color: 'danger',  
                message: "FAILURE: Project *${env.JOB_NAME}* - Build #${env.BUILD_NUMBER} failed! (<${env.BUILD_URL}|Open in Jenkins>)"
            )
        }
        
        unstable {
           
            slackSend (
                channel: '#team',
                color: 'warning', 
                message: "UNSTABLE: Project *${env.JOB_NAME}* - Build #${env.BUILD_NUMBER} is unstable! (<${env.BUILD_URL}|Open in Jenkins>)"
            )
        }
    }
}
