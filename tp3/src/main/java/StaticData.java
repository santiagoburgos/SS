package main.java;

public class StaticData {
    private float size;
    private float r1;
    private float r2;
    private float m1;
    private float m2;

    public StaticData(float size, float r1, float r2, float m1, float m2) {
        this.size = size;
        this.r1 = r1;
        this.r2 = r2;
        this.m1 = m1;
        this.m2 = m2;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getR1() {
        return r1;
    }

    public void setR1(float r1) {
        this.r1 = r1;
    }

    public float getR2() {
        return r2;
    }

    public void setR2(float r2) {
        this.r2 = r2;
    }

    public float getM1() {
        return m1;
    }

    public void setM1(float m1) {
        this.m1 = m1;
    }

    public float getM2() {
        return m2;
    }

    public void setM2(float m2) {
        this.m2 = m2;
    }
}
