/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-war)
 * =============================================================================
 */
package io.javadog.cws.war;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import io.javadog.cws.api.common.Action;
import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.common.ReturnCode;
import io.javadog.cws.api.dtos.DataType;
import io.javadog.cws.api.dtos.Metadata;
import io.javadog.cws.api.requests.FetchDataRequest;
import io.javadog.cws.api.requests.FetchDataTypeRequest;
import io.javadog.cws.api.requests.FetchSignatureRequest;
import io.javadog.cws.api.requests.ProcessDataRequest;
import io.javadog.cws.api.requests.ProcessDataTypeRequest;
import io.javadog.cws.api.requests.SignRequest;
import io.javadog.cws.api.requests.VerifyRequest;
import io.javadog.cws.api.responses.FetchDataResponse;
import io.javadog.cws.api.responses.FetchDataTypeResponse;
import io.javadog.cws.api.responses.FetchSignatureResponse;
import io.javadog.cws.api.responses.ProcessDataResponse;
import io.javadog.cws.api.responses.ProcessDataTypeResponse;
import io.javadog.cws.api.responses.SignResponse;
import io.javadog.cws.api.responses.VerifyResponse;
import io.javadog.cws.common.exceptions.CWSException;
import io.javadog.cws.model.DatabaseSetup;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Kim Jensen
 * @since CWS 1.0
 */
public class ShareServiceTest extends DatabaseSetup {

