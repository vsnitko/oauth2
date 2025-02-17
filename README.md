![Passing](../../actions/workflows/on_master.yml/badge.svg) 
![Coverage](../badges/badges/jacoco.svg) 
![Branches](../badges/badges/branches.svg)

# Main page
![Screenshot_2](https://user-images.githubusercontent.com/54511054/209580686-ec7d44bf-0999-4110-8b53-334498d5c763.png)

Spring-React project with implementation of both OAuth2 auth and basic auth with email. Uses JWT tokens to authorize users

### Technologies used
* Backend
    * Java 21, Spring Boot 3, Maven
    * MySql 
    * Spring Security 6
* Frontend
    * React 18 (TypeScript based)
    * Nginx
    * Redux Toolkit
    * Chakra UI
* CI/CD
    * Jenkins
    * Docker, docker-compose

### Before you start
In order to use authorization through Google or GitHub, you should register your application in these services

#### Google
1. Visit https://console.cloud.google.com/apis/credentials
2. Click "Create Credentials" -> OAuth client ID -> Application Type (Web application).
3. In "Authorized redirect URIs" enter `http://localhost:8080/login/oauth2/code/google`
4. Click "Create" and copy Client ID and Client Secret in `application.yml` or env variables

#### GitHub
1. Visit https://github.com/settings/applications/new
2. In "Homepage URL" enter `http://localhost:8080`
3. In "Authorization callback URL" enter `http://localhost:8080/login/oauth2/code/github`
4. Click "Register application", generate Client Secret and copy it with Client ID in `application.yml` or env variables

Using env variables is preferred way, after setting don't forget to restart IDEA/cmd.

If you paste variables in `application.yml`, docker-compose will fail 
because it overrides properties in `application.yml`. 
To solve this you can delete `environment:` section in `docker-compose.yml` 
or run spring and react in a basic way (e.g. with IntelliJ IDEA). 

## How to run
After setting-up Google and GitHub registries, you can run app using docker-compose
or each service separately:

### Run each service separately
1. Run MySql server manually (password=root;db_name=oauth2_db) or with command `docker-compose up -d mysql`
2. Run Spring app
    * IntelliJ IDEA. In file `spring-oauth2/src/main/java/com/vsnitko/oauth2/Oauth2Application.java` 
    click green run button on Oauth2Application class
    * cmd. From `spring-oauth2` run `mvn spring-boot:run`    
3. Run React app 
   * IntelliJ IDEA. In file `react-oauth2/package.json` click green button in "scripts"."start"
   * cmd. From `react-oauth2` run `npm install` and `npm start`

### Run with docker-compose:
1. Make sure that env variables are set e.g. with `echo %GOOGLE_CLIENT_ID%` or `echo $Env:GOOGLE_CLIENT_ID`. 
Or delete `environment:` section in `docker-compose.yml`
2. You can run commands below after `ctrl` double click in IDEA or in corresponding folders in cmd
3. Build react app with `npm install` and `npm run build:local`
4. Build spring app with `mvn package -DskipTests`
5. Run `docker-compose up -d`

### Known issues / Points to improve / TODO
* Do global update for chatting
* Consider building frontend in webpack
* Add Jenkins deployment guide to README
* Refresh jwt token is not supported
* Api for default avatar outdated
