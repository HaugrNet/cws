/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-api)
 * =============================================================================
 */
package io.javadog.cws.api;

import io.javadog.cws.api.requests.FetchObjectRequest;
import io.javadog.cws.api.requests.FetchObjectTypeRequest;
import io.javadog.cws.api.requests.ProcessObjectRequest;
import io.javadog.cws.api.requests.ProcessObjectTypeRequest;
import io.javadog.cws.api.requests.SignRequest;
import io.javadog.cws.api.requests.VerifyRequest;
import io.javadog.cws.api.responses.FetchObjectResponse;
import io.javadog.cws.api.responses.FetchObjectTypeResponse;
import io.javadog.cws.api.responses.ProcessObjectResponse;
import io.javadog.cws.api.responses.ProcessObjectTypeResponse;
import io.javadog.cws.api.responses.SignResponse;
import io.javadog.cws.api.responses.VerifyResponse;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public interface Share {

    ProcessObjectTypeResponse processObjectType(ProcessObjectTypeRequest request);
    FetchObjectTypeResponse fetchObjectTypes(FetchObjectTypeRequest request);

    ProcessObjectResponse processObject(ProcessObjectRequest request);
    FetchObjectResponse fetchObject(FetchObjectRequest request);

    SignResponse sign(SignRequest request);
    VerifyResponse verify(VerifyRequest request);
}
