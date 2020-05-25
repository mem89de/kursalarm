pipeline {
   agent any
   
   stages {
      stage('Build') {
         steps {
            sh "mvn clean compile"
         }
      }
      stage('Test') {
         steps {
            sh "mvn test"
         }

         post {
            // If Maven was able to run the tests, even if some of the test
            // failed, record the test results and archive the jar file.
            success {
               junit '**/target/surefire-reports/TEST-*.xml'
               archiveArtifacts 'target/*.jar'
            }
         }
      }
   }
}
