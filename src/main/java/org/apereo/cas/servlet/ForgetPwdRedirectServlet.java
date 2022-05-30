package org.apereo.cas.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description:
 *
 * @author : niudongjun
 * @date : 2022/4/15 18:08
 */
public class ForgetPwdRedirectServlet extends HttpServlet {
    private final String redirectUrl;

    public ForgetPwdRedirectServlet(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(redirectUrl);
    }
}
