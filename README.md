# OpenMindValley

This is the test android project for MindValley. It contains latest architectural design patterns as well as industry level new technologies stated below:
- MVVM as architectural design pattern (data-domain layer)
- Hilt for DI
- Kotlin Coroutine
- Unit test using Mockito
- Jetpack Compose for UI

## What part of the test was most challenging?
Generating test cases for viewmodel <br>
### Explanation 
Testing the components of viewmodel i.e. repository, use cases etc was easier but putting it all together and mocking data for successful test cases was a bit challenging

## Feature development plan
- Creating a 'view all' page with a grid or list layout
- Category filter should be functional
- Search functionality
- Real time internet connectivity observer
- Architectural improvement aand refactoring like introducing BaseActivity, BaseFragment etc
- Separate error view for sepate segment of the UI (as per api data)
- Introducing UI testing for Compose
