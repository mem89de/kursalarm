pipeline {
   agent any
   
   stages {
      stage('Build') {
         steps {
            // Get some code from a GitHub repository
            git branch: 'develop', url: 'https://github.com/mem89de/kursalarm.git'

            // Run Maven on a Unix agent.
            sh "mvn -Dmaven.test.failure.ignore=true clean package"
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
