package com.pp.miniq.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.pp.miniq.core.Message;
import com.pp.miniq.utility.InflateDeflateUtility;

public class MessageMapper implements ResultSetMapper<Message> {

    public Message map(int idx, ResultSet rs, StatementContext ctx) throws SQLException {
        String id = rs.getString("id");
        Date cal = rs.getTimestamp("ts");

        byte[] descInBytes = rs.getBytes("description");

        InflateDeflateUtility zipper = new InflateDeflateUtility();
        String description = null;
        try {
            description = zipper.deCompress(descInBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Message msg = new Message(id, description, cal, descInBytes);
        return msg;
    }

    Calendar getTimeStampAsCalendar(ResultSet rs, String col) {
        Date ts = null;
        try {
            ts = rs.getTimestamp(col);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (ts == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(ts);
        return cal;
    }
}
