# Diffblue DCover CLI Test

- Original repository: https://github.com/rieckpil/testing-spring-boot-applications-masterclass

## Notes


- Experiments with `Diffblue Cover 2022.07.02-ecf5b52`
- Environment:
  - Apple Mac Book Pro 2021 with M1 16 GB RAM
  - Java 17 (Temurin)
  - Apache Maven 3.8.5

- issues with jEnv:

```
16:19:46.958 INFO  Maven Using ${maven.home} of: '/Users/rieckpil/.jenv'.
16:19:46.958 ERROR Maven invocation failed. Exit code: 1. Standard output: . Standard error: []
```

- Duration

```text
dcover create de.rieckpil.courses.book.management.Book --testing-framework=junit-5
```

Thought this would only generate for one class but it did detect other in the folder, but not all.

Timing: Start `16:21:06.156` End ``


Confusing docu https://docs.diffblue.com/getting-started/cli/cover-cli -> states that it will create for owner class, but assume that's the package.

- Maybe mention in the docs to add `.diffblue` folder to `.gitignore`
- Maybe add the CLI Manual to the quick start guide: https://docs.diffblue.com/knowledge-base/cli/cli-manual/
- Second test for the entire project

Start: 16:36:26.885
```

dcover create de.rieckpil.courses --testing-framework=junit-5

Maven Command Executing: /bin/sh -c cd '/Users/rieckpil/Development/git/diffblue-dcover-test' && '/opt/homebrew/Cellar/maven/3.8.5/bin/mvn' '-B' '-s' '/Users/rieckpil/.m2/settings.xml' '-D' 'test=DiffblueCover_JUnitLauncherDetection_DiffblueTest' 'test'
16:37:13.536 WARN  E011: Problems in the local build environment have been detected that will prevent Diffblue Cover from validating the written tests.

16:37:15.727 INFO  Gathering methods to test...
16:37:20.470 INFO  Found 205 callable methods in 27 classes

Hanging 10 min for 

16:38:25.105 INFO   [4/27] de.rieckpil.courses.book.management.BookManagementService
16:38:25.106 INFO   [4/27]   Tests created: 1

Stopped after 20 minutes, only mkaing 3% progress
```

- What's E011 where to lookup? Onside search https://docs.diffblue.com/knowledge-base/cli/cli-manual/ didn't help


Next try:

```
dcover create de.rieckpil.courses.book.review --testing-framework=junit-5 --skip-test-validation

17:22:30.035 INFO  Summary:
17:22:30.035 INFO  --------
17:22:30.035 INFO  Total:                                              75 methods
17:22:30.035 INFO  31 tests created for:                               36 methods
17:22:30.035 INFO
17:22:30.035 INFO  Tested indirectly via other methods:                 3 methods
17:22:30.035 INFO      3 T005: Trivial constructor
17:22:30.035 INFO  No tests created for:                               36 methods
17:22:30.035 INFO     21 R025: Skipped due to previous failure
17:22:30.035 INFO     14 R026: Failed to create Spring context
17:22:30.035 INFO      1 R024: Analysis service shutdown unexpectedly

```