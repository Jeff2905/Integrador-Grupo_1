# Upgrade Plan: odontologia-backend (20260915025439)

- **Generated**: 2026-09-14
- **HEAD Branch**: N/A
- **HEAD Commit ID**: N/A

## Available Tools

**JDKs**
- JDK 17.0.12: `C:\Program Files\Java\jdk-17\bin` (base JDK, used for baseline)
- JDK 25: **<TO_BE_INSTALLED>** (required by upgrade and final validation)

**Build Tools**
- Maven Wrapper: 3.9.16 distribution via `backend/.mvn/wrapper/maven-wrapper.properties`
- Global Maven: not available; wrapper will be used

Version control is unavailable because the workspace is not a Git repository; changes will remain uncommitted.

## Guidelines

> Note: You can add any specific guidelines or constraints for the upgrade process here if needed, bullet points are preferred.

## Options

- Working branch: N/A (version control unavailable)
- Run tests before and after the upgrade: true

## Upgrade Goals

- Java runtime and compilation target: 25 (latest LTS)

## Technology Stack

| Technology/Dependency | Current | Min Compatible Version | Why Incompatible |
| -------------------- | ------- | ---------------------- | ---------------- |
| Java | 17 | 25 | User requested Java 25 |
| Spring Boot | 4.1.1 | 4.1.1 | No change requested; compatible with Java 25 |
| Maven Wrapper | 3.9.16 distribution | 3.9.6+ | Already compatible with Java 25 |
| Spring Boot Maven Plugin | 4.1.1 managed | 4.1.1 | Managed by Spring Boot parent |
| MySQL Connector/J | Spring Boot managed | compatible managed version | No explicit pin; retain BOM management |

## Derived Upgrades

- Set `java.version` to `25` so Spring Boot's compiler configuration targets Java 25.
- Retain Spring Boot 4.1.1 and its dependency management because it is outside the requested upgrade scope and no incompatible source/configuration patterns were found.
- Use Maven Wrapper 3.9.16; no build-tool upgrade is needed.
- Install JDK 25 because it is required for compilation and runtime validation.

## Impact Analysis

### Dependency Changes

| File | Dependency | Current | Action | Target | Reason |
|------|------------|---------|--------|--------|--------|
| `backend/pom.xml` | `java.version` | 17 | upgrade | 25 | Sets the project compiler/runtime target to Java 25 |
| `backend/pom.xml` | Spring Boot parent | 4.1.1 | retain | 4.1.1 | Already compatible and not requested for change |

### Source Code Changes

| File | Location | Current | Required Change | Reason |
|------|----------|---------|-----------------|--------|
| None | N/A | No Java 17-specific APIs, internal JDK imports, or `javax.*` references found | None | Java source is compatible with the target based on the compatibility scan |

### Configuration Changes

| File | Property/Setting | Current | Required Change | Reason |
|------|------------------|---------|-----------------|--------|
| None | N/A | No Java-version-specific application properties found | None | Runtime configuration is unaffected |

### CI/CD Changes

| File | Location | Current | Required Change |
|------|----------|---------|-----------------|
| None found | N/A | No CI/CD files detected in the project scope | None |

### Risks & Warnings

- **JDK 25 availability**: JDK 25 is not currently installed. **Mitigation**: Install Microsoft/Eclipse Adoptium JDK 25 before changing the project target and use it for final validation.
- **Runtime compatibility beyond tests**: The current test suite is minimal. **Mitigation**: Run clean compilation and all tests on JDK 25; report residual runtime risk if coverage does not exercise application startup.
- **No version control**: The workspace is not a Git repository. **Mitigation**: Preserve focused edits and record all verification results in `progress.md` and `summary.md`.

## Upgrade Steps

- Step 1: Setup Environment
  - **Rationale**: Install the required JDK 25 and confirm the Maven Wrapper is available.
  - **Changes to Make**: Install JDK 25; use the existing Maven Wrapper 3.9.16.
  - **Verification**: List JDKs and invoke the wrapper version check; expected JDK 25 available and Maven 3.9.16 usable.

- Step 2: Setup Baseline
  - **Rationale**: Establish compilation and test results on the current Java 17 runtime before changing the target.
  - **Changes to Make**: None.
  - **Verification**: `mvnw.cmd clean compile test-compile -q` and `mvnw.cmd clean test -q` with JDK 17; expected baseline success.

- Step 3: Upgrade Java Target
  - **Rationale**: Apply the requested Java LTS upgrade while preserving the existing Spring Boot stack and source behavior.
  - **Changes to Make**: Apply the Dependency Changes table entry in `backend/pom.xml`.
  - **Verification**: `mvnw.cmd clean test-compile -q` with JDK 25; expected main and test compilation success.

- Step 4: CVE Validation
  - **Rationale**: Confirm that the resulting direct dependency set has no known vulnerabilities introduced or exposed by the upgrade.
  - **Changes to Make**: Scan resolved direct dependencies; upgrade only vulnerable explicit versions if reported, then recompile and rescan.
  - **Verification**: Dependency CVE scan and `mvnw.cmd clean test-compile -q`; expected no unresolved fixable CVEs.

- Step 5: Final Validation
  - **Rationale**: Verify the complete Java 25 upgrade against the success criteria.
  - **Changes to Make**: Resolve any compilation/test failures discovered during validation; no deferred TODOs.
  - **Verification**: `mvnw.cmd clean test -q` with JDK 25; expected 100% test pass rate and all target versions met.
