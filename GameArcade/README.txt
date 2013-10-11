========================================================================
		A* Algorithm : Assessment
========================================================================
		Author	:	Carene Smith
		Date	:	2013/10/11
		Version :	V1.0.0.0
========================================================================
		
========================================================================
-------------------
JKD Version: 1.4
-------------------


-------------------
Build instructions:
-------------------
Copy files into a project folder
Locate the <project_folder>\"GameArcade" directory
Run the ant script:

ant test: Will run the main class.
          Note, you can set the input file with the map you want to run in the build.properties file. (Currently set to largeMap.txt
ant testsuite: This will run the TestSuite for the classes in the application

** Note the default target test.

-------
Output:
-------
The system will generate lage_map.txt and put in tin the "<project_folder>\out" directory



----------------------------------
OO & Design & Design patterns used
----------------------------------
The Node class use encapsulation and hide the implementation and only give access to the variables via public getters and setters.
It serves as an entity class that handles all the "blocks" as Node objects with their different entities.

The AStarPlayer used the Singletons pattern and only once instance of the game can run at a given time. 
It also implements an interface, that is based on the strategy design pattern where algorithms are uses.
Games that use algorithms, will always initialise, calculate a field of play (terrainMap)	 
do pre-game validations and start the game.

If another algorithm is added, the same interface can be implemented to play the game.




