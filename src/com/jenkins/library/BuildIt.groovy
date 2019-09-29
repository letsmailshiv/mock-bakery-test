package com.jenkins.library
//This is to build the docker image using buildah on container itself.
def buildit(def dockerFilePath,def dockerImage) {
    sh """
        echo INFO: building dockerfile with docker
        docker build -t ${dockerImage} -f ${pwd()}/${dockerFilePath} .
    """
}
