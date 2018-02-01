def call(Closure body) {
    def label = 'myPod'

	podTemplate(label: label, containers: [
		containerTemplate(name: 'maven', image: 'maven:3.3.9-jdk-8-alpine', ttyEnabled: true, command: 'cat'),
		containerTemplate(name: 'golang', image: 'golang:1.8.0', ttyEnabled: true, command: 'cat')
	  ]) {
		node(label) {
		  body()
		}
	}
}
