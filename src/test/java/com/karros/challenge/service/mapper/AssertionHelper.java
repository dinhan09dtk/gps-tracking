package com.karros.challenge.service.mapper;

import com.karros.challenge.domain.Metadata;
import com.karros.challenge.service.dto.gps.MetadataType;
import org.w3c.dom.Element;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertionHelper {

    public static void assertMetadata(Metadata entity, Metadata newMetadata) {
        assertThat(entity).isEqualToComparingFieldByField(newMetadata);
    }

    public static void assertMetadataType(MetadataType metadataType, MetadataType newMetadataType) {
        assertThat(metadataType).isEqualToComparingOnlyGivenFields(newMetadataType, new String[] {"name", "desc", "keywords"});
        assertThat(metadataType.getAuthor().getEmail())
            .isEqualToComparingFieldByField(newMetadataType.getAuthor().getEmail());
        assertThat(metadataType.getAuthor().getLink())
            .isEqualToComparingFieldByField(newMetadataType.getAuthor().getLink());

        assertThat(metadataType.getCopyright()).isEqualToComparingFieldByField(newMetadataType.getCopyright());
        assertThat(metadataType.getLink().size() == 1).isTrue();
        assertThat(metadataType.getLink().get(0))
            .isEqualToComparingFieldByField(newMetadataType.getLink().get(0));

        assertThat(metadataType.getExtensions().getAny().size() == 1).isTrue();
        Element element = (Element) metadataType.getExtensions().getAny().get(0);
        Element elementOriginal = (Element) newMetadataType.getExtensions().getAny().get(0);
        assertThat(element.isEqualNode(elementOriginal)).isTrue();
    }
}
