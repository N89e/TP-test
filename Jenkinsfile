pipeline {
        agent {
                label "deploy-site-web"
        }
stages {
stage("Dependances") {
steps {
stages {
        stage('Setup Apache2 et déploiement index.html') {
            steps {
                sh 'chmod +x setup_apache.sh'
                sh './setup_apache.sh'
            }
        }
    }
}
}
stage('Checkout') {
steps {
// Récupération du code
}
}
stage('Deploy') {
steps {
// Copie des fichiers vers le serveur web (/var/www/html/)
}
}
stage('Test') {
steps {
// Vérification du déploiement
}
}
}
post {
success {
// Action en cas de succès (echo, ou autre)
}
failure {
// Action en cas d'échec
}
always {
// Nettoyage
// Supprimer les fichiers copiés dans /var/www/html
// Desinstaller apache2
}
}
}
