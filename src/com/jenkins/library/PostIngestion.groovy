package com.jenkins.library
//This function will move changes to ingestedImages.yaml
def postProcess(Map data=[:],def yamlPath,def imageType,def image) {
    testYamlPath= "${data.containerStructureTestPath}"
    yamlSource= "${yamlPath}"
    singleYaml="singleImage.yaml"
    yamlDest= "${pwd()}/images/${imageType}/ingestedImages.yaml"
    //Merge back change to master records.
    sh "rm -rf ${singleYaml}"
    writeYaml file: "${singleYaml}", data: ["image":["${image}":data]]
    sh "cat ${singleYaml}"
    yamlMerge(
        fileA: "${singleYaml}",
        fileB: "${yamlDest}",
        mergedFile: "${yamlDest}"
    )
    //Move testcase 
    sh  """
        mkdir -p ${pwd()}/images/${imageType}/${image}/tests/ && mv ${pwd()}/${testYamlPath} ${pwd()}/images/${imageType}/${image}/tests/
    """
    if (data.dockerFileExists == true) {
        sh  """
            cp -r ${pwd()}/${data.dockerFileLocation} ${pwd()}/images/${imageType}/${image}/Dockerfile
            rm -rf ${pwd()}/${data.dockerFileLocation}
        """
    }

    //Purge ingestionRequest
    sh """
    echo 'images: null' > ${yamlSource}
    """
    //Git Commit
    commitChange(credentialsId,gituser,gitemail)
}


def commitChange(credentialsId,gituser,gitemail){
    //GIT push
    withCredentials([[$class: 'SSHUserPrivateKeyBinding', keyFileVariable: 'gitKey', passphraseVariable: 'gitKeyPass', credentialsId: credentialsId]]) {
    //withCredentials([sshUserPrivateKey(credentialsId: credentialsId, keyFileVariable: 'gitKey')]) {
        def tagCommand = """
                git config --global user.email \"${gitemail}\"
                git config --global user.name \"${gituser}\"
                git checkout master
                git add . 
                git commit -m \"Updating yaml\"; 
                git push;
            """
        def cmd = """
                chmod 600 ${env.gitKey}; 
                eval `ssh-agent`; 
                mkdir -p ~/.ssh
                ssh-keyscan -t rsa ${gitRepoHostname} >> ~/.ssh/known_hosts
                ssh-add ${env.gitKey};
                ${tagCommand}
                kill -s term \$SSH_AGENT_PID
            """
        out = sh(rturnStdout: true, script: cmd)
    }

}
