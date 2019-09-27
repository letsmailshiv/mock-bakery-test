import com.jenkins.library.ImageIngestionSuite

//import groovy.io.FileType

def call(Map config=[:]) {
    def yamlFile = config.yamlFile ? config.yamlFile : "${env.WORKSPACE}/pipelines/conf/imageingestionRequestDEV.yaml"
    Map yamlData = readYaml file: yamlFile
    yamlFile.path = yamlFile
    ImageingestionSuite imageingestion = new ImageingestionSuite();
    imageingestion.ingestionSuite(yamlData)

}

