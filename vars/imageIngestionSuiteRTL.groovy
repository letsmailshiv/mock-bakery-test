import com.jenkins.library.ImageIngestionSuite

//import groovy.io.FileType

def call(Map config=[:]) {
    def yamlFile = config.yamlFile ? config.yamlFile : "${env.WORKSPACE}/pipelines/conf/imageIngestionRequestRTL.yaml"
    Map yamlData = readYaml file: yamlFile

    
    if(yamlData.images==null)
    {
        echo "INFO: ${yamlFile} Yaml is empty."
        echo "INFO: no action needed"
    }
    else {

        yamlData.put('imageType','RTL');
        yamlData.put('yamlPath',"${yamlFile}");
        yamlData.put('registry',"harbor.instg.pscloudhub.com")
        yamlData.put('registryProject',"global-bakery-rtl")
        yamlData.put('registrycreds',"rtl-robot")

        ImageIngestionSuite imageingestion = new ImageIngestionSuite();
        imageingestion.ingestionSuite(yamlData)
    }    
}

