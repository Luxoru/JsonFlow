pipeline {
    agent any
    tools {
        jdk 'java23'
    }
    environment {
        // Replace with your actual webhook URL
        DISCORD_WEBHOOK_URL = "https://discordapp.com/api/webhooks/1356210763815059486/mi2m-E4GbZ-HTa2DLGSrfHPAMzsguo-g-gCfcToPll5sHbWinBenabJvjnwGho7F2Yqt"
        // If necessary, set GitHub token or credentials
        GITHUB_TOKEN = credentials('github-PAT')  // Set this in Jenkins Credentials
    }
    stages {
        stage('Build') {
            steps {
                echo "Building..."
                script {
                    // Run Maven build
                    sh 'mvn clean install'
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
                    // Update the status on GitHub
                    def status = currentBuild.currentResult == 'SUCCESS' ? 'success' : 'failure'
                    githubNotify(
                        status: status,
                        context: 'Jenkins Build',
                        description: "Build completed",
                        targetUrl: env.BUILD_URL
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
