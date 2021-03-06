package com.contrastsecurity.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import com.atlassian.sal.api.auth.LoginUriProvider;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.templaterenderer.TemplateRenderer;

import javax.inject.Inject;
import javax.inject.Named;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

@Named("ContrastPlugin")
public class ConnectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@ComponentImport
	private final UserManager userManager;
	@ComponentImport
	private final LoginUriProvider loginUriProvider;
	@ComponentImport
	private final TemplateRenderer templateRenderer;

	@Inject
	public ConnectionServlet(UserManager userManager, LoginUriProvider loginUriProvider, TemplateRenderer templateRenderer){
		this.userManager = userManager;
		this.loginUriProvider = loginUriProvider;
		this.templateRenderer = templateRenderer;
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = userManager.getRemoteUsername(request);
		if (username == null || !userManager.isSystemAdmin(username)) {
			redirectToLogin(request, response);
			return;
		}
		response.setContentType("text/html;charset=utf-8");
		templateRenderer.render("admin.vm", response.getWriter());
	}
	private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect(loginUriProvider.getLoginUri(getUri(request)).toASCIIString());
	}
	private URI getUri(HttpServletRequest request) {
		StringBuffer builder = request.getRequestURL();
		if (request.getQueryString() != null){
			builder.append("?");
			builder.append(request.getQueryString());
		}
		return URI.create(builder.toString());
	}

}