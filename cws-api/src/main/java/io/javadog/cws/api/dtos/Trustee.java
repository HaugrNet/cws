package io.javadog.cws.api.dtos;

import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.common.TrustLevel;
import io.javadog.cws.api.common.Verifiable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>A Trustee, is a Member of a Circle, with a granted Trust Level.</p>
 *
 * @author Kim Jensen
 * @since  CWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "trustee", propOrder = { "id", "circle", "member", "trustLevel", "modified", "since" })
public final class Trustee extends Verifiable {

    /** {@link Constants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;
    private static final String FIELD_ID = "id";
    private static final String FIELD_CIRCLE = "circle";
    private static final String FIELD_MEMBER = "member";
    private static final String FIELD_TRUSTLEVEL = "trustLevel";

    @XmlElement                  private String id = null;
    @XmlElement(required = true) private Circle circle = null;
    @XmlElement(required = true) private Member member = null;
    @XmlElement(required = true) private TrustLevel trustLevel = null;
    @XmlElement                  private Date modified = null;
    @XmlElement                  private Date since = null;

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    @Pattern(regexp = Constants.ID_PATTERN_REGEX)
    public void setId(final String id) {
        ensurePattern(FIELD_ID, id, Constants.ID_PATTERN_REGEX);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @NotNull
    public void setCircle(final Circle circle) {
        ensureNotNull(FIELD_CIRCLE, circle);
        ensureVerifiable(FIELD_CIRCLE, circle);
        this.circle = circle;
    }

    public Circle getCircle() {
        return circle;
    }

    @NotNull
    public void setMember(final Member member) {
        ensureNotNull(FIELD_MEMBER, member);
        ensureVerifiable(FIELD_MEMBER, member);
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    @NotNull
    public void setTrustLevel(final TrustLevel trustLevel) {
        ensureNotNull(FIELD_TRUSTLEVEL, trustLevel);
        this.trustLevel = trustLevel;
    }

    public TrustLevel getTrustLevel() {
        return trustLevel;
    }

    public void setModified(final Date modified) {
        this.modified = modified;
    }

    public Date getModified() {
        return modified;
    }

    public void setSince(final Date since) {
        this.since = since;
    }

    public Date getSince() {
        return since;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> errors = new HashMap<>();

        checkPattern(errors, FIELD_ID, id, Constants.ID_PATTERN_REGEX, "The Trustee Id is invalid.");
        if (circle != null) {
            extendErrors(errors, circle.validate(), "Circle :: ");
        } else {
            checkNotNull(errors, FIELD_CIRCLE, circle, "The Circle is missing, null or invalid.");
        }
        if (member != null) {
            extendErrors(errors, member.validate(), "Member :: ");
        } else {
            checkNotNull(errors, FIELD_MEMBER, member, "The Member is missing, null or invalid.");
        }
        checkNotNull(errors, FIELD_TRUSTLEVEL, trustLevel, "The TrustLevel is missing, null or invalid.");

        return errors;
    }
}