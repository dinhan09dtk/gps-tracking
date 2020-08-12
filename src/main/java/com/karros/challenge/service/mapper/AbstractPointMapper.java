package com.karros.challenge.service.mapper;

import com.karros.challenge.domain.AbstractPoint;
import com.karros.challenge.service.dto.gps.LinkType;
import com.karros.challenge.service.dto.gps.WptType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractPointMapper<T extends AbstractPoint> {
    @Autowired
    private CommonMapper commonMapper;

    protected void wptTypeListToEntityList(WptType wptType, AbstractPoint entity) {
        if (wptType == null || entity == null) {
            return;
        }

        entity.setAgeofdgpsdata(wptType.getAgeofdgpsdata());
        entity.setCmt(wptType.getCmt());
        entity.setDesc(wptType.getDesc());
        entity.setDgpsid(wptType.getDgpsid());
        entity.setEle(wptType.getEle());
        entity.setExtensions(commonMapper.toJson(wptType.getExtensions()));
        entity.setFix(wptType.getFix());
        entity.setHdop(wptType.getHdop());
        entity.setPdop(wptType.getPdop());
        entity.setVdop(wptType.getVdop());
        entity.setGeoidheight(wptType.getGeoidheight());
        entity.setLat(wptType.getLat());
        entity.setLon(wptType.getLon());
        entity.setLink(commonMapper.toJson(wptType.getLink()));
        entity.setMagvar(wptType.getMagvar());
        entity.setName(wptType.getName());
        entity.setSat(wptType.getSat());
        entity.setSrc(wptType.getSrc());
        entity.setSym(wptType.getSym());
        entity.setType(wptType.getType());
        entity.setFix(wptType.getFix());
        entity.setTime(commonMapper.xmlDateToDate(wptType.getTime()));
    }

    public abstract T wptTypeToEntity(WptType wptType, Object parent);

    public List<T> wptTypeListToEntityList(List<WptType> wptTypeList, Object parent) {
        if (CollectionUtils.isEmpty(wptTypeList)) {
            return Collections.emptyList();
        }
        return wptTypeList.stream()
            .map(wptType -> this.wptTypeToEntity(wptType, parent))
            .collect(Collectors.toList());
    }

    public WptType entityToWptType(T entity) {
        if (entity == null) {
            return null;
        }
        WptType wptType = new WptType();

        wptType.setAgeofdgpsdata(entity.getAgeofdgpsdata());
        wptType.setCmt(entity.getCmt());
        wptType.setDesc(entity.getDesc());
        wptType.setDgpsid(entity.getDgpsid());
        wptType.setEle(entity.getEle());
        wptType.setExtensions(commonMapper.fromJson(entity.getExtensions()));
        wptType.setFix(entity.getFix());
        wptType.setHdop(entity.getHdop());
        wptType.setPdop(entity.getPdop());
        wptType.setVdop(entity.getVdop());
        wptType.setGeoidheight(entity.getGeoidheight());
        wptType.setLat(entity.getLat());
        wptType.setLon(entity.getLon());
        wptType.setLink(commonMapper.fromJsonList(entity.getLink()));
        wptType.setMagvar(entity.getMagvar());
        wptType.setName(entity.getName());
        wptType.setSat(entity.getSat());
        wptType.setSrc(entity.getSrc());
        wptType.setSym(entity.getSym());
        wptType.setType(entity.getType());
        wptType.setFix(entity.getFix());
        wptType.setTime(commonMapper.dateToXmlDate(entity.getTime()));
        return wptType;
    }

    public List<WptType> entityListToWptTypeList(List<T> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }
        return entities.stream()
            .map(this::entityToWptType)
            .collect(Collectors.toList());
    }
}
