# Android_n-back
Android application to perform the n-back test.

The [n-back test](https://en.wikipedia.org/wiki/N-back) is an assessment method in cognitive science to measure working memory.
The subject of the test is tasked with indicating when a pattern or signal in a sequence is the same as _n_ steps earlier. Such a 
test could be made with numbers and/or symbols, colors, or positions in a grid.

The intention is to develop an android application that simulates this test, initially using numbers in a sequence, but other
methods or _game modes_ might be implemented later.

##### Difficulty
There are a number of factors that affects how difficult the n-back test can be. 
The first and most important factor is the value of _n_.
A 1-back test only requires the subject to remember the previous value and respond if it's the same as the one currently being shown.
As the value of _n_ increases, so does the steps the user will have to keep track of in their "_memory buffer_".
Speed is another factor; time constraint will affect the subject's ability to correctly memorize a sequence and the ability
to remember and decide if a step is a hit or not.  
The variation on steps is another thing worth considering. It would likely be easier to remember each step in a sequence if the
only possible values were O and X, than if each step was a number between 0 and 9.


##### Interaction
After setup, the test will start once the user has indicated that they are ready. 
When the test starts it will go through the sequence of steps, and the user simply taps the screen when they
think they've seen the same signal as _n_ steps earlier. If the user is correct, it counts as a hit, if they are wrong it will
count as a miss.  
How the test reaches an end state is not yet determined. It can be when the test has gone through a specified number of steps,
it could be based on time, or until the user has gotten a specific amount of hits or misses.

##### Mode
The simplest implementation is to simply flash a symbol or number on the screen for the given amount of time and then 
continue with the next step. A visual indicator could show how much time is left on that step, and thus how much time the 
user has left to make a decision.
Another implementation is to construct the test around the position of an object in a grid, as the example given in the 
[wikipedia article](https://en.wikipedia.org/wiki/N-back#/media/File:Single_n-back_task_animation.gif).
It would be intersting to see if a majority of users finds one mode easier than the other.  
A dual n-back test could merge the two methods together, with numbers popping up in a 3x3 grid, and the subject must 
respond when the same number **or** position as _n_ steps earlier occurs.  

There should be visual feedback whenever a hit or miss occurs, but it should not be too distracting.
