package com.example.lighthouse.api;

import com.example.lighthouse.post.BottleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/bottles")
public class BottleController {

    private final BottleService bottleService;

    public BottleController(BottleService bottleService) {
        this.bottleService = bottleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BottleView cast(@Valid @RequestBody CastRequest request) {
        return BottleView.from(bottleService.cast(request.getFromName(), request.getWish()));
    }

    @GetMapping("/{id}")
    public BottleView get(@PathVariable String id) {
        return bottleService.find(id)
                .map(BottleView::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "bottle not found"));
    }
}
