# tdc-clj

Web server streaming fake football events on a web socket.

The repo contains: 
* An HTTP kit server pushing out messages on a web socket
* A simple web client listing last 20 messages from server

## Server

* Install https://github.com/technomancy/leiningen
* Open command shell
* Run the command ```lein repl```
* Once inside the REPL, run ```(start!)```

At that point your server runs at http://localhost:8080/socket

Type ```(restart!)``` to reload code and restart server. 

Type ```(stop!)``` to stop it.

## Client

* Open up a second command shell, type ```lein figwheel```

Your client is now running and can be reached at <http://localhost:3449/client>

## Runtime tweaking

When the server starts, a loop is also started with random events sent every second. This loop can be stopped with `(stop-random-forever)` and restarted again with `(start-random-forever)`

To run a predefined sequence of events, run `(run-sequence)`
