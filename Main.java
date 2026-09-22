package com.hydroguard;

/**
 * Entry point for the HydroGuard Truss Simulator.
 *
 * Compile:
 *   javac -d out src/main/java/com/hydroguard/*.java
 *
 * Run:
 *   java -cp out com.hydroguard.Main
 *   java -cp out com.hydroguard.Main 800
 */
public class Main {

    public static void main(String[] args) {
        HydroGuardTruss bridge = new HydroGuardTruss(
                "HydroGuard-01  |  Utkarsh 5.0 Bridge Bust",
                40.0,   // span in cm
                12.0    // height in cm
        );

        double testLoadN = 400.0;   // default ~40 kg

        if (args.length > 0) {
            try {
                testLoadN = Double.parseDouble(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Usage: java com.hydroguard.Main [load_in_newtons]");
                System.err.println("Using default load 400 N");
            }
        }

        bridge.applyCentralLoad(testLoadN);
        bridge.printReport();

        System.out.println("  -----------------------------------------------------------");
        System.out.println("  Team: Samadrita Chakraborty (CSE)  |  Supritam Chakraborty (ECE)");
        System.out.println("        Shubham Das (Electical)");
        System.out.println("  Tripura Institute of Technology  |  Bridge Bust 2026");
        System.out.println("  -----------------------------------------------------------");
    }
}  
    
