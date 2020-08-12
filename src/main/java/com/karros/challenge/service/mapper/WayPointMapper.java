package com.karros.challenge.service.mapper;

import com.karros.challenge.domain.Trip;
import com.karros.challenge.domain.WayPoint;
import com.karros.challenge.service.dto.gps.WptType;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class WayPointMapper extends AbstractPointMapper<WayPoint> {
    @Override
    public WayPoint wptTypeToEntity(WptType wptType, Object parent) {
        if (wptType == null || !(parent instanceof Trip)) {
            return null;
        }
        WayPoint wayPoint = new WayPoint();
        wptTypeListToEntityList(wptType, wayPoint);
        wayPoint.setTrip((Trip) parent);
        return wayPoint;
    }
}
