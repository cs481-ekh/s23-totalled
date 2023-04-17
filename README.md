# Totalled Senior Design Project
[![CI](https://github.com/cs481-ekh/s23-totalled/actions/workflows/ci.yml/badge.svg)](https://github.com/cs481-ekh/s23-totalled/actions/workflows/ci.yml)
---

## Team: [Totalled](https://cs481-ekh.github.io/s23-totalled/)
### Bree Latimer, Chase Stauts, D Metz, Paul Ellis
## Sponsors
### Boise State University Mechanical and Biomedical Engineering Department

---

## Description

This is a project that is intended to take in Excel Workbooks of Spending and Team data for a Senior Design
Project and analyze, group and create team based Expense Breakdown Excel Workbooks for the Department to use
in creating Invoices for the sponsors of projects. We implemented our solution using Kotlin and the Gradle
build system managing our dependencies. This document will describe steps for building, testing, and deploying
the final solution.

--- 

## Building and running
### Just building
If you only want to build the project you can use the provided script
```console
./build.sh
```
Or you can run:
```console
./gradlew build
```

### Building a distributable locally
Using the following command to build without creating an installer:
```console
./gradlew createDistributable
```

To create a msi for installing on windows:
```console
./gradlew packageMsi
```

### Running
To build and then run the program use:
```console
./gradlew run
```

## Testing

In order to test the program you can run the following terminal command from within the project directory:

```console
./test.sh
```

If this command fails you can try the following terminal commands instead:

```console
./gradlew test
```

## Clean up

You can clean up all build artifacts by running the following command:

```console
./gradlew clean
```

---

## Deployment

### Building and Deploying

In order to deploy the program to the end users you will need to follow the steps:

1. Go to the GitHub Actions tab.
2. Navigate to the Deploy action on the left hand side of the page.
3. Click the "Run workflow" button.
4. Select the branch you want to deploy from the drop down menu. (It's okay to select the default settings when running the workflow.)
5. Click the "Run workflow" button when the settings are correct.
6. Wait for the workflow to complete.
7. Select the deploy that you would like to publish, most likely the one with the most recent timestamp.
8. Send the link for the deploy to the Dr. Henderson. He will upload the deploy to the CS481 website.
