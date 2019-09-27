import com.jenkins.library.ImageIngestionSuite

//import groovy.io.FileType

def call(Map config=[:]) {
    def yamlFile = config.yamlFile ? config.yamlFile : "${env.WORKSPACE}/pipelines/conf/imageIngestionRequestDEV.yaml"
    Map yamlData = readYaml file: yamlFile
    yamlFile.path = yamlFile
    ImageIngestionSuite imageingestion = new ImageIngestionSuite();
    imageingestion.ingestionSuite(yamlData)

}

