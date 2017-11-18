/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-client)
 * =============================================================================
 */
package io.javadog.cws.client;

import io.javadog.cws.api.common.Action;
import io.javadog.cws.api.common.CredentialType;
import io.javadog.cws.api.common.ReturnCode;
import io.javadog.cws.api.common.TrustLevel;
import io.javadog.cws.api.dtos.Circle;
import io.javadog.cws.api.dtos.DataType;
import io.javadog.cws.api.dtos.Member;
import io.javadog.cws.api.dtos.Metadata;
import io.javadog.cws.api.dtos.Signature;
import io.javadog.cws.api.dtos.Trustee;
import io.javadog.cws.api.requests.Authentication;
import io.javadog.cws.api.responses.CwsResponse;
import io.javadog.cws.ws.CwsResult;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public final class Mapper {

    /**
     * Private Constructor, this is a utility Class.
     */
    private Mapper() {
    }

    // =========================================================================
    // Mapping of Standard Request & Response information
    // =========================================================================

    public static void fillAuthentication(final io.javadog.cws.ws.Authentication ws, final Authentication api) {
        ws.setAccountName(api.getAccountName());
        ws.setCredential(api.getCredential());
        ws.setCredentialType(map(api.getCredentialType()));
    }

    public static void fillResponse(final CwsResponse api, final CwsResult ws) {
        api.setReturnCode(map(ws.getReturnCode()));
        api.setReturnMessage(ws.getReturnMessage());
    }

    // =========================================================================
    // Mapping of Enum types
    // =========================================================================

    private static io.javadog.cws.ws.CredentialType map(final CredentialType api) {
        return (api != null) ? io.javadog.cws.ws.CredentialType.valueOf(api.name()) : null;
    }

    public static io.javadog.cws.ws.Action map(final Action api) {
        return (api != null) ? io.javadog.cws.ws.Action.valueOf(api.name()) : null;
    }

    private static ReturnCode map(final io.javadog.cws.ws.ReturnCode ws) {
        return (ws != null) ? ReturnCode.valueOf(ws.name()) : null;
    }

    private static TrustLevel map(final io.javadog.cws.ws.TrustLevel ws) {
        return (ws != null) ? TrustLevel.valueOf(ws.name()) : null;
    }

    public static io.javadog.cws.ws.TrustLevel map(final TrustLevel api) {
        return (api != null) ? io.javadog.cws.ws.TrustLevel.valueOf(api.name()) : null;
    }

    // =========================================================================
    // Mapping of Collections
    // =========================================================================

    public static List<Circle> mapCircles(final List<io.javadog.cws.ws.Circle> ws) {
        final List<Circle> api = new ArrayList<>();

        if (ws != null) {
            for (final io.javadog.cws.ws.Circle wsCircle : ws) {
                final Circle circle = new Circle();
                circle.setCircleId(wsCircle.getCircleId());
                circle.setCircleName(wsCircle.getCircleName());
                circle.setAdded(map(wsCircle.getAdded()));
                api.add(circle);
            }
        }

        return api;
    }

    public static List<DataType> mapDataTypes(final List<io.javadog.cws.ws.DataType> ws) {
        final List<DataType> api = new ArrayList<>();

        for (final io.javadog.cws.ws.DataType wsDataType : ws) {
            api.add(map(wsDataType));
        }

        return api;
    }

    public static List<Member> mapMembers(final List<io.javadog.cws.ws.Member> ws) {
        final List<Member> api = new ArrayList<>();

        for (final io.javadog.cws.ws.Member wsMember : ws) {
            api.add(map(wsMember));
        }

        return api;
    }

    public static List<Metadata> mapMetadata(final List<io.javadog.cws.ws.Metadata> ws) {
        final List<Metadata> api = new ArrayList<>();

        for (final io.javadog.cws.ws.Metadata wsMetadata : ws) {
            final Metadata metadata = new Metadata();
            metadata.setDataId(wsMetadata.getDataId());
            metadata.setCircleId(wsMetadata.getCircleId());
            metadata.setFolderId(wsMetadata.getFolderId());
            metadata.setDataName(wsMetadata.getDataName());
            metadata.setDataType(map(wsMetadata.getDataType()));
            metadata.setAdded(map(wsMetadata.getAdded()));

            api.add(metadata);
        }

        return api;
    }

    public static List<Signature> mapSignatures(final List<io.javadog.cws.ws.Signature> ws) {
        final List<Signature> api = new ArrayList<>();

        for (final io.javadog.cws.ws.Signature wsSignature : ws) {
            final Signature signature = new Signature();
            signature.setChecksum(wsSignature.getChecksum());
            signature.setExpires(map(wsSignature.getExpires()));
            signature.setVerifications(wsSignature.getVerifications());
            signature.setLastVerification(map(wsSignature.getLastVerification()));
            signature.setAdded(map(wsSignature.getAdded()));

            api.add(signature);
        }

        return api;
    }

    public static List<Trustee> mapTrustees(final List<io.javadog.cws.ws.Trustee> ws) {
        final List<Trustee> api = new ArrayList<>();

        for (final io.javadog.cws.ws.Trustee wsTrustee : ws) {
            final Trustee trustee = new Trustee();
            trustee.setCircle(map(wsTrustee.getCircle()));
            trustee.setMember(map(wsTrustee.getMember()));
            trustee.setTrustLevel(map(wsTrustee.getTrustLevel()));
            trustee.setChanged(map(wsTrustee.getChanged()));
            trustee.setAdded(map(wsTrustee.getAdded()));
            api.add(trustee);
        }

        return api;
    }

    // =========================================================================
    // Mapping of Internal Object Types
    // =========================================================================

    private static Circle map(final io.javadog.cws.ws.Circle ws) {
        Circle api = null;

        if (ws != null) {
            api = new Circle();
            api.setCircleId(ws.getCircleId());
            api.setCircleName(ws.getCircleName());
            api.setAdded(map(ws.getAdded()));
        }

        return api;
    }

    public static DataType map(final io.javadog.cws.ws.DataType ws) {
        DataType api = null;

        if (ws != null) {
            api = new DataType();
            api.setTypeName(ws.getTypeName());
            api.setType(ws.getType());
        }

        return api;
    }

    private static Member map(final io.javadog.cws.ws.Member ws) {
        Member api = null;

        if (ws != null) {
            api = new Member();
            api.setMemberId(ws.getMemberId());
            api.setAccountName(ws.getAccountName());
            api.setAdded(map(ws.getAdded()));
        }

        return api;
    }

    private static Date map(final XMLGregorianCalendar ws) {
        Date api = null;

        if (ws != null) {
            api = ws.toGregorianCalendar().getTime();
        }

        return api;
    }

    public static XMLGregorianCalendar map(final Date api) {
        XMLGregorianCalendar ws = null;

        if (api != null) {
            final GregorianCalendar calendar = new GregorianCalendar();
            // Throws a NullPointerException without the null check
            calendar.setTime(api);

            try {
                ws = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            } catch (DatatypeConfigurationException e) {
                throw new CWSClientException(e);
            }
        }

        return ws;
    }
}
