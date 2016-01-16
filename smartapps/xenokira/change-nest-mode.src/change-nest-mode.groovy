/**
 *  Change Nest Mode
 *
 *  Author: brian@bevey.org
 *  Date: 5/5/14
 *
 *  Simply marks any thermostat "away" if able (primarily focused on the Nest
 *  thermostat).  This is intended to be used with an "Away" or "Home" mode.
 * 
 *  Change log:
 *  2016.01.15
 *      * Manually moved to personal repository for better integration with SmartThings.
 *  
 */

definition(
    name:        "Change Nest Mode",
    namespace:   "xenokira",
    author:      "nathaniel.pitts@gmail.com",
    description: "Simply marks any thermostat 'away' if able (primarily focused on the Nest thermostat).  This is intended to be used with an 'Away' or 'Home' mode.",
    category:    "Green Living",
    iconUrl:     "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url:   "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience%402x.png"
)

preferences {
  section() {
    input "awayMode", "mode", title: "If SmartThings changes to", required: true
    input "thermostats", "capability.thermostat", title: "then set these themostats to Away", multiple: true
  }
}

def installed() {
  subscribe(location, changeMode)
  subscribe(app, changeMode)
}

def updated() {
  unsubscribe()
  subscribe(location, changeMode)
  subscribe(app, changeMode)
}

def changeMode(evt) {
  def curMode = location.currentMode
  if(curMode.name == "awayMode") {
    log.debug("Current mode is ${curMode.name}")
    log.info("Marking Away")
    thermostats?.away()
  }

  else {
    log.info("Marking Present")
    log.debug("Current mode is ${curMode.name}")
    thermostats?.present()
  }
}