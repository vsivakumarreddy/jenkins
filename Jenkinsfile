pipeline {
    agent any

    tools {
        // Ensure this Maven version is configured in Jenkins > Global Tool Configuration
        maven 'MVN_HOME'
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
                git url: 'https://github.com/vsivakumarreddy/jenkins.git', branch: 'main'
            }
        }

        stage('Clean Maven Local Repo') {
            steps {
                sh "rm -rf ${HOME}/.m2/repository/com/visualpathit/SimpleCustomerApp"
                sh "rm -rf ${HOME}/.m2/repository/com/visualpathit/Simpleservlet"
                echo "Cleaned old artifacts from local Maven repository."
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
                    sh 'mvn sonar:sonar -Dsonar.projectKey=Simpleservlet'
                }
            }
        }

        stage('Upload to Nexus') {
            steps {
                script {
                    withCredentials([usernamePassword(
                        credentialsId: NEXUS_CREDENTIALS_ID,
                        usernameVariable: 'NEXUS_USERNAME',
                        passwordVariable: 'NEXUS_PASSWORD'
                    )]) {
                        writeFile file: 'nexus-settings.xml', text: """
<settings xmlns="http://maven.apache.org/SETTINGS/1.1.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.1.0 http://maven.apache.org/xsd/settings-1.1.0.xsd">
  <servers>
    <server>
      <id>nexus</id>
      <username>${NEXUS_USERNAME}</username>
      <password>${NEXUS_PASSWORD}</password>
    </server>
  </servers>
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

                        echo "--- Effective Maven Settings ---"
                        sh 'cat nexus-settings.xml'
                        sh "mvn help:effective-settings -s nexus-settings.xml"

                        sh "mvn deploy -DskipTests -s nexus-settings.xml"
                    }
                }
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                script {
                    withCredentials([usernamePassword(
                        credentialsId: TOMCAT_CREDENTIALS_ID,
                        usernameVariable: 'TOMCAT_USERNAME',
                        passwordVariable: 'TOMCAT_PASSWORD'
                    )]) {
                        writeFile file: 'tomcat-settings.xml', text: """
<settings xmlns="http://maven.apache.org/SETTINGS/1.1.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.1.0 http://maven.apache.org/xsd/settings-1.1.0.xsd">
  <servers>
    <server>
      <id>tomcat-server</id>
      <username>${TOMCAT_USERNAME}</username>
      <password>${TOMCAT_PASSWORD}</password>
    </server>
  </servers>
</settings>
"""
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
                message: "Project *${env.JOB_NAME}* - Build #${env.BUILD_NUMBER} finished with status: *${currentBuild.currentResult}* (<${env.BUILD_URL}|View>)"
            )
        }
        success {
            echo 'Pipeline finished successfully!'
            slackSend (
                channel: '#devops',
                color: 'good',
                message: "✅ SUCCESS: *${env.JOB_NAME}* - Build #${env.BUILD_NUMBER} deployed! (<${env.BUILD_URL}|Open>)"
            )
        }
        failure {
            echo 'Pipeline failed!'
            slackSend (
                channel: '#devops',
                color: 'danger',
                message: "❌ FAILURE: *${env.JOB_NAME}* - Build #${env.BUILD_NUMBER} failed. (<${env.BUILD_URL}|Check logs>)"
            )
        }
        unstable {
            slackSend (
                channel: '#team',
                color: 'warning',
                message: "⚠️ UNSTABLE: *${env.JOB_NAME}* - Build #${env.BUILD_NUMBER} had issues. (<${env.BUILD_URL}|See details>)"
            )
        }
    }
}
