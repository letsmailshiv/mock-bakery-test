import com.jenkins.library.PullImage
import com.jenkins.library.StructureTest
import com.jenkins.library.AquaScan
import com.jenkins.library.ImagePush

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
        if (data.StructureTest == true) {
            stage("Container Structure Test") { 
                container("structure-test"){ 
                    StructureTest structureTest = new StructureTest();
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
        stage("Image Push") { 
            container("docker"){ 
                ImagePush push = new ImagePush();
                push.upload(data)
            }
        }
    }
}

