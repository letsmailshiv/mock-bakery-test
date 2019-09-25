import com.jenkins.library.PullImage
import com.jenkins.library.ContainerStructureTest
import com.jenkins.library.AquaScan
//import groovy.io.FileType

def call(Map config=[:]) {
    def yamlFile = config.yamlFile ? config.yamlFile : "${env.WORKSPACE}/pipelines/conf/imageIngessionRequestDEV.yaml"
    Map yamlData = readYaml file: yamlFile

    yamlData.images.each { image, data  -> 
        if (data.dockerFileExists == false) {
            stage("docker pull") { 
                container("docker"){ 
                    PullImage dockerPull = new PullImage();
                    dockerPull.pull(data.dockerImage)
                }
            }
        }
        if (data.containerStructureTest == true) {
            stage("Container Structure Test") { 
                container("structure-test"){ 
                    ContainerStructureTest structureTest = new ContainerStructureTest();
                    structureTest.runTest(data)
                }
            }
        }
        if (data.securityScan == true) {
            stage("AquaScan") { 
                container("docker"){ 
                    AquaScan scan = new AquaScan();
                    scan.runScan(data)
                }
            }
        }

    }
}

