package com.jenkins.library

def push(Map data=[:],def registry, def registryProject) { 
    println "Publishing the newly created docker image onto registry..."
    def registry = registry
    def registryProject = registryProject
    def dockerImage = data.dockerImage
    sh """ 
    /kaniko/executor --dockerfile=${data.dockerFileLocation} --context=/${pwd()}/ --destination=${registry}/${registryProject}/${dockerImage}
    """
}
