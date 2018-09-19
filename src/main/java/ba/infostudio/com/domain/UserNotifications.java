package ba.infostudio.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UserNotifications.
 */
@Entity
@Table(name = "un")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserNotifications implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_job_application", nullable = false)
    private Long id_job_application;

    @NotNull
    @Column(name = "id_job_notification", nullable = false)
    private Long id_job_notification;

    @NotNull
    @Column(name = "is_read", nullable = false)
    private String is_read;

    @Column(name = "id_user")
    private Long idUser;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private NotificationTemplates notification_templates;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_job_application() {
        return id_job_application;
    }

    public UserNotifications id_job_application(Long id_job_application) {
        this.id_job_application = id_job_application;
        return this;
    }

    public void setId_job_application(Long id_job_application) {
        this.id_job_application = id_job_application;
    }

    public Long getId_job_notification() {
        return id_job_notification;
    }

    public UserNotifications id_job_notification(Long id_job_notification) {
        this.id_job_notification = id_job_notification;
        return this;
    }

    public void setId_job_notification(Long id_job_notification) {
        this.id_job_notification = id_job_notification;
    }

    public String getIs_read() {
        return is_read;
    }

    public UserNotifications is_read(String is_read) {
        this.is_read = is_read;
        return this;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public Long getIdUser() {
        return idUser;
    }

    public UserNotifications idUser(Long idUser) {
        this.idUser = idUser;
        return this;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public NotificationTemplates getNotificationTemplates() {
        return notification_templates;
    }

    public UserNotifications notification_templates(NotificationTemplates notification_templates) {
        this.notification_templates = notification_templates;
        return this;
    }

    public void setNotificationTemplates(NotificationTemplates notification_templates) {
        this.notification_templates = notification_templates;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserNotifications userNotifications = (UserNotifications) o;
        if (userNotifications.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userNotifications.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserNotifications{" +
            "id=" + getId() +
            ", id_job_application=" + getId_job_application() +
            ", id_job_notification=" + getId_job_notification() +
            ", is_read='" + getIs_read() + "'" +
            ", idUser=" + getIdUser() +
            "}";
    }
}
