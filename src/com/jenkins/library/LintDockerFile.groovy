package com.jenkins.library
//This is to build the docker image using buildah on container itself.
def LintDockerFile(def dockerFilePath) {
    sh "hadolint ${dockerFilePath}"
}
