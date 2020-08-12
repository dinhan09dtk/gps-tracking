package com.karros.challenge.service.mapper;

import com.karros.challenge.domain.Route;
import com.karros.challenge.domain.RoutePoint;
import com.karros.challenge.service.dto.gps.WptType;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class RoutePointMapper extends AbstractPointMapper<RoutePoint> {
    @Override
    public RoutePoint wptTypeToEntity(WptType wptType, Object parent) {
        if (wptType == null || !(parent instanceof Route)) {
            return null;
        }
        RoutePoint routePoint = new RoutePoint();
        wptTypeListToEntityList(wptType, routePoint);
        routePoint.setRoute((Route) parent);
        return routePoint;
    }
}
