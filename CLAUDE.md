# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Development

### Prerequisites
- Java 11+ (or version specified in project)
- Maven 3.6+ or Gradle 7+ (depending on build tool)

### Common Commands
(Update based on your build tool - Maven or Gradle)

**Maven:**
```bash
mvn clean install              # Build project
mvn test                       # Run all tests
mvn test -Dtest=ClassName     # Run single test class
mvn test -Dtest=ClassName#methodName  # Run single test method
mvn spotbugs:check            # Static analysis (if configured)
mvn pmd:check                 # Code quality checks (if configured)
```

**Gradle:**
```bash
./gradlew build               # Build project
./gradlew test                # Run all tests
./gradlew test --tests ClassName  # Run single test class
./gradlew spotbugsMain        # Static analysis (if configured)
```

### IDE Setup
- IntelliJ IDEA: Import as Maven/Gradle project (auto-detects pom.xml or build.gradle)
- VS Code: Install Extension Pack for Java, Gradle for Java/Maven for Java extensions

### Application Running
(Update once you determine if this is a CLI app, web service, or library)

## Project Architecture

### Package Structure
- `src/main/java/` - Source code
- `src/test/java/` - Test code
- `src/main/resources/` - Configuration files, properties

### Key Components
(Document main modules/packages and their responsibilities once structure is defined)

### Dependencies
(Maven: See pom.xml / Gradle: See build.gradle for dependency management)

## Testing

### Test Organization
- Unit tests: Test individual components in isolation
- Integration tests: (Describe if used)
- Test naming: Follow `Test` or `Tests` suffix convention (e.g., `UserServiceTest.java`)

### Running Tests
```bash
# All tests
mvn test

# Single test class
mvn test -Dtest=UserServiceTest

# Single test method
mvn test -Dtest=UserServiceTest#testUserCreation

# With coverage (if jacoco configured)
mvn jacoco:report
```

## Code Style & Quality

### Formatting
(Document if you use spotless, google-java-format, or similar)

### Code Standards
- Follow Java conventions (camelCase for methods/variables, PascalCase for classes)
- Keep methods focused and testable
- Use meaningful variable and method names

### Static Analysis
(Configure and document tools used: SpotBugs, PMD, Checkstyle, etc.)

## Common Development Tasks

### Adding a New Class
1. Create in appropriate package under `src/main/java/`
2. Follow existing package structure patterns
3. Write tests in corresponding location under `src/test/java/`

### Adding Dependencies
- Maven: Add to `<dependencies>` in `pom.xml`
- Gradle: Add to `dependencies` block in `build.gradle`

### Running a Specific Module
(Update once module structure is defined - if multi-module project)

## Debugging

### Debug Mode
(Document how to run in debug mode with your IDE/build tool)

### Common Issues
(Add troubleshooting as patterns emerge)

## Version Control

### Branch Strategy
- `main` - stable, tested code
- Feature branches: Create from main for new features

### Commit Messages
- Use descriptive, present-tense messages
- Example: "Add user authentication service" instead of "Added auth"

## Notes for Future Work

### Known Limitations or TODOs
(Document any known issues or architectural debt)

### Performance Considerations
(Add as relevant to the project)

---

**Last Updated:** 2026-09-09
**Created for:** Fundación SPLAI Bootcamp Java Project
