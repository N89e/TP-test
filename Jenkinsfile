pipeline {
    agent any

    stages {
        stage('Dependances') {
            steps {
                echo "🔧 Installation d'Apache2..."
                sh '''
                    apt update -y
                    apt install -y apache2
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
                echo '🚀 Déploiement du site...'
                sh 'cp index.html /var/www/html/index.html'
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
        echo '🧹 Nettoyage...'
        sh 'rm -rf /var/www/html/index.html'
        sh 'apt remove -y apache2'
        sh 'rm -rf /var/www/html.backup'
    }
    }
}
