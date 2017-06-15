/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-api)
 * =============================================================================
 */
package io.javadog.cws.api.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>When invoking a processing Request to the CWS, it must be with a specific
 * type of Action, which may or may not be allowed.</p>
 *
 * <p>This enumerated type contain all the allowed Actions, and each Request
 * will allow one or more of these.</p>
 *
 * @author Kim Jensen
 * @since  CWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "action", namespace = "api.cws.javadog.io")
public enum Action {

    /**
     * <p>If no Actions are allowed, this is a dummy value.</p>
     */
    NONE,

    /**
     * <p>The Action Process covers creating and updating records.</p>
     */
    PROCESS,

    /**
     * <p>For Invitations, which will allow the System Administrator to create
     * new Accounts by issuing a signed invitation.</p>
     */
    INVITE,

    /**
     * <p>The Action Delete covers removal of records, which is a permanent
     * irreversible Action.</p>
     */
    DELETE,

    /**
     * <p>This Action adds a Trustee to a Circle.</p>
     */
    ADD,

    /**
     * <p>This Action Alters a Trustee's relation to a Circle, which means
     * change the Trust Level. If invoked by the System Administrator, then it
     * can only be used to alter the TrustLevel, not to grant access to the
     * Data by giving the Member access to the Circle Key, as the System
     * Administrator itself is not having access to this.</p>
     */
    ALTER,

    /**
     * <p>This Action allows a Circle or System Administrator to remove a Member
     * from a Circle. A Circle Administrator cannot remove themselves from a
     * Circle.</p>
     */
    REMOVE,

    /**
     * <p>This is a special Action, which will enforce a complete re-encryption
     * of all data belonging the Object in question.</p>
     *
     * <p>Note, that only for those Objects where a Key is stored, is this
     * Action allowed, which include processing Members and processing Circles.
     * For other requests, this Action is not allowed.</p>
     */
    REKEY
}
