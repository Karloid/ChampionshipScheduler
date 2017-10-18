package com.krld;

import java.util.List;

public class Random2Algh implements ScheduleAlgorithm {

    private int iterations;

    @Override
    public Championship schedule(Championship c) {
        int tours = c.teams.size() - 1;

        c.createTours();
        for (int i = 0; i < c.matches.size(); i++) {
            c.tours.get(i % tours).matches.add(c.matches.get(i));
        }

        iterations = 0;
        for (; ; ) {
            iterations++;
            int i1 = tours + (int) (Math.random() * (c.matches.size() - tours));
            int i2 = tours + (int) (Math.random() * (c.matches.size() - tours));

            if (i1 == i2) {
                continue;
            }

            List<Match> tour1 = c.tours.get(i1 % tours).matches;
            List<Match> tour2 = c.tours.get(i2 % tours).matches;

            int ii1 = i1 / tours;
            int ii2 = i2 / tours;
            Match tmp = tour1.get(ii1);
            tour1.set(ii1, tour2.get(ii2));
            tour2.set(ii2, tmp);


            if (c.isValid()) {
                break;
            }
        }

        return c;

    }

    @Override
    public int getIterations() {
        return iterations;
    }

    @Override
    public String getName() {
        return "Random2";
    }
}
