![Passing](../../actions/workflows/on_master.yml/badge.svg) 
![Coverage](../badges/badges/jacoco.svg) 
![Branches](../badges/badges/branches.svg)

# Main page
![Screenshot_2](https://user-images.githubusercontent.com/54511054/209580686-ec7d44bf-0999-4110-8b53-334498d5c763.png)

Spring-React project with implementation of both OAuth2 auth and basic auth with email. Uses JWT tokens to authorize users

### Technologies used
* Backend
    * Java 20, Spring Boot 3
    * MySql database
    * Spring Security 6
* Frontend
    * React 18 (TypeScript based)
    * Redux Toolkit
    * Chakra UI

### Before you start
In order to use authorization through Google or GitHub, you should register your application in these services

Create `application-prod.yml` file in `spring-oauth2/src/main/resources` folder with this content:
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: #paste client id here
            client-secret: #paste client secret here
          github:
            client-id: #paste client id here
            client-secret: #paste client secret here
```

#### Google
1. Visit https://console.cloud.google.com/apis/credentials
2. Click "Create Credentials" -> OAuth client ID -> Application Type (Web application).
3. In "Authorized redirect URIs" enter `http://localhost:8080/login/oauth2/code/google`
4. Click "Create" and copy Client ID and Client Secret in `application-prod.yml`

#### GitHub
1. Visit https://github.com/settings/applications/new
2. In "Homepage URL" enter `http://localhost:8080`
3. In "Authorization callback URL" enter `http://localhost:8080/login/oauth2/code/github`
4. Click "Register application", generate Client Secret and copy it with Client ID in `application-prod.yml`

## How to run
After setting-up Google and GitHub registries, you can run database, spring and react with docker-compose:

### Run with Docker:
```console
docker-compose up
```
After this Spring Boot will run on port 8080, React on port 3000

### Run without Docker:
1. Run MySql manually (password=root;db_name=oauth2_db) or with command
   ```console
   docker run -d --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=oauth2_db mysql:latest
   ```
2. Run Spring Boot (server will run on port 8080). From `/spring-oauth2` call command:
   ```console
   mvn spring-boot:run -Dspring-boot.run.profiles=prod
   ```

3. Run React (server will run on port 3000). From `/react-oauth2` call command:
   ```console
   npm install
   npm start
   ```

### Notes
* It doesn't matter weather you Sign In or Sign Up if you use OAuth2 providers. 
User will be created if you didn't signed-up it previously, or you will be signed-in if you did
* If different providers provide the same email, it will be qualified as the same user in this app

### Known issues / Points to improve
* Refresh token is not supported
* Not possible to update user avatar
* Not possible to verify email for basic auth


