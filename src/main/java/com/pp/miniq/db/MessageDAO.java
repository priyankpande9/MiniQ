package com.pp.miniq.db;

import java.util.Date;
import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.pp.miniq.core.Message;
import com.pp.miniq.mappers.MessageMapper;

@RegisterMapper(MessageMapper.class)
public interface MessageDAO {
    static final String TABLE      = "minq_msgs";
    static final String TABLE_ACKS = "minq_msgs_acks";

    @SqlUpdate("create table " + TABLE + "(id varchar(256) primary key, description longblob, ts timestamp)")
    void createMessageTable();

    @SqlUpdate("insert into " + TABLE + "(description, id) values (:descInBytes, :id)")
    public void insert(@BindBean Message msg);

    @SqlQuery("select minq_msgs.id, minq_msgs.description, minq_msgs.ts from " + TABLE + " where minq_msgs.id = :id")
    public Message retrieve(@Bind("id") String id);

    @SqlQuery("select * from " + TABLE + " limit 20")
    public List<Message> retrieveAll();

    @SqlUpdate("delete from " + TABLE + " where id = :id")
    public void delete(@Bind("id") String id);

    @SqlUpdate("update " + TABLE + " set description = :descInBytes where id = :id")
    public void update(@BindBean Message msg);

    @SqlUpdate("insert into " + TABLE_ACKS + " (id, description) values (:id, :descInBytes)")
    public void insertAcks(@BindBean Message msg);

    @SqlUpdate("update " + TABLE + " set ts = :ts where id = :id")
    public void update(@Bind("ts") Date ts, @Bind("id") String id);
}
