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
	b. have a JAVA IDE, we use IntelliJ so we will use it as the example IDE in this documentation.  
	c. have Java and maven installed on the computer, have lombok installed in the IntelliJ  
	d. have jdk, maven configured in IntelliJ  
	e. File --> import --> Existing Maven Project --> choose the downloaded file from the corresponding directory --> finish  
	f. wait for the maven to auto-download all required dependencies jar  
	g. Build Project --> click the top "Build" icon to build the entire project

Run:  

  	Run the project locally:  
		a. build the project first described above  
		b. from the dropdown menu on the right of "build" icon, select "W4156nosecurityApplication" and click the "run" next to the menu

	!NEED UPDATE! Run the project on the VM(deployment):  
		a. right-click on the project folder --> run as --> maven clean  
		b. right-click on the project folder --> run as --> maven install  
		c. upload the service-auth-0.0.1-SNAPSHOT.jar(or service-operation-0.0.1-SNAPSHOT.jar) file in the src/target/ to the VM  
		d. have java, maven, MySQL installed on the VM  
		e. on the VM, run "nohup java -jar service-auth(operation)-0.0.1-SNAPSHOT.jar &"  
		
Test:

	Unit Test:  
		In IDE:  
			a. all test cases/code are written in src/test/java/com/insomnia_studio/w4156pj 
			b. run w4156pj as Junit Test(right-click on the folder --> Run 'Tests in 'com.insomnia_studio.w4156pj''  
		!NEED UPDATE! In Linux:
			javac -cp .:service-operation(auth)-0.0.1-SNAPSHOT.jar org.junit.runner.JUnitCore service-operation(auth)/src/test/java/com/sangria/operation(auth)/ServiceOperation(Auth)ApplicationTests.java  
		
	Test via Postman:  
		a. have the project built and run on IntelliJ 
		b. in the Postman, send example requests to the endpoints specified in the next section  
		



## API Entry Points

1. POST http://localhost:8080/api/v1/client/register
request body:
{
	"clientName": "clienttest"
}
3. GET http://localhost:8080/api/v1/comment/{{commentId}}
request body:
{
	"commentId": "client1"
}
5. DELETE http://localhost:8080/api/v1/comment/{{commentId}}
request body:
{
	"clientName": "client1"
}
7. POST http://localhost:8080/api/v1/post/{{postId}}/comment/add
request body:
{
	"user": "usertest",
	"post": "posttest",
	"LikesNum": 50,
	"disLikesNum": 30,
	"Content": "contenttest"
}
9. POST http://localhost:8080/api/v1/post
request body:
{
	"tags": ["tagtest"],
	"title": "titletest",
	"content": "contenttest"
}
11. GET http://localhost:8080/api/v1/post/{{postId}}
request body:
{
	"clientName": "client1"
}
13. PUT http://localhost:8080/api/v1/post/{{postId}}
request body:
{
	"clientName": "client1"
}
15. DELETE http://localhost:8080/api/v1/post/{{postId}}
request body:
{
	"clientName": "client1"
}
17. POST http://localhost:8080/api/v1/user
request body:
{
	"firstName": "usertest_first",
	"lastName": "usertest_last",
	"follower": ["followertest"],
	"followedBy": ["followedbytest"],
	"posts": ["posttest"] 
}
19. GET http://localhost:8080/api/v1/user/{{userId}}
request body:
{
	"clientName": "client1"
}
21. PUT http://localhost:8080/api/v1/user/{{userId}}
request body:
{
	"clientName": "client1"
}
23. DELETE http://localhost:8080/api/v1/user/{{userId}}
request body:
{
	"clientName": "client1"
}
25. PUT http://localhost:8080/api/v1/user/{{userId}}/addFollower/{{followerId}}
request body:
{
	"clientName": "client1"
}
27. PUT http://localhost:8080/api/v1/user/{{userId}}/deleteFollower/{{followerId}}
request body:
{
	"clientName": "client1"
}

Style Checker and Static Bug Finder:  

iamge insert here
	
Coverage Test:  
under development

