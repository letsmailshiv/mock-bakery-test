package com.jenkins.library

def pull(def dockerImage) { 
    sh "
        docker pull ${dockerImage}
    "
}
