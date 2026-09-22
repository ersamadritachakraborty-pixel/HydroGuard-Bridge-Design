package com.hydroguard;

/**
 * Represents a single member in the HydroGuard truss.
 * Each member carries either tension or compression under load.
 */
public class TrussMember {

    private final String id;
    private final double length;
    private final int stickLayers;
    private double force;
    private ForceType type;

    public enum ForceType {
        TENSION, COMPRESSION, ZERO, UNKNOWN
    }

    public TrussMember(String id, double length, int stickLayers) {
        if (stickLayers < 1 || stickLayers > 3) {
            throw new IllegalArgumentException("Stick layers must be 1, 2 or 3");
        }
        this.id = id;
        this.length = length;
        this.stickLayers = stickLayers;
        this.force = 0.0;
        this.type = ForceType.UNKNOWN;
    }

    public void setForce(double force) {
        this.force = force;
        if (Math.abs(force) < 1e-6) {
            this.type = ForceType.ZERO;
        } else if (force > 0) {
            this.type = ForceType.TENSION;
        } else {
            this.type = ForceType.COMPRESSION;
        }
    }

    public String getId() {
        return id;
    }

    public double getLength() {
        return length;
    }

    public int getStickLayers() {
        return stickLayers;
    }

    public double getForce() {
        return force;
    }

    public ForceType getType() {
        return type;
    }

    public double estimatedCapacity() {
        return 250.0 * stickLayers;   // illustrative capacity
    }

    public boolean isOverloaded() {
        return Math.abs(force) > estimatedCapacity();
    }

    @Override
    public String toString() {
        String status = isOverloaded() ? " [!] OVERLOAD" : " [OK]";
        return String.format("%-8s | %5.1f cm | %d layer(s) | %8.1f N %-12s%s",
                id, length, stickLayers, force, type, status);
    }
}
