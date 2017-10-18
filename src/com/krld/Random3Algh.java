package com.krld;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Random3Algh implements ScheduleAlgorithm {

    private int iter;
    private volatile Championship solved;
    private ExecutorService executorService;
    private final int nThreads;

    public Random3Algh(int nThreads) {

        this.nThreads = nThreads;
    }

    @Override
    public Championship schedule(final Championship c) {
        executorService = Executors.newFixedThreadPool(nThreads);
        for (int i = 0; i < nThreads; i++) {
            try {

                executorService.submit(() -> solve(new Championship(c)));
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        try {
            executorService.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return solved == null ? c : solved;
    }

    private void solve(Championship c) {
        int matchesInTour = c.teams.size() / 2;
        int tours = c.teams.size() - 1;

        ArrayList<Match> freeMatches;
        List<Match> validMatches;

        iter = 0;
        while (true) {
            iter++;
            c.createTours();

            freeMatches = new ArrayList<>(c.matches);

            for (int i = 0; i < tours; i++) {
                c.tours.get(i).matches.add(freeMatches.get(i));
            }

            //Util.println(freeMatches.toString());
            freeMatches.removeAll(freeMatches.subList(0, tours));

            //Util.println(freeMatches.toString());


            for (int i = 0; !freeMatches.isEmpty(); i++) {
                if (solved != null) {
                    break;
                }
                validMatches = new ArrayList<>(freeMatches.size());

                Tour tour = c.tours.get(i % tours);

                for (int i1 = 0; i1 < freeMatches.size(); i1++) {
                    Match fm = freeMatches.get(i1);
                    if (tour.isValid(fm)) {
                        validMatches.add(fm);
                    }
                }

                if (validMatches.isEmpty()) {
                    break;
                }

                Match m = Util.getRandom(validMatches);

                freeMatches.remove(m);

                tour.matches.add(m);

            }

            if (c.isValid()) {
                solved = c;
                executorService.shutdownNow();
                break;
            }

            if (solved != null) {
                break;
            }
        }
    }

    @Override
    public int getIterations() {
        return iter;
    }

    @Override
    public String getName() {
        return "Random3";
    }
}
