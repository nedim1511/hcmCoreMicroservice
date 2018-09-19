package ba.infostudio.com.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserNotifications entity.
 */
public class UserNotificationsDTO implements Serializable {

    private Long id;

    @NotNull
    private Long id_job_application;

    @NotNull
    private String is_read;

    private Long idUser;

    private Long notification_templatesId;

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

    public Long getId_job_application() {
        return id_job_application;
    }

    public void setId_job_application(Long id_job_application) {
        this.id_job_application = id_job_application;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getNotification_templatesId() {
        return notification_templatesId;
    }

    public void setNotification_templatesId(Long notification_templatesId) {
        this.notification_templatesId = notification_templatesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserNotificationsDTO userNotificationsDTO = (UserNotificationsDTO) o;
        if(userNotificationsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userNotificationsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserNotificationsDTO{" +
            "id=" + getId() +
            ", id_job_application=" + getId_job_application() +
            ", id_job_notification=" + getNotification_templatesId() +
            ", is_read='" + getIs_read() + "'" +
            ", idUser=" + getIdUser() +
            "}";
    }
}
