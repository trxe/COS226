## Egg Drop

### 1 egg, T tosses max

sequential search

### lg(n) eggs, lg(n) tosses max

binary search

### lg(T) eggs, 2lg(T) tosses max

sequential search in intervals 1, 2, 4, 8, ... , ceil(log(T)). 
let k = ceil(log(T)).

then search within the
interval 

### 2 eggs, 2*sqrt(n) tosses max

split into sqrt(n) intervals, sequential search for the kth interval where the
egg breaks. then sequential search within that kth interval for T

### 2 eggs, c*sqrt(T) tosses max

sequential search in intervals 1, 2, 3, ..., T^2/2.
sequential search within that kth interval until we have T.
