package com.lort.mail;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by nikita on 19.06.17.
 */

public class Task implements Parcelable {


    public String name = "ТОО Тамур";
    private String address = "ул. Абая, д. 2, ВП-2";
    private String time = "19:54";
    private String status = "wait";
    private ArrayList<Barcode> barcodes;
    private String phone = "+7 (909) 777-77-77";
    private String contact = "Иванов Василий Иванович";

    public Task() {

    }

    public Task(String name, String address, String time, String status, String phone, String contact) {
        this.name = name;
        this.address = address;
        this.time = time;
        this.status = status;
    }

    public Task(String name, String address, String time, String status, String phone, String contact, ArrayList<Barcode> barcodes) {
        this.name = name;
        this.address = address;
        this.time = time;
        this.status = status;
    }

        public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Barcode> getBarcodes() {
        return barcodes;
    }

    public void setBarcodes(ArrayList<Barcode> barcodes) {
        this.barcodes = barcodes;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.time);
        dest.writeString(this.status);
        dest.writeList(this.barcodes);
        dest.writeString(this.phone);
        dest.writeString(this.contact);
    }

    protected Task(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
        this.time = in.readString();
        this.status = in.readString();
        this.barcodes = new ArrayList<Barcode>();
        in.readList(this.barcodes, Barcode.class.getClassLoader());
        this.phone = in.readString();
        this.contact = in.readString();
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
