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
import io.javadog.cws.api.common.Action;
import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.common.TrustLevel;
import java.util.Map;
import java.util.UUID;
import org.junit.Test;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public final class ProcessTrusteeRequestTest {

    @Test
    public void testClassflow() {
        final String circleId = UUID.randomUUID().toString();
        final String memberId = UUID.randomUUID().toString();

        final ProcessTrusteeRequest request = new ProcessTrusteeRequest();
        request.setAccountName(Constants.ADMIN_ACCOUNT);
        request.setCredential(TestUtilities.convert(Constants.ADMIN_ACCOUNT));
        request.setAction(Action.ADD);
        request.setCircleId(circleId);
        request.setMemberId(memberId);
        request.setTrustLevel(TrustLevel.WRITE);
        final Map<String, String> errors = request.validate();

        assertThat(errors.isEmpty(), is(true));
        assertThat(request.getAccountName(), is(Constants.ADMIN_ACCOUNT));
        assertThat(TestUtilities.convert(request.getCredential()), is(Constants.ADMIN_ACCOUNT));
        assertThat(request.getAction(), is(Action.ADD));
        assertThat(request.getCircleId(), is(circleId));
        assertThat(request.getMemberId(), is(memberId));
        assertThat(request.getTrustLevel(), is(TrustLevel.WRITE));
    }

    @Test
    public void testEmptyClass() {
        final ProcessTrusteeRequest request = new ProcessTrusteeRequest();
        final Map<String, String> errors = request.validate();

        assertThat(errors.size(), is(3));
        assertThat(errors.get(Constants.FIELD_ACCOUNT_NAME), is("AccountName is missing, null or invalid."));
        assertThat(errors.get(Constants.FIELD_CREDENTIAL), is("The Credential is missing."));
        assertThat(errors.get(Constants.FIELD_ACTION), is("No action has been provided."));
    }

    @Test
    public void testInvalidAction() {
        final ProcessTrusteeRequest request = new ProcessTrusteeRequest();
        request.setAccountName(Constants.ADMIN_ACCOUNT);
        request.setCredential(TestUtilities.convert(Constants.ADMIN_ACCOUNT));
        request.setAction(Action.PROCESS);

        final Map<String, String> errors = request.validate();
        assertThat(errors.size(), is(1));
        assertThat(errors.get(Constants.FIELD_ACTION), is("Not supported Action has been provided."));
    }

    @Test
    public void testActionAdd() {
        final ProcessTrusteeRequest request = new ProcessTrusteeRequest();
        request.setAccountName(Constants.ADMIN_ACCOUNT);
        request.setCredential(TestUtilities.convert(Constants.ADMIN_ACCOUNT));
        request.setCircleId(UUID.randomUUID().toString());
        request.setMemberId(UUID.randomUUID().toString());
        request.setTrustLevel(TrustLevel.WRITE);
        request.setAction(Action.ADD);

        final Map<String, String> errors = request.validate();
        assertThat(errors.isEmpty(), is(true));
    }

    @Test
    public void testActionAddFail() {
        final ProcessTrusteeRequest request = new ProcessTrusteeRequest();
        request.setAccountName(Constants.ADMIN_ACCOUNT);
        request.setCredential(TestUtilities.convert(Constants.ADMIN_ACCOUNT));
        request.setCircleId(null);
        request.setMemberId("Invalid MemberId");
        request.setTrustLevel(null);
        request.setAction(Action.ADD);

        final Map<String, String> errors = request.validate();
        assertThat(errors.size(), is(3));
        assertThat(errors.get(Constants.FIELD_CIRCLE_ID), is("Cannot add a Trustee to a Circle, without a Circle Id."));
        assertThat(errors.get(Constants.FIELD_MEMBER_ID), is("Cannot add a Trustee to a Circle, without a Member Id."));
        assertThat(errors.get(Constants.FIELD_TRUSTLEVEL), is("Cannot add a Trustee to a Circle, without an initial TrustLevel."));
    }

    @Test
    public void testActionAlter() {
        final ProcessTrusteeRequest request = new ProcessTrusteeRequest();
        request.setAccountName(Constants.ADMIN_ACCOUNT);
        request.setCredential(TestUtilities.convert(Constants.ADMIN_ACCOUNT));
        request.setCircleId(UUID.randomUUID().toString());
        request.setMemberId(UUID.randomUUID().toString());
        request.setTrustLevel(TrustLevel.WRITE);
        request.setAction(Action.ALTER);

        final Map<String, String> errors = request.validate();
        assertThat(errors.isEmpty(), is(true));
    }

    @Test
    public void testActionAlterFail() {
        final ProcessTrusteeRequest request = new ProcessTrusteeRequest();
        request.setAccountName(Constants.ADMIN_ACCOUNT);
        request.setCredential(TestUtilities.convert(Constants.ADMIN_ACCOUNT));
        request.setCircleId("Invalid Circle Id");
        request.setMemberId(null);
        request.setTrustLevel(null);
        request.setAction(Action.ALTER);

        final Map<String, String> errors = request.validate();
        assertThat(errors.size(), is(3));
        assertThat(errors.get(Constants.FIELD_CIRCLE_ID), is("Cannot alter a Trustees TrustLevel, without knowing the Circle Id."));
        assertThat(errors.get(Constants.FIELD_MEMBER_ID), is("Cannot alter a Trustees TrustLevel, without knowing the Member Id."));
        assertThat(errors.get(Constants.FIELD_TRUSTLEVEL), is("Cannot alter a Trustees TrustLevel, without knowing the new TrustLevel."));
    }

    @Test
    public void testActionRemove() {
        final ProcessTrusteeRequest request = new ProcessTrusteeRequest();
        request.setAccountName(Constants.ADMIN_ACCOUNT);
        request.setCredential(TestUtilities.convert(Constants.ADMIN_ACCOUNT));
        request.setCircleId(UUID.randomUUID().toString());
        request.setMemberId(UUID.randomUUID().toString());
        request.setAction(Action.REMOVE);

        final Map<String, String> errors = request.validate();
        assertThat(errors.isEmpty(), is(true));
    }

    @Test
    public void testActionRemoveFail() {
        final ProcessTrusteeRequest request = new ProcessTrusteeRequest();
        request.setAccountName(Constants.ADMIN_ACCOUNT);
        request.setCredential(TestUtilities.convert(Constants.ADMIN_ACCOUNT));
        request.setCircleId(null);
        request.setMemberId("invalid Member Id");
        request.setAction(Action.REMOVE);

        final Map<String, String> errors = request.validate();
        assertThat(errors.size(), is(2));
        assertThat(errors.get(Constants.FIELD_CIRCLE_ID), is("Cannot remove a Trustee from a Circle, without knowing the Circle Id."));
        assertThat(errors.get(Constants.FIELD_MEMBER_ID), is("Cannot remove a Trustee from a Circle, without knowing the Member Id."));
    }
}
