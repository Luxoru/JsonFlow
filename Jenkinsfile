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
                    sh 'mvn clean install -U'
                }
            }
        }
        stage('Test') {
            steps {
                echo "Testing..."
            }
        }
    }
    post {
        always {
            script {
                def buildStatus = currentBuild.currentResult.toLowerCase()
                def changeSet = ""
                if (currentBuild.changeSets.size() == 0) {
                    changeSet = "No changes."
                } else {
                    // Format your change info here if needed
                    changeSet = "Changes detected."
                }
                
                def artifactsList = ""
                def artifacts = currentBuild.rawBuild.artifacts
                if (artifacts.size() > 0) {
                    artifactsList = "**Artifacts:**\n"
                    artifacts.each { artifact ->
                        artifactsList += "• ${env.BUILD_URL}artifact/${artifact.relativePath}\n"
                    }
                }
                
                def description = """**Build:** ${env.BUILD_NUMBER}
**Status:** ${buildStatus}
${changeSet}

${artifactsList}"""
                
                discordSend(
                    description: description,
                    footer: "Jenkins v${Jenkins.instance.getVersion()}, Discord Notifier v${DiscordNotifier.version}",
                    link: env.BUILD_URL,
                    result: currentBuild.currentResult,
                    title: "${env.JOB_NAME} #${env.BUILD_NUMBER}",
                    webhookURL: DISCORD_WEBHOOK_URL
                )
            }
            
            script {
                // Get the commit SHA (make sure the commit has been checked out)
                def commitSha = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
                
                // Update the status on GitHub using the GitHub plugin
                def status = currentBuild.currentResult == 'SUCCESS' ? 'success' : 'failure'
                github(
                    credentialsId: 'github-private-key', // Use the correct GitHub credentials ID
                    account: 'Luxoru',  // Replace with your GitHub username
                    repo: 'JsonFlow',  // Replace with your repository name
                    sha: commitSha,                // Use the commit SHA
                    status: status,                // 'success' or 'failure'
                    context: 'Jenkins Build',      // A description or context for the status
                    description: "Build completed", // Additional description
                    targetUrl: env.BUILD_URL       // Link back to Jenkins build URL
                )
            }
        }
    
        success {
            echo 'Build successful!'
        }
        failure {
            echo 'Build failed.'
        }
    }
}
