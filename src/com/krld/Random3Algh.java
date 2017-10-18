package com.krld;

import java.util.ArrayList;
import java.util.List;

public class Random3Algh implements ScheduleAlgorithm {

    private int iter;

    @Override
    public Championship schedule(Championship c) {
        
        int matchesInTour = c.teams.size() / 2;
        int tours = c.teams.size() - 1;

        iter = 0;
        while (true) {
            iter++;
            c.createTours();

            ArrayList<Match> freeMatches = new ArrayList<>(c.matches);

            for (int i = 0; i < tours; i++) {
                c.tours.get(i).matches.add(freeMatches.get(i));
            }

            //Util.println(freeMatches.toString());
            freeMatches.removeAll(freeMatches.subList(0, tours));

            //Util.println(freeMatches.toString());


            for (int i = 0; !freeMatches.isEmpty(); i++) {
                List<Match> validMatches = new ArrayList<>(freeMatches.size());

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
                break;
            }
        }
        return c;
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
