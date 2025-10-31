pipeline {
    agent any

    stages {
        stage("Dependances") {
            steps {
                sh 'chmod +x setup_apache.sh'
                sh './setup_apache.sh'
            }
        }
        stage('Checkout') {
            steps {
                echo 'Récupération du code...'
                // Example: checkout scm
            }
        }
        stage('Deploy') {
            steps {
                echo 'Copie des fichiers vers le serveur web...'
                // sh 'sudo cp index.html /var/www/html/index.html'
            }
        }
        stage('Test') {
            steps {
                echo 'Vérification du déploiement...'
                // sh 'curl -f http://localhost/index.html'
            }
        }
    }
    post {
        success {
            echo 'Déploiement réussi !'
        }
        failure {
            echo 'Le pipeline a échoué.'
        }
        always {
            echo 'Nettoyage...'
            // sh 'sudo rm -f /var/www/html/index.html'
            // sh 'sudo apt purge -y apache2'
        }
    }
}
