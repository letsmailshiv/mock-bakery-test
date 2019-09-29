package com.jenkins.library
//This is to build the docker image using buildah on container itself.
def lint(def dockerFilePath) {
    sh """
        echo INFO: Linting dockerfile
        hadolint ${dockerFilePath}
    """    
}