    @Test
    public void testProcessDataType() {
        final ShareService service = prepareShareService();
        final ProcessDataTypeRequest request = prepareRequest(ProcessDataTypeRequest.class, Constants.ADMIN_ACCOUNT);
        request.setAction(Action.PROCESS);
        final DataType type = new DataType();
        type.setName("TestType");
        type.setType("Test Type Value");
        request.setDataType(type);

        final ProcessDataTypeResponse response = service.processDataType(request);
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS));
        assertThat(response.getReturnMessage(), is("Ok"));
        assertThat(response.getDataType(), is(not(nullValue())));
        assertThat(response.getDataType().getName(), is("TestType"));
        assertThat(response.getDataType().getType(), is("Test Type Value"));
    }

    @Test
    public void testProcessDataTypeWithNullRequest() {
        final ShareService service = prepareShareService();
        final ProcessDataTypeRequest request = null;

        final ProcessDataTypeResponse response = service.processDataType(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING));
    }

    @Test
    public void testProcessDataTypeWithEmptyRequest() {
        final ShareService service = prepareShareService();
        final ProcessDataTypeRequest request = new ProcessDataTypeRequest();

        final ProcessDataTypeResponse response = service.processDataType(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING));
    }

    @Test
    public void testFlawedProcessDataType() {
        final ShareService service = prepareFlawedShareService();
        final ProcessDataTypeRequest request = new ProcessDataTypeRequest();

        final ProcessDataTypeResponse response = service.processDataType(request);
        assertThat(response.getReturnCode(), is(ReturnCode.ERROR));
    }

    @Test
    public void testFetchDataTypes() {
        final ShareService service = prepareShareService();
        final FetchDataTypeRequest request = prepareRequest(FetchDataTypeRequest.class, "member1");

        final FetchDataTypeResponse response = service.fetchDataTypes(request);
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS));
        assertThat(response.getReturnMessage(), is("Ok"));
        assertThat(response.getTypes().size(), is(2));
        assertThat(response.getTypes().get(0).getName(), is("data"));
        assertThat(response.getTypes().get(0).getType(), is("Data Object"));
        assertThat(response.getTypes().get(1).getName(), is("folder"));
        assertThat(response.getTypes().get(1).getType(), is("Folder"));
    }

    @Test
    public void testFetchDataTypesWithNullRequest() {
        final ShareService service = prepareShareService();
        final FetchDataTypeRequest request = null;

        final FetchDataTypeResponse response = service.fetchDataTypes(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING));
    }

    @Test
    public void testFetchDataTypesWithEmptyRequest() {
        final ShareService service = prepareShareService();
        final FetchDataTypeRequest request = new FetchDataTypeRequest();

        final FetchDataTypeResponse response = service.fetchDataTypes(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING));
    }

    @Test
    public void testFlawedFetchDataTypes() {
        final ShareService service = prepareFlawedShareService();
        final FetchDataTypeRequest request = new FetchDataTypeRequest();

        final FetchDataTypeResponse response = service.fetchDataTypes(request);
        assertThat(response.getReturnCode(), is(ReturnCode.ERROR));
    }

    @Test
    public void testProcessData() {
        final ShareService service = prepareShareService();
        final ProcessDataRequest request = prepareRequest(ProcessDataRequest.class, "member1");
        request.setAction(Action.PROCESS);
        request.setBytes("alfa beta gamma".getBytes(settings.getCharset()));
        final Metadata metadata = new Metadata();
        metadata.setCircleId("d8838d7d-71e7-433d-8790-af7c080e9de9");
        metadata.setName("Data Name");
        metadata.setTypeName("data");
        request.setMetadata(metadata);

        final ProcessDataResponse response = service.processData(request);
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS));
        assertThat(response.getReturnMessage(), is("Ok"));
        assertThat(response.getMetadata().getId(), is(not(nullValue())));
    }

    @Test
    public void testProcessDataWithNullRequest() {
        final ShareService service = prepareShareService();
        final ProcessDataRequest request = null;

        final ProcessDataResponse response = service.processData(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING));
    }

    @Test
    public void testProcessDataWithEmptyRequest() {
        final ShareService service = prepareShareService();
        final ProcessDataRequest request = new ProcessDataRequest();

        final ProcessDataResponse response = service.processData(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING));
    }

    @Test
    public void testFlawedProcessData() {
        final ShareService service = prepareFlawedShareService();
        final ProcessDataRequest request = new ProcessDataRequest();

        final ProcessDataResponse response = service.processData(request);
        assertThat(response.getReturnCode(), is(ReturnCode.ERROR));
    }

    @Test
    public void testFetchData() {
        final ShareService service = prepareShareService();
        final FetchDataRequest request = prepareRequest(FetchDataRequest.class, "member1");
        request.setCircleId("d8838d7d-71e7-433d-8790-af7c080e9de9");

        final FetchDataResponse response = service.fetchData(request);
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS));
        assertThat(response.getReturnMessage(), is("Ok"));
        assertThat(response.getData().isEmpty(), is(true));
    }

    @Test
    public void testFetchDataWithNullRequest() {
        final ShareService service = prepareShareService();
        final FetchDataRequest request = null;

        final FetchDataResponse response = service.fetchData(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING));
    }

    @Test
    public void testFetchDataWithEmptyRequest() {
        final ShareService service = prepareShareService();
        final FetchDataRequest request = new FetchDataRequest();

        final FetchDataResponse response = service.fetchData(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING));
    }

    @Test
    public void testFlawedFetchData() {
        final ShareService service = prepareFlawedShareService();
        final FetchDataRequest request = new FetchDataRequest();

        final FetchDataResponse response = service.fetchData(request);
        assertThat(response.getReturnCode(), is(ReturnCode.ERROR));
    }

    @Test
    public void testSignatures() {
        final ShareService service = prepareShareService();
        final SignRequest signRequest = prepareRequest(SignRequest.class, "member1");
        final byte[] data = "alfa".getBytes(settings.getCharset());
        signRequest.setData(data);
        final SignResponse signResponse = service.sign(signRequest);
        assertThat(signResponse.getReturnCode(), is(ReturnCode.SUCCESS));

        final VerifyRequest verifyRequest = prepareRequest(VerifyRequest.class, "member2");
        verifyRequest.setData(data);
        verifyRequest.setSignature(signResponse.getSignature());
        final VerifyResponse verifyResponse = service.verify(verifyRequest);
        assertThat(verifyResponse.getReturnCode(), is(ReturnCode.SUCCESS));
    }

    @Test
    public void testSignWithNullRequest() {
        final ShareService service = prepareShareService();
        final SignRequest request = null;

        final SignResponse response = service.sign(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING));
    }

    @Test
    public void testSignWithEmptyRequest() {
        final ShareService service = prepareShareService();
        final SignRequest request = new SignRequest();

        final SignResponse response = service.sign(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING));
    }

    @Test
    public void testFlawedSign() {
        final ShareService service = prepareFlawedShareService();
        final SignRequest request = new SignRequest();

        final SignResponse response = service.sign(request);
        assertThat(response.getReturnCode(), is(ReturnCode.ERROR));
    }

    @Test
    public void testVerifyWithNullRequest() {
        final ShareService service = prepareShareService();
        final VerifyRequest request = null;

        final VerifyResponse response = service.verify(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING));
    }

    @Test
    public void testVerifyWithEmptyRequest() {
        final ShareService service = prepareShareService();
        final VerifyRequest request = new VerifyRequest();

        final VerifyResponse response = service.verify(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING));
    }

    @Test
    public void testFlawedVerify() {
        final ShareService service = prepareFlawedShareService();
        final VerifyRequest request = new VerifyRequest();

        final VerifyResponse response = service.verify(request);
        assertThat(response.getReturnCode(), is(ReturnCode.ERROR));
    }

    @Test
    public void testFetchSignatures() {
        final ShareService service = prepareShareService();
        final FetchSignatureRequest request = prepareRequest(FetchSignatureRequest.class, "member1");

        final FetchSignatureResponse response = service.fetchSignatures(request);
        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS));
        assertThat(response.getReturnMessage(), is("Ok"));
        assertThat(response.getSignatures().isEmpty(), is(true));
    }

    @Test
    public void testFetchSignaturesWithNullRequest() {
        final ShareService service = prepareShareService();
        final FetchSignatureRequest request = null;

        final FetchSignatureResponse response = service.fetchSignatures(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING));
    }

    @Test
    public void testFetchSignaturesWithEmptyRequest() {
        final ShareService service = prepareShareService();
        final FetchSignatureRequest request = new FetchSignatureRequest();

        final FetchSignatureResponse response = service.fetchSignatures(request);
        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING));
    }

    @Test
    public void testFlawedFetchSignatures() {
        final ShareService service = prepareFlawedShareService();
        final FetchSignatureRequest request = new FetchSignatureRequest();

        final FetchSignatureResponse response = service.fetchSignatures(request);
        assertThat(response.getReturnCode(), is(ReturnCode.ERROR));
    }

    // =========================================================================
    // Internal Methods
    // =========================================================================

    private static ShareService prepareFlawedShareService() {
        try {
            final ShareService service = ShareService.class.getConstructor().newInstance();
            setField(service, "bean", null);

            return service;
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new CWSException(ReturnCode.ERROR, "Cannot instantiate Service Object", e);
        }
    }

    private ShareService prepareShareService() {
        try {
            final ShareBean bean = ShareBean.class.getConstructor().newInstance();
            setField(bean, "entityManager", entityManager);

            final ShareService service = ShareService.class.getConstructor().newInstance();
            setField(service, "bean", bean);

            return service;
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new CWSException(ReturnCode.ERROR, "Cannot instantiate Service Object", e);
        }
    }

    private static void setField(final Object instance, final String fieldName, final Object value) {
        try {
            final Class<?> clazz = instance.getClass();
            final Field field;

            field = clazz.getDeclaredField(fieldName);
            final boolean accessible = field.isAccessible();

            field.setAccessible(true);
            field.set(instance, value);
            field.setAccessible(accessible);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new CWSException(ReturnCode.ERROR, "Cannot set Field", e);
        }
    }
}