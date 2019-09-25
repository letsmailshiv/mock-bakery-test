import com.jenkins.library.ImageIngessionSuite

//import groovy.io.FileType

def call(Map config=[:]) {
    def yamlFile = config.yamlFile ? config.yamlFile : "${env.WORKSPACE}/pipelines/conf/imageIngessionRequestDEV.yaml"
    Map yamlData = readYaml file: yamlFile

    ImageIngessionSuite imageingession = new ImageIngessionSuite();
    imageingession.ingessionSuite(yamlData)

}
/*
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
                Connection dockerregistry = new Connection();
                dockerregistry.login(data)
                ImagePush upload = new ImagePush();
                upload.push(data)
            }
        }
    }
}
*/

