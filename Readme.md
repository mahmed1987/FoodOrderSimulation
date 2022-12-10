# Food ordering simulation

###### A demo showcasing how to use compose in an MVI styled application

This application showcases how the model view intent design paradigm works with compose as the UI
framework of choice. We will start off by defining as simply as possible , the basic dogma behind
MVI

## MVI

MVI is an abbreviation for Model-View-Intent , a design paradigm in which the system is broken down
into the three aforementioned parts. Each part has a specific responsibility as detailed below

###### Model

Contains the state of the system/screen/ui element. In the context of this application the model
would be [`OrderUiState`][OrderUiState]

###### View

The model above drives two views namely ,  [`OrderScreen.kt`][OrdersUi]
and [`OrderDetailsScreen.kt`][OrderDetailsUi]

###### Intentions

The follow intentions mutate the state of the view represented by [`OrderIntention`][OrderUiState]

- AddOrderToQueue (Adds the order to the order queue)
- AdvanceStatus (Advances the status of the order ,for example from New -> Preparing)
- RemovedDeliveredOrder (Removes the order from the queue after 15 seconds have elapsed)

## How are intentions turned into State?

The convention is to use a function called "reduce", that takes an old state , and the new intention
, and mutates the state. Take a look at the reduce function in the ['OrdersViewModel][OrderVm]

[OrderUiState]: app/src/main/java/com/multibank/foodordersimulation/ui/screens/OrdersViewModel.kt

[OrderVm]: app/src/main/java/com/multibank/foodordersimulation/ui/screens/OrdersViewModel.kt

[OrdersUi]: app/src/main/java/com/multibank/foodordersimulation/ui/screens/OrderScreen.kt

[OrderDetailsUi]: app/src/main/java/com/multibank/foodordersimulation/ui/screens/OrderDetailsScreen.kt