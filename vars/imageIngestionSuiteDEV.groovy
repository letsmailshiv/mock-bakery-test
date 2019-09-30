import com.jenkins.library.ImageIngestionSuite

//import groovy.io.FileType

def call(Map config=[:]) {
    def yamlFile = config.yamlFile ? config.yamlFile : "${env.WORKSPACE}/pipelines/conf/imageIngestionRequestDEV.yaml"
    Map yamlData = readYaml file: yamlFile

    if(yamlData.images==null)
    {
        echo "INFO: ${yamlFile} Yaml is empty."
        echo "INFO: no action needed"
    }
    else {

        yamlData.put('imageType','DEV');
        yamlData.put('yamlPath',"${yamlFile}");
        //For pushimg image
        yamlData.put('registry',"harbor.instg.pscloudhub.com")
        yamlData.put('registryProject',"global-bakery-dev")
        yamlData.put('registrycreds',"dev-robot")

        ImageIngestionSuite imageingestion = new ImageIngestionSuite();
        imageingestion.ingestionSuite(yamlData)
    }    
}
