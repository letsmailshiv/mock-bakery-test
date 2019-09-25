import com.jenkins.library.PullImage
import com.jenkins.library.AquaScan
import com.jenkins.library.ContainerStructureTest
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
                    AquaScan runScan = new AquaScan();
                    runScan.runScan(data)
                }
            }
        }

    }
    /*
    def foundYamlFiles = sh(script: "ls -1 ${env.WORKSPACE}/pipelines/conf/", returnStdout: true).split()
    foundYamlFiles.each  { yamlName ->
        def yamlPath = "${env.WORKSPACE}/pipelines/conf/" + yamlName
        println('Image Ingession source files -> ' + yamlPath)
        def yamlContent = readYamlFile(yamlPath)

            //stage('Lint Dockerfile') {
            //    container('hadolint') {
            //        LintDockerFile dockerimage = new LintDockerFile();
            //        dockerimage.build(yamlContent.dockerFilePath)
            //    }
            //}
            stage('build Dockerfile') {
                container('docker') {
                    echo 'build Dockerfile'
                }
            }
            stage('Container Structure Test') {
                container('structure-test') {
                    echo 'Container Structure Test'
                }
            }
            stage('Security Scan') {
                container('docker') {
                    echo 'Security Scan'
                }
            }
        
    }
    */
}


def readYamlFile(def readYamlFile) {
    String configPath = "${readYamlFile}"
    Map yamlData = readYaml file: configPath
    

    return yamlData;
    echo "Inside readYamlFile function"
    if (yamlData.dockerFileExists == true) {
        lintDockerFile(yamlData.dockerFilePath)
        buildContainerImage(yamlData)
    }
    else {
        imagePullFunc(yamlData)
    }
    if(yamlData.containerStructureTest == true) {
        runContainerStructureTest(yamlData.containerStructureTestPath)
    }
    if(yamlData.securityScan == true) {
        runSecurityScan()
    }
}

def runSecurityScan() {
    echo "Inside SecurityScan"
}
def runContainerStructureTest(def containerTestDir) {
    echo "Inside ContainerStructureTest = ${containerTestDir}"
}
def buildContainerImage(Map yamlData = [:]) {

    def dockerFileExists = yamlData.dockerFileExists ? yamlData.dockerFileExists : true
    def dockerFilePath =  yamlData.dockerFilePath
    def dockerImageLocation =  yamlData.dockerImageLocation 
    def description = yamlData.description 
    def source = yamlData.source 
    def md5DockerFile = yamlData.md5DockerFile

    echo "Inside buildContainerImage  = ${dockerFileExists}"

}
