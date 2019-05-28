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
package io.javadog.cws.core.model.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import io.javadog.cws.core.DatabaseSetup;
import org.junit.Test;

import java.util.UUID;

/**
 * @author Kim Jensen
 * @since CWS 1.0
 */
public final class CircleEntityTest extends DatabaseSetup {

    @Test
    public void testEntity() {
        final CircleEntity entity = prepareCircle(UUID.randomUUID().toString(), "Circle 1");
        entityManager.flush();
        entityManager.clear();

        final CircleEntity found = find(CircleEntity.class, entity.getId());
        assertNotNull(found);
        assertEquals(entity.getName(), found.getName());

        found.setName("Circle 2");
        persist(found);
        entityManager.flush();
        entityManager.clear();

        final CircleEntity updated = find(CircleEntity.class, entity.getId());
        assertNotNull(updated);
        assertNotEquals(entity.getName(), updated.getName());
    }
}
