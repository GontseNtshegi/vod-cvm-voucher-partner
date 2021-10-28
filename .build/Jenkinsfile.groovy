@Library('microservices-pipeline@master') _
echo "test"
/* Pipeline Configs */
def configs = [

/* 	i.  If default value is prefered for a variable, comment out the variable
	ii. If custom value is prefered for a variable, uncomment variable and set value */

unitTests				: true,                            //Run/Skip Unit tests?
qualityAnalysis			: true,                            //Run/Skip quality analysis?
createArtifact			: true,                             //Package artifact?
deployToNexus			: true,								//Send artifact to Nexus? N.B Cannot be set to true if project is on a feature/pull request branch
createRelease			: true,                             //Send artifact to Open Shift?

/* Edit according to your project */
buildProfile			: "",                               //Include extra profiles desired; excluding dev, prod and test. ( comma seperated) "buildProfile : zip, zipkif" etc
//Leave empty if default buildProfile is prefered
targetPlatformTC		: "master.prod.tc.ocp.vodacom.corp",// Edit here
targetPlatformFS		: "master.prod.fs.ocp.vodacom.corp",// Edit here
targetNamespace			: "cvm-loyalty-voucher",            	// Edit here
targetDeploymentConfig	: "voucher-partner",        		// Edit here N.B Ensure Image Stream for project exists on openshift
targetBuildConfig		: "voucher-partner",             	// Edit here N.B Ensure Buildconfig
sonarQubeUrl    		: 'https://sonarqube.orbit.vodacom.aws.corp/',
codeCoverage			: 20

]

/* calling jenkins file under vars */
Jenkins( configs )
