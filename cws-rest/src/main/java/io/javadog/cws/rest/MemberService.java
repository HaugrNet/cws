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
import io.javadog.cws.api.requests.FetchMemberRequest;
import io.javadog.cws.api.requests.ProcessMemberRequest;
import io.javadog.cws.api.responses.FetchMemberResponse;
import io.javadog.cws.api.responses.ProcessMemberResponse;
import io.javadog.cws.core.SettingBean;
import io.javadog.cws.core.SystemBean;
import io.javadog.cws.core.misc.LoggingUtil;
import io.javadog.cws.core.model.Settings;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
@Path("/members")
public class MemberService {

    private static final Logger log = Logger.getLogger(MemberService.class.getName());

    @Inject private SettingBean settings;
    @Inject private SystemBean bean;

    @POST
    @Path("/createMember")
    @Consumes(CwsApplication.CONSUMES)
    @Produces(CwsApplication.PRODUCES)
    public Response create(@NotNull final ProcessMemberRequest createMemberRequest) {
        return processMember(createMemberRequest, Action.CREATE, "createMember");
    }

    @POST
    @Path("/inviteMember")
    @Consumes(CwsApplication.CONSUMES)
    @Produces(CwsApplication.PRODUCES)
    public Response invite(@NotNull final ProcessMemberRequest inviteMemberRequest) {
        return processMember(inviteMemberRequest, Action.INVITE, "inviteMember");
    }

    @POST
    @Path("/updateMember")
    @Consumes(CwsApplication.CONSUMES)
    @Produces(CwsApplication.PRODUCES)
    public Response update(@NotNull final ProcessMemberRequest updateMemberRequest) {
        return processMember(updateMemberRequest, Action.UPDATE, "updateMember");
    }

    @POST
    @DELETE
    @Path("/deleteMember")
    @Consumes(CwsApplication.CONSUMES)
    @Produces(CwsApplication.PRODUCES)
    public Response delete(@NotNull final ProcessMemberRequest deleteMemberRequest) {
        return processMember(deleteMemberRequest, Action.DELETE, "deleteMember");
    }

    @POST
    @Path("/fetchMembers")
    @Consumes(CwsApplication.CONSUMES)
    @Produces(CwsApplication.PRODUCES)
    public Response fetch(@NotNull final FetchMemberRequest fetchMembersRequest) {
        final Long startTime = System.nanoTime();
        FetchMemberResponse response;

        try {
            response = bean.fetchMembers(fetchMembersRequest);
            log.log(Settings.INFO, () -> LoggingUtil.requestDuration(settings.getSettings().getLocale(), "fetchMembers", startTime));
        } catch (RuntimeException e) {
            log.log(Settings.ERROR, () -> LoggingUtil.requestDuration(settings.getSettings().getLocale(), "fetchMembers", startTime, e));
            response = new FetchMemberResponse(ReturnCode.ERROR, e.getMessage());
        }

        return CwsApplication.buildResponse(response);
    }

    private Response processMember(final ProcessMemberRequest request, final Action action, final String logAction) {
        final Long startTime = System.nanoTime();
        ProcessMemberResponse response;

        try {
            request.setAction(action);
            response = bean.processMember(request);
            log.log(Settings.INFO, () -> LoggingUtil.requestDuration(settings.getSettings().getLocale(), logAction, startTime));
        } catch (RuntimeException e) {
            log.log(Settings.ERROR, () -> LoggingUtil.requestDuration(settings.getSettings().getLocale(), logAction, startTime, e));
            response = new ProcessMemberResponse(ReturnCode.ERROR, e.getMessage());
        }

        return CwsApplication.buildResponse(response);
    }
}