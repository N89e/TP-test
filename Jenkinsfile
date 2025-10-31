pipeline {
    agent {
        label "deploy-site-web"
    }

    stages {
        stage("Dependances") {
            steps {
                echo 'Installation des dépendances...'
            }
        }
        stage('Checkout') {
            steps {
                echo 'Récupération du code...'
                git url: 'https://github.com/N89e/TP-test.git', branch: 'main'
            }
        }

        stage('Backup') {
            steps {
                echo 'Sauvegarde de /var/www/html...'
                //sh 'cp -r /var/www/html /var/www/html.backup'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploy'
                ///sh 'sudo cp index.html /var/www/html/index.html'
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
            echo 'Nettoyage en cours...'
            // sh 'sudo rm -f /var/www/html/index.html'
            // sh 'sudo apt purge -y apache2'
            // sh 'rm -rf /var/www/html.backup'
        }
    }
}
