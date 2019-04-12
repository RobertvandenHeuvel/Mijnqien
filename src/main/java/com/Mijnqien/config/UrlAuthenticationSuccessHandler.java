//package com.Mijnqien.config;
//
//public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//	  
//	    protected Log logger = LogFactory.getLog(this.getClass());
//	 
//	    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//	 
//	    @Override
//	    public void onAuthenticationSuccess(HttpServletRequest request, 
//	      HttpServletResponse response, Authentication authentication)
//	      throws IOException {
//	  
//	        handle(request, response, authentication);
//	        clearAuthenticationAttributes(request);
//	    }
//	 
//	    protected void handle(HttpServletRequest request, 
//	      HttpServletResponse response, Authentication authentication)
//	      throws IOException {
//	  
//	        String targetUrl = determineTargetUrl(authentication);
//	 
//	        if (response.isCommitted()) {
//	            logger.debug(
//	              "Response has already been committed. Unable to redirect to "
//	              + targetUrl);
//	            return;
//	        }
//	 
//	        redirectStrategy.sendRedirect(request, response, targetUrl);
//	    }
//	 
//	    protected String determineTargetUrl(Authentication authentication) {
//	        boolean isTrainee = false;
//	        boolean isAdmin = false;
//	        boolean isKlant = false;
//	        Collection<? extends GrantedAuthority> authorities
//	         = authentication.getAuthorities();
//	        for (GrantedAuthority grantedAuthority : authorities) {
//	            if (grantedAuthority.getAuthority().equals("ROLE_TRAINEE")) {
//	                isTrainee = true;
//	                break;
//	            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
//	                isAdmin = true;
//	                break;
//	            } else if (grantedAuthority.getAuthority().equals("ROLE_KLANT")) {
//	                isKlant = true;
//	                break;
//	            }
//	        }
//	 
//	        if (isTrainee) {
//	            return "/traineeinlog.html";
//	        } else if (isAdmin) {
//	            return "/Admin.html";
//	        } else if (isKlant) {
//	            return "/klantinlog.html";
//	        } else {
//	            throw new IllegalStateException();
//	        }
//	    }
//	 
//	    protected void clearAuthenticationAttributes(HttpServletRequest request) {
//	        HttpSession session = request.getSession(false);
//	        if (session == null) {
//	            return;
//	        }
//	        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//	    }
//	 
//	    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
//	        this.redirectStrategy = redirectStrategy;
//	    }
//	    protected RedirectStrategy getRedirectStrategy() {
//	        return redirectStrategy;
//	    }
//	}