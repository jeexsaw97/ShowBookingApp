package com.project.showbooking.repository;

import com.project.showbooking.model.Show;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ShowRepository {

    private Map<Integer, Show> shows = new HashMap<>();

    public void addShow(Show show) {
        shows.put(show.getShowNumber(), show);
    }

    public Show getShow(int showNumber) {
        return shows.get(showNumber);
    }
}
