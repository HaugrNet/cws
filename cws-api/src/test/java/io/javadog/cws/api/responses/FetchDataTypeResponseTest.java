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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.javadog.cws.api.common.ReturnCode;
import io.javadog.cws.api.dtos.DataType;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author Kim Jensen
 * @since CWS 1.0
 */
public final class FetchDataTypeResponseTest {

    @Test
    public void testClassflow() {
        final List<DataType> dataTypes = new ArrayList<>(3);
        dataTypes.add(new DataType());
        dataTypes.add(new DataType());
        dataTypes.add(new DataType());

        final FetchDataTypeResponse response = new FetchDataTypeResponse();
        response.setDataTypes(dataTypes);

        assertEquals(ReturnCode.SUCCESS.getCode(), response.getReturnCode());
        assertEquals("Ok", response.getReturnMessage());
        assertTrue(response.isOk());
        assertEquals(dataTypes, response.getDataTypes());
    }

    @Test
    public void testError() {
        final String msg = "FetchDataType Request failed due to Verification Problems.";
        final FetchDataTypeResponse response = new FetchDataTypeResponse(ReturnCode.VERIFICATION_WARNING, msg);

        assertEquals(ReturnCode.VERIFICATION_WARNING.getCode(), response.getReturnCode());
        assertEquals(msg, response.getReturnMessage());
        assertFalse(response.isOk());
        assertTrue(response.getDataTypes().isEmpty());
    }
}
