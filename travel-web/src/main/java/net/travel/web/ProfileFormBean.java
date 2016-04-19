package net.travel.web;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import net.travel.ejb.request.RequestBeanRemote;
import net.travel.ejb.request.UserData;
import net.travel.web.util.PasswordUtil;

@ManagedBean(name = "profileForm")
@RequestScoped
public class ProfileFormBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private RequestBeanRemote request;

	private UserData userData;

	private String currentPassword;

	public UserData getUserData() {
		if (userData == null) {
			Object objId = getClientId();
			if (objId instanceof Integer) {
				int clientId = (Integer) objId;
				userData = request.getUserData(clientId);
			}
		}
		return userData;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	private Integer getClientId() {
		return LoginFormBean.getClientIdOrNull();
	}

	public String getProfileTitle() {
		Integer clientId = getClientId();
		if (clientId != null) {
			ResourceBundle bundle = ResourceBundle.getBundle("Bundle");
			return MessageFormat.format(bundle.getString("EditProfile_title"),
					request.getUserName(clientId));
		}
		return "User is not logged in.";
	}

	public String updateProfile() {
		if (userData != null) {
			Object objId = getClientId();
			if (objId instanceof Integer) {
				request.updateProfileData((Integer) objId, userData);
				return "profileUpdated";
			}
		}
		return null;
	}

	public void validatePasswords(FacesContext context, UIComponent toValidate, Object value) {
		if (userData == null) {
			throw new ValidatorException(new FacesMessage(
					"Invalid request. User data is not found."));
		}
		String confirm = (String) value;
		UIInput passComp = (UIInput) toValidate.getAttributes().get("passwordComponent");
		String password = (String) passComp.getValue();
		if (!password.equals(confirm)) {
			FacesMessage message = new FacesMessage("Password and confirm password don't match");
			throw new ValidatorException(message);
		} else {
			UIInput currentPassComp = (UIInput) toValidate.getAttributes().get("currentPasswordComponent");
			String currentPasswordValue = (String) currentPassComp.getValue();
			String oldHash = PasswordUtil.getShaSumTry(currentPasswordValue).substring(0,
					LoginFormBean.MAX_PASS_COLUMN_LENGTH);
			String passwordHash = userData.getPasswordHash();
			if (passwordHash != null) {
				passwordHash = passwordHash.substring(0, LoginFormBean.MAX_PASS_COLUMN_LENGTH);
			}
			if (!oldHash.equals(passwordHash)) {
				throw new ValidatorException(new FacesMessage("The old password is invalid."));
			}
			if (oldHash.equals(PasswordUtil.getShaSumTry(password).substring(0,
					LoginFormBean.MAX_PASS_COLUMN_LENGTH))) {
				throw new ValidatorException(new FacesMessage("The new password is the same like the old password."));
			}
		}
	}
	
	public String updatePassword() {
		Integer clientId = getClientId();
		if (clientId != null) {
			String newHash = PasswordUtil.getShaSumTry(userData.getPassword()).substring(0,
					LoginFormBean.MAX_PASS_COLUMN_LENGTH);
			request.updatePassword(clientId, newHash);
			return "passwordChanged";
			
		}
		return null;
	}
}
