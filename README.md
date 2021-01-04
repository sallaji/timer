## CUIE - Assignment 2 - TimeControl

### Bearbeitet von

- Mauro Schällmann
- Llano Jiménez Santiago

## Study timer
Timer application created using java and the javafx framework

### Functionalities

#### Digit wise time input
You can enter the time using digits. The application automatically separates the digits by hours,
minutes and seconds
<img src="/bilder/digit_input.JPG"/>

#### Easy time input by pattern matching conversion
If you don't want to use only digits you have at your disposal a series of combinations that 
can be used to assign the desired time to the timer. The combination becomes a valid time by 
clicking the start button (▶) or pressing the enter key

|Character combination|Description|Acceptable Digit range|
|---|---|---|
|1p|Converts the desired time into a 15-minute break|from 1 to 9 (example: 3p for 45 minutes)
|1l|Converts the desired time into a 45-minute lesson|from 1 to 9 (example: 2l for 90 minutes)
|1h|Converts the desired time into one hour|from 1 to 24 (example: 24h for 24 hours)
|1m|Converts the desired time into one minute|from 1 to 60 (example: 60m for 1 hour)
|1s|Converts the desired time into one second|from 1 to 60 (example: 60m for 1 second)

<img src="/bilder/1p_example.JPG"/>
<img src="/bilder/1h_example.JPG"/>

#### Guess of "fuzzy" input values
Time digit entries that are somehow incomplete are attempted to be guessed at by the application. 
The input shown in the following image would be converted in one minute and one second 
(or 00:01:01 in correct time format) 
<img src="/bilder/guess_input.JPG"/>

#### Start the timer

The timer can be started by pressing the start button (▶)
<img src="/bilder/started.JPG"/>

#### Pause the timer

When active, the timer can be paused by pressing the left button or by clicking directly in the text field.

#### Reset the timer
The timer can be reset to the default value (00:45:00) by clicking the center button

#### Initialization of Audio Clip when time is up
An audio clip is started and the color of the text field is changed so that you can be notified 
when the time has expired. The notification is deactivated by pressing the center button or by 
clicking directly on the text field

<img src="/bilder/time_over.JPG"/>

#### Popup

By clicking on the button on the right, the application opens a popup indicating that the time 
can be increased by 15 or 30 minutes 

<img src="/bilder/popup.JPG"/>

#### Keyboard shortcuts

The time can be increased or decreased by five minutes by pressing the up key (↑) or down key (↓) respectively