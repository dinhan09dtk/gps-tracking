package com.karros.challenge.service.dto.gps;


public class GpxDTO {
    private Long id;
    private MetadataType metadata;
    private ExtensionsType extensions;
    private String version;
    private String creator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MetadataType getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataType metadata) {
        this.metadata = metadata;
    }

    public ExtensionsType getExtensions() {
        return extensions;
    }

    public void setExtensions(ExtensionsType extensions) {
        this.extensions = extensions;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
