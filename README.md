# tdc-clj

Web server streaming fake football events on a web socket.

## Run server

* Install https://github.com/technomancy/leiningen
* Open command shell
* Run the command ```lein repl```
* Once inside the REPL, run ```(start!)```

At that point your server runs at http://localhost:8080/socket

Type ```(restart!)``` to reload code and restart server. 

Type ```(stop!)``` to stop it.

## Run client

* Open up a second command shell, type ```lein figwheel```

Your client is now running and can be reached at http://localhost:3449/client
