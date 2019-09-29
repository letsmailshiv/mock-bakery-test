package com.jenkins.library
//This is to build the docker image using buildah on container itself.
def buildit(def dockerFilePath) {
    sh """
        INFO: Linting dockerfile
        kaniko --dockerfile=${pwd()}/${dockerFilePath} --context=${pwd()}/pipelines/conf --no-push
        sleep 1500
    """    
}
