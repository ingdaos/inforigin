package co.com.ml.inforigin;

import co.com.ml.inforigin.filter.IpFilter;
import co.com.ml.inforigin.utilities.Paths;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ingda
 */
@Configuration
public class InfoOriginConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }

    @Bean
    public FilterRegistrationBean ipFilterReg(IpFilter ipFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(ipFilter);
        registration.addUrlPatterns(Paths.IP_INFO);
        registration.setOrder(1);
        return registration;
    }

}
