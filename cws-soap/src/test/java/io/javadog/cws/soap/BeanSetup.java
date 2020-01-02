/*
 * CWS, Cryptographic Web Store - open source Cryptographic Storage system.
 * Copyright (C) 2016-2020, JavaDog.io
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
package io.javadog.cws.soap;

import io.javadog.cws.api.common.ReturnCode;
import io.javadog.cws.core.DatabaseSetup;
import io.javadog.cws.core.ManagementBean;
import io.javadog.cws.core.ShareBean;
import io.javadog.cws.core.exceptions.CWSException;
import io.javadog.cws.core.model.Settings;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Kim Jensen
 * @since CWS 1.0
 */
public class BeanSetup extends DatabaseSetup {

    protected static ShareService prepareFlawedShareService() {
        try {
            final ShareService service = ShareService.class.getConstructor().newInstance();
            setField(service, "bean", null);

            return service;
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new CWSException(ReturnCode.ERROR, "Cannot instantiate Service Object", e);
        }
    }

    protected ShareService prepareShareService() {
        try {
            final ShareBean bean = ShareBean.class.getConstructor().newInstance();
            setField(bean, "entityManager", entityManager);

            final ShareService service = ShareService.class.getConstructor().newInstance();
            setField(service, "bean", bean);

            return service;
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new CWSException(ReturnCode.ERROR, "Cannot instantiate Service Object", e);
        }
    }

    protected ManagementService prepareFlawedManagementService() {
        try {
            final ManagementService service = ManagementService.class.getConstructor().newInstance();
            setField(service, "bean", null);

            return service;
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new CWSException(ReturnCode.ERROR, "Cannot instantiate Service Object", e);
        }
    }

    protected ManagementService prepareManagementService() {
        return prepareManagementService(settings);
    }

    protected ManagementService prepareManagementService(final Settings customSettings) {
        try {
            final ManagementBean bean = ManagementBean.class.getConstructor().newInstance();
            setField(bean, "entityManager", entityManager);
            setField(bean, "settings", customSettings);

            final ManagementService service = ManagementService.class.getConstructor().newInstance();
            setField(bean, "settings", customSettings);
            setField(service, "bean", bean);

            return service;
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new CWSException(ReturnCode.ERROR, "Cannot instantiate Service Object", e);
        }
    }
}
