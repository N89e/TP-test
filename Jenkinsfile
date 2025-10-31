pipeline {
    agent {
        label "agent"
    }
    stages {
        stage("Dependances") {
            steps {
                echo '🔧 Vérification et installation d\'Apache2...'
                sh '''
                    if command -v apache2 >/dev/null 2>&1; then
                        echo "Apache2 est déjà installé."
                    else
                        echo "Non trouvé. Installation avec sudo..."
                        sudo apt update -y && sudo apt install -y apache2
                    fi
                '''
            }
        }

        stage('Checkout') {
            steps {
                echo "📦 Récupération du code..."
                checkout scm
            }
        }

        stage('Backup') {
            steps {
                echo '💾 Sauvegarde du répertoire...'
                sh '''
                    if [ -d /var/www/html ]; then
                        cp -r /var/www/html /var/www/html.backup
                    else
                        echo "/var/www/html n'existe pas, pas de sauvegarde."
                    fi
                '''
            }
        }

        stage('Deploy') {
            steps {
                echo '🚀 Déploiement du site...'
                sh 'sudo cp index.html /var/www/html/index.html'
            }
        }

        stage('Test') {
            steps {
                echo "🔍 Vérification..."
                sh '''
                    sudo service apache2 start || echo "Impossible de démarrer Apache."
                    sleep 3
                    curl -f http://localhost || (echo "Erreur d'accès" && exit 1)
                '''
            }
        }
    }

    post {
        success {
            echo "✅ Déploiement réussi !"
        }
        failure {
            echo "❌ Le déploiement a échoué."
        }
        always {
            echo '🧹 Nettoyage...'
            sh '''
                if [ -w /var/www/html/index.html ]; then
                    sudo rm -f /var/www/html/index.html
                else
                    echo "Pas les droits pour supprimer /var/www/html/index.html"
                fi
                if [ -d /var/www/html.backup ]; then
                    sudo rm -rf /var/www/html.backup
                fi
            '''
        }
    }
}
