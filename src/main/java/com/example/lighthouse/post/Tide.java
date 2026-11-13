package com.example.lighthouse.post;

import com.example.lighthouse.domain.Bottle;
import com.example.lighthouse.domain.BottleRepository;
import com.example.lighthouse.domain.BottleStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 潮汐把瓶子送出海，再带回灯塔。
 */
@Component
public class Tide {

    private static final Logger log = LoggerFactory.getLogger(Tide.class);

    private final BottleRepository bottles;
    private final ExecutorService executor = Executors.newFixedThreadPool(4, runnable -> {
        Thread thread = new Thread(runnable, "tide");
        thread.setDaemon(true);
        return thread;
    });

    public Tide(BottleRepository bottles) {
        this.bottles = bottles;
    }

    public void deliver(String id) {
        executor.execute(() -> {
            int attempt = 0;
            boolean arrived = false;
            while (attempt < 2 && !arrived) {
                attempt++;
                arrived = sail(id);
            }
        });
    }

    private boolean sail(String id) {
        Bottle bottle = bottles.findById(id).orElseThrow();
        bottle.setStatus(BottleStatus.DRIFTING);
        bottle.setTrips(bottle.getTrips() + 1);
        bottles.save(bottle);
        log.info("瓶子 {} 出海，第 {} 次", id, bottle.getTrips());

        try {
            Thread.sleep(2_000);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }

        bottle = bottles.findById(id).orElseThrow();
        String echo = "海面送来回音：「" + bottle.getFromName() + "」";
        bottle.setEcho(echo);
        bottle.setVoyageLog(append(bottle.getVoyageLog(), "第 " + bottle.getTrips() + " 次出海 · " + echo));
        bottle.setStatus(BottleStatus.BOTTLED);
        bottles.save(bottle);
        log.info("瓶子 {} 靠岸，状态={}", id, bottle.getStatus());
        return false;
    }

    private String append(String voyageLog, String line) {
        if (voyageLog == null || voyageLog.isBlank()) {
            return line;
        }
        return voyageLog + "\n" + line;
    }
}
