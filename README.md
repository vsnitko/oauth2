![Coverage](.github/badges/jacoco.svg) ![Branches](.github/badges/branches.svg)

# Main page
![Screenshot_2](https://user-images.githubusercontent.com/54511054/209580686-ec7d44bf-0999-4110-8b53-334498d5c763.png)

Spring-React project with implementation of both OAuth2 auth and basic auth with email. Uses JWT tokens to authorize users

### Technologies used
* Backend
    * Java 19, Spring Boot 3
    * MySql database
    * Spring Security 6
* Frontend
    * React 18 (TypeScript based)
    * Redux Toolkit
    * Chakra UI

### Before you start
In order to use authorization through Google or GitHub, you should register your application in these services

#### Google
Visit https://console.cloud.google.com/apis/credentials

Click "Create Credentials" -> OAuth client ID -> Application Type (Web application). 

In "Authorized redirect URIs" enter `http://localhost:8080/login/oauth2/code/google`

Click "Create" and copy Client ID and Client Secret in application.yml

#### GitHub
Visit https://github.com/settings/applications/new

In "Homepage URL" enter `http://localhost:8080`

In "Authorization callback URL" enter `http://localhost:8080/login/oauth2/code/github`

Click "Register application", generate Client Secret and copy it with Client ID in application.yml

### How to run
From project root: 

Run MySql
#### `docker run -d --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=oauth2_db mysql:latest`

Run Spring Boot 3 with Java 19 (server will run on port 8080)
#### `mvn clean install`
#### `java -jar target/oauth2-demo.jar`

Run React (server will run on port 3000)

From /react-oauth2 (`cd react-oauth2`):

#### `npm install`
#### `npm start`

### Notes
* It doesn't matter weather you Sign In or Sign Up if you use OAuth2 providers. 
User will be created if you didn't signed-up it previously, or you will be signed-in if you did
* If different providers provide the same email, it will be qualified as the same user in this app

### Known issues / Points to improve
* Refresh token is not supported
* Not possible to update user avatar
* Not possible to verify email for basic auth


