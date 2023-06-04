# MSI/MERSI - Embedded Systems - 22/23

## Group info
Group Number: 1

Group Members:
* up201905810 - Carolina Ferreira
* up202200195 - João Amaral
* up201606796 - Tiago Eusébio
* up201904872 - Tiago André

## [Automatic Lights](src/)
* In this project light(s) in a room should be turned on when users enter the room and turned off when users exit the room.
* The system should detect a specific set of users (more than one) and determine when they enter/leave the room. A welcome message should be displayed for the specific user when (s)he enters the room.
* On the Android phone of the user, the status of the room's light should be displayed. It should be possible to turn it on/off from the phone. However, the light should not be turned off if a known (in the list) user is in the room. The name of the user (which the system uses for the welcome message) should be defined in the Android phone.
Lights should not be turned on if luminosity on room is above a defined threshold.

* Requirements:
  * turning on light should be after opening door. Vice-versa for off and door close;
  * lights on could be before entering the room;
  * lights off should be after leaving the room;
  * user detection should be under 5 sec;
  * remotely turning on/off lights should be under 4 sec since button pressed;
  * information on phone regarding status of light should be under 4 sec.
