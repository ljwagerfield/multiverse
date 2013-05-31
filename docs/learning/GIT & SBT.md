# SBT

## Adding library dependencies
These are added to build.sbt. These are not added to plugins.sbt as this is purely the build project, not *your* actual project.

The following commands must be issued to SBT once the dependency has been added:
- reload (Causes SBT to re-read the *.sbt files.)
- update (Downloads new dependencies.)
- gen-idea (Updates IDEA modules, providing references to the new dependencies.)

# GIT

## Getting latest

- git fetch origin (Retrieves data from all branches from the specified alias.)
- git merge origin/master (Merges the specified branch into the current one. Fetched branches have their names mapped in the style alias/branch)

## Adding/deleting files

- git add . (Stages all modified or new files which are not ignored. Modified & new, not deleted.)
- git add -u (Stages all modified or deleted files. Modified & deleted, not new.)
- git add -A (Stages all modified, new and deleted files.)
