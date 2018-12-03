package avito;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*@WebFilter(filterName = "FilterSign", urlPatterns = "/*")*/
public class FilterSign implements Filter {

    private FilterConfig filterConfig;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        if (httpRequest.getRequestURI().contains("/sign")) {
            chain.doFilter(request, response);
        } else {
            synchronized (session) {
                if (session.getAttribute("user") == null) {
                    ((HttpServletResponse) response).sendRedirect(String.format("%s/sign", httpRequest.getContextPath()));
                    return;
                }
            }
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
