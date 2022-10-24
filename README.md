# COMS4156 Project

Team Members: Qingyue Jiao(qj2168), Dawei He(dh3027), Lixuan Yang(ly2555), Zhen Tao(zt2276)

Project Description:

This is an API service for formatting and managing posts for clients like StreetEasy.com, Instagram, Facebook Marketplace


## Platform & Framework

REST frameworks: Springboot

Database: MySQL

Build tool: Maven

Style checker: CheckStyle

Static analysis bug finder: JUnit

Test runner & mocking framework: built-in tools in IntelliJ IDEA & Mockito
 
Coverage tracker: built-in tools in IntelliJ IDEA


## How to Build, Test, Run

Build:  

	a. clone the repo to the local machine.  
	b. have a JAVA IDE, we use Eclipse so we will use it as the example IDE in this documentation.  
	c. have Java and maven installed on the computer, have lombok installed in the eclipse  
	d. have jdk, maven configured in the eclipse  
	e. File --> import --> Existing Maven Project --> choose the folder service-auth(or service-operation) --> finish  
	f. wait for the maven to auto-download all required dependencies jar  
	g. Project --> clean to clean the project first  
	h. right-click on the project folder --> maven --> Update Project to update and build the project  

Run:  

  	Run the project locally:  
		a. build the project first described above  
		b. run src/main/java/ServiceAuthApplication.java(or src/main/java/ServiceOperationApplication.java for service-operation) as java Application (right-click on the file --> run as.. --> Java Application)  

	Run the project on the VM(deployment):  
		a. right-click on the project folder --> run as --> maven clean  
		b. right-click on the project folder --> run as --> maven install  
		c. upload the service-auth-0.0.1-SNAPSHOT.jar(or service-operation-0.0.1-SNAPSHOT.jar) file in the src/target/ to the VM  
		d. have java, maven, MySQL installed on the VM  
		e. on the VM, run "nohup java -jar service-auth(operation)-0.0.1-SNAPSHOT.jar &"  
		
Test:

	Unit Test:  
		In IDE:  
			a. all test cases/code are written in src/test/java/com/sangria/auth(operation)/ServiceAuth(Operation)ApplicationTests.java  
			b. run the .java as Junit Test(right-click on the file --> run as --> Junit Test)  
		In Linux:
			javac -cp .:service-operation(auth)-0.0.1-SNAPSHOT.jar org.junit.runner.JUnitCore service-operation(auth)/src/test/java/com/sangria/operation(auth)/ServiceOperation(Auth)ApplicationTests.java  
		
	Test via Postman:  
		a. have the project deployed and run on the vm as described above  
		b. in the Postman, send requests to the endpoints: e.g. 35.196.112.19/auth/gameManager/login with parameters  
		
	Test in CI:  
		All Unit tests have been included in the Github Action and will run on every push.  


## API Entry Points


Style Checker and Static Bug Finder:  
We are using the SonarCloud as the style checker and static bug finder during the process of CI using Github Action, and the report could be found here: 
	
Coverage Test:  
under development

