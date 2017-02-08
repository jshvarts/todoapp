# TODO App

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
