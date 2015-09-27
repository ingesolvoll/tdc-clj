# tdc-clj

Web server streaming fake football events on a web socket.

To build and run

* Install https://github.com/technomancy/leiningen
* Open command shell
* Run the command ```lein repl```
* Once inside the REPL, run ```(def app (start!))```

At that point, your app resides in the app variable. 

Type ```(restart! app)``` to reload code and restart server. 

Type ```(stop! app)``` to stop it.
