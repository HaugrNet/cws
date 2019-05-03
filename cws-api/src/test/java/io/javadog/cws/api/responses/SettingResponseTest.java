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
package io.javadog.cws.api.responses;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import io.javadog.cws.api.common.ReturnCode;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public final class SettingResponseTest {

    @Test
    public void testClassflow() {
        final Map<String, String> settings = new HashMap<>();
        settings.put("key1", "value1");
        settings.put("key2", "value2");
        settings.put("key3", "value3");

        final SettingResponse response = new SettingResponse();
        response.setSettings(settings);

        assertThat(response.getReturnCode(), is(ReturnCode.SUCCESS.getCode()));
        assertThat(response.getReturnMessage(), is("Ok"));
        assertThat(response.isOk(), is(true));
        assertThat(response.getSettings(), is(settings));
    }

    @Test
    public void testError() {
        final String msg = "Setting Request failed due to Verification Problems.";
        final SettingResponse response = new SettingResponse(ReturnCode.VERIFICATION_WARNING, msg);

        assertThat(response.getReturnCode(), is(ReturnCode.VERIFICATION_WARNING.getCode()));
        assertThat(response.getReturnMessage(), is(msg));
        assertThat(response.isOk(), is(false));
        assertThat(response.getSettings().isEmpty(), is(true));
    }
}
