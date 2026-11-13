package com.example.lighthouse.api;

import com.example.lighthouse.domain.Bottle;
import com.example.lighthouse.domain.BottleStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class BottleView {

    private String id;
    private String fromName;
    private String wish;
    private String echo;
    private BottleStatus status;
    private int trips;
    private List<String> voyages;
    private Instant createdAt;

    public static BottleView from(Bottle bottle) {
        BottleView view = new BottleView();
        view.id = bottle.getId();
        view.fromName = bottle.getFromName();
        view.wish = bottle.getWish();
        view.echo = bottle.getEcho();
        view.status = bottle.getStatus();
        view.trips = bottle.getTrips();
        view.voyages = split(bottle.getVoyageLog());
        view.createdAt = bottle.getCreatedAt();
        return view;
    }

    private static List<String> split(String voyageLog) {
        List<String> lines = new ArrayList<>();
        if (voyageLog == null || voyageLog.isBlank()) {
            return lines;
        }
        for (String line : voyageLog.split("\n")) {
            if (!line.isBlank()) {
                lines.add(line);
            }
        }
        return lines;
    }

    public String getId() {
        return id;
    }

    public String getFromName() {
        return fromName;
    }

    public String getWish() {
        return wish;
    }

    public String getEcho() {
        return echo;
    }

    public BottleStatus getStatus() {
        return status;
    }

    public int getTrips() {
        return trips;
    }

    public List<String> getVoyages() {
        return voyages;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
