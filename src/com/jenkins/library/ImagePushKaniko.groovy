package com.jenkins.library

def push(Map data=[:]) { 
    println "Publishing the newly created docker image onto registry..."
    def registry = data.registry
    def registryProject = data.registryProject
    def dockerImage = data.dockerImage
    sh """ 
    /kaniko/executor --dockerfile=${data.dockerFileLocation} --context=/${pwd()}/ --destination=${registry}/${registryProject}/${dockerImage}
    """
}
