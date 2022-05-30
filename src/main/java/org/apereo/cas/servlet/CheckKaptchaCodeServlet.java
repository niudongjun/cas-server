package org.apereo.cas.servlet;

import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.localcache.CacheFactory;
import org.apereo.cas.localcache.CacheNames;
import org.apereo.cas.localcache.SimpleCache;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * description:
 *
 * @author : niudongjun
 * @date : 2022/4/29 21:30
 */
public class CheckKaptchaCodeServlet extends HttpServlet {

    private static final SimpleCache SIMPLE_CACHE = CacheFactory.getSimpleCache(CacheNames.K_CODE_CACHE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String kCode = req.getParameter("kCode");
        String service = req.getParameter("service");
        HttpSession session = req.getSession();
        if (StringUtils.isBlank(kCode) || session == null
                || session.getAttribute("KAPTCHA_SESSION_KEY") == null) {
            resp.sendError(404);
        } else if (StringUtils.equals(session.getAttribute("KAPTCHA_SESSION_KEY").toString(), kCode)) {
            SIMPLE_CACHE.put(req.getRemoteHost() + "kCode", kCode);
            //设置跳转地址
            resp.setHeader("redirectUrl", "/cas/login?service=" + service);
            //设置跳转使能
            resp.setHeader("enableRedirect","true");
            resp.flushBuffer();
        } else {
            resp.sendError(500);
        }

    }
}
