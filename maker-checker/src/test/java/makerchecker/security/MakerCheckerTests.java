package makerchecker.security;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=MakerCheckerTestsConfig.class)
public class MakerCheckerTests {

	private User maker;
	private Authentication makerAuthentication;
	private SecurityContext makerSecurityContext;

	private User checker;
	private Authentication checkerAuthentication;
	private SecurityContext checkerSecurityContext;

	@Before
	public void setUp() throws Exception {
		maker = new User(
				"maker", "password", Collections.emptyList());
		makerAuthentication = new TestingAuthenticationToken(
				maker, "password", "USER");
		assertTrue(makerAuthentication.isAuthenticated());
		assertEquals(maker.getUsername(),
				((User) makerAuthentication.getPrincipal()).getUsername());
		makerSecurityContext = new SecurityContextImpl();
		makerSecurityContext.setAuthentication(makerAuthentication);

		checker = new User(
				"checker", "password", Collections.emptyList());
		checkerAuthentication = new TestingAuthenticationToken(
				checker, "password", "USER");
		assertTrue(checkerAuthentication.isAuthenticated());
		assertEquals(checker.getUsername(),
				((User) checkerAuthentication.getPrincipal()).getUsername());
		checkerSecurityContext = new SecurityContextImpl();
		checkerSecurityContext.setAuthentication(checkerAuthentication);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void makerNotAllowedToCompleteTransaction() {
		PurchaseOrder po = new PurchaseOrder();
		po.setCreatedBy(maker);

		SecurityContextHolder.setContext(makerSecurityContext);
		try {
			try {
				po.issue();
				fail("Maker should not be allowed to complete the purchase order s/he made");
			} catch (Exception e) {
				// pass!
			}
		} finally {
			SecurityContextHolder.clearContext();
		}
		
		SecurityContextHolder.setContext(checkerSecurityContext);
		try {
			try {
				po.issue();
				assertTrue(po.isIssued());
				// pass!
			} catch (Exception e) {
				fail("Checker should be allowed to complete the purchase order made by maker (that is not him/her)");
			}
		} finally {
			SecurityContextHolder.clearContext();
		}
	}

}
