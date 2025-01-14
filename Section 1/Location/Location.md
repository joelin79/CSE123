Location [Starter Comparable Programming]
---
Problem Description
Consider a class Location that stores information about places as a pair of latitude-longitude coordinates. Each location keeps track of its name (a string), its latitude (a real number), and its longitude (a real number).

Modify the class to be Comparable by adding an appropriate compareTo method. Locations should be ordered first by latitude with locations closer to the equator (closer to 0) considered less than locations farther from the equator. When the latitudes are equal, you should examine longitudes with locations closer to the prime meridian (closer to 0) considered less than locations farther from the prime meridian.

You may assume that your constructor is passed legal values for latitude and longitude. Use the Math.abs method to find the absolute value of a number.

Testing Your Code
Press Run to run the given LocationClient.java code's main method. Press Mark to run our JUnit tests. LocationTest.java is also available to be seen/edited - simply add test cases to the given code and hit 'Check' (not Mark) to see your code run on your new test suite!
