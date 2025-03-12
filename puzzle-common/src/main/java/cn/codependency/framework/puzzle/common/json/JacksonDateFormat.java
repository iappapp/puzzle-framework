package cn.codependency.framework.puzzle.common.json;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.apache.commons.lang3.time.DateUtils;

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JacksonDateFormat extends StdDateFormat {
    private static final long serialVersionUID = 1L;
    private static final String[] PATTERNS = {
            "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd",
            "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd"
    };


    @Override
    public Date parse(String dateStr, ParsePosition pos) {
        try {
            return DateUtils.parseDate(dateStr, PATTERNS);
        } catch (ParseException ignore) {
        }
        return super.parse(dateStr, pos);
    }

    @Override
    public Date parse(String dateStr) throws ParseException {
        try {
            return DateUtils.parseDate(dateStr, PATTERNS);
        } catch (ParseException ignore) {
        }
        return super.parse(dateStr);
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date, toAppendTo, fieldPosition);
    }

    @Override
    public JacksonDateFormat clone() {
        return new JacksonDateFormat();
    }
}
