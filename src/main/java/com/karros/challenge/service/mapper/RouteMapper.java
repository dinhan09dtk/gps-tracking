package com.karros.challenge.service.mapper;

import com.karros.challenge.domain.Route;
import com.karros.challenge.domain.RoutePoint;
import com.karros.challenge.domain.Trip;
import com.karros.challenge.service.dto.gps.ExtensionsType;
import com.karros.challenge.service.dto.gps.LinkType;
import com.karros.challenge.service.dto.gps.RteType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class RouteMapper {
    private RoutePointMapper routePointMapper;
    private CommonMapper commonMapper;

    public RouteMapper(RoutePointMapper routePointMapper, CommonMapper commonMapper) {
        this.routePointMapper = routePointMapper;
        this.commonMapper = commonMapper;
    }

    public Route rteTypeToRoute(RteType rteType) {
        if (rteType == null) {
            return null;
        }

        Route route = new Route();
        route.setCmt(rteType.getCmt());
        route.setDesc(rteType.getDesc());
        route.setExtensions(commonMapper.toJson(rteType.getExtensions()));
        route.setLink(commonMapper.toJson(rteType.getLink()));
        route.setName(rteType.getName());
        route.setNumber(rteType.getNumber());
        if (CollectionUtils.isNotEmpty(rteType.getRtept())) {
            List<RoutePoint> routePoints = routePointMapper.wptTypeListToEntityList(rteType.getRtept(), route);
            route.setRtept(routePoints);
        }
        route.setSrc(rteType.getSrc());
        route.setType(rteType.getType());
        return route;
    }

    public List<Route> rteTypeListToRouteList(List<RteType> rteTypeList, Trip trip) {
        if (CollectionUtils.isEmpty(rteTypeList) || trip == null) {
            return Collections.emptyList();
        }
        return rteTypeList.stream()
            .map(rteType -> {
                Route route = this.rteTypeToRoute(rteType);
                route.setTrip(trip);
                return route;
            }).collect(Collectors.toList());
    }

    public RteType routeToRteType(Route route) {
        if (route == null) {
            return null;
        }

        RteType rteType = new RteType();
        rteType.setCmt(route.getCmt());
        rteType.setDesc(route.getDesc());
        rteType.setExtensions(commonMapper.fromJson(route.getExtensions()));
        rteType.setLink(commonMapper.fromJsonList(route.getLink()));
        rteType.setName(route.getName());
        rteType.setNumber(route.getNumber());
        if (CollectionUtils.isNotEmpty(route.getRtept())) {
            rteType.setRtept(routePointMapper.entityListToWptTypeList(route.getRtept()));
        }
        rteType.setSrc(route.getSrc());
        rteType.setType(route.getType());
        return rteType;
    }

    public List<RteType> routeListToRteTypeList(List<Route> routes) {
        if (CollectionUtils.isEmpty(routes)) {
            return Collections.emptyList();
        }
        return routes.stream()
            .map(this::routeToRteType)
            .collect(Collectors.toList());
    }
}
