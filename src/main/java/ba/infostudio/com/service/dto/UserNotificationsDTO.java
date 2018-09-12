package ba.infostudio.com.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
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
    private Long id_job_notification;

    @NotNull
    private String is_read;

    private Long notification_templatesId;

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

    public Long getId_job_notification() {
        return id_job_notification;
    }

    public void setId_job_notification(Long id_job_notification) {
        this.id_job_notification = id_job_notification;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
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

        UserNotificationsDTO userNofitificationsDTO = (UserNotificationsDTO) o;
        if(userNofitificationsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userNofitificationsDTO.getId());
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
            ", id_job_notification=" + getId_job_notification() +
            ", is_read='" + getIs_read() + "'" +
            "}";
    }
}
