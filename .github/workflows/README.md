## CI Pipeline

This project uses GitHub Actions for continuous integration.

### Triggering the Pipeline

The CI pipeline is triggered automatically on:

- Push to the `main` branch.
- Pull requests to the `main` branch.

### Steps

1. **Checkout code**: Fetches the latest code from the repository.
2. **Set up JDK 11**: Sets up Java Development Kit 11.
3. **Cache Gradle packages**: Caches Gradle dependencies to speed up builds.
4. **Build with Gradle**: Compiles the project using Gradle.
5. **Run tests with Gradle**: Executes the test suite.
6. **Archive test results**: Uploads the test results as artifacts.
7. **Archive log files**: Uploads the log files as artifacts.

### Accessing CI Results

- Go to the "Actions" tab in the repository.
- Select the workflow run to see the logs and artifacts.
- Download test results from the "Artifacts" section.
