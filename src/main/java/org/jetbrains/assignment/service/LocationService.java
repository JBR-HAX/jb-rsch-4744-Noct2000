package org.jetbrains.assignment.service;

import org.jetbrains.assignment.model.Location;
import org.jetbrains.assignment.model.dto.DirectionDto;
import org.jetbrains.assignment.model.dto.LocationDto;
import java.util.List;

public interface LocationService {
    Location save(Location location);

    List<Location> getAll();

    Location getLastLocation();

    List<Location> makeSteps(List<DirectionDto> directionDtos);

    List<DirectionDto> inverseMove(List<LocationDto> locationDtos);
}
