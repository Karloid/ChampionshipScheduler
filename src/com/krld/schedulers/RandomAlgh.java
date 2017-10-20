package com.krld.schedulers;

import com.krld.models.Championship;
import com.krld.models.Match;

import java.util.Collections;
import java.util.List;

import static com.krld.Util.print;
import static com.krld.Util.println;

public class RandomAlgh implements Scheduler {

    private int iterations;

    @Override
    public Championship schedule(Championship c) {
        int tours = c.teams.size() - 1;

        List<Match> matches = c.matches.subList(c.tours.size(), c.matches.size());

        for (Match match : matches) {
            print(match + ", ");
        }
        println("qty " + matches.size());
        iterations = 0;
        while (true) {
            iterations++;
            Collections.shuffle(matches);

            c.createTours();
            for (int i = 0; i < c.matches.size(); i++) {
                c.tours.get(i % tours).matches.add(c.matches.get(i));
            }

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
        return "Random";
    }
}
