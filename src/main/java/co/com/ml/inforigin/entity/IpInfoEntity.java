package co.com.ml.inforigin.entity;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author ingda
 */
@Data
@ToString
@Entity
@Cacheable(false)
@Table(name = "IP_INFO")
public class IpInfoEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "secBlacklist", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "secBlacklist", sequenceName = "SQ_BLACKLIST", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IPADDRESS")
    private String ipAddress;

    @Column(name = "STATUS")
    private String status;

}
