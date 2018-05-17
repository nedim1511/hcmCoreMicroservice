package ba.infostudio.com.service.dto;


import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the DmDocumentLinks entity.
 */
public class DmDocumentLinksDTO implements Serializable {

    private Long id;

    private String documentName;

    private String fileName;

    private String filePath;

    private String uri;

    @Lob
    private byte[] documentBlob;
    private String documentBlobContentType;

    private String createdBy;

    private Instant createdAt;

    private String updatedBy;

    private Instant updatedAt;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public byte[] getDocumentBlob() {
        return documentBlob;
    }

    public void setDocumentBlob(byte[] documentBlob) {
        this.documentBlob = documentBlob;
    }

    public String getDocumentBlobContentType() {
        return documentBlobContentType;
    }

    public void setDocumentBlobContentType(String documentBlobContentType) {
        this.documentBlobContentType = documentBlobContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DmDocumentLinksDTO dmDocumentLinksDTO = (DmDocumentLinksDTO) o;
        if(dmDocumentLinksDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dmDocumentLinksDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DmDocumentLinksDTO{" +
            "id=" + getId() +
            ", documentName='" + getDocumentName() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", filePath='" + getFilePath() + "'" +
            ", uri='" + getUri() + "'" +
            ", documentBlob='" + getDocumentBlob() + "'" +
            "}";
    }
}
