## Event-Driven Simulation

A priority queue is used not just for queues, but also for molecular dynamics
for instance. To simulate motion of N moving particles elastically colliding

##### hard-disc model

- Moving particles interact via elastic collision
- at each point: *position, velocity, mass and radius* are known.
- relating macroscoping observables to microscoping dynamics
    - maxwell-boltzmann
    - brownian motion

The ``` Ball ``` class must have the parameters for **position, velocity, mass, and
radius**. The ``` move(double dt) ``` method must also be supplied, which simply
reverses the x-coord and y-coord velocity when it hits the left/right wall or
top/bottom wall respectively. it moves the ball ahead by ``` dt ``` time.

How to do the ball collisions though? How to do it in n lg n time instead of n^2
time?

###### Idea 1: Time-driven simulation.

Discrete time in quanta of ``` dt ```. Run the sim and if it detects a
collision, roll back time and update the velocities of the colliding particles.

Main drawbacks:
- N^2/2 overlap checks per time quantum
- dt too large: miss collisions.
- dt too small: too much computation

###### Idea 2: Event-driven simulation.

**Change things only when something happens.** Predict all collisions between
two particles travelling in straight lines. Sort these in a priority queue. Then
resolve them when they do happen (remove min time from the PQ).

Collision prediction: (x,y) and (vx,vy) and radius of particles --> point of
next collision and time?

Collision resolution: After a collision, update according to the laws of elastic
collision.

The math looks too confusing... just watch the video.

Collision system: main loop for the collision system

PQ fill with all potential particle wall collisions, don't care about
intervention of other collisions first. Then fill with potential
particle-particle collisions. (only check if an event is invalidated after
removal from PQ) We will need a ``` compareTo(Event that) ``` in order to be
``` Comparable ```.
