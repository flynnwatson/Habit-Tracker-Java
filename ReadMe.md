# Habit Tracker 

**Author:** Flynn Watson  
**Student ID:** 49061461  
**Unit:** COMP1010 major assingment

## Overview
The project I made is  a simple **Habit Tracker** built in Java using object-oriented programming.  
It lets users create and manage personal habits under different group categories for example something like "health or fitness".  
The program runs through the console based menu where users can add new habits, view all habits, and  then save their progress
the data is saved to a  plane text file called habits.txt and can be reloaded when the program restarts.

My goal for this project was to really try follow the marking criteria strictly and use everything we have learnt so far in this unit, that being classes,objects,methods,arrays/lists and like the other side of things such as testing.
For this project I really wanted to understand what I was doing and not get lost. Programming is quite challenging for me and I just wanted this to be as efficiently smoothly ran as possible whilst also reaching marking criteria expectations. 
---

## Reflection
I learnt a lot from this projectmainly how classes work together. I found everything very overwhelming but kinda simplified what classes were more important then others.  Also testing with JUnit was something I didn’t fully understand before this but once I got a few tests passing it really helped me feel confident the program worked.
I really lacked patience in debugging and like every small thing would be so crucial like the amount of missing semicolons or higher cases i done was so annoying.
Im very happy with how I learnt this but I do need to give credit to  bro code on youtube he was kinda a saviour instead of me watching every lecture and i Kinda related his style of code to how I done the project.I also want to acknowledge that I used ai to help with concepts and also when i really couldnt understand something like why my code isnt running but it was a small little thing it picked up. 

## How to Compile and Run
### Windows (PowerShell / CMD)
```bat
javac -cp "lib/*" -d bin src/*.java
javac -cp "bin;lib/*" -d bin test/*.java
java  -cp "bin;lib/*" org.junit.runner.JUnitCore HabitTest HabitTrackerTest FileIoTest HabitGroupTest
java  -cp "bin" Main
