import com.jenkins.library.ImageIngestionSuite

//import groovy.io.FileType

def call(Map config=[:]) {
    def yamlFile = config.yamlFile ? config.yamlFile : "${env.WORKSPACE}/pipelines/conf/imageIngestionRequestDEV.yaml"
    Map yamlData = readYaml file: yamlFile

    //For pushimg image
    yamlData.registry = "harbor.instg.pscloudhub.com"
    yamlData.registryProject = "global-bakery-dev"
    yamlData.dockerImage = "dev-robot"

    yamlData.put('imageType','DEV');
    yamlData.put('yamlPath',"${yamlFile}");

    if(yamlData.images==null)
    {
        echo "INFO: ${yamlFile} Yaml is empty."
        echo "INFO: no action needed"
    }
    else {
        ImageIngestionSuite imageingestion = new ImageIngestionSuite();
        imageingestion.ingestionSuite(yamlData)
    }    
}
