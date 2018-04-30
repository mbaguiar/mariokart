# Final Project Specification - Mario Kart
<br>

### Package and Class Diagram
----
#### Package Diagram
![Package diagram](https://i.imgur.com/SNpnjDg.png)
<br>

#### mk-desktop Package Class Diagram
![Class diagram](https://i.imgur.com/iwQZfix.png)
<br> <br>
*Mario Kart* - Represents the game <br>
*GameModel* - Stores all the data about the game and the itens of the game (EntityModel) <br>
*EntityModel* - Represents an item of the game and stores all its data and information <br>
*Player* - Represents a player in the game, it has a KartModel associated and a Race <br>
*Race* - Represents a race in the game, it has various Players associated and a TrackModel <br>
*GameView* - Represents the visualization of the game based on the data stored in the GameModel <br>
*EntityView* - Represents the view of the itens of the game <br>
*GameController* - Updates the data stored in the GameModel based on any user inputs or Messages received and updates the view when any data is changed <br>
*EntityBody* - Represents the Body of an item in the game and is used to update its position using the Physics engine in libgdx <br>
*SeverManager* - Creates the server socket and creates a new Thread(ClientManager) for every new connection <br>
*Message* - Shared between host and client to facilitate communication. Enables the use of serialization <br>
*ClientManager* - Represents a connected client and handles communication (reading and writing) to the client <br> <br>

#### mk-android Package Class Diagram
![Class diagram](https://i.imgur.com/TBJWkuZ.png)
<br><br>
*Mario Kart* - Represents the game <br>
*GameModel* - Stores all the data about the game and its itens <br>
*GameView* - Represents the visualization of the game based on the data stored in the GameModel <br>
*GameController* - Updates the data stored in the GameModel based on any user inputs or Messages received and updates the view when any data is changed <br>
*Connector* - Creates the socket connection to the server and creates a new Thread(Receiver) to handle communication from the server. It also sends Messages to the server <br>
*Receiver* - Handles Messages received from the server <br>
*Message* - Shared between host and client to facilitate communication. Enables the use of serialization <br> <br>

### Behavioural Aspects
----
![Behaviour diagram](https://i.imgur.com/tvUchia.png)
<br>

### Design Patterns
----
* #### Model View Controller (MVC) 
To be used to separate the game's graphics, model and logic and to reduce dependency between these 3 components (GameModel, GameController, GameView) 
* #### Singleton 
To be used in several different classes to ensure that they only have one instance and to provide a global and easy point of access to each one of them (GameModel, GameController, GameView)
* #### Factory Method
To be used to create EntityViews and EntityBodies for each EntityModel
* #### State
The server side (desktop) and the client side (android) both have their own state that changes based on events. This allows the GameController to change its behaviour based on the state
<br>

### Main Functionalities 
----
* 2D Multiplayer game
* Networking (2-way communication between desktop and android phone)
* Physics (using libgdx physics engine)
* Mobile (Android controller app)

<br>

### GUI Mockups
----
<p align="center">
  <img src="https://github.com/mbaguiar/mariokart/blob/master/Mockup/Main%20Screen.png"/> <br> 
  Main screen for the desktop app.
  <br><br>
  <img src="https://github.com/mbaguiar/mariokart/blob/master/Mockup/App%20-%20Main%20Screen.png"/> <br> 
  Main screen for the Android app.
  <br><br>
  <img src="https://github.com/mbaguiar/mariokart/blob/master/Mockup/Lobby.png"/> <br> 
  Lobby screen while players connect.
  <br><br>
  <img src="https://github.com/mbaguiar/mariokart/blob/master/Mockup/App%20-%20Connection.png"/> <br> 
  Mobile connection screen.
  <br><br>
  <img src="https://github.com/mbaguiar/mariokart/blob/master/Mockup/Instructions.png" /> <br> 
  Desktop instructions.
  <br><br>
  <img src="https://github.com/mbaguiar/mariokart/blob/master/Mockup/App%20-%20Instructions.png"/> <br> 
  Mobile instructions.
  <br><br>
  <img src="https://github.com/mbaguiar/mariokart/blob/master/Mockup/CharacterPicker.png" /> <br> 
  Character picking screen. Dynamic and updated as the players pick their character.
  <br><br>
  <img src="https://github.com/mbaguiar/mariokart/blob/master/Mockup/App%20-%20Character%20picker.png" /><br> 
  Character picking screen in the app. This is a by-turn operation, thus there are different possible views for this activity.
  <br><br>
  <img src="https://github.com/mbaguiar/mariokart/blob/master/Mockup/TrackPicker.png" /> <br> 
  Track picking screen.
  <br><br>
  <img src="https://github.com/mbaguiar/mariokart/blob/master/Mockup/App%20-%20Track%20picker.png" /> <br> 
  Track voting screen.
  <br><br>
  <img src="https://github.com/mbaguiar/mariokart/blob/master/Mockup/Race.png" /> <br> 
  Race view: 2d track overview, timer, number of laps and dynamic standings update.
  <br><br>
  <img src="https://github.com/mbaguiar/mariokart/blob/master/Mockup/App%20-%20Controller.png" /> <br> 
  Controller state while race is active. 2 possible control trypes (w/ or wo/Accelerometer)
  <br><br>
  <img src="https://github.com/mbaguiar/mariokart/blob/master/Mockup/Final%20result.png" /> <br> 
  Final race standings.
  <br><br>
  <img src="https://github.com/mbaguiar/mariokart/blob/master/Mockup/App%20-%20Final%20result.png" /> <br> 
  Mobile final race standings.
  <br><br>
</p> <br> <br>


### Test cases
----
* Test kart behaviour according to the implemented physics' variables
* Test kart behaviour based on the different user inputs
* Test kart behaviour based on different recieved Messages
* Test collisions between the kart and other game objects
* Test behaviour of the special items (Banana, Mushroom, Mystery Box, ...)
* Test updates on the game state based on the coccurence of different events
* Test all the buttons' behaviour (play, instructions, quit)

<br>
