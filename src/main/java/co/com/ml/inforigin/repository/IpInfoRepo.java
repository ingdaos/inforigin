package co.com.ml.inforigin.repository;

import co.com.ml.inforigin.entity.IpInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ingda
 */
@Repository
public interface IpInfoRepo extends JpaRepository<IpInfoEntity, Long> {

    public IpInfoEntity findByIpAddress(String ip);

    @Query("SELECT a.status FROM IpInfoEntity a WHERE a.ipAddress = :ip")
    public String getStatus(@Param("ip") String ip);

}
