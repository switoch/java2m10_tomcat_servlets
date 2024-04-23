import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String value = URLEncoder.encode(req.getParameter("timezone"), StandardCharsets.UTF_8);
        resp.setContentType("text/html; charset=utf-8");


        //current timezone
        TimeZone timeZone;
        Date date = new Date();

        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss zzz ", Locale.getDefault());

        //Entered by a user
        if (!value.equals("UTC")) {

            char[] values1 = value.toCharArray();
            String sign = String.valueOf(values1[3]);
            int values = Integer.parseInt(sign + value.split("\\+|\\-")[1]);

            //Declaration of a date with user's entered timezone
            Date date2 = (DateTime.now(DateTimeZone.forOffsetHours(values))).toDate();

            //Adapt timezone format to the entered UTC
            simpleDateFormat.setTimeZone(DateTimeZone.forOffsetHours(values).toTimeZone());

            resp.getWriter().write("Entered UTC timezone:     " + simpleDateFormat.format(date2));
        }

        //UTC
        else {
            timeZone = TimeZone.getTimeZone("UTC");
            simpleDateFormat.setTimeZone(timeZone);
            resp.getWriter().write("UTC:     " + simpleDateFormat.format(date));

        }
        resp.getWriter().close();
    }
}
