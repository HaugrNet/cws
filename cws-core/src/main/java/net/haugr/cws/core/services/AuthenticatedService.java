/*
 * CWS, Cryptographic Web Share - open source Cryptographic Sharing system.
 * Copyright (c) 2016-2022, haugr.net
 * mailto: cws AT haugr DOT net
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
package net.haugr.cws.core.services;

import net.haugr.cws.api.common.ReturnCode;
import net.haugr.cws.api.requests.Authentication;
import net.haugr.cws.api.responses.AuthenticateResponse;
import net.haugr.cws.core.enums.Permission;
import net.haugr.cws.core.model.CommonDao;
import net.haugr.cws.core.model.Settings;
import java.util.Arrays;
import javax.persistence.EntityManager;

/**
 * <p>Business Logic implementation for the CWS Authenticated request.</p>
 *
 * @author Kim Jensen
 * @since CWS 1.1
 */
public final class AuthenticatedService extends Serviceable<CommonDao, AuthenticateResponse, Authentication> {

    public AuthenticatedService(final Settings settings, final EntityManager entityManager) {
        super(settings, new CommonDao(entityManager));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticateResponse perform(final Authentication request) {
        // Pre-checks, & destruction of credentials
        verifyRequest(request, Permission.PROCESS_MEMBER);
        Arrays.fill(request.getCredential(), (byte) 0);

        // And done... Authentication check completed
        final var response = new AuthenticateResponse();
        response.setReturnMessage(member.getName() + " successfully authenticated.");
        response.setMemberId(member.getExternalId());
        response.setReturnCode(ReturnCode.SUCCESS);

        return response;
    }
}
