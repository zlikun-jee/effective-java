package com.zlikun.jee.j004;

import java.io.*;
import java.util.Date;

/**
 * @see org.apache.hadoop.io.Writable
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/5 13:06
 */
public class Data2 implements Serializable {

    private Integer a;
    private Long b;
    private Float c;
    private Double d;
    private Boolean e;
    private Character f;
    private Byte g;
    private Short h;

    private int a0;
    private long b0;
    private float c0;
    private double d0;
    private boolean e0;
    private char f0;
    private byte g0;
    private short h0;

    private String i;
    private Date j;

    public byte[] serialize() throws IOException {
        return Data2.serialize(this);
    }

    /**
     * 序列化当前对象
     *
     * @return
     */
    public static final byte[] serialize(Data2 data) throws IOException {
        assert data != null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutput output = new DataOutputStream(baos);

        // 序列化的数据参考 JdkSerializeTest 中的Data对象
        // 序列化、反序列化的过程都是一个字段一个字段的实现，虽然繁琐，但序列化后的大小和性能都比JDK原生序列化API强很多
        output.writeInt(data.getA());
        output.writeInt(data.getA0());
        output.writeLong(data.getB());
        output.writeLong(data.getB0());
        output.writeFloat(data.getC());
        output.writeFloat(data.getC0());
        output.writeDouble(data.getD());
        output.writeDouble(data.getD0());
        output.writeBoolean(data.getE());
        output.writeBoolean(data.isE0());
        output.writeChar(data.getF());
        output.writeChar(data.getF0());
        output.writeByte(data.getG());
        output.writeByte(data.getG0());
        output.writeShort(data.getH());
        output.writeShort(data.getH0());
        writeString(output, data.getI());
        // 序列化日期时使用时间戳表示
        output.writeLong(data.getJ().getTime());

        return baos.toByteArray();
    }

    /**
     * 反序列化 Data2 对象
     *
     * @param buf
     * @return
     */
    public static final Data2 deserialize(byte[] buf) throws IOException {
        // 执行反序列化，注意读取的顺序与写入的顺序要一致
        ByteArrayInputStream bais = new ByteArrayInputStream(buf);
        DataInput input = new DataInputStream(bais);
        Data2 data = new Data2();
        data.setA(input.readInt());
        data.setA0(input.readInt());
        data.setB(input.readLong());
        data.setB0(input.readLong());
        data.setC(input.readFloat());
        data.setC0(input.readFloat());
        data.setD(input.readDouble());
        data.setD0(input.readDouble());
        data.setE(input.readBoolean());
        data.setE0(input.readBoolean());
        data.setF(input.readChar());
        data.setF0(input.readChar());
        data.setG(input.readByte());
        data.setG0(input.readByte());
        data.setH(input.readShort());
        data.setH0(input.readShort());
        data.setI(readString(input));
        data.setJ(new Date(input.readLong()));
        return data;
    }

    /**
     * 向 DataOutput 写入字符类型稍微复杂一些
     *
     * @param out
     * @param s
     * @throws IOException
     * @see org.apache.hadoop.io.WritableUtils#writeString(DataOutput, String)
     */
    private static final void writeString(DataOutput out, String s) throws IOException {
        if (s != null) {
            byte[] buffer = s.getBytes("UTF-8");
            int len = buffer.length;
            // 先写入字符串长度
            out.writeInt(len);
            // 再写入字符串内容(字节数组)
            out.write(buffer, 0, len);
        } else {
            out.writeInt(-1);
        }
    }

    /**
     * 与 writeString(DataOutput, String) 方法相反，用于读取字符串类型数据
     *
     * @param in
     * @return
     * @throws IOException
     * @see #writeString(DataOutput, String)
     */
    private static final String readString(DataInput in) throws IOException {
        int length = in.readInt();
        if (length == -1) return null;
        byte[] buffer = new byte[length];
        in.readFully(buffer);      // could/should use readFully(buffer,0,length)?
        return new String(buffer, "UTF-8");
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Long getB() {
        return b;
    }

    public void setB(Long b) {
        this.b = b;
    }

    public Float getC() {
        return c;
    }

    public void setC(Float c) {
        this.c = c;
    }

    public Double getD() {
        return d;
    }

    public void setD(Double d) {
        this.d = d;
    }

    public Boolean getE() {
        return e;
    }

    public void setE(Boolean e) {
        this.e = e;
    }

    public Character getF() {
        return f;
    }

    public void setF(Character f) {
        this.f = f;
    }

    public Byte getG() {
        return g;
    }

    public void setG(Byte g) {
        this.g = g;
    }

    public Short getH() {
        return h;
    }

    public void setH(Short h) {
        this.h = h;
    }

    public int getA0() {
        return a0;
    }

    public void setA0(int a0) {
        this.a0 = a0;
    }

    public long getB0() {
        return b0;
    }

    public void setB0(long b0) {
        this.b0 = b0;
    }

    public float getC0() {
        return c0;
    }

    public void setC0(float c0) {
        this.c0 = c0;
    }

    public double getD0() {
        return d0;
    }

    public void setD0(double d0) {
        this.d0 = d0;
    }

    public boolean isE0() {
        return e0;
    }

    public void setE0(boolean e0) {
        this.e0 = e0;
    }

    public char getF0() {
        return f0;
    }

    public void setF0(char f0) {
        this.f0 = f0;
    }

    public byte getG0() {
        return g0;
    }

    public void setG0(byte g0) {
        this.g0 = g0;
    }

    public short getH0() {
        return h0;
    }

    public void setH0(short h0) {
        this.h0 = h0;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public Date getJ() {
        return j;
    }

    public void setJ(Date j) {
        this.j = j;
    }
}
