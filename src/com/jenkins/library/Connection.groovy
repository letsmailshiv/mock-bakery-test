package com.jenkins.library
//This is to login onto registry to pull/push the images onto it.
def login(Map config) {
    def registrycreds = config.registrycreds
    def registry = config.registry
    println("registrycreds = ${registrycreds}")
    println("registrycreds = ${registry}")
    println "Connecting with Docker Registry..."
 
    withCredentials([usernamePassword(credentialsId: "${registrycreds}", passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
        def status = sh(returnStatus: true, script: "docker login --username=${USERNAME} --password=${PASSWORD} ${registry} > loginstatus.txt")
        if (status != 0) {
            println "Oops, The registry credentials you supplied are incorrect."
            currentBuild.result = 'FAILED'
            def output = readFile('loginstatus.txt').trim()
            error 'Login Failed'
        }
        else { println "Login Succeeded!"}
    }
}