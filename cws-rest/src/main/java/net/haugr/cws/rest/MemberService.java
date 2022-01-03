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
package net.haugr.cws.rest;

import net.haugr.cws.api.common.Action;
import net.haugr.cws.api.common.Constants;
import net.haugr.cws.api.common.ReturnCode;
import net.haugr.cws.api.requests.FetchMemberRequest;
import net.haugr.cws.api.requests.ProcessMemberRequest;
import net.haugr.cws.api.responses.FetchMemberResponse;
import net.haugr.cws.api.responses.ProcessMemberResponse;
import net.haugr.cws.core.ManagementBean;
import net.haugr.cws.core.misc.LoggingUtil;
import net.haugr.cws.core.model.Settings;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * <p>REST interface for the Member functionality.</p>
 *
 * @author Kim Jensen
 * @since CWS 1.0
 */
@Path(Constants.REST_MEMBERS_BASE)
public class MemberService {

    private static final Logger LOG = Logger.getLogger(MemberService.class.getName());

    @Inject
    private ManagementBean bean;
    private final Settings settings = Settings.getInstance();

    @POST
    @Consumes(RestUtils.CONSUMES)
    @Produces(RestUtils.PRODUCES)
    @Path(Constants.REST_MEMBERS_CREATE)
    public Response create(@NotNull final ProcessMemberRequest createMemberRequest) {
        return processMember(createMemberRequest, Action.CREATE, Constants.REST_MEMBERS_CREATE);
    }

    @POST
    @Consumes(RestUtils.CONSUMES)
    @Produces(RestUtils.PRODUCES)
    @Path(Constants.REST_MEMBERS_INVITE)
    public Response invite(@NotNull final ProcessMemberRequest inviteMemberRequest) {
        return processMember(inviteMemberRequest, Action.INVITE, Constants.REST_MEMBERS_INVITE);
    }

    @POST
    @Consumes(RestUtils.CONSUMES)
    @Produces(RestUtils.PRODUCES)
    @Path(Constants.REST_MEMBERS_LOGIN)
    public Response login(@NotNull final ProcessMemberRequest updateMemberRequest) {
        return processMember(updateMemberRequest, Action.LOGIN, Constants.REST_MEMBERS_LOGIN);
    }

    @POST
    @Consumes(RestUtils.CONSUMES)
    @Produces(RestUtils.PRODUCES)
    @Path(Constants.REST_MEMBERS_LOGOUT)
    public Response logout(@NotNull final ProcessMemberRequest updateMemberRequest) {
        return processMember(updateMemberRequest, Action.LOGOUT, Constants.REST_MEMBERS_LOGOUT);
    }

    @POST
    @Consumes(RestUtils.CONSUMES)
    @Produces(RestUtils.PRODUCES)
    @Path(Constants.REST_MEMBERS_ALTER)
    public Response alter(@NotNull final ProcessMemberRequest updateMemberRequest) {
        return processMember(updateMemberRequest, Action.ALTER, Constants.REST_MEMBERS_ALTER);
    }

    @POST
    @Consumes(RestUtils.CONSUMES)
    @Produces(RestUtils.PRODUCES)
    @Path(Constants.REST_MEMBERS_UPDATE)
    public Response update(@NotNull final ProcessMemberRequest updateMemberRequest) {
        return processMember(updateMemberRequest, Action.UPDATE, Constants.REST_MEMBERS_UPDATE);
    }

    @POST
    @Consumes(RestUtils.CONSUMES)
    @Produces(RestUtils.PRODUCES)
    @Path(Constants.REST_MEMBERS_INVALIDATE)
    public Response invalidate(@NotNull final ProcessMemberRequest invalidateRequest) {
        return processMember(invalidateRequest, Action.INVALIDATE, Constants.REST_MEMBERS_INVALIDATE);
    }

    @POST
    @Consumes(RestUtils.CONSUMES)
    @Produces(RestUtils.PRODUCES)
    @Path(Constants.REST_MEMBERS_DELETE)
    public Response delete(@NotNull final ProcessMemberRequest deleteMemberRequest) {
        return processMember(deleteMemberRequest, Action.DELETE, Constants.REST_MEMBERS_DELETE);
    }

    @POST
    @Consumes(RestUtils.CONSUMES)
    @Produces(RestUtils.PRODUCES)
    @Path(Constants.REST_MEMBERS_FETCH)
    public Response fetch(@NotNull final FetchMemberRequest fetchMembersRequest) {
        final String restAction = Constants.REST_MEMBERS_BASE + Constants.REST_MEMBERS_FETCH;
        final long startTime = System.nanoTime();
        FetchMemberResponse response;

        try {
            response = bean.fetchMembers(fetchMembersRequest);
            LOG.log(Settings.INFO, () -> LoggingUtil.requestDuration(settings.getLocale(), restAction, startTime));
        } catch (RuntimeException e) {
            LOG.log(Settings.ERROR, () -> LoggingUtil.requestDuration(settings.getLocale(), restAction, startTime, e));
            response = new FetchMemberResponse(ReturnCode.ERROR, e.getMessage());
        }

        return RestUtils.buildResponse(response);
    }

    private Response processMember(final ProcessMemberRequest request, final Action action, final String logAction) {
        final String restAction = Constants.REST_MEMBERS_BASE + logAction;
        final long startTime = System.nanoTime();
        ProcessMemberResponse response;

        try {
            request.setAction(action);
            response = bean.processMember(request);
            LOG.log(Settings.INFO, () -> LoggingUtil.requestDuration(settings.getLocale(), restAction, startTime));
        } catch (RuntimeException e) {
            LOG.log(Settings.ERROR, () -> LoggingUtil.requestDuration(settings.getLocale(), restAction, startTime, e));
            response = new ProcessMemberResponse(ReturnCode.ERROR, e.getMessage());
        }

        return RestUtils.buildResponse(response);
    }
}
