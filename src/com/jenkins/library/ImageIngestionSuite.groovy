package com.jenkins.library
import com.jenkins.library.PullImage
import com.jenkins.library.StructureTest
import com.jenkins.library.AquaScan
import com.jenkins.library.ImagePush
import com.jenkins.library.Connection
import com.jenkins.library.PostIngestion


def ingestionSuite(Map yamlData=[:]) { 

    yamlData.images.each { image, data  -> 
        /*
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
                    StructureTest structureTest = new StructureTest();
                    structureTest.runTest(data)
                }
            }
        }
        if (data.securityScan == true) {
            stage("TODO: AquaScan") { 
                container("docker"){ 
                    AquaScan scan = new AquaScan();
                    scan.runScan(data)
                }
            }
        }
        stage("Image Push") { 
            container("docker"){ 
                Connection dockerregistry = new Connection();
                dockerregistry.login(data)
                ImagePush upload = new ImagePush();
                upload.push(data)
            }
        }
        */
        stage("Post Processing") { 
            container("docker"){ 
                PostIngestion process = new PostIngestion();
                process.postProcess(data)
            }
        }


    }
}