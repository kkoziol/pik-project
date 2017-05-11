import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.configuration.HibernateConfig;
import com.configuration.MvcConfig;
import com.configuration.SecurityConfig;
import com.models.entities.User;
import com.services.UserService;

import junit.framework.TestCase;
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
(
		classes ={
   MvcConfig.class, HibernateConfig.class, SecurityConfig.class 
  }
)
public class Test extends TestCase {
	private UserService userService;
		
	@Autowired(required = true)
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@org.junit.Before
	@Transactional
	public void testAddUser() {
		User user = new User();
		user.setName("TEST");
		userService.saveOrUpdate(user);
	}
		
	@org.junit.Test
	public void testGetUser() {
		User user = userService.getUser(1);
		assertEquals(user.getUserId().intValue(),1); 
	}
	
}
