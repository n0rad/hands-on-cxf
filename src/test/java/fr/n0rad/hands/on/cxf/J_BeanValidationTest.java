package fr.n0rad.hands.on.cxf;

import static java.util.Arrays.asList;
import javax.validation.ValidationException;
import net.awired.ajsl.ws.rest.RestContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import fr.n0rad.hands.on.cxf.global.User;
import fr.n0rad.hands.on.cxf.j.J_UsersResource;

/**
 * CXF server does not support Bean Validation yet but you can already do it if you are using spring
 * 
 * @author n0rad
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class J_BeanValidationTest {

    @Configuration
    @ComponentScan("net.n0rad")
    static class Conf {
        @Bean(name = "validator")
        public LocalValidatorFactoryBean validatorFactoryBean() {
            return new LocalValidatorFactoryBean();
        }

        @Bean
        public MethodValidationPostProcessor validationPostProcessor() {
            return new MethodValidationPostProcessor();
        }
    }

    private String url = "http://127.0.0.1:8080";
    private RestContext context = new RestContext();

    @Autowired
    private J_UsersResource usersService;

    @Test(expected = ValidationException.class)
    public void should_validate_param() throws Exception {
        context.prepareServer(url, asList(usersService));

        J_UsersResource users = context.prepareClient(J_UsersResource.class, url, null, true);

        users.createUser(new User("a", "l"));
    }
}
