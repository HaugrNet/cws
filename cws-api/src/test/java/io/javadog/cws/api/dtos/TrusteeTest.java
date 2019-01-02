/*
 * CWS, Cryptographic Web Store - open source Cryptographic Storage system.
 * Copyright (C) 2016-2019, JavaDog.io
 * mailto: cws AT JavaDog DOT io
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
package io.javadog.cws.api.dtos;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import io.javadog.cws.api.common.TrustLevel;
import io.javadog.cws.api.common.Utilities;
import java.util.Date;
import java.util.UUID;
import org.junit.Test;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public final class TrusteeTest {

    @Test
    public void testClassflow() {
        final String memberId = UUID.randomUUID().toString();
        final String publicKey = UUID.randomUUID().toString();
        final String circleId = UUID.randomUUID().toString();
        final TrustLevel trustLevel = TrustLevel.WRITE;
        final Date lastModified = new Date(456L);
        final Date added = new Date(123L);

        final Trustee trustee = new Trustee();
        trustee.setMemberId(memberId);
        trustee.setPublicKey(publicKey);
        trustee.setCircleId(circleId);
        trustee.setTrustLevel(trustLevel);
        trustee.setChanged(lastModified);
        trustee.setAdded(added);

        assertThat(trustee.getMemberId(), is(memberId));
        assertThat(trustee.getPublicKey(), is(publicKey));
        assertThat(trustee.getCircleId(), is(circleId));
        assertThat(trustee.getTrustLevel(), is(trustLevel));
        assertThat(trustee.getChanged(), is(lastModified));
        assertThat(trustee.getAdded(), is(added));
    }

    @Test
    public void testStandardMethods() {
        final Trustee trustee = new Trustee();
        final Trustee sameTrustee = new Trustee();
        final Trustee emptyTrustee = new Trustee();

        trustee.setMemberId(UUID.randomUUID().toString());
        trustee.setPublicKey(UUID.randomUUID().toString());
        trustee.setCircleId(UUID.randomUUID().toString());
        trustee.setTrustLevel(TrustLevel.WRITE);
        trustee.setChanged(Utilities.newDate(456L));
        trustee.setAdded(Utilities.newDate(123L));
        sameTrustee.setMemberId(trustee.getMemberId());
        sameTrustee.setPublicKey(trustee.getPublicKey());
        sameTrustee.setCircleId(trustee.getCircleId());
        sameTrustee.setTrustLevel(trustee.getTrustLevel());
        sameTrustee.setChanged(trustee.getChanged());
        sameTrustee.setAdded(trustee.getAdded());

        assertThat(trustee.toString(), is(sameTrustee.toString()));
        assertThat(trustee.toString(), is(not(emptyTrustee.toString())));
    }
}
