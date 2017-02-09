# TODO App

TODO-List Sample application demonstrating proper archtecture with the following
aspects:

* Model / View / Presenter
* Reactive Functional Programming
* Kotlin
* High-Coverage Unit Testing
* Dependency Injection
* Singleton-Proxy Pattern (to ease unit testing, no PowerMock)

## Packages

* Feature Driven -- Each logical component should be within it's own package
* Flatter is Cleaner -- Keep to a minimum of subpackages to keep code clean

```
Example Screen Feature:

- com.exallium.todoapp.myscreen
- - MyScreenComponent.kt    -- Dagger Component Class
- - MyScreenController.kt   -- View / Controller Implementation
- - MyScreenModel.kt        -- Model Interface
- - MyScreenModelImpl.kt    -- Model Implementation
- - MyScreenModule.kt       -- Dagger Module
- - MyScreenView.kt         -- View Interface
- - MyScreenPresenter.kt    -- Presentation Logic Class
```

## MVP

* View -- A conductor Controller instance (Interfaced) Responsible for all
  direct communication with Android views
* Presenter -- An object which is given a reference to a View Interface and a
  reference to a Model Interface.  Responsible for all presentation logic.
* Model -- An Interactor object which is acted upon by the presenter, and
  communicates with the rest of the system.

## Reactive

* Views expose events, like clicks and other such events as observables.  Views
  also expose simple getters and setters for current view-state.
* Models will communicate via observables / singles, as these often require
  background processing, unless it makes sense not to do so.

## Conductor

* The application is a single-activity, custom-view architecture.  Conductor
  makes this very easy to implement.

## Dagger

* The "factory" for the application is a DAG.  All components should be
  generatable via this graph, and care should be taken to reduce creation of
logical objects within other logical objects as much as possible.  This eases
testing.
* Every object, with the exception of System-Created objects, should be
  generated via dagger with construction time injection.

## Butterknife

```
// Usage:
@BindView(R.id.view)
lateinit var view
```
## Unit Tests

This project attempts to maintain as high code coverage as possible.

Testing from the command line can be done like so:

```./gradlew testDebug```

Jacoco is used to generate code coverage reports for debug builds. To get code coverage on a specific build variant:

```
./gradlew jacoco<build-type>TestReport
./gradlew jacocoDebugTestReport # code coverage for debug builds
```

Mockito 2 is utilized to allow proper mocking of final methods and classes, which allows us to avoid PowerMock.

## Contributing

We also provide a githook that prefixes each commit to feature/ or bugfix/ with the appropriate issue id taken from the current branch name. 
Copy the *config/prepare-commit-msg* to *.git/hooks/* folder to take effect.
