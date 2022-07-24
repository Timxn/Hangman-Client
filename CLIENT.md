# Client Documentation

The Client communicates with sockets with the server. This allows needs a continous connection to the server. It is currently implemented to run on the same machine as the server.
Every Client gets a unique "ClientThread" on the server, wich converts the as json send "command + payload" and executes it. It either responds with a "successful" message (including the response) or an exceptonMessage.


Client:Socket -> calls:8001 -> ServerSocket:Server;
Server opens a unique ClientThread;
Server:ServerSocket -> responds:8001 with portOfUniqueClientThread -> Client:Socket


all communication afterwards follows this pattern:
Client:Socket <-> communication:portOfUniqueClientThread <-> Server:UniqueClientThread

## Errorhandling

Errorhandling via display of errorMessages (no exit from program if an error persists -> requires user intervention)

---

| ClientSide | JSONCommand |
| ------ | ------ |
| updateLobby() | updateWaiting |
| updateGame() | updateGame |

the updateFunctions get the data that needs to be continously polled for the corresponding screen

---

| ClientSide | JSONCommand |
| ------ |
| createGame() | createRoom |
| joinGame() | joinRoom |
| startGame() | startGame |
| isGod() | isGod |
| setWord() | setWord |
| isStarted() | isStarted |
| hasWord() | hasWord |
| guessLetter() | guessLetter |
| getWinner() | getWinner |
| leaveGame() | quitGame |
| getScoreboard() | getScoreboard |
| getGameID() | getGameID |
| unused (solved by local caching in client of the GameID) |
| ------ |
| close() | close |
| closes the connection between the server and the client in a safe manner, no communication is possible after successful running this command |

---

## used packages (API)
- org.json.*
- java.lang.*
- java.io.*
- java.net.*
- java.util.*


## usesd packages (GUI + Logic)
- javafx.*
- java.lang.*
- java.io.*
- java.util.*
- java.net.*


## usesd packages (Utils)
- java.net.*
- java.lang.*
- java.io.*
- java.util.*
- javafx.*
