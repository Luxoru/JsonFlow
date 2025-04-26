pipeline {
    agent any
    tools {
        jdk 'java23'
    }
    environment {
        // Replace with your actual webhook URL
        DISCORD_WEBHOOK_URL = credentials('discord-webhook')
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
                
                   def changes = []
                    currentBuild.changeSets.each { cs ->
                        cs.items.each { item ->
                            def commitId = item.commitId.substring(0, 6)  // Get first 6 chars of commit hash
                            def commitMsg = item.msg.split('\n')[0].trim() // Get first line of commit message
                            def author = item.author.toString()
                            changes.add("• ${commitId} ${commitMsg} - ${author}")
                        }
                    }
                    
                    // Join commit messages with a line break
                    if (changes.size() > 0) {
                        changeSet = "**Changes**:\n" + changes.join('\n')
                    } else {
                        changeSet = "No changes."
                    }
                }
                
                // Instead of using rawBuild.artifacts, we'll use archiveArtifacts first
                // then list artifacts with known paths
                def artifactsList = ""
                def artifactsPath = "${env.JOB_NAME}/${env.BUILD_NUMBER}"
                
                def description = """**Build:** ${env.BUILD_NUMBER}
**Status:** ${buildStatus}
${changeSet}

${artifactsList}"""
                
                discordSend(
                    description: description,
                    footer: "Jenkins",  // Simplified to avoid version issues
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
                    credentialsId: GITHUB_TOKEN, // Use the correct GitHub credentials ID
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
