/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-rest)
 * =============================================================================
 */
package io.javadog.cws.rest;

import io.javadog.cws.api.common.Action;
import io.javadog.cws.api.common.ReturnCode;
import io.javadog.cws.api.requests.FetchDataTypeRequest;
import io.javadog.cws.api.requests.ProcessDataTypeRequest;
import io.javadog.cws.api.responses.FetchDataTypeResponse;
import io.javadog.cws.api.responses.ProcessDataTypeResponse;
import io.javadog.cws.core.ShareBean;
import io.javadog.cws.core.misc.StringUtil;
import io.javadog.cws.core.model.Settings;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
@Path("/dataTypes")
@Consumes(MediaType.APPLICATION_JSON)
public class DataTypeService {

    private static final Logger log = Logger.getLogger(DataTypeService.class.getName());

    @Inject private ShareBean bean;

    @POST
    @Path("/processDataType")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response process(@NotNull final ProcessDataTypeRequest request) {
        ReturnCode returnCode = ReturnCode.ERROR;
        ProcessDataTypeResponse response = null;

        try {
            final Long startTime = System.nanoTime();
            request.setAction(Action.PROCESS);
            response = bean.processDataType(request);
            returnCode = response.getReturnCode();
            log.log(Settings.INFO, () -> StringUtil.durationSince("processDataType", startTime));
        } catch (RuntimeException e) {
            log.log(Settings.ERROR, e.getMessage(), e);
        }

        return Response.status(returnCode.getHttpCode()).entity(response).build();
    }

    @POST
    @DELETE
    @Path("/deleteDataType")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@NotNull final ProcessDataTypeRequest request) {
        ReturnCode returnCode = ReturnCode.ERROR;
        ProcessDataTypeResponse response = null;

        try {
            final Long startTime = System.nanoTime();
            request.setAction(Action.DELETE);
            response = bean.processDataType(request);
            returnCode = response.getReturnCode();
            log.log(Settings.INFO, () -> StringUtil.durationSince("deleteDataType", startTime));
        } catch (RuntimeException e) {
            log.log(Settings.ERROR, e.getMessage(), e);
        }

        return Response.status(returnCode.getHttpCode()).entity(response).build();
    }

    @POST
    @Path("/fetchDataTypes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetch(@NotNull final FetchDataTypeRequest request) {
        ReturnCode returnCode = ReturnCode.ERROR;
        FetchDataTypeResponse response = null;

        try {
            final Long startTime = System.nanoTime();
            response = bean.fetchDataTypes(request);
            returnCode = response.getReturnCode();
            log.log(Settings.INFO, () -> StringUtil.durationSince("fetchDataTypes", startTime));
        } catch (RuntimeException e) {
            log.log(Settings.ERROR, e.getMessage(), e);
        }

        return Response.status(returnCode.getHttpCode()).entity(response).build();
    }
}
