## Fundamental data types

### Features

- Value: collection of objects
- Operations: **insert**, **remove**, **iterate**
- Which order of removal

### Stacks

LIFO discipline: last in first out. Peek reveals the last thing that had been
added. 

#### Implementation styles

##### Linked List

- Benefits: constant O(1) for ```push()``` and ```pop()``` in **worst case**. size does not have to be declared on creation
  - *Time complexity: approx 4 per ```push``` or ```pop```. Total 4N*.
  - *Space complexity: 40N*.
- Drawbacks: extra time and space required to deal with links.

##### Resizing Array

- Benefits: constant amortized time of 1, overall less time. Less wasted space.
  - *Time complexity (of N ```push``` or ```pop```): N + (1 + 2 + 4 + ... + N)  3N*
  - *Space complexity: 8N to 32N*.
- Drawbacks: Worst case is still O(N). 

### Queues

FIFO discipline: first in first out. Peek reveals the first thing that had been
added and not been removed. 
