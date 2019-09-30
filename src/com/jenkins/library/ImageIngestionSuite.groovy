package com.jenkins.library
import com.jenkins.library.PullImage
import com.jenkins.library.StructureTest
import com.jenkins.library.AquaScan
import com.jenkins.library.ImagePush
import com.jenkins.library.Connection
import com.jenkins.library.PostIngestion
import com.jenkins.library.LintDockerFile
import com.jenkins.library.BuildItKaniko

def ingestionSuite(Map yamlData=[:]) { 

    yamlData.images.each { image, data  -> 
        if (data.dockerFileExists == false) {
            stage("docker pull") { 
                container("docker"){ 
                    PullImage dockerPull = new PullImage();
                    dockerPull.pull(data.dockerImage)
                }
            }
        }
        else{
            stage("Lint Dockerfile") { 
                container("hadolint"){ 
                    LintDockerFile lintDocker = new LintDockerFile();
                    lintDocker.lint(data.dockerFileLocation)
                }
            }
            stage("Build Image") { 
                container("docker"){ 
                    //unstash 'dockerConfig'
                    BuildIt imageBuild = new BuildIt();
                    imageBuild.buildit(data.dockerFileLocation,data.dockerImage)
                }
            }
        }
        stage("Container Structure Test") { 
            container("structure-test"){ 
                StructureTest structureTest = new StructureTest();
                structureTest.runTest(data)
            }
        }
        stage("TODO: AquaScan") { 
            container("docker"){ 
                AquaScan scan = new AquaScan();
                scan.runScan(data)
            }
        }
        stage("Image Push") { 
			if(gitWorkFlow == 'integration-branch')
			{
                container("docker"){ 
                    Connection dockerregistry = new Connection();
                    dockerregistry.login(yamlData.registry yamlData.registrycreds)
                    ImagePush upload = new ImagePush();
                    upload.push(data,yamlData.registry,yamlData.registryProject)
                }
            }
        }
        stage("Post Processing") { 
			if(gitWorkFlow == 'integration-branch')
			{
                container("utility"){ 
                    PostIngestion process = new PostIngestion();
                    process.postProcess(data,yamlData.yamlPath,yamlData.imageType,image)
                }
            }
        }
    }
}