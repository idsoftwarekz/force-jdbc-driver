package kz.idsoftware.force.jdbc;

import com.sforce.soap.partner.CallOptions;
import com.sforce.soap.partner.InvalidIdFault;
import com.sforce.soap.partner.LoginFault;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.LoginScopeHeader;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.UnexpectedErrorFault;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import javax.xml.ws.BindingProvider;

public class PartnerConnectionBuilder {

  private String url;
  private String user;
  private String password;
  private String token;
  private boolean disableSslCheck = false;
  private boolean debugIn = false;
  private boolean debugOut = false;
  private ForceConnectionBase forceConnectionBase;

  public PartnerConnectionBuilder url(String url) {
    this.url = url;
    return this;
  }

  public PartnerConnectionBuilder user(String user) {
    this.user = user;
    return this;
  }

  public PartnerConnectionBuilder password(String password) {
    this.password = password;
    return this;
  }

  public PartnerConnectionBuilder token(String token) {
    this.token = token;
    return this;
  }

  public PartnerConnectionBuilder disableSslCheck(boolean disableSslCheck) {
    this.disableSslCheck = disableSslCheck;
    return this;
  }

  public PartnerConnectionBuilder forceConnectionBase(ForceConnectionBase forceConnectionBase) {
    this.forceConnectionBase = forceConnectionBase;
    return this;
  }

  public PartnerConnectionBuilder debugOut(boolean debugOut) {
    this.debugOut = debugOut;
    return this;
  }

  public PartnerConnectionBuilder debugIn(boolean debugIn) {
    this.debugIn = debugIn;
    return this;
  }

  public PartnerConnection build() throws UnexpectedErrorFault, LoginFault, InvalidIdFault {

    JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    factory.setAddress(url);
    factory.setServiceClass(PartnerConnection.class);

    if (debugOut) {
      LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
      loggingOutInterceptor.setPrettyLogging(true);
      factory.getOutInterceptors().add(loggingOutInterceptor);
    }

    if (debugIn) {
      LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
      loggingInInterceptor.setPrettyLogging(true);
      factory.getInInterceptors().add(loggingInInterceptor);
    }

    PartnerConnection partnerConnection = (PartnerConnection) factory.create();

    LoginScopeHeader loginScopeHeader = ForceConnectionUtils.getLoginScopeHeader();

    CallOptions callOptions = ForceConnectionUtils.getCallOptions();

    LoginResult loginResult = partnerConnection.login(user, password + token, loginScopeHeader, callOptions);

    if (forceConnectionBase!=null) {
      forceConnectionBase.setSessionId(loginResult.getSessionId());
    }

    String serverUrl = loginResult.getServerUrl();

    BindingProvider bp = (BindingProvider) partnerConnection;
    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serverUrl);

    return partnerConnection;
  }
}
