package com.jenkins.library
//This is to build the docker image using buildah on container itself.
def postProcess(def yamlSource,def yamlDest,def data[:] ) {
    testYamlPath= "${data.containerStructureTestPath}"
    //Merge back change to master records.
    yamlMerge(
        sourceFile: "${yamlSource}",
        destFile: "${yamlDest}",
    )

    //Move testcase 
    sh  """
        mv ${testYamlPath} images/DEV/tests/
    """

    //Purge ingessionRequest
    sh """
    > ${yamlSource}
    """
    //Git Commit
    sh "sleep 1500"
}
