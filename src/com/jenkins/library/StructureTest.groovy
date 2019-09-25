
package com.jenkins.library

def runTest(Map data=[:]) { 
    def structureTest = data.containerStructureTestPath
    def image = data.dockerImage
    sh """ 
        container-structure-test test --image ${image} -c ${structureTest}
    """
}
