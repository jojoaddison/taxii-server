package io.cisa.taxiiserver.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Collection.
 */
@Document(collection = "collection")
public class Collection implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("url")
    private String url;

    @Field("display_name")
    private String displayName;

    @Field("description")
    private String description;

    @Field("can_read")
    private Boolean canRead;

    @Field("can_write")
    private Boolean canWrite;

    @Field("media_types")
    private Set<String> mediaTypes = new HashSet<>();

    @Field("objects_count")
    private Integer objectsCount;
    
    @Field("manifest")
    private Set<Manifest> manifest = new HashSet<>();
    
    @Field("objects")
    private Set<Object> objects = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public Collection url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Collection displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public Collection description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isCanRead() {
        return canRead;
    }

    public Collection canRead(Boolean canRead) {
        this.canRead = canRead;
        return this;
    }

    public void setCanRead(Boolean canRead) {
        this.canRead = canRead;
    }

    public Boolean isCanWrite() {
        return canWrite;
    }

    public Collection canWrite(Boolean canWrite) {
        this.canWrite = canWrite;
        return this;
    }

    public void setCanWrite(Boolean canWrite) {
        this.canWrite = canWrite;
    }

    public Set<String> getMediaTypes() {
        return mediaTypes;
    }

    public Collection mediaTypes(Set<String> mediaTypes) {
        this.mediaTypes = mediaTypes;
        return this;
    }

    public void setMediaTypes(Set<String> mediaTypes) {
        this.mediaTypes = mediaTypes;
    }

    public Integer getObjectsCount() {
        return objectsCount;
    }

    public Collection objectsCount(Integer objectsCount) {
        this.objectsCount = objectsCount;
        return this;
    }

    public void setObjectsCount(Integer objectsCount) {
        this.objectsCount = objectsCount;
    }

    public Set<Manifest> getManifest() {
		return manifest;
	}

	public void setManifest(Set<Manifest> manifest) {
		this.manifest = manifest;
	}

	public Set<Object> getObjects() {
		return objects;
	}

	public void setObjects(Set<Object> objects) {
		this.objects = objects;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Collection collection = (Collection) o;
        if (collection.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), collection.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Collection{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", description='" + getDescription() + "'" +
            ", canRead='" + isCanRead() + "'" +
            ", canWrite='" + isCanWrite() + "'" +
            ", mediaTypes='" + getMediaTypes() + "'" +
            ", objectsCount='" + getObjectsCount() + "'" +
            "}";
    }
}
