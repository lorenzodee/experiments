package makerchecker.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;

/**
 * A sample business transaction that requires two individuals for its completion.
 * The maker of the purchase order cannot be the same individual who issues it.
 */
// @javax.persistence.Entity
public class PurchaseOrder {
	
	// @javax.persistence.Id

	// Other fields not included for brevity

	// @CreatedBy
	private User createdBy;
	
	private boolean issued;
	
	public PurchaseOrder() {
		this.issued = false;
	}

	public boolean isIssued() {
		return issued;
	}

	@PreAuthorize("this.createdBy.username != principal.username")
	public void issue() {
		issued = true;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

}
