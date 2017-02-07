package io.javadog.cws.model;

import io.javadog.cws.model.entities.CWSEntity;
import io.javadog.cws.model.entities.CircleEntity;
import io.javadog.cws.model.entities.MemberEntity;
import io.javadog.cws.model.entities.SettingEntity;
import io.javadog.cws.model.entities.TrusteeEntity;

import java.util.List;

/**
 * Common DAO Class for CWS.
 *
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public interface CommonDao {

    <E extends CWSEntity> E persist(E entity);

    MemberEntity findMemberByNameCredential(String credential);

    List<TrusteeEntity> findTrustByMember(MemberEntity member);

    List<SettingEntity> readSettings();

    List<TrusteeEntity> findTrusteesByCircle(String externalCircleId);

    List<TrusteeEntity> findTrusteesByCircle(Long circleId);

    List<CircleEntity> findAllCircles();
}
