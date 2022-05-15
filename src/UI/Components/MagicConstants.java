package UI.Components;

import Utils.Pair;

public class MagicConstants {
    public static final double AsteroidSize = 30;
    public static final double ShipSize = 20;
    public static final boolean FullScreen = true;
    // Number of sectors
    public static final int sectorNumber = 8;
    // the distance where asteroids doesnt overlap each other
    public static final double asteroidTooClose = 0.15;
    // the distance where asteroids doesnt overlap each other
    public static final double sunTooClose = 0.17;

    public static final double MaxGeneratedDistance = 1.5;

    public static final double SunDiameter = 200;

    public static final double CoreInfoImageSize = 42;

    // number of ships
    public static int shipNumber = 1;
    // number of asteroids
    public static int asteroidNumber = 80;
    // max distance when asteroids are linked
    public static double neighbourMaxDistance = 0.25;

    public static void setShipNumber(int shipNumber) {
        MagicConstants.shipNumber = shipNumber;
    }

    public static void setMapSize(int i) {
        if(i == 0){
            asteroidNumber = 60;
            neighbourMaxDistance = 0.28;
        } else if (i == 1){
            asteroidNumber = 80;
            neighbourMaxDistance = 0.25;
        } else if (i == 2) {
            asteroidNumber = 120;
            neighbourMaxDistance = 0.22;
        }
    }

    public static final double BaseImageSize = 25;

    public static final double NotificationDuration = 5000.0;
    public static final double NotificationFadeStart = 4000.0;
    public static final double ExplosionDuration = 3000.0;
    public static final double ExplosionGrowth = 5.0;
}
