def call(body) {
   def config = [:]
   body.resolveStrategy = Closure.DELEGATE_FIRST
   body.delegate = config
   body()

   usePodTemplates{
           stage('Get a Maven project') {
            git 'https://github.com/jenkinsci/kubernetes-plugin.git'
            container('maven') {
                     stage('Build a Maven project') {
                     sh 'mvn -B clean install'
                     }
            }
            }
            stage('Get a Golang project') {
             git url: 'https://github.com/hashicorp/terraform.git'
             container('golang') {
                     stage('Build a Go project') {
                    sh """
                    mkdir -p /go/src/github.com/hashicorp
                    ln -s `pwd` /go/src/github.com/hashicorp/terraform
                    cd /go/src/github.com/hashicorp/terraform && make core-dev
                    """
                    }
            }
     }
}
