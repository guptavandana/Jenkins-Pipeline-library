def call(body) {
   def config = [:]
   body.resolveStrategy = Closure.DELEGATE_FIRST
   body.delegate = config
   body()


  def namespace
  stage('User Input'){
  	timeout(5) {
  	   namespace = input id: 'namespace', message: 'Enter Namespace', ok: 'Proceed?', parameters: [string(defaultValue: '', description: '', name: 'namespace')]
  	}
  }

   usePodTemplates('testpod'){

    stage('deploy') {
      container('kubectl') {
        useKubeConfig {
            try{
              sh "kubectl create namespace ${namespace}"
            }catch(e){
              echo "Namespace ${namespace} already exists."
            }
          }
	}
      }



	   stage('Get a Maven project') {

		container('maven') {
			stage('Build a Maven project') {
				 sh 'mvn -version'
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
						cd /go/src/github.com/hashicorp/terraform
					"""
				}
			}
		}
	}
}
