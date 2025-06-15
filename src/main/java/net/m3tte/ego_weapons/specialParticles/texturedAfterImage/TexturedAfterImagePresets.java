package net.m3tte.ego_weapons.specialParticles.texturedAfterImage;

public enum TexturedAfterImagePresets {
    STANDARD(0.9f, 0.7f, 0.5f, 0.3f, 0.05f, 0.05f, 0.05f, -0.02f, 20),
    RED_MIST(0.8f, 0.5f, 0.5f, 0.6f, 0.1f, 0.02f, 0.02f, -0.02f, 15),

    SWIFT_FIREFIST(1f, 0.7f, 0.5f, 0.3f, 0.05f, 0.05f, 0.05f, -0.03f, 20);

    private final float r;
    private final float g;
    private final float b;
    private final float a;

    private final float dr;
    private final float dg;
    private final float db;
    private final float da;

    private final int lifetime;


    public int getLifetime() {
        return lifetime;
    }

    public float getDa() {
        return da;
    }

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }

    public float getA() {
        return a;
    }

    public float getDr() {
        return dr;
    }

    public float getDg() {
        return dg;
    }

    public float getDb() {
        return db;
    }

    TexturedAfterImagePresets(float r, float g, float b, float a, float dr, float dg, float db, float da, int lifetime) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.dr = dr;
        this.dg = dg;
        this.db = db;
        this.da = da;
        this.lifetime = lifetime;
    }
}
