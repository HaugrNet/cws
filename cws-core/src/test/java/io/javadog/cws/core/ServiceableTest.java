/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-core)
 * =============================================================================
 */
package io.javadog.cws.core;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.common.ReturnCode;
import io.javadog.cws.api.requests.FetchCircleRequest;
import io.javadog.cws.api.requests.SettingRequest;
import io.javadog.cws.common.exceptions.AuthenticationException;
import io.javadog.cws.common.exceptions.AuthorizationException;
import io.javadog.cws.core.services.FetchCircleService;
import io.javadog.cws.core.services.SettingService;
import io.javadog.cws.model.DatabaseSetup;
import org.junit.Test;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public final class ServiceableTest extends DatabaseSetup {

    @Test
    public void testAccesWithInvalidPassword() {
        prepareCause(AuthenticationException.class, ReturnCode.AUTHENTICATION_WARNING, "Cannot authenticate the Account 'admin' from the given Credentials.");

        final SettingService service = new SettingService(settings, entityManager);
        final SettingRequest request = new SettingRequest();
        request.setAccountName(Constants.ADMIN_ACCOUNT);
        request.setCredential("Invalid Credentials");
        assertThat(request, is(not(nullValue())));

        service.perform(request);
    }

    @Test
    public void testAccessSettingsAsMember() {
        prepareCause(AuthorizationException.class, ReturnCode.AUTHORIZATION_WARNING, "Cannot complete this request, as it is only allowed for the System Administrator.");

        final SettingService service = new SettingService(settings, entityManager);
        final SettingRequest request = prepareRequest(SettingRequest.class, MEMBER_1);
        assertThat(request.validate().isEmpty(), is(true));

        service.perform(request);
    }

    @Test
    public void testAuthorizationWithInvalidCredentials() {
        prepareCause(AuthenticationException.class, ReturnCode.AUTHENTICATION_WARNING, "Cannot authenticate the Account 'member5' from the given Credentials.");

        final FetchCircleService service = new FetchCircleService(settings, entityManager);
        final FetchCircleRequest request = prepareRequest(FetchCircleRequest.class, MEMBER_5);
        request.setCredential("something wrong");
        assertThat(request.validate().isEmpty(), is(true));

        service.perform(request);
    }

    @Test
    public void testAuthorizationWithCredentialTypingMistake() {
        prepareCause(AuthenticationException.class, ReturnCode.AUTHENTICATION_WARNING, "Cannot authenticate the Account 'member5' from the given Credentials.");

        final FetchCircleService service = new FetchCircleService(settings, entityManager);
        final FetchCircleRequest request = prepareRequest(FetchCircleRequest.class, MEMBER_5);
        request.setCredential(MEMBER_4);
        assertThat(request.validate().isEmpty(), is(true));

        service.perform(request);
    }

    @Test
    public void testFetchCirclesAsNonExistingMember() {
        prepareCause(AuthenticationException.class, ReturnCode.AUTHENTICATION_WARNING, "Could not uniquely identify an account for 'member6'.");

        final FetchCircleService service = new FetchCircleService(settings, entityManager);
        final FetchCircleRequest request = prepareRequest(FetchCircleRequest.class, "member6");
        assertThat(request.validate().isEmpty(), is(true));

        service.perform(request);
    }
}