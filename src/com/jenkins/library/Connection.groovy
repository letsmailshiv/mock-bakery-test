package com.jenkins.library
//This is to login onto registry to pull/push the images onto it.
def login(Map config) {
    def REGISTRYCREDS = config.registrycreds
    def registry = config.registry
    println "Connecting with Docker Registry..."
    withCredentials([usernamePassword(credentialsId: "dev-robot", passwordVariable: 'registryPASS', usernameVariable: 'registryUSER')]) {
        def status = sh(returnStatus: true, script: "docker login --username=${registryUSER} --password=${registryPASS} ${registry} > loginstatus.txt")
        if (status != 0) {
            println "Oops, The registry credentials you supplied are incorrect."
            currentBuild.result = 'FAILED'
            def output = readFile('loginstatus.txt').trim()
            error 'Login Failed'
        }
        else { println "Login Succeeded!"}
    }
}