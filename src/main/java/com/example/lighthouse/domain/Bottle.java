package com.example.lighthouse.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "bottle")
public class Bottle {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 40)
    private String fromName;

    @Column(nullable = false, length = 500)
    private String wish;

    @Column(length = 500)
    private String echo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private BottleStatus status;

    private int trips;

    @Column(length = 2000)
    private String voyageLog;

    @Column(nullable = false)
    private Instant createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }

    public String getEcho() {
        return echo;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }

    public BottleStatus getStatus() {
        return status;
    }

    public void setStatus(BottleStatus status) {
        this.status = status;
    }

    public int getTrips() {
        return trips;
    }

    public void setTrips(int trips) {
        this.trips = trips;
    }

    public String getVoyageLog() {
        return voyageLog;
    }

    public void setVoyageLog(String voyageLog) {
        this.voyageLog = voyageLog;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
