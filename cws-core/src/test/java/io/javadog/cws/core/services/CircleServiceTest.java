/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-core)
 * =============================================================================
 */
package io.javadog.cws.core.services;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.common.ReturnCode;
import io.javadog.cws.api.requests.FetchCircleRequest;
import io.javadog.cws.api.requests.ProcessCircleRequest;
import io.javadog.cws.api.responses.FetchCircleResponse;
import io.javadog.cws.common.Settings;
import io.javadog.cws.common.exceptions.ModelException;
import io.javadog.cws.common.exceptions.VerificationException;
import io.javadog.cws.model.DatabaseSetup;
import io.javadog.cws.model.entities.CircleEntity;
import org.junit.Test;

import java.util.UUID;

/**
 * <p>Common test class for the Process & Fetch Circle Services.</p>
 *
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public final class CircleServiceTest extends DatabaseSetup {

    @Test
    public void testEmptyFetchRequest() {
        prepareCause(VerificationException.class, ReturnCode.VERIFICATION_WARNING,
                "Request Object contained errors:" +
                        "\nKey: credentialType, Error: CredentialType is missing, null or invalid." +
                        "\nKey: credential, Error: Credential is missing, null or invalid." +
                        "\nKey: account, Error: Account is missing, null or invalid.");

        final FetchCircleService service = new FetchCircleService(settings, entityManager);
        final FetchCircleRequest request = new FetchCircleRequest();
        // Just making sure that the account is missing
        assertThat(request.getAccount(), is(nullValue()));

        // Should throw a VerificationException, as the request is invalid.
        service.perform(request);
    }

    @Test
    public void testEmptyProcessRequest() {
        prepareCause(VerificationException.class, ReturnCode.VERIFICATION_WARNING,
                "Request Object contained errors:" +
                        "\nKey: credentialType, Error: CredentialType is missing, null or invalid." +
                        "\nKey: credential, Error: Credential is missing, null or invalid." +
                        "\nKey: action, Error: No action has been provided." +
                        "\nKey: account, Error: Account is missing, null or invalid.");

        final ProcessCircleService service = new ProcessCircleService(settings, entityManager);
        final ProcessCircleRequest request = new ProcessCircleRequest();
        // Just making sure that the account is missing
        assertThat(request.getAccount(), is(nullValue()));

        // Should throw a VerificationException, as the request is invalid.
        service.perform(request);
    }

    @Test
    public void testFetchNotExistingCircle() {
        final FetchCircleService service = new FetchCircleService(settings, entityManager);
        final FetchCircleRequest request = prepareRequest(FetchCircleRequest.class, Constants.ADMIN_ACCOUNT);
        request.setCircleId(UUID.randomUUID().toString());
        final FetchCircleResponse response = service.perform(request);

        // Verify that we have found the correct data
        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(false));
        assertThat(response.getReturnCode(), is(ReturnCode.IDENTIFICATION_WARNING));
        assertThat(response.getReturnMessage(), is("The requested Circle cannot be found."));
    }

    @Test
    public void testFetchAllCirclesAsAdmin() {
        final FetchCircleService service = new FetchCircleService(settings, entityManager);
        final FetchCircleRequest request = prepareRequest(FetchCircleRequest.class, Constants.ADMIN_ACCOUNT);
        final FetchCircleResponse response = service.perform(request);

        assertThat(response, is(not(nullValue())));
        assertThat(response.isOk(), is(true));
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS));
        assertThat(response.getReturnMessage(), is("Ok"));
        assertThat(response.getCircles().size(), is(3));
        assertThat(response.getCircles().get(0).getName(), is("circle1"));
        assertThat(response.getCircles().get(1).getName(), is("circle2"));
        assertThat(response.getCircles().get(2).getName(), is("circle3"));
        assertThat(response.getTrustees().isEmpty(), is(true));
    }

    @Test
    public void testFetchAllCirclesAsMember1() {
        final FetchCircleService service = new FetchCircleService(settings, entityManager);
        final FetchCircleRequest request = prepareRequest(FetchCircleRequest.class, "member1");

        final FetchCircleResponse response = service.perform(request);
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS));
        assertThat(response.getReturnMessage(), is("Ok"));
        assertThat(response.getCircles().size(), is(3));
        assertThat(response.getCircles().get(0).getName(), is("circle1"));
        assertThat(response.getCircles().get(1).getName(), is("circle2"));
        assertThat(response.getCircles().get(2).getName(), is("circle3"));
        assertThat(response.getTrustees().size(), is(0));
    }

    @Test
    public void testFetchCircle1WithShowTrueAsAdmin() {
        // Ensure that we have the correct settings for the Service
        settings.set(Settings.SHOW_TRUSTEES, "true");

        final FetchCircleService service = new FetchCircleService(settings, entityManager);
        final CircleEntity circle = findFirstCircle();
        final FetchCircleRequest request = prepareRequest(FetchCircleRequest.class, Constants.ADMIN_ACCOUNT);
        request.setCircleId(circle.getExternalId());
        final FetchCircleResponse response = service.perform(request);

        assertThat(response, is(not(nullValue())));
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS));
        assertThat(response.getReturnMessage(), is("Ok"));
        assertThat(response.getCircles().size(), is(1));
        assertThat(response.getCircles().get(0).getName(), is("circle1"));
        assertThat(response.getTrustees().size(), is(3));
        assertThat(response.getTrustees().get(0).getMember().getAuthentication().getAccount(), is("member1"));
        assertThat(response.getTrustees().get(1).getMember().getAuthentication().getAccount(), is("member2"));
        assertThat(response.getTrustees().get(2).getMember().getAuthentication().getAccount(), is("member3"));
    }

    @Test
    public void testFetchCircle1WithShowFalseAsAdmin() {
        // Ensure that we have the correct settings for the Service
        settings.set(Settings.SHOW_TRUSTEES, "false");

        final FetchCircleService service = new FetchCircleService(settings, entityManager);
        final CircleEntity circle = findFirstCircle();
        final FetchCircleRequest request = prepareRequest(FetchCircleRequest.class, Constants.ADMIN_ACCOUNT);
        request.setCircleId(circle.getExternalId());
        final FetchCircleResponse response = service.perform(request);

        assertThat(response, is(not(nullValue())));
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS));
        assertThat(response.getReturnMessage(), is("Ok"));
        assertThat(response.getCircles().size(), is(1));
        assertThat(response.getCircles().get(0).getName(), is("circle1"));
        assertThat(response.getTrustees().size(), is(3));
        assertThat(response.getTrustees().get(0).getMember().getAuthentication().getAccount(), is("member1"));
        assertThat(response.getTrustees().get(1).getMember().getAuthentication().getAccount(), is("member2"));
        assertThat(response.getTrustees().get(2).getMember().getAuthentication().getAccount(), is("member3"));
    }

    @Test
    public void testFetchCircle1WithShowTrueAsMember1() {
        // Ensure that we have the correct settings for the Service
        settings.set(Settings.SHOW_TRUSTEES, "true");

        final FetchCircleService service = new FetchCircleService(settings, entityManager);
        final CircleEntity circle = findFirstCircle();
        final FetchCircleRequest request = prepareRequest(FetchCircleRequest.class, "member1");
        request.setCircleId(circle.getExternalId());
        final FetchCircleResponse response = service.perform(request);

        assertThat(response, is(not(nullValue())));
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS));
        assertThat(response.getReturnMessage(), is("Ok"));
        assertThat(response.getCircles().size(), is(1));
        assertThat(response.getCircles().get(0).getName(), is("circle1"));
        assertThat(response.getTrustees().size(), is(3));
        assertThat(response.getTrustees().get(0).getMember().getAuthentication().getAccount(), is("member1"));
        assertThat(response.getTrustees().get(1).getMember().getAuthentication().getAccount(), is("member2"));
        assertThat(response.getTrustees().get(2).getMember().getAuthentication().getAccount(), is("member3"));
    }

    @Test
    public void testFetchCircle1WithShowFalseAsMember1() {
        // Ensure that we have the correct settings for the Service
        settings.set(Settings.SHOW_TRUSTEES, "false");

        final FetchCircleService service = new FetchCircleService(settings, entityManager);
        final CircleEntity circle = findFirstCircle();
        final FetchCircleRequest request = prepareRequest(FetchCircleRequest.class, "member1");
        request.setCircleId(circle.getExternalId());
        final FetchCircleResponse response = service.perform(request);

        assertThat(response, is(not(nullValue())));
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS));
        assertThat(response.getReturnMessage(), is("Ok"));
        assertThat(response.getCircles().size(), is(1));
        assertThat(response.getCircles().get(0).getName(), is("circle1"));
        assertThat(response.getTrustees().size(), is(3));
        assertThat(response.getTrustees().get(0).getMember().getAuthentication().getAccount(), is("member1"));
        assertThat(response.getTrustees().get(1).getMember().getAuthentication().getAccount(), is("member2"));
        assertThat(response.getTrustees().get(2).getMember().getAuthentication().getAccount(), is("member3"));
    }

    @Test
    public void testFetchCircle1WithShowTrueAsMember5() {
        prepareCause(ModelException.class, "No member found with 'member5'.");

        // Ensure that we have the correct settings for the Service
        settings.set(Settings.SHOW_TRUSTEES, "true");

        final FetchCircleService service = new FetchCircleService(settings, entityManager);
        final CircleEntity circle = findFirstCircle();
        final FetchCircleRequest request = prepareRequest(FetchCircleRequest.class, "member5");
        assertThat(request, is(not(nullValue())));
        request.setCircleId(circle.getExternalId());
        service.perform(request);
    }

    @Test
    public void testFetchCircle1WithShowFalseAsMember5() {
        prepareCause(ModelException.class, "No member found with 'member5'.");

        // Ensure that we have the correct settings for the Service
        settings.set(Settings.SHOW_TRUSTEES, "false");

        final FetchCircleService service = new FetchCircleService(settings, entityManager);
        final CircleEntity circle = findFirstCircle();
        final FetchCircleRequest request = prepareRequest(FetchCircleRequest.class, "member5");
        assertThat(request, is(not(nullValue())));
        request.setCircleId(circle.getExternalId());
        service.perform(request);
    }

    // =========================================================================
    // Internal Helper Methods
    // =========================================================================

    private CircleEntity findFirstCircle() {
        return entityManager.find(CircleEntity.class, 1L);
    }
}