/*
 * CWS, Cryptographic Web Store - open source Cryptographic Storage system.
 * Copyright (C) 2016-2019, JavaDog.io
 * mailto: cws AT JavaDog DOT io
 *
 * CWS is free software; you can redistribute it and/or modify it under the
 * terms of the Apache License, as published by the Apache Software Foundation.
 *
 * CWS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the Apache License for more details.
 *
 * You should have received a copy of the Apache License, version 2, along with
 * this program; If not, you can download a copy of the License
 * here: https://www.apache.org/licenses/
 */
package io.javadog.cws.api.requests;

import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.common.CredentialType;
import io.javadog.cws.api.common.Utilities;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Base Authentication Object for all incoming Requests. It contains the
 * name of the Account, plus the credentials to unlock the Account. Both the
 * name and credentials are mandatory, whereas the type of credential is
 * optional with a fallback to PassPhrase.</p>
 *
 * @author Kim Jensen
 * @since  CWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authentication", propOrder = { Constants.FIELD_ACCOUNT_NAME, Constants.FIELD_CREDENTIAL, Constants.FIELD_CREDENTIALTYPE })
public class Authentication extends Verifiable {

    /** {@link Constants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

    @NotNull
    @Size(min = 1, max = Constants.MAX_NAME_LENGTH)
    @XmlElement(name = Constants.FIELD_ACCOUNT_NAME, required = true)
    private String accountName = null;

    @NotNull
    @XmlElement(name = Constants.FIELD_CREDENTIAL, required = true)
    private byte[] credential = null;

    @NotNull
    @XmlElement(name = Constants.FIELD_CREDENTIALTYPE, required = true)
    private CredentialType credentialType = CredentialType.PASSPHRASE;

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public final void setAccountName(final String accountName) {
        this.accountName = accountName;
    }

    public final String getAccountName() {
        return accountName;
    }

    public final void setCredential(final byte[] credential) {
        this.credential = Utilities.copy(credential);
    }

    public final byte[] getCredential() {
        return Utilities.copy(credential);
    }

    public final void setCredentialType(final CredentialType credentialType) {
        this.credentialType = credentialType;
    }

    public final CredentialType getCredentialType() {
        return credentialType;
    }

    // =========================================================================
    // Standard Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> errors = new ConcurrentHashMap<>();

        // If the Credential Type is undefined, it will be set to the
        // standard, which is Passphrase
        credentialType = (credentialType == null) ? CredentialType.PASSPHRASE : credentialType;

        switch (credentialType) {
            case SESSION:
                checkNotNullOrEmpty(errors, Constants.FIELD_CREDENTIAL, credential, "The Session (Credential) is missing.");
                break;
            case SIGNATURE:
                checkNotNullEmptyOrTooLong(errors, Constants.FIELD_ACCOUNT_NAME, accountName, Constants.MAX_NAME_LENGTH, "AccountName is missing, null or invalid.");
                checkNotNullOrEmpty(errors, Constants.FIELD_CREDENTIAL, credential, "The Credential is missing.");
                break;
            default:
                checkNotNullEmptyOrTooLong(errors, Constants.FIELD_ACCOUNT_NAME, accountName, Constants.MAX_NAME_LENGTH, "AccountName is missing, null or invalid.");
                checkNotNullOrEmpty(errors, Constants.FIELD_CREDENTIAL, credential, "The Credential is missing.");
                credentialType = CredentialType.PASSPHRASE;
                break;
        }

        return errors;
    }
}
