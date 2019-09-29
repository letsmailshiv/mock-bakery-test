package com.jenkins.library
//This is to build the docker image using buildah on container itself.
def buildit(def dockerFilePath) {
    sh """
        echo INFO: Linting dockerfile
        echo kaniko --dockerfile=${pwd()}/${dockerFilePath} --context=${pwd()}/pipelines/conf --no-push
    """    
}
