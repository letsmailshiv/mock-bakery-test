import com.jenkins.library.ImageIngestionSuite

//import groovy.io.FileType

def call(Map config=[:]) {
    def yamlFile = config.yamlFile ? config.yamlFile : "${env.WORKSPACE}/pipelines/conf/imageIngestionRequestRTL.yaml"
    Map yamlData = readYaml file: yamlFile
    ImageIngestionSuite imageingestion = new ImageIngestionSuite();
    echo "Hello"
    exit(0)
    imageingestion.ingestionSuite(yamlData,yamlFile)
    
}

