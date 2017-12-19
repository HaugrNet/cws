/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-core)
 * =============================================================================
 */
package io.javadog.cws.core.model.entities;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import io.javadog.cws.core.DatabaseSetup;
import org.junit.Test;

import java.util.List;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public final class SettingEntityTest extends DatabaseSetup {

    @Test
    public void testEntity() {
        final SettingEntity entity = new SettingEntity();
        entity.setName("My.New.Setting");
        entity.setSetting("The Setting Value");
        entity.setModifiable(false);
        persist(entity);

        final SettingEntity found = find(SettingEntity.class, entity.getId());
        assertThat(found, is(not(nullValue())));
        assertThat(found.getName(), is("My.New.Setting"));
        assertThat(found.getSetting(), is("The Setting Value"));
        assertThat(found.isModifiable(), is(false));

        found.setName("My.Altered.Setting");
        found.setSetting("The Altered Setting");
        found.setModifiable(true);
        persist(found);

        final SettingEntity updated = find(SettingEntity.class, entity.getId());
        assertThat(updated, is(not(nullValue())));
        assertThat(updated.getName(), is("My.Altered.Setting"));
        assertThat(updated.getSetting(), is("The Altered Setting"));
        assertThat(updated.isModifiable(), is(true));

        final List<SettingEntity> mySettings = dao.readSettings();
        assertThat(mySettings, is(not(nullValue())));
        assertThat(mySettings.size(), is(13));
    }

    @Test
    public void testDaoFindSettings() {
        final List<SettingEntity> mySettings = dao.readSettings();
        assertThat(mySettings, is(not(nullValue())));
        assertThat(mySettings.size(), is(12));
    }
}