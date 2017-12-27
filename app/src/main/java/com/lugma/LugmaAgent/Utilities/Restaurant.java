package com.lugma.LugmaAgent.Utilities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CodeBele-PC on 14-12-2017.
 */

public class Restaurant implements Parcelable {
    int rest_id;
    String restaurant_name,restaurant_info,website,contact_person,designation,email,mobile,telephone,whatsapp_mobile,facebook,instagram,twitter,meal_for_two,status,groupname;

    public Restaurant(int rest_id, String restaurentName, String info, String website, String status, String groupname) {
        this.rest_id = rest_id;
        this.restaurant_name = restaurentName;
        this.restaurant_info = info;
        this.website = website;
        this.status = status;
        this.groupname = groupname;
    }

    public Restaurant(int rest_id, String restaurant_name, String restaurant_info, String website, String contact_person, String designation, String email, String mobile, String telephone, String whatsapp_mobile, String facebook, String instagram, String twitter, String meal_for_two, String status, String groupname) {
        this.rest_id = rest_id;
        this.restaurant_name = restaurant_name;
        this.restaurant_info = restaurant_info;
        this.website = website;
        this.contact_person = contact_person;
        this.designation = designation;
        this.email = email;
        this.mobile = mobile;
        this.telephone = telephone;
        this.whatsapp_mobile = whatsapp_mobile;
        this.facebook = facebook;
        this.instagram = instagram;
        this.twitter = twitter;
        this.meal_for_two = meal_for_two;
        this.status = status;
        this.groupname = groupname;
    }

    protected Restaurant(Parcel in) {
        rest_id = in.readInt();
        restaurant_name = in.readString();
        restaurant_info = in.readString();
        website = in.readString();
        contact_person = in.readString();
        designation = in.readString();
        email = in.readString();
        mobile = in.readString();
        telephone = in.readString();
        whatsapp_mobile = in.readString();
        facebook = in.readString();
        instagram = in.readString();
        twitter = in.readString();
        meal_for_two = in.readString();
        status = in.readString();
        groupname = in.readString();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public void setRest_id(int rest_id) {
        this.rest_id = rest_id;
    }

    public int getRest_id() {
        return rest_id;
    }

    public void setInfo(String info) {
        this.restaurant_info = info;
    }

    public String getInfo() {
        return restaurant_info;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setRestaurentName(String restaurentName) {
        this.restaurant_name = restaurentName;
    }

    public String getRestaurentName() {
        return restaurant_name;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setRestaurant_info(String restaurant_info) {
        this.restaurant_info = restaurant_info;
    }

    public String getRestaurant_info() {
        return restaurant_info;
    }

    public void setMeal_for_two(String meal_for_two) {
        this.meal_for_two = meal_for_two;
    }

    public String getMeal_for_two() {
        return meal_for_two;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setWhatsapp_mobile(String whatsapp_mobile) {
        this.whatsapp_mobile = whatsapp_mobile;
    }

    public String getWhatsapp_mobile() {
        return whatsapp_mobile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(rest_id);
        parcel.writeString(restaurant_name);
        parcel.writeString(restaurant_info);
        parcel.writeString(website);
        parcel.writeString(contact_person);
        parcel.writeString(designation);
        parcel.writeString(email);
        parcel.writeString(mobile);
        parcel.writeString(telephone);
        parcel.writeString(whatsapp_mobile);
        parcel.writeString(facebook);
        parcel.writeString(instagram);
        parcel.writeString(twitter);
        parcel.writeString(meal_for_two);
        parcel.writeString(status);
        parcel.writeString(groupname);
    }
}


