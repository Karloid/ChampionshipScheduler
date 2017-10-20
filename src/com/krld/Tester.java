package com.krld;

import com.krld.models.Championship;
import com.krld.schedulers.PathFindingScheduler;
import com.krld.schedulers.Random3Algh;
import com.krld.schedulers.Scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.krld.Util.println;

public class Tester {

    public static final int TEAM_COUNT = 8;

    public void run() {
        println("start");

        // check(new Random2Algh());
        // check(new Random2Algh());
        // check(new Random2Algh());
        // check(new RandomAlgh());

        check(new PathFindingScheduler());
        //checkThreadsForRandom3();
       // ResultInfo res = check(new Random3Algh(8));
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

    private ResultInfo check(Scheduler scheduler) {
        Championship initialChamp = initChampionship();
        println("\n\nAlgorithm name: " + scheduler.getName() + " Start checking, " +
                "teams count: " + initialChamp.matches.size());

        long start = System.currentTimeMillis();
        Championship c = scheduler.schedule(initialChamp);
        long spentTime = System.currentTimeMillis() - start;
        if (c == null) {
            c = initialChamp;
        }
        c.sortTours();
        c.printTours();

        int iterations = scheduler.getIterations();

        println("Algorithm name: " + scheduler.getName() + "\nResult is valid " + (c.isValid() ? "YES" : "NO") +
                "\nTime spent: " + spentTime + " iterations: " + iterations);

        return new ResultInfo(spentTime, iterations);
    }

    private Championship initChampionship() {
        Championship c = new Championship();

        c.addTeams(TEAM_COUNT);

        c.printAllTeams();

        c.createAllMatches();

        c.printAllMatches();

        c.createTours();
        return c;
    }
}
