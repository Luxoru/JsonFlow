pipeline {
    agent any
    tools {
        jdk 'java23'
    }
    environment {
        // Replace with your actual webhook URL
        DISCORD_WEBHOOK_URL = "https://discordapp.com/api/webhooks/1356210763815059486/mi2m-E4GbZ-HTa2DLGSrfHPAMzsguo-g-gCfcToPll5sHbWinBenabJvjnwGho7F2Yqt"
        // If necessary, set GitHub token or credentials
        GITHUB_TOKEN = credentials('github-private-key')  // Set this in Jenkins Credentials
    }
    stages {
        stage('Build') {
            steps {
                echo "Building..."
                script {
                    // Run Maven build
                    sh mvn clean install -U
                }
            }
        }
        stage('Test') {
            steps {
                echo "Testing..."
            }
        }
        stage('Notify Discord') {
            steps {
                script {
                    // Send message to Discord
                    discordSend(
                        description: "Jenkins Pipeline Build",
                        footer: "Footer Text",
                        link: env.BUILD_URL,
                        result: currentBuild.currentResult,
                        title: env.JOB_NAME,
                        webhookURL: DISCORD_WEBHOOK_URL
                    )
                }
            }
        }
        stage('Update GitHub Status') {
            steps {
                script {
                    // Get the commit SHA (make sure the commit has been checked out)
                    def commitSha = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
                    
                    // Update the status on GitHub using the GitHub plugin
                    def status = currentBuild.currentResult == 'SUCCESS' ? 'success' : 'failure'
                    github(
                        credentialsId: GITHUB_TOKEN, // Use the correct GitHub credentials
                        repoOwner: 'Luxoru',  // Replace with your GitHub username
                        repository: 'JsonFlow',  // Replace with your repository name
                        commitSha: commitSha,                // Use the commit SHA
                        status: status,                      // 'success' or 'failure'
                        context: 'Jenkins Build',            // A description or context for the status
                        description: "Build completed",      // Additional description
                        targetUrl: env.BUILD_URL            // Link back to Jenkins build URL
                    )
                }
            }
        }
    }
    post {
        success {
            echo 'Build successful!'
        }
        failure {
            echo 'Build failed.'
        }
    }
}
