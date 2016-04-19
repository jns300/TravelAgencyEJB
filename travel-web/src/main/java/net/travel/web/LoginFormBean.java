package net.travel.web;

import java.io.Serializable;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

import net.travel.ejb.data.model.Tadresy;
import net.travel.ejb.data.model.Tklient;
import net.travel.ejb.data.model.Tosoby;
import net.travel.ejb.data.model.Tpanstwa;
import net.travel.ejb.request.RequestBeanRemote;
import net.travel.ejb.request.UserData;
import net.travel.web.util.PasswordUtil;

@ManagedBean(name = "loginForm")
@RequestScoped
public class LoginFormBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(LoginFormBean.class.getName());

	@EJB
	private RequestBeanRemote requestBean;

	public static final int MAX_PASS_COLUMN_LENGTH = 34;

	private String login;

	private String password;

	private UserData dataForm;

	private boolean isSignUpDone;

	private UIInput passwordVerControl;

	private UIInput loginInputText;

	public LoginFormBean() {
	}

	public UIInput getLoginInputText() {
		return loginInputText;
	}

	public void setLoginInputText(UIInput loginInputText) {
		this.loginInputText = loginInputText;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserData getDataForm() {
		return dataForm;
	}

	public void setDataForm(UserData dataForm) {
		this.dataForm = dataForm;
	}

	public UIInput getPasswordVerControl() {
		return passwordVerControl;
	}

	public void setPasswordVerControl(UIInput passwordVerControl) {
		this.passwordVerControl = passwordVerControl;
	}

	public void setSignUpDone(boolean isSignUpDone) {
		this.isSignUpDone = isSignUpDone;
	}

	public static boolean isClientLoggedOn() {
		Object obj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get(KeyValues.LOGGED_CLIENT_ID);
		return obj instanceof Integer;
	}

	public boolean isLoggedOn() {
		return isClientLoggedOn();
	}
	
	public boolean isSuperUser() {
		Tosoby person = requestBean.getPerson(getClientId());
		return person != null && person.getBPracownik();
	}

	public boolean isSignUpDone() {
		return isSignUpDone;
	}

	public void setSignUpState(boolean value) {
		isSignUpDone = value;
	}

	public String getUserName() {
		if (isLoggedOn()) {
			return requestBean.getUserName((int) getClientId());
		}
		return "<unknown>";
	}

	public static Integer getClientIdOrNull() {
		Integer id = (Integer) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get(KeyValues.LOGGED_CLIENT_ID);
		return id;
	}
	public static int getClientId() {
		Integer id = (Integer) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get(KeyValues.LOGGED_CLIENT_ID);
		if (id == null) {
			return -1;
		}
		return id;
	}

	public UserData getUserData() {
		if (dataForm == null) {
			dataForm = new UserData();
		}
		return dataForm;
	}

	public String signIn() {
		isSignUpDone = false;
		dataForm = null;
		Tklient client = requestBean.getClient(login);
		FacesContext context = FacesContext.getCurrentInstance();
		Tklient loggedClient = null;
		if (client != null) {
			String sum = PasswordUtil.getShaSumTry(password).substring(0, MAX_PASS_COLUMN_LENGTH);
			if (sum.equals(client.getHaslo().substring(0, MAX_PASS_COLUMN_LENGTH))) {
				loggedClient = client;
				context.getExternalContext().getSessionMap()
						.put(KeyValues.LOGGED_CLIENT_ID, client.getIDKlienta());
				return "signInResult";
			}
		}
		if (loggedClient == null) {
			context.addMessage(loginInputText.getClientId(context), new FacesMessage(
					"Login failed."));
		}
		return null;
	}

	public String logOut() {
		isSignUpDone = false;
		dataForm = null;
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove(KeyValues.LOGGED_CLIENT_ID);
		((HttpSession) FacesContext.getCurrentInstance().getExternalContext()
		         .getSession(false)).invalidate();
		return "signInResult";
	}

	public String signUp() {
		// Store in database
		Tpanstwa country = requestBean.getCountry(dataForm.getCountry());
		Tadresy address = new Tadresy(dataForm.getAddress(), dataForm.getFax(),
				dataForm.getZipCode(), dataForm.getCity(), dataForm.getDistrict(),
				dataForm.getPhone(), country);
		String secret = PasswordUtil.getShaSumTry(dataForm.getPassword()).substring(0,
				MAX_PASS_COLUMN_LENGTH);

		Tklient client = new Tklient(false, dataForm.getEmail(), secret);
		Tosoby person = new Tosoby(false, dataForm.getName(), dataForm.getLastName(),
				dataForm.getNip(), address, client);
		requestBean.addPerson(person);

		isSignUpDone = true;
		return "login";
	}

	public void validateCountry(FacesContext context, UIComponent toValidate, Object value) {

		String input = (String) value;
		Tpanstwa country = requestBean.getCountry(input);
		if (country == null) {
			((UIInput) toValidate).setValid(false);

			FacesMessage message = new FacesMessage("Country is invalid");
			context.addMessage(toValidate.getClientId(context), message);
		}
	}

	public void validatePhone(FacesContext context, UIComponent toValidate, Object value) {
		doValidatePhone(context, toValidate, value, "Phone number is invalid");
	}

	private void doValidatePhone(FacesContext context, UIComponent toValidate, Object value,
			String messageText) {
		String input = (String) value;
		if (input == null || input.trim().length() == 0) {
			return;
		}
		if (!Pattern.matches("^[\\(\\)0-9 -]+$", input) || getDigitCount(input) == 0) {
			((UIInput) toValidate).setValid(false);
			FacesMessage message = new FacesMessage(messageText);
			context.addMessage(toValidate.getClientId(context), message);
		}
	}

	public void validateFax(FacesContext context, UIComponent toValidate, Object value) {
		doValidatePhone(context, toValidate, value, "Fax number is invalid");
	}

	public void validateNip(FacesContext context, UIComponent toValidate, Object value) {
		String input = (String) value;
		if (input == null || input.trim().length() == 0) {
			return;
		}
		if (!Pattern.matches("^[0-9]+[0-9-]+[0-9]+$", input) || getDigitCount(input) == 0) {
			FacesMessage message = new FacesMessage("NIP is invalid");
			((UIInput) toValidate).setValid(false);
			context.addMessage(toValidate.getClientId(context), message);
		}
	}

	public void validatePasswords(FacesContext context, UIComponent toValidate, Object value) {
		String confirm = (String) value;
		UIInput passComp = (UIInput) toValidate.getAttributes().get("passwordComponent");
		String password = (String) passComp.getValue();
		if (!password.equals(confirm)) {
			FacesMessage message = new FacesMessage("Password and confirm password don't match");
			throw new ValidatorException(message);
		}
	}

	private int getDigitCount(String input) {
		int c = 0;
		int len = input.length();
		for (int i = 0; i < len; i++) {
			if (Character.isDigit(input.charAt(i))) {
				c++;
			}
		}
		return c;
	}
}
