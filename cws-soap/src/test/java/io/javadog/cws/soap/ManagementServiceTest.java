/*
 * =============================================================================
 * Copyright (c) 2016-2018, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-soap)
 * =============================================================================
 */
package io.javadog.cws.soap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import io.javadog.cws.api.common.Action;
import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.common.ReturnCode;
import io.javadog.cws.api.common.TrustLevel;
import io.javadog.cws.api.requests.FetchCircleRequest;
import io.javadog.cws.api.requests.FetchMemberRequest;
import io.javadog.cws.api.requests.FetchTrusteeRequest;
import io.javadog.cws.api.requests.MasterKeyRequest;
import io.javadog.cws.api.requests.ProcessCircleRequest;
import io.javadog.cws.api.requests.ProcessMemberRequest;
import io.javadog.cws.api.requests.ProcessTrusteeRequest;
import io.javadog.cws.api.requests.SanityRequest;
import io.javadog.cws.api.requests.SettingRequest;
import io.javadog.cws.api.responses.FetchCircleResponse;
import io.javadog.cws.api.responses.FetchMemberResponse;
import io.javadog.cws.api.responses.FetchTrusteeResponse;
import io.javadog.cws.api.responses.MasterKeyResponse;
import io.javadog.cws.api.responses.ProcessCircleResponse;
import io.javadog.cws.api.responses.ProcessMemberResponse;
import io.javadog.cws.api.responses.ProcessTrusteeResponse;
import io.javadog.cws.api.responses.SanityResponse;
import io.javadog.cws.api.responses.SettingResponse;
import io.javadog.cws.api.responses.VersionResponse;
import io.javadog.cws.core.enums.StandardSetting;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public final class ManagementServiceTest extends BeanSetup {

    @Test
    public void testVersion() throws IOException {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final String propertiesFile = "cws.config";

        if (loader != null) {
            final ManagementService management = prepareManagementService();
            final String version;

            try (final InputStream stream = loader.getResourceAsStream(propertiesFile)) {
                final Properties properties = new Properties();
                properties.load(stream);
                version = properties.getProperty("cws.version");
            }

            final VersionResponse response = management.version();
            assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS.getCode()));
            assertThat(response.getVersion(), is(version));
        } else {
            fail("Could not open the Class Loader, to read the '" + propertiesFile + "' file from the test resource path.");
        }
    }

    @Test
    public void testFlawedVersion() {
        final ManagementService management = prepareFlawedSystemService();

        final VersionResponse response = management.version();
        assertThat(response.getReturnCode(), is(ReturnCode.ERROR.getCode()));
    }

    @Test
    public void testSettingsAsMember() {
        final ManagementService management = prepareManagementService();
        final SettingRequest request = prepareRequest(SettingRequest.class, MEMBER_1);

        final SettingResponse response = management.settings(request);
        assertThat(response.getReturnCode(), is(ReturnCode.AUTHORIZATION_WARNING.getCode()));
        assertThat(response.getSettings().size(), is(0));
    }

    @Test
    public void testSettings() {
        final ManagementService management = prepareManagementService();
        final SettingRequest request = prepareRequest(SettingRequest.class, Constants.ADMIN_ACCOUNT);

        final SettingResponse response = management.settings(request);
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS.getCode()));
    }

    @Test
    public void testUpdateSettingsWithInvalidData() {
        final ManagementService management = prepareManagementService();
        final SettingRequest request = prepareRequest(SettingRequest.class, Constants.ADMIN_ACCOUNT);
        final Map<String, String> map = request.getSettings();
        map.put(StandardSetting.PBE_ALGORITHM.getKey(), "Hash Them");
        request.setSettings(map);

        final SettingResponse response = management.settings(request);
        assertThat(response.getReturnCode(), is(ReturnCode.SETTING_WARNING.getCode()));
        assertThat(response.getSettings().size(), is(StandardSetting.values().length));
    }

    @Test
    public void testSettingsWithNullRequest() {
        final ManagementService management = prepareManagementService();
        final SettingRequest request = null;

        final SettingResponse response = management.settings(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING.getCode()));
    }

    @Test
    public void testSettingsWithEmptyRequest() {
        final ManagementService management = prepareManagementService();
        final SettingRequest request = new SettingRequest();

        final SettingResponse response = management.settings(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING.getCode()));
    }

    @Test
    public void testFlawedSettings() {
        final ManagementService management = prepareFlawedSystemService();
        final SettingRequest request = null;

        final SettingResponse response = management.settings(request);
        assertThat(response.getReturnCode(), is(ReturnCode.ERROR.getCode()));
    }

    @Test
    public void testMasterKey() {
        final ManagementService management = prepareManagementService();
        final MasterKeyRequest request = prepareRequest(MasterKeyRequest.class, Constants.ADMIN_ACCOUNT);
        request.setSecret(request.getCredential());

        final MasterKeyResponse response = management.masterKey(request);
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS.getCode()));
    }

    @Test
    public void testMAsterKeyWithNullRequest() {
        final ManagementService management = prepareManagementService();
        final MasterKeyRequest request = null;

        final MasterKeyResponse response = management.masterKey(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING.getCode()));
    }

    @Test
    public void testMasterKeyWithEmptyRequest() {
        final ManagementService management = prepareManagementService();
        final MasterKeyRequest request = new MasterKeyRequest();

        final MasterKeyResponse response = management.masterKey(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING.getCode()));
    }

    @Test
    public void testFlawedMasterKey() {
        final ManagementService management = prepareFlawedSystemService();
        final MasterKeyRequest request = null;

        final MasterKeyResponse response = management.masterKey(request);
        assertThat(response.getReturnCode(), is(ReturnCode.ERROR.getCode()));
    }

    @Test
    public void testSanity() {
        final ManagementService management = prepareManagementService();
        final SanityRequest request = prepareRequest(SanityRequest.class, Constants.ADMIN_ACCOUNT);

        final SanityResponse response = management.sanitized(request);
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS.getCode()));
    }

    @Test
    public void testSanityWithNullRequest() {
        final ManagementService management = prepareManagementService();
        final SanityRequest request = null;

        final SanityResponse response = management.sanitized(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING.getCode()));
    }

    @Test
    public void testSanityWithEmptyRequest() {
        final ManagementService management = prepareManagementService();
        final SanityRequest request = new SanityRequest();

        final SanityResponse response = management.sanitized(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING.getCode()));
    }

    @Test
    public void testFlawedSanity() {
        final ManagementService management = prepareFlawedSystemService();
        final SanityRequest request = null;

        final SanityResponse response = management.sanitized(request);
        assertThat(response.getReturnCode(), is(ReturnCode.ERROR.getCode()));
    }

    @Test
    public void testFetchMembers() {
        final ManagementService management = prepareManagementService();
        final FetchMemberRequest request = prepareRequest(FetchMemberRequest.class, MEMBER_1);

        final FetchMemberResponse response = management.fetchMembers(request);
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS.getCode()));
    }

    @Test
    public void testFetchMembersWithNullRequest() {
        final ManagementService management = prepareManagementService();
        final FetchMemberRequest request = null;

        final FetchMemberResponse response = management.fetchMembers(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING.getCode()));
    }

    @Test
    public void testFetchMembersWithEmptyRequest() {
        final ManagementService management = prepareManagementService();
        final FetchMemberRequest request = new FetchMemberRequest();

        final FetchMemberResponse response = management.fetchMembers(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING.getCode()));
    }

    @Test
    public void testFlawedFetchMembers() {
        final ManagementService management = prepareFlawedSystemService();
        final FetchMemberRequest request = null;

        final FetchMemberResponse response = management.fetchMembers(request);
        assertThat(response.getReturnCode(), is(ReturnCode.ERROR.getCode()));
    }

    @Test
    public void testProcessMember() {
        final ManagementService management = prepareManagementService();
        final ProcessMemberRequest request = prepareRequest(ProcessMemberRequest.class, Constants.ADMIN_ACCOUNT);
        request.setAction(Action.INVITE);
        request.setNewAccountName("new Account");

        final ProcessMemberResponse response = management.processMember(request);
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS.getCode()));
    }

    @Test
    public void testProcessMemberWithNullRequest() {
        final ManagementService management = prepareManagementService();
        final ProcessMemberRequest request = null;

        final ProcessMemberResponse response = management.processMember(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING.getCode()));
    }

    @Test
    public void testProcessMemberWithEmptyRequest() {
        final ManagementService management = prepareManagementService();
        final ProcessMemberRequest request = new ProcessMemberRequest();

        final ProcessMemberResponse response = management.processMember(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING.getCode()));
    }

    @Test
    public void testFlawedProcessMember() {
        final ManagementService management = prepareFlawedSystemService();
        final ProcessMemberRequest request = null;

        final ProcessMemberResponse response = management.processMember(request);
        assertThat(response.getReturnCode(), is(ReturnCode.ERROR.getCode()));
    }

    @Test
    public void testFetchCircle() {
        final ManagementService management = prepareManagementService();
        final FetchCircleRequest request = prepareRequest(FetchCircleRequest.class, MEMBER_1);

        final FetchCircleResponse response = management.fetchCircles(request);
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS.getCode()));
    }

    @Test
    public void testFetchCircleWithNullRequest() {
        final ManagementService management = prepareManagementService();
        final FetchCircleRequest request = null;

        final FetchCircleResponse response = management.fetchCircles(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING.getCode()));
    }

    @Test
    public void testFetchCircleWithEmptyRequest() {
        final ManagementService management = prepareManagementService();
        final FetchCircleRequest request = new FetchCircleRequest();

        final FetchCircleResponse response = management.fetchCircles(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING.getCode()));
    }

    @Test
    public void testFlawedFetchCircle() {
        final ManagementService management = prepareFlawedSystemService();
        final FetchCircleRequest request = null;

        final FetchCircleResponse response = management.fetchCircles(request);
        assertThat(response.getReturnCode(), is(ReturnCode.ERROR.getCode()));
    }

    @Test
    public void testProcessCircle() {
        final ManagementService management = prepareManagementService();
        final ProcessCircleRequest request = prepareRequest(ProcessCircleRequest.class, Constants.ADMIN_ACCOUNT);
        request.setAction(Action.CREATE);
        request.setCircleName("Test Circle");
        request.setMemberId(MEMBER_1_ID);

        final ProcessCircleResponse response = management.processCircle(request);
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS.getCode()));
    }

    @Test
    public void testProcessCircleWithNullRequest() {
        final ManagementService management = prepareManagementService();
        final ProcessCircleRequest request = null;

        final ProcessCircleResponse response = management.processCircle(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING.getCode()));
    }

    @Test
    public void testProcessCircleWithEmptyRequest() {
        final ManagementService management = prepareManagementService();
        final ProcessCircleRequest request = new ProcessCircleRequest();

        final ProcessCircleResponse response = management.processCircle(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING.getCode()));
    }

    @Test
    public void testFlawedProcessCircle() {
        final ManagementService management = prepareFlawedSystemService();
        final ProcessCircleRequest request = null;

        final ProcessCircleResponse response = management.processCircle(request);
        assertThat(response.getReturnCode(), is(ReturnCode.ERROR.getCode()));
    }

    @Test
    public void testFetchTrustee() {
        final ManagementService management = prepareManagementService();
        final FetchTrusteeRequest request = prepareRequest(FetchTrusteeRequest.class, MEMBER_1);
        request.setCircleId(CIRCLE_1_ID);

        final FetchTrusteeResponse response = management.fetchTrustees(request);
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS.getCode()));
    }

    @Test
    public void testFetchTrusteeWithNullRequest() {
        final ManagementService management = prepareManagementService();
        final FetchTrusteeRequest request = null;

        final FetchTrusteeResponse response = management.fetchTrustees(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING.getCode()));
    }

    @Test
    public void testFetchTrusteeWithEmptyRequest() {
        final ManagementService management = prepareManagementService();
        final FetchTrusteeRequest request = new FetchTrusteeRequest();

        final FetchTrusteeResponse response = management.fetchTrustees(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING.getCode()));
    }

    @Test
    public void testFlawedFetchTrustee() {
        final ManagementService management = prepareFlawedSystemService();
        final FetchTrusteeRequest request = null;

        final FetchTrusteeResponse response = management.fetchTrustees(request);
        assertThat(response.getReturnCode(), is(ReturnCode.ERROR.getCode()));
    }

    @Test
    public void testProcessTrustee() {
        final ManagementService management = prepareManagementService();
        final ProcessTrusteeRequest request = prepareRequest(ProcessTrusteeRequest.class, MEMBER_1);
        request.setAction(Action.ALTER);
        request.setMemberId(MEMBER_2_ID);
        request.setCircleId(CIRCLE_1_ID);
        request.setTrustLevel(TrustLevel.WRITE);

        final ProcessTrusteeResponse response = management.processTrustee(request);
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS.getCode()));
    }

    @Test
    public void testProcessTrusteeWithNullRequest() {
        final ManagementService management = prepareManagementService();
        final ProcessTrusteeRequest request = null;

        final ProcessTrusteeResponse response = management.processTrustee(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING.getCode()));
    }

    @Test
    public void testProcessTrusteeWithEmptyRequest() {
        final ManagementService management = prepareManagementService();
        final ProcessTrusteeRequest request = new ProcessTrusteeRequest();

        final ProcessTrusteeResponse response = management.processTrustee(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING.getCode()));
    }

    @Test
    public void testFlawedProcessTrustee() {
        final ManagementService management = prepareFlawedSystemService();
        final ProcessTrusteeRequest request = null;

        final ProcessTrusteeResponse response = management.processTrustee(request);
        assertThat(response.getReturnCode(), is(ReturnCode.ERROR.getCode()));
    }
}
