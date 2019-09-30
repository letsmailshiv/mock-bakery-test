package com.jenkins.library

def push(Map data=[:],def registry, def registryProject) { 
    println "Publishing the newly created docker image onto registry..."
    //def registry = registry
    //def registryProject = registryProject
    def dockerImage = data.dockerImage
    sh """ 
        docker tag ${dockerImage} ${registry}/${registryProject}/${dockerImage}
        docker push ${registry}/${registryProject}/${dockerImage}
    """
}
