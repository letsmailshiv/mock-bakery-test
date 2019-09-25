package com.jenkins.library

def push(Map data=[:]) { 
    println "Publishing the newly created docker image onto registry..."
    def registry = data.registry
    def registryProject = data.registryProject
    sh """ 
        docker tag ${dockerImage} ${registry}/${registryProject}/${dockerImage}
        docker push ${registry}/${registryProject}/${dockerImage}
    """
}
