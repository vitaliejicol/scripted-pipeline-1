properties([
    parameters([
        string(defaultValue: '', description: 'Please enter VM IP', name: 'NodeIP', trim: true)
        ])
    ])

if (NodeIP?.trim()) {
    node {
        withCredentials([sshUserPrivateKey(credentialsId: 'master-jenkins-private', keyFileVariable: 'SSHKEY', passphraseVariable: '', usernameVariable: 'SSHUSERNAME')]) {
            stage('Init') {
                sh 'ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${NodeIP} yum install epel-release -y'
            }
            stage("Install git") {
                sh 'ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${NodeIP} yum install git -y'
            }
            stage("Install Java"){
                sh 'ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${NodeIP} yum install java-1.8.0-openjdk-devel -y'
            }
            stage("Install Ansible"){
                sh 'ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${NodeIP} yum install ansible -y'
            }
        }
    }
}
else {
    error 'Please enter valid IP address'
}


