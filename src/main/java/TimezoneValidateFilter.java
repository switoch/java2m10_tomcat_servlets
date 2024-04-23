import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse resp,
                            FilterChain chain) throws IOException, ServletException {

        //Encode parameter to work correctly with plus sign
        String value = URLEncoder.encode(req.getParameter("timezone"), StandardCharsets.UTF_8);

        //Parameter should be equal to UTC+/-offset hours
        Matcher matcher = Pattern.compile("(UTC|GMT)([+\\-])(\\b(?:[0-9]|1[0-2])\\b)").matcher(value);
        if (matcher.matches()) {
            chain.doFilter(req, resp);
        } else if (value.equals("UTC")) {
            chain.doFilter(req, resp);
        } else {
            resp.setStatus(400);
            resp.setContentType("application/json");
            resp.getWriter().write("Invalid timezone");
            resp.getWriter().close();

        }
    }


}
