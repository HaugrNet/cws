/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-war)
 * =============================================================================
 */
package io.javadog.cws.war;

import static io.javadog.cws.war.CommonBean.destroy;

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
import io.javadog.cws.core.Serviceable;
import io.javadog.cws.core.services.FetchDataService;
import io.javadog.cws.core.services.FetchDataTypeService;
import io.javadog.cws.core.services.FetchSignatureService;
import io.javadog.cws.core.services.ProcessDataService;
import io.javadog.cws.core.services.ProcessDataTypeService;
import io.javadog.cws.core.services.SignService;
import io.javadog.cws.core.services.VerifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
@Stateless
public class ShareBean {

    private static final Logger log = LoggerFactory.getLogger(ShareBean.class);

    @PersistenceContext(unitName = "cwsDS")
    private EntityManager entityManager;

    @Inject
    private SettingBean settingBean;

    @Transactional(Transactional.TxType.REQUIRED)
    public ProcessDataTypeResponse processDataType(final ProcessDataTypeRequest request) {
        Serviceable<ProcessDataTypeResponse, ProcessDataTypeRequest> service = null;
        ProcessDataTypeResponse response;

        try {
            service = new ProcessDataTypeService(settingBean.getSettings(), entityManager);
            response = service.perform(request);
        } catch (CWSException e) {
            // Any Warning or Error thrown by the CWS contain enough information
            // so it can be dealt with by the requesting System. Logging the
            // error is thus not needed, as all information is provided in the
            // response.
            log.trace(e.getMessage(), e);
            response = new ProcessDataTypeResponse(e.getReturnCode(), e.getMessage());
        } finally {
            destroy(service);
        }

        return response;
    }

    @Transactional(Transactional.TxType.NEVER)
    public FetchDataTypeResponse fetchDataTypes(final FetchDataTypeRequest request) {
        Serviceable<FetchDataTypeResponse, FetchDataTypeRequest> service = null;
        FetchDataTypeResponse response;

        try {
            service = new FetchDataTypeService(settingBean.getSettings(), entityManager);
            response = service.perform(request);
        } catch (CWSException e) {
            // Any Warning or Error thrown by the CWS contain enough information
            // so it can be dealt with by the requesting System. Logging the
            // error is thus not needed, as all information is provided in the
            // response.
            log.trace(e.getMessage(), e);
            response = new FetchDataTypeResponse(e.getReturnCode(), e.getMessage());
        } finally {
            destroy(service);
        }

        return response;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public ProcessDataResponse processData(final ProcessDataRequest request) {
        Serviceable<ProcessDataResponse, ProcessDataRequest> service = null;
        ProcessDataResponse response;

        try {
            service = new ProcessDataService(settingBean.getSettings(), entityManager);
            response = service.perform(request);
        } catch (CWSException e) {
            // Any Warning or Error thrown by the CWS contain enough information
            // so it can be dealt with by the requesting System. Logging the
            // error is thus not needed, as all information is provided in the
            // response.
            log.trace(e.getMessage(), e);
            response = new ProcessDataResponse(e.getReturnCode(), e.getMessage());
        } finally {
            destroy(service);
        }

        return response;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public FetchDataResponse fetchData(final FetchDataRequest request) {
        Serviceable<FetchDataResponse, FetchDataRequest> service = null;
        FetchDataResponse response;

        try {
            service = new FetchDataService(settingBean.getSettings(), entityManager);
            response = service.perform(request);
        } catch (CWSException e) {
            // Any Warning or Error thrown by the CWS contain enough information
            // so it can be dealt with by the requesting System. Logging the
            // error is thus not needed, as all information is provided in the
            // response.
            log.trace(e.getMessage(), e);
            response = new FetchDataResponse(e.getReturnCode(), e.getMessage());
        } finally {
            destroy(service);
        }

        return response;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public SignResponse sign(final SignRequest request) {
        Serviceable<SignResponse, SignRequest> service = null;
        SignResponse response;

        try {
            service = new SignService(settingBean.getSettings(), entityManager);
            response = service.perform(request);
        } catch (CWSException e) {
            // Any Warning or Error thrown by the CWS contain enough information
            // so it can be dealt with by the requesting System. Logging the
            // error is thus not needed, as all information is provided in the
            // response.
            log.trace(e.getMessage(), e);
            response = new SignResponse(e.getReturnCode(), e.getMessage());
        } finally {
            destroy(service);
        }

        return response;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public VerifyResponse verify(final VerifyRequest request) {
        Serviceable<VerifyResponse, VerifyRequest> service = null;
        VerifyResponse response;

        try {
            service = new VerifyService(settingBean.getSettings(), entityManager);
            response = service.perform(request);
        } catch (CWSException e) {
            // Any Warning or Error thrown by the CWS contain enough information
            // so it can be dealt with by the requesting System. Logging the
            // error is thus not needed, as all information is provided in the
            // response.
            log.trace(e.getMessage(), e);
            response = new VerifyResponse(e.getReturnCode(), e.getMessage());
        } finally {
            destroy(service);
        }

        return response;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public FetchSignatureResponse fetchSignatures(final FetchSignatureRequest request) {
        Serviceable<FetchSignatureResponse, FetchSignatureRequest> service = null;
        FetchSignatureResponse response;

        try {
            service = new FetchSignatureService(settingBean.getSettings(), entityManager);
            response = service.perform(request);
        } catch (CWSException e) {
            // Any Warning or Error thrown by the CWS contain enough information
            // so it can be dealt with by the requesting System. Logging the
            // error is thus not needed, as all information is provided in the
            // response.
            log.trace(e.getMessage(), e);
            response = new FetchSignatureResponse(e.getReturnCode(), e.getMessage());
        } finally {
            destroy(service);
        }

        return response;
    }
}
