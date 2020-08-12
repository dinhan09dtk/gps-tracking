package com.karros.challenge.service.mapper;

import com.karros.challenge.domain.Metadata;
import com.karros.challenge.service.dto.gps.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link MetadataMapper}.
 */
public class MetadataMapperTest {

    private static final String DEFAULT_NAME = "metadata_name";
    private static final String DEFAULT_DESC = "default_desc";
    private static final String DEFAULT_KEYWORD = "default_keyword";

    private CommonMapper commonMapper;
    private MetadataMapper metadataMapper;
    private Metadata metadata;
    private MetadataType metadataType;

    @BeforeEach
    public void init() {
        commonMapper = new CommonMapper();
        metadataMapper = new MetadataMapper(commonMapper);

        EmailType emailType = new EmailType();
        emailType.setId("1");
        emailType.setDomain("johndoe@localhost");

        LinkType linkType = new LinkType();
        linkType.setHref("http://localhost.com");
        linkType.setText("localhost");
        linkType.setType(null);

        PersonType author = new PersonType();
        author.setEmail(emailType);
        author.setLink(linkType);

        BoundsType boundsType = new BoundsType();
        boundsType.setMaxlat(BigDecimal.valueOf(1));
        boundsType.setMaxlon(BigDecimal.valueOf(2));
        boundsType.setMinlat(BigDecimal.valueOf(3));
        boundsType.setMinlon(BigDecimal.valueOf(4));

        CopyrightType copyrightType = new CopyrightType();
        copyrightType.setAuthor("author");
        copyrightType.setLicense("lincense");
        copyrightType.setYear(null);

        ExtensionsType extensionsType = new ExtensionsType();
        String jsonExtension = "<?xml version=\"1.0\" encoding=\"UTF-16\"?>\n<gpxtpx:TrackPointExtension xmlns=\"http://www.topografix.com/GPX/1/1\" xmlns:gpxtpx=\"http://www.garmin.com/xmlschemas/TrackPointExtension/v1\" xmlns:gpxx=\"http://www.garmin.com/xmlschemas/GpxExtensions/v3\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><gpxtpx:hr>171</gpxtpx:hr>\n          </gpxtpx:TrackPointExtension>";
        List<Element> documents = commonMapper.stringListToDocumentList(Arrays.asList(jsonExtension));
        extensionsType.setAny(new ArrayList<>(documents));

        metadataType = new MetadataType();
        metadataType.setAuthor(author);
        metadataType.setBounds(boundsType);
        metadataType.setCopyright(copyrightType);
        metadataType.setDesc(DEFAULT_DESC);
        metadataType.setExtensions(extensionsType);
        metadataType.setLink(Arrays.asList(linkType));
        metadataType.setKeywords(DEFAULT_KEYWORD);
        metadataType.setName(DEFAULT_NAME);

        metadata = new Metadata();
        metadata.setAuthor(commonMapper.toJson(author));
        metadata.setBounds(commonMapper.toJson(boundsType));
        metadata.setCopyright(commonMapper.toJson(copyrightType));
        metadata.setDesc(DEFAULT_DESC);
        metadata.setExtensions(commonMapper.toJson(extensionsType));
        metadata.setLink(commonMapper.toJson(Arrays.asList(linkType)));
        metadata.setKeywords(DEFAULT_KEYWORD);
        metadata.setName(DEFAULT_NAME);
    }

    @Test
    public void metadataToMetadataType() {
        MetadataType metadataType = metadataMapper.metadataToMetadataType(metadata);
        AssertionHelper.assertMetadataType(metadataType, this.metadataType);
    }

    @Test
    public void metadataTypeToMetadata() {
        Metadata metadata = metadataMapper.metadataTypeToMetadata(metadataType);
        AssertionHelper.assertMetadata(metadata, this.metadata);
    }
}
