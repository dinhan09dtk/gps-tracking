package com.karros.challenge.service.mapper;

import com.karros.challenge.domain.Metadata;
import com.karros.challenge.service.dto.gps.*;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Objects;

/**
 *
 */
@Service
public class MetadataMapper {
    private CommonMapper commonMapper;

    public MetadataMapper(CommonMapper commonMapper) {
        this.commonMapper = commonMapper;
    }

    public Metadata metadataTypeToMetadata(MetadataType metadataType) {
        if (metadataType == null) {
            return null;
        }

        Metadata metadata = new Metadata();
        metadata.setAuthor(commonMapper.toJson(metadataType.getAuthor()));
        metadata.setBounds(commonMapper.toJson(metadataType.getBounds()));
        metadata.setCopyright(commonMapper.toJson(metadataType.getCopyright()));
        metadata.setDesc(metadataType.getDesc());
        metadata.setExtensions(commonMapper.toJson(metadataType.getExtensions()));
        metadata.setLink(commonMapper.toJson(metadataType.getLink()));
        metadata.setKeywords(metadataType.getKeywords());
        metadata.setName(metadataType.getName());

        XMLGregorianCalendar gregorianCalendar = metadataType.getTime();
        if (Objects.nonNull(gregorianCalendar)) {
            metadata.setTime(gregorianCalendar.toGregorianCalendar().getTime());
        }
        return metadata;
    }

    public MetadataType metadataToMetadataType(Metadata metadata) {
        if (metadata == null) {
            return null;
        }

        MetadataType metadataType = new MetadataType();
        metadataType.setAuthor(commonMapper.fromJson(metadata.getAuthor(), PersonType.class));
        metadataType.setBounds(commonMapper.fromJson(metadata.getBounds(), BoundsType.class));
        metadataType.setCopyright(commonMapper.fromJson(metadata.getCopyright(), CopyrightType.class));
        metadataType.setDesc(metadata.getDesc());
        metadataType.setExtensions(commonMapper.fromJson(metadata.getExtensions()));
        metadataType.setLink(commonMapper.fromJsonList(metadata.getLink()));
        metadataType.setKeywords(metadata.getKeywords());
        metadataType.setName(metadata.getName());
        metadataType.setTime(commonMapper.dateToXmlDate(metadata.getTime()));
        return metadataType ;
    }
}
