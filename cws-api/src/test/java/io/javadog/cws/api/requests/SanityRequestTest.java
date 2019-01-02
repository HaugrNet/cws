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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import io.javadog.cws.api.TestUtilities;
import io.javadog.cws.api.common.Constants;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import org.junit.Test;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public final class SanityRequestTest {

    @Test
    public void testClassflow() {
        final String circleId = UUID.randomUUID().toString();
        final Date since = new Date();

        final SanityRequest sanityRequest = new SanityRequest();
        sanityRequest.setAccountName(Constants.ADMIN_ACCOUNT);
        sanityRequest.setCredential(TestUtilities.convert(Constants.ADMIN_ACCOUNT));
        sanityRequest.setCircleId(circleId);
        sanityRequest.setSince(since);

        final Map<String, String> errors = sanityRequest.validate();
        assertThat(errors.isEmpty(), is(true));
        assertThat(sanityRequest.getAccountName(), is(Constants.ADMIN_ACCOUNT));
        assertThat(TestUtilities.convert(sanityRequest.getCredential()), is(Constants.ADMIN_ACCOUNT));
        assertThat(sanityRequest.getCircleId(), is(circleId));
        assertThat(sanityRequest.getSince(), is(since));
    }

    @Test
    public void testEmptyClass() {
        final SanityRequest sanityRequest = new SanityRequest();
        final Map<String, String> errors = sanityRequest.validate();

        assertThat(errors.isEmpty(), is(false));
        assertThat(errors.size(), is(2));
        assertThat(errors.get(Constants.FIELD_ACCOUNT_NAME), is("AccountName is missing, null or invalid."));
        assertThat(errors.get(Constants.FIELD_CREDENTIAL), is("The Credential is missing."));
    }

    @Test
    public void testClassWithInvalidValues() {
        final String circleId = "Invalid Circle Id";

        final SanityRequest sanityRequest = new SanityRequest();
        sanityRequest.setCircleId(circleId);

        final Map<String, String> errors = sanityRequest.validate();
        assertThat(errors.isEmpty(), is(false));
        assertThat(errors.size(), is(3));
        assertThat(errors.get(Constants.FIELD_ACCOUNT_NAME), is("AccountName is missing, null or invalid."));
        assertThat(errors.get(Constants.FIELD_CREDENTIAL), is("The Credential is missing."));
        assertThat(errors.get(Constants.FIELD_CIRCLE_ID), is("The Circle Id is invalid."));
    }
}
