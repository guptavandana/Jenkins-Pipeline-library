def call(Closure body) { 
	withKubeConfig(caCertificate: '', credentialsId: 'kubectl', serverUrl: 'https://http://47.74.228.246:6443') 
	 {
		body()
	 }
}
