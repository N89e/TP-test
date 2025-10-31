pipeline {
    agent {
        label "agent"
    }
    stages {
        stage("Dependances") {
            steps {
                echo '🔧 Installation d\'Apache2 avec sudo...'
                sh '''
                    if command -v apache2 >/dev/null 2>&1; then
                        echo "Apache2 est déjà installé."
                    else
                        echo "Apache2 non trouvé. Installation avec sudo..."
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
                        echo "/var/www/html n'existe pas, pas de sauvegarde"
                    fi
                '''
            }
        }

        stage('Deploy') {
            steps {
                echo '🚀 Déploiement du site avec sudo...'
                sh 'sudo cp index.html /var/www/html/index.html'
            }
        }

        stage('Test') {
            steps {
                echo "🔍 Vérification..."
                sh '''
                    service apache2 start  true
                    sleep 3
                    curl -f http://localhost
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
            echo '🧹 Nettoyage avec sudo...'
            sh '''
                if [ -w /var/www/html/index.html ]; then
                    sudo rm -rf /var/www/html/index.html
                else
                    echo "Pas les droits pour supprimer /var/www/html/index.html"
                fi
            '''
        }
    }
}
