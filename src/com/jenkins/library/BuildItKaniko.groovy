package com.jenkins.library
//This is to build the docker image using buildah on container itself.
def buildit(def dockerFilePath) {
    sh """
        echo INFO: Linting dockerfile
        cp -r ${pwd()}/.docker ~/.docker && chmod 700 ~/.docker
        echo /kaniko/executor --dockerfile=${pwd()}/${dockerFilePath} --context=${pwd()}/pipelines/conf --no-push
        sleep 1500
    """    
}
