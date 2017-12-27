package com.lugma.LugmaAgent.Utilities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CodeBele-PC on 14-12-2017.
 */

public class Group implements Parcelable {
    int group_id;
    String group_name,website,address,contact_person,designation,telephone,mobile,email;

    public Group(Parcel in) {

        group_id = in.readInt();
        group_name = in.readString();
        website = in.readString();
        address = in.readString();
        contact_person = in.readString();
        telephone = in.readString();
        mobile = in.readString();
        email = in.readString();
    }

    public Group(int group_id, String group_name) {
        this.group_id = group_id;
        this.group_name = group_name;
    }

    public Group(int group_id, String group_name, String website, String address, String contact_person, String telephone, String mobile, String email) {
        this.group_id = group_id;
        this.group_name = group_name;
        this.website = website;
        this.address = address;
        this.contact_person = contact_person;
        this.telephone = telephone;
        this.mobile = mobile;
        this.email = email;
    }

    public Group(String group_name, String website, String address, String contact_person, String designation, String telephone, String mobile, String email) {

        this.group_name = group_name;
        this.website = website;
        this.address = address;
        this.contact_person = contact_person;
        this.designation = designation;
        this.telephone = telephone;
        this.mobile = mobile;
        this.email = email;
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(group_id);
        parcel.writeString(group_name);
        parcel.writeString(website);
        parcel.writeString(address);
        parcel.writeString(contact_person);
        parcel.writeString(telephone);
        parcel.writeString(mobile);
        parcel.writeString(email);
    }

}
