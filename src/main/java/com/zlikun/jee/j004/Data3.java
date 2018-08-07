package com.zlikun.jee.j004;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/5 13:06
 */
public class Data3 implements Externalizable {

    private Integer id;
    private String name;
    private Date birthday;

    @Override
    public void writeExternal(ObjectOutput output) throws IOException {
        output.writeInt(this.id);
        output.writeUTF(this.name);
        output.writeObject(this.birthday);
    }

    @Override
    public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
        this.id = input.readInt();
        this.name = input.readUTF();
        this.birthday = (Date) input.readObject();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
