import com.jenkins.library.ImageIngessionSuite

//import groovy.io.FileType

def call(Map config=[:]) {
    def yamlFile = config.yamlFile ? config.yamlFile : "${env.WORKSPACE}/pipelines/conf/imageIngessionRequestRTL.yaml"
    Map yamlData = readYaml file: yamlFile

    ImageIngessionSuite imageingession = new ImageIngessionSuite();
    imageingession.ingessionSuite(yamlData)
    
}

