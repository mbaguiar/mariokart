# Final Project - Mario Kart
<br>

### Team Members
Mariana Barbosa Aguiar
* Student Number: 201605904
* E-Mail: up201605904@fe.up.pt

Tiago José Viana Fragoso
* Student Number: 201606040
* E-Mail: up201606040@fe.up.pt
<br><br>

### Project Setup
----
* Run mariokart-desktop.jar and install mariokart-android.apk in a android device

### [Documentation](https://mbaguiar.github.io/mariokart)<br>

### Class Diagram
----
#### mk-desktop Class Diagram
![Class diagram](https://github.com/mbaguiar/mariokart/blob/master/Screenshots/mk-desktop_uml.png)
<br> <br>

#### mk-android Class Diagram
![Class diagram](https://github.com/mbaguiar/mariokart/blob/master/Screenshots/mk-android_uml.png)
<br><br>

### Design Patterns
----
* #### Model View Controller (MVC) 
Used in to separate the game's graphics, model and logic (controller) and to reduxe dependency between these 3 components (GameModel, GameController and RaceController and all the view classes, CharacterPickerView, InstructionsView, LobbyView, MenyView, RaceView)

* #### Singleton 
Used in several different classes to ensure that they only have one instance and to provide a global and esay point of access to each one of them (GameModel, GameController, MarioKart)

* #### FlyWeight
Used to share the same image for various diferent objects of the same class. Uses the same EntityView to draw diferent EntityModels of the same subclass (RaceView)

* #### State
The server side (desktop) and the client side (android) both have their own state that changes based on events. This allows the GameController to change its behaviour based on the state.
The class KartBody also has its own inner state from which it changes its movement, accelerate, break and stee, this state is changed based on the messages received from the clients.

* #### Update Method
The class KartBody has an update method that simulates one frame of this body behaviour. The RaceController updates in each frame every KartBody.
<br>

### Dificulties
----


### Time spent developing
----
Both students put in an equal amount of effort into the development of this project.

### User manual
----

<br>
