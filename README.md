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
. * Run mariokart-desktop.jar and install mariokart-android.apk in a android device

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
Used in to separate the game's graphics, model and logic (controller) and to reduxe dependency between these 3 components (GameModel, GameController and RaceController and 
To be used to separate the game's graphics, model and logic and to reduce dependency between these 3 components (GameModel, GameController, GameView) 
* #### Singleton 
To be used in several different classes to ensure that they only have one instance and to provide a global and easy point of access to each one of them (GameModel, GameController, GameView)
* #### FlyWeight

* #### State
The server side (desktop) and the client side (android) both have their own state that changes based on events. This allows the GameController to change its behaviour based on the state
<br>

### Dificulties
----


### Time spent developing
----
Both students put in an equal amount of effort into the development of this project.

### User manual
----

<br>
