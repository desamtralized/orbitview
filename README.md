# OrbitView
### Android custom View to create animated orbiting objects.
![Sample App](https://media.giphy.com/media/J4U60wsupnApKQYRvk/giphy.gif)

# How-to
### Just add 1 or many `PlanetView` inside an `OrbitView`, set it's drawable and that's it.
```java
<com.sambarboza.orbitview.OrbitView
    android:layout_width="298dp"
    android:layout_height="298dp"
    android:layout_centerInParent="true"
    app:orbitColor="@color/orbit_color"
    app:orbitWidth="1dp">

    <com.sambarboza.orbitview.PlanetView
        android:layout_width="45dp"
        android:layout_height="45dp"
        app:drawable="@drawable/earth"
        app:orbitDuration="5000"
        app:repeatCount="-1" />

</com.sambarboza.orbitview.OrbitView>
```
# Installing
[Add it to your project using JitPack](https://jitpack.io/#sambarboza/orbitview/0.1.0)
# Available Parameters
#### For `OrbitView`
- `orbitColor`: defines the color of the orbit (line)
- `orbitWidth`: how thick the orbit line should be
#### For `PlanetView`
- `drawable`: the drawable (image) for your orbiting object
- `animStartDelay`: how long until the orbit animation starts
- `fromAngle` and `toAngle`: starting and ending angle of the orbiting object, deafaults are `0` and `360`
- `orbitDuration`: in milliseconds
- `repeatCount`: default is `-1` for an infinite orbit
- `interpolator`: options are `linear`, `accelerate`, `accelerate_decelerate`, `anticipate`, `anticipate_overshoot`, `bounce`, `decelerate`, `overshoot`

# License
### This project is released under the MIT license.

### Contributions are welcome!
