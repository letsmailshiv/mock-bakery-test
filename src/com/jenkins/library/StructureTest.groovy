
package com.jenkins.library

def runTest(Map data=[:]) { 
    def structureTest = data.containerStructureTestPath
    sh """ 
        echo structure-test  
    """
}
