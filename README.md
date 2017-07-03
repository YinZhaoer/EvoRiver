# EvoRiver
Reproduce the optimize algorithm of  EvoRiver

![Image text](https://raw.githubusercontent.com/YinZhaoer/imgFolder/master/EvoRiver/EvoRiverDome.png)

Description
-----------
Once we want to show a state of something that changes over time, we may use stackmap, but if there are many things with a state mentioned before? In order to show multiple objects with timechanging state, we propose a method called “EvoRiver” which could reduce the crossover caused by state transitions and makes the graphics looks more symmetrical. The optimization we used is shown below,and mosek will help us to find solve, if you want to know more details, please see the paper.
 
 ![Image text](https://github.com/YinZhaoer/imgFolder/blob/master/EvoRiver/algorithm.png)


Instructions
------------
### input format：
enter the data as follows in Data.java, each line represents a timestamp, and each timestamp contains attribute values for multiple objects. For example, the positive and negative values here correspond to the two states of the object.
  ![Image text](https://raw.githubusercontent.com/YinZhaoer/imgFolder/master/EvoRiver/data.png)
  
### get result in console
run mian.java and get output in console,copy the coordinates and width
 ![Image text](https://raw.githubusercontent.com/YinZhaoer/imgFolder/master/EvoRiver/java.png)
 
### copy the result to js 
copy the coordinates and width to layoutdata.js
 ![Image text](https://raw.githubusercontent.com/YinZhaoer/imgFolder/master/EvoRiver/js.png)

### open layout.html
  
