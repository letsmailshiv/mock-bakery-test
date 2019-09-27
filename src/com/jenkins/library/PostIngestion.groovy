package com.jenkins.library
//This is to build the docker image using buildah on container itself.
def postProcess(Map data=[:]) {
    testYamlPath= "${data.containerStructureTestPath}"
    yamlSource= "${data.path}"
    //Merge back change to master records.
    //yamlMerge(
    //    sourceFile: "${yamlSource}",
    //    destFile: "${yamlDest}",
    //)

    //Move testcase 
    sh  """
        mv ${testYamlPath} images/DEV/tests/
    """

    //Purge ingestionRequest
    sh """
    > ${yamlSource}
    """
    //Git Commit
    sh "sleep 1500"
}
