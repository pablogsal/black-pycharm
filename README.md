# black-pycharm

## General usage instructions
* To use this plugin, [Black](https://black.readthedocs.io/en/stable/) must be installed.
* By default, the plugin assumes that the Black binary is `/usr/local/bin/black`. 

## Development instructions
* Open the repository root folder with IntelliJ IDEA.
* You can immediately _run_ and _debug_ the "Plugin" configuration.
* In the Gradle window, under Tasks->intellij, you can _buildPlugin_, which will generate a plugin .zip file under build/distributions.
* Building and running the plugin are sensitive to the `intellij {}` settings in settings.gradle.  If you get errors building or deploying the plugin, try changing the `version ''` to your version of IDEA.  