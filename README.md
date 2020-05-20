# hello-world-test
Simple login\register example using jwt 

To run the program do following steps
* clone th repo `git clone https://github.com/dikhim/hello-world-test.git`
* build the program `mvn clean package`
* run program with `java - jar target\hello-world-test-1.0-SNAPSHOT.jar`
* go to http://127.0.0.1:5000/swagger-ui.html
* register user with `POST /auth/register`
* login with `POST /auth/login`
* apply token value in the authorize field and gor to the `GET /hello` to check that all permissions are granted