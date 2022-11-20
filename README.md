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
		

## Guidance for checking

### HTTP response
1. **[200 OK]** for success requests.
2. **[404 Not Found]** for GET, PUT, or DELETE comment, post and user with invalid comment, post and user ID.
3. **[404 Not Found]** for POST comment with invalid post ID 
4. **[404 Not Found]** for add/delete User follower list with not existed user ID.
5. **[403 Forbidden]** for any requests with invalid client ID.
6. **[422 Unprocessable Entity]** for POST user without entering user ID in the request body.
7. **[400 Bad Request]** for using incorrect format when sending requests.
### API Entry Points

Client:
1. POST http://localhost:8080/api/v1/client/register

Request body example:

{
"clientName": "clienttest"
}

Comment:
1. GET http://localhost:8080/api/v1/comment/{{commentId}}

2. DELETE http://localhost:8080/api/v1/comment/{{commentId}}

3. POST http://localhost:8080/api/v1/post/{{postId}}/comment

Request body example:

{
"clientId": "7ee7059a-b498-47dd-a8a7-2517eeb72100",
"userId": "usertest",
"postId": "53242f3a-0d56-47e7-82cc-dccdf497b322",
"content": "commenttest"
}

4. PUT http://localhost:8080/api/v1/comment/{{commentId}}

Request body example:

{
"clientId": "7ee7059a-b498-47dd-a8a7-2517eeb72100",
"userId": "usertest",
"postId": "53242f3a-0d56-47e7-82cc-dccdf497b322",
"content": "updatecommenttest"
}

Post:
1. POST http://localhost:8080/api/v1/post

Request body example:

{
"clientId": "7ee7059a-b498-47dd-a8a7-2517eeb72100",
"userId": "usertest",
"tags": ["tag1"],
"title": "titletest",
"content": "posttest"
}

2. GET http://localhost:8080/api/v1/post/{{postId}}

3. PUT http://localhost:8080/api/v1/post/{{postId}}

Request body example:

{
"clientId": "7ee7059a-b498-47dd-a8a7-2517eeb72100",
"userId": "usertest",
"tags": ["tag1"],
"title": "puttest",
"content": "updateposttest"
}

4. DELETE http://localhost:8080/api/v1/post/{{postId}}

User:
1. POST http://localhost:8080/api/v1/user

Request body example:

{
"userId": "usertest",
"clientId": "ee865a46-bfa4-473c-831f-3be2c507bb6b",
"firstName": "Software",
"lastName": "Engineer"
}

2. GET http://localhost:8080/api/v1/user/{{userId}}

3. PUT http://localhost:8080/api/v1/user/{{userId}}

Request body example:

{
"clientId": "ee865a46-bfa4-473c-831f-3be2c507bb6b",
"firstName": "Junior Software",
"lastName": "Engineer"
}

4. DELETE http://localhost:8080/api/v1/user/{{userId}}

Request body example:

{
"clientId": "ee865a46-bfa4-473c-831f-3be2c507bb6b"
}

5. PUT http://localhost:8080/api/v1/user/{{userId}}/addFollower/{{followerId}}

6. PUT http://localhost:8080/api/v1/user/{{userId}}/deleteFollower/{{followerId}}

Style Checker and Static Bug Finder:  
![alt text](https://github.com/DavidHo666/w4156pj/blob/master/stylecheck.png?raw=true)
If image not shown correctly, it is also added to the repository
Coverage Test:  
under development

Problem to be fixed:
The comment POST API  and User API need to be fixed because we added new figures lastly. We will fix them during nex iteration.

