//import com.jenkins.library.ParseImageRequestYamls
//import groovy.io.FileType

def call(Map config=[:]) {
    def yamlDir = config.yamlDir ? config.yamlDir : "${env.WORKSPACE}/pipelines/conf/"

    def foundYamlFiles = sh(script: "ls -1 ${env.WORKSPACE}/pipelines/conf/", returnStdout: true).split()

    foundYamlFiles.each  { yamlName ->
        def yamlPath = "${env.WORKSPACE}/pipelines/conf/" + yamlName
        println('Image Ingession source files -> ' + yamlPath)
        readYamlFile(yamlPath)

        node {
            stage('Example') {
                    echo 'I only execute on the master branch'
            }
        }
        
    }
}


def readYamlFile(def readYamlFile) {
    String configPath = "${readYamlFile}"
    Map yamlData = readYaml file: configPath
    
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
def imagePullFunc(Map yamlData = [:]) { 
    echo "Inside imagePullFunc  = ${yamlData.dockerImageLocation}"
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
def lintDockerFile(def dockerFilePath) {
        echo "linting dockerfile  = ${dockerFilePath}"
        sh "hadolint ${dockerFilePath}"
}