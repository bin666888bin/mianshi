package com.example.lighthouse.post;

import com.example.lighthouse.domain.Bottle;
import com.example.lighthouse.domain.BottleRepository;
import com.example.lighthouse.domain.BottleStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class BottleService {

    private final BottleRepository bottles;
    private final Tide tide;

    public BottleService(BottleRepository bottles, Tide tide) {
        this.bottles = bottles;
        this.tide = tide;
    }

    public Bottle cast(String fromName, String wish) {
        Bottle bottle = new Bottle();
        bottle.setId(UUID.randomUUID().toString());
        bottle.setFromName(fromName.trim());
        bottle.setWish(wish.trim());
        bottle.setStatus(BottleStatus.BOTTLED);
        bottle.setTrips(0);
        bottle.setVoyageLog("");
        bottle.setCreatedAt(Instant.now());
        bottles.save(bottle);
        tide.deliver(bottle.getId());
        return bottle;
    }

    public Optional<Bottle> find(String id) {
        return bottles.findById(id);
    }
}
