package com.krld;

import java.util.*;

import static com.krld.Util.println;

public class Scheduler {
    public void run() {
        println("start");

        // check(new Random2Algh());
        // check(new Random2Algh());
        // check(new Random2Algh());
        // check(new RandomAlgh());


        checkThreadsForRandom3();
    }

    private void checkThreadsForRandom3() {
        Map<Integer, Long> results = new HashMap<>();
        int attempts = 30;
        for (int i = 1; i <= 18; i++) {
            results.put(i, 0L);
            for (int j = 0; j < attempts; j++) {
                ResultInfo res = check(new Random3Algh(i));
                results.put(i, results.get(i) + res.spentTime);
            }
        }

        List<Map.Entry<Integer, Long>> entries = new ArrayList<>(results.entrySet());
        for (int i = 0; i < entries.size(); i++) {
            entries.get(i).setValue((long) (entries.get(i).getValue() / attempts));
        }
        //noinspection ComparatorCombinators
        entries.sort((o2, o1) -> o2.getValue().compareTo(o1.getValue()));
        println(entries.toString());
    }

    private ResultInfo check(ScheduleAlgorithm algorithm) {
        Championship initialChamp = initChampionship();
        println("\n\nAlgorithm name: " + algorithm.getName() + " Start checking, " +
                "teams count: " + initialChamp.matches.size());

        long start = System.currentTimeMillis();
        Championship c = algorithm.schedule(initialChamp);
        c.sortTours();
        c.printTours();
        long spentTime = System.currentTimeMillis() - start;
        int iterations = algorithm.getIterations();

        println("Algorithm name: " + algorithm.getName() + "\nResult is valid " + (c.isValid() ? "YES" : "NO") +
                "\nTime spent: " + spentTime + " iterations: " + iterations);

        return new ResultInfo(spentTime, iterations);
    }

    private Championship initChampionship() {
        Championship c = new Championship();

        c.addTeams(10);

        c.printAllTeams();

        c.createAllMatches();

        c.printAllMatches();

        c.createTours();
        return c;
    }
}
