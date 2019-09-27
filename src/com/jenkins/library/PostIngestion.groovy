package com.jenkins.library
//This is to build the docker image using buildah on container itself.
def postProcess(Map data=[:],def yamlPath) {
    testYamlPath= "${data.containerStructureTestPath}"
    yamlSource= "${data.path}"
    //Merge back change to master records.
    //yamlMerge(
    //    sourceFile: "${yamlSource}",
    //    destFile: "${yamlDest}",
    //)

    //Move testcase 
    sh  """
        mkdir -p ${pwd()}/images/DEV/tests/ && mv ${pwd()}/${testYamlPath} ${pwd()}/images/DEV/tests/
    """

    //Purge ingestionRequest
    sh """
    > ${yamlSource}
    """
    //Git Commit
    sh "sleep 1500"
}
