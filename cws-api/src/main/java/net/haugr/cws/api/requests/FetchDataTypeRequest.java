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
package net.haugr.cws.api.requests;

import net.haugr.cws.api.Share;
import net.haugr.cws.api.common.Constants;

/**
 * <p>When the Share Request 'FetchDataTypeRequest' is invoked, it requires a
 * Request Object, with the Authentication information.</p>
 *
 * <p>For more details, please see the 'fetchCircles' request in the Management
 * interface: {@link Share#fetchDataTypes(FetchDataTypeRequest)}</p>
 *
 * @author Kim Jensen
 * @since CWS 1.0
 */
public final class FetchDataTypeRequest extends Authentication {

    /** {@link Constants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

}