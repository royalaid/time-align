# time-align

Life is about how time is spent. Use this tool to create a goal for how to spend time, and record how time is actually spent. Work towards aligning those two tracks.  

generated using Luminus version "2.9.11.46"

## Prerequisites
- [Vagrant][1]
- [VirtualBox][2]

[1]: https://www.vagrantup.com/
[2]: https://www.virtualbox.org/wiki/VirtualBox

## Running

- clone repo
- run `vagrant up`
- run `bash start.sh` (will open an ssh connection to vm)
- run `./cider-deps-repl` (in vm, will start an nrepl)
- connect to nrepl using cider at port 7000
- run `(start)` (launches the server)
- run `(start-fw)` (transpiles cljs and starts figwheel server)

## checklist for Proof Of Concept
- [ ] full crud interface for time-align
  - [x] structure in db.clj
  - [ ] periods
    - [ ] create new
    - [ ] read
      - [x] planned/actual wheel display
      - [ ] queue (any without start/stop)
    - [x] update
      - [ ] slidding
      - [ ] stretching/shrinking
    - [ ] delete
  - [ ] categories
    - [ ] create 
      - [ ] name
      - [ ] color
      - [ ] priority?
    - [ ] read 
    - [ ] update
    - [ ] delete
  - [ ] tasks
    - [ ] create
      - [ ] assign category (color + name)
      - [ ] meta data (name, desc, completed, dependencies)
      - [ ] priority
    - [ ] read
      - [ ] task list 
        - [ ] one list
        - [ ] sorting
          - [ ] date created
          - [ ] last modified
          - [ ] number of periods
          - [ ] category
          - [ ] priority
    - [ ] update
      - [ ] edit all data (can't delete unless no periods)
    - [ ] delete

## misc
### css solution garden
https://blog.estimate-work.com/a-new-world-writing-css-in-clojurescript-and-life-after-sass-bdf5bc80a24f

## License
???

## work space
The action section is getting tricky. I've figured out how to transition between sets of actions depending on app state. Basically I have a list of possible states in the app-db, and whenever a back action or button that leads to a new state is triggered one of two handlers figures out what the next state is. Keeps all the logic out of the views and the app-db is still clean.  

The only problem is that multiple floating action buttons look like bad. How do I put together up to 6 actions in a reachable space on mobile? There doesn't seem to be a readily accessible solution in MUI.

## long term cleanup
- all handlers have non-anon functions
- all subscriptions have non-anon functions
- custom svg defn's have name format
  - svg-(mui-)-[icon-name]
- enforced rule for subs/handlers
  - only give back individual values or lists never chunks of structure?
- all subscriptions in render code at top most levels and fed down --- Maybe not...
  - will make testing easier
  - seems too _complex_ to have individual components subscribe to things
  - then would too many components be unessentially injecting subs they dont' care about for their children?
