/*
 * =============================================================================
 * Copyright (c) 2016-2018, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-api)
 * =============================================================================
 */
package io.javadog.cws.api.responses;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import io.javadog.cws.api.common.ReturnCode;
import org.junit.Test;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public final class VerifyResponseTest {

    @Test
    public void testClassflow() {
        final VerifyResponse response = new VerifyResponse();
        response.setVerified(true);

        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS));
        assertThat(response.getReturnMessage(), is("Ok"));
        assertThat(response.isOk(), is(true));
        assertThat(response.isVerified(), is(true));
    }

    @Test
    public void testError() {
        final String msg = "Verify Request failed due to Verification Problems.";
        final VerifyResponse response = new VerifyResponse(ReturnCode.VERIFICATION_WARNING, msg);

        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING));
        assertThat(response.getReturnMessage(), is(msg));
        assertThat(response.isOk(), is(false));
        assertThat(response.isVerified(), is(false));
    }
}
