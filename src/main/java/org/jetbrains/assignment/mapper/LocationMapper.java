package org.jetbrains.assignment.mapper;

import org.jetbrains.assignment.model.Location;
import org.jetbrains.assignment.model.dto.LocationDto;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    public LocationDto toResponseDto(Location location) {
        return new LocationDto(location.getX(), location.getY());
    }
}
