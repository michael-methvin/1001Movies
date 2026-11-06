Next session: finish the walking skeleton

 Confirm docker --version and docker compose version work in PowerShell
 Write docker-compose.yml at the repo root
 Fill in datasource and jpa in application.yml
 Reload Gradle, create db/migration/, boot the app, and read the logs for the connection
 Root .gitignore and first commit (if not done yet), then commit the skeleton

Backend foundations

 Add a trivial ping endpoint with a test
 Design the tables (movies, users, ratings, watch history) and write the first Flyway migration
 Entities and repositories, with a few derived queries typed from memory
 Seed the movie list through a migration
 Daily pick logic (no repeats per user)
 Validation and a global exception handler
 Ratings and stats endpoints
 Auth

Front end

 Create the Vite React TypeScript app in frontend/
 Build the daily movie page and rating component against mocked data
 History and stats pages
 Connect to the real API

Later

 TMDB integration with caching
 GitHub Actions CI
 README with design decisions
 Deploy with a hosted Postgre