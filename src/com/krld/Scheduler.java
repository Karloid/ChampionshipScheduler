package com.krld;

import static com.krld.Util.println;

public class Scheduler {
    public void run() {
        println("start");

        check(new Random2Algh());
        check(new Random2Algh());
        check(new Random2Algh());
        check(new RandomAlgh());
    }

    private void check(ScheduleAlgorithm algorithm) {
        Championship initialChamp = initChampionship();
        println("\n\nAlgorithm name: " + algorithm.getName() + " Start checking, " +
                "teams count: " + initialChamp.matches.size());

        long start = System.currentTimeMillis();
        Championship c = algorithm.schedule(initialChamp);

        c.printTours();
        println("Algorithm name: " + algorithm.getName() + "\nResult is valid " + (c.isValid() ? "YES" : "NO") +
                "\nTime spent: " + (System.currentTimeMillis() - start) + " iterations: " + algorithm.getIterations());
    }

    private Championship initChampionship() {
        Championship c = new Championship();

        c.addTeams(6);

        c.printAllTeams();

        c.createAllMatches();

        c.printAllMatches();

        c.createTours();
        return c;
    }
}
