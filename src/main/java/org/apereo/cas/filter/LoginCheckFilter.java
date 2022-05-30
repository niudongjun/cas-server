package org.apereo.cas.filter;

import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.localcache.CacheFactory;
import org.apereo.cas.localcache.CacheNames;
import org.apereo.cas.localcache.SimpleCache;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * description:
 *
 * @author : niudongjun
 * @date : 2022/5/4 9:13
 */
public class LoginCheckFilter implements Filter {
    private static final SimpleCache SIMPLE_CACHE = CacheFactory.getSimpleCache(CacheNames.K_CODE_CACHE);

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if (req instanceof HttpServletRequest && resp instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) resp;
            HttpSession session = request.getSession();
            if (session == null || session.getAttribute("KAPTCHA_SESSION_KEY") == null
                    || !StringUtils.equals(session.getAttribute("KAPTCHA_SESSION_KEY").toString(),
                    SIMPLE_CACHE.get(req.getRemoteHost() + "kCode"))) {
                response.sendRedirect("/cas/preLogin?" + request.getQueryString());
            }
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {
    }
}
