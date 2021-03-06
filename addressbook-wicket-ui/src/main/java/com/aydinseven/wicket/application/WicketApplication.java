package com.aydinseven.wicket.application;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AnnotationsRoleAuthorizationStrategy;
import org.apache.wicket.core.request.handler.PageProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;

import com.aydinseven.wicket.pages.error.ExpiredPage;
import com.aydinseven.wicket.pages.error.InternalErrorPage;
import com.aydinseven.wicket.pages.error.NotAllowedPage;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start
 * class.
 *
 * @see com.aydinseven.wicket.Start#main(String[])
 */
public class WicketApplication extends AuthenticatedWebApplication {

    /**
     * Constructor.
     */
    public WicketApplication() {
    	
    }
	
    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage() {
        
    	return SignIn.class;
    }
    
    /**
     * @see org.apache.wicket.protocol.http.WebApplication#newSession(Request, Response)
     */
    @Override
    public Session newSession(Request request, Response response) { 
    	
        return new SignInSession(request);
    }
    
	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		
		return SignInSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		
		return SignIn.class;
	}

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
    	
        super.init();

        // Register the authorization strategy 
        getSecuritySettings().setAuthorizationStrategy(new AnnotationsRoleAuthorizationStrategy(this));
        
        // Set different error pages
        getApplicationSettings().setAccessDeniedPage(NotAllowedPage.class);
        getApplicationSettings().setPageExpiredErrorPage(ExpiredPage.class);
        
        // Un-comment to set a general error page for catching exceptions:
//        getRequestCycleListeners().add(new AbstractRequestCycleListener() {
//            @Override
//            public IRequestHandler onException(RequestCycle cycle, Exception e) {
//                return new RenderPageRequestHandler(new PageProvider(new InternalErrorPage()));
//            }
//        }); 
        
        getSecuritySettings().setUnauthorizedComponentInstantiationListener(new IUnauthorizedComponentInstantiationListener() {

    				@Override public void onUnauthorizedInstantiation(Component component) {
    					component.setResponsePage(NotAllowedPage.class); 
    				} 
    	});
        
        // Turn off Ajax debug settings in browser:
        getDebugSettings().setAjaxDebugModeEnabled(false); 
    }

}