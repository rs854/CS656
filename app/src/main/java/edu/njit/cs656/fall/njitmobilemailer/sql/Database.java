package edu.njit.cs656.fall.njitmobilemailer.sql;

import android.content.Context;

/**
 * Created by Eugen on 11/4/2017.
 */

public class Database {
    private Create create;
    private Read read;
    private Update update;
    private Delete delete;

    public Database(Context context) {
        create = new Create(context);
        read = new Read(context);
        update = new Update(context);
        delete = new Delete(context);
    }

    public Create getCreate() {
        return create;
    }

    public void setCreate(Create create) {
        this.create = create;
    }

    public Delete getDelete() {
        return delete;
    }

    public void setDelete(Delete delete) {
        this.delete = delete;
    }

    public Read getRead() {
        return read;
    }

    public void setRead(Read read) {
        this.read = read;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

}
