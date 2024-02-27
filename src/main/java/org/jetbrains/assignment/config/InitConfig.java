package org.jetbrains.assignment.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.jetbrains.assignment.model.Location;
import org.jetbrains.assignment.service.LocationService;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InitConfig {
    private final LocationService locationService;

    @PostConstruct
    public void insertBaseLocation() {
        Location baseLocation = new Location();
        baseLocation.setX(0);
        baseLocation.setY(0);
        locationService.save(baseLocation);
    }
}
