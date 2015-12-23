package makerchecker.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;

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
