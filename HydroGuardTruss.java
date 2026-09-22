package com.hydroguard;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * HydroGuard Redundant Hybrid Truss - core model.
 *
 * Minimalist statics simulation of a simplified Warren-style truss
 * with hybrid reinforcement and multi-layer members.
 *
 * This is an educational / competition demonstration tool.
 * It does NOT replace professional structural analysis software.
 */
public class HydroGuardTruss {

    private final String name;
    private final double spanCm;
    private final double heightCm;
    private final Map<String, TrussMember> members;
    private double appliedLoadN;

    public HydroGuardTruss(String name, double spanCm, double heightCm) {
        this.name = name;
        this.spanCm = spanCm;
        this.heightCm = heightCm;
        this.members = new LinkedHashMap<>();
        this.appliedLoadN = 0.0;
        buildDefaultGeometry();
    }

    private void buildDefaultGeometry() {
        // Bottom chord (tension) - 2 layers
        members.put("BC-L", new TrussMember("BC-L", spanCm / 2, 2));
        members.put("BC-R", new TrussMember("BC-R", spanCm / 2, 2));

        // Top chord (compression) - 2 layers
        members.put("TC-L", new TrussMember("TC-L", spanCm / 2, 2));
        members.put("TC-R", new TrussMember("TC-R", spanCm / 2, 2));

        // Diagonals (Warren pattern)
        double diagLen = Math.sqrt(Math.pow(spanCm / 2, 2) + Math.pow(heightCm, 2));
        members.put("DIAG-L", new TrussMember("DIAG-L", diagLen, 1));
        members.put("DIAG-R", new TrussMember("DIAG-R", diagLen, 1));

        // Verticals (Howe influence)
        members.put("VERT-C", new TrussMember("VERT-C", heightCm, 2));
        members.put("VERT-L", new TrussMember("VERT-L", heightCm, 2));
        members.put("VERT-R", new TrussMember("VERT-R", heightCm, 2));
    }

    public void applyCentralLoad(double loadNewton) {
        if (loadNewton < 0) {
            throw new IllegalArgumentException("Load must be non-negative");
        }
        this.appliedLoadN = loadNewton;
        analyse();
    }

    /**
     * Simplified force distribution for demonstration.
     * Captures qualitative behaviour of HydroGuard:
     * bottom chord in tension, top chord in compression,
     * diagonals and verticals sharing the load.
     */
    private void analyse() {
        double halfLoad = appliedLoadN / 2.0;
        double reaction = appliedLoadN / 2.0;

        double bottomForce = reaction * (spanCm / 2) / heightCm;
        double topForce    = -bottomForce;
        double diagForce   = reaction * 0.85;
        double vertForce   = -halfLoad * 0.6;

        members.get("BC-L").setForce(bottomForce);
        members.get("BC-R").setForce(bottomForce);
        members.get("TC-L").setForce(topForce);
        members.get("TC-R").setForce(topForce);
        members.get("DIAG-L").setForce(diagForce);
        members.get("DIAG-R").setForce(diagForce);
        members.get("VERT-C").setForce(vertForce);
        members.get("VERT-L").setForce(-reaction * 0.3);
        members.get("VERT-R").setForce(-reaction * 0.3);
    }

    public void printReport() {
        System.out.println();
        System.out.println("+--------------------------------------------------------------+");
        System.out.println("|           HYDROGUARD REDUNDANT HYBRID TRUSS                  |");
        System.out.println("|           Minimalist Force Simulation (Java)                 |");
        System.out.println("+--------------------------------------------------------------+");
        System.out.println();
        System.out.printf("  Project   : %s%n", name);
        System.out.printf("  Span      : %.1f cm%n", spanCm);
        System.out.printf("  Height    : %.1f cm%n", heightCm);
        System.out.printf("  Load      : %.1f N  (central downward)%n", appliedLoadN);
        System.out.println();
        System.out.println("  Member   | Length | Layers |   Force    Type          Status");
        System.out.println("  ---------|--------|--------|-------------------------------");

        boolean anyOverload = false;
        for (TrussMember m : members.values()) {
            System.out.println("  " + m);
            if (m.isOverloaded()) {
                anyOverload = true;
            }
        }

        System.out.println();
        if (anyOverload) {
            System.out.println("  [!]  One or more members exceed illustrative capacity.");
            System.out.println("       -> Increase layers or reduce load in real build.");
        } else {
            System.out.println("  [OK] All members within illustrative capacity.");
            System.out.println("       -> Geometry shows balanced force distribution.");
        }
        System.out.println();
        System.out.println("  Design philosophy: Triangular stability + Hybrid force path");
        System.out.println("                     + Multi-layer redundancy + Strong abutments");
        System.out.println();
        System.out.println("  Note: This is an educational demonstration only.");
        System.out.println("        Real structural design requires professional tools.");
        System.out.println();
    }

    public Map<String, TrussMember> getMembers() {
        return members;
    }

    public double getAppliedLoad() {
        return appliedLoadN;
    }
}
