pipeline {
   agent any
   
   stages {
      stage('Build') {
         steps {
            sh "mvn clean compile package"
         }
      }
      stage('Test') {
         steps {
            sh "mvn test"
         }

         post {
            success {
               junit '**/target/surefire-reports/TEST-*.xml'
               archiveArtifacts 'target/*.jar'
            }
         }
      }
   }
}
