package src.model;

import src.physics.Vect;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

class CollisionDetails {
    private final double tuc;
    private final Vect velo;

    CollisionDetails(double t, Vect v) {
        tuc = t;
        velo = v;
    }

    double getTuc() {
        return tuc;
    }

    Vect getVelo() {
        return velo;
    }

}

