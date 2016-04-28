package com.project.frame.bean;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhe
 *         <p/>
 *         ���ݵ���ʵ����
 */
public class VenuesInfo {

    public String attendingway;
    public String channel_name;
    public String seatType;
    public String remark;
    public String speaker;
    public String organizer;
    public String ticketNum;
    public String type;
    public String dateType;
    public String guideUrl;
    public String content_url;
    public String title;
    public String distance;
    public String content_id;
    public String address;
    public String coorganizer;
    public String content_txt;
    public String title_img;
    public String longitude;
    public String latitude;
    public String ups;
    public String telephone;
    public String activityTime;

    public String getGuideUrl() {
        return guideUrl;
    }

    public void setGuideUrl(String guideUrl) {
        this.guideUrl = guideUrl;
    }

    public String getAttendingway() {
        return attendingway;
    }

    public void setAttendingway(String attendingway) {
        this.attendingway = attendingway;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(String ticketNum) {
        this.ticketNum = ticketNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoorganizer() {
        return coorganizer;
    }

    public void setCoorganizer(String coorganizer) {
        this.coorganizer = coorganizer;
    }

    public String getContent_txt() {
        return content_txt;
    }

    public void setContent_txt(String content_txt) {
        this.content_txt = content_txt;
    }

    public String getTitle_img() {
        return title_img;
    }

    public void setTitle_img(String title_img) {
        this.title_img = title_img;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getUps() {
        return ups;
    }

    public void setUps(String ups) {
        this.ups = ups;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public static List<VenuesInfo> venues;

    public static List<VenuesInfo> getDatas(Context context, String jsonObj){
        venues = new ArrayList<VenuesInfo>();

        //实例化Gson对象
        Gson gson = new Gson();

        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(jsonObj.toString()).getAsJsonObject();
        JsonArray array = object.getAsJsonArray("detail");

        for(int i = 0; i<array.size(); i++){
            JsonElement element = array.get(i);
            VenuesInfo venue = gson.fromJson(element, VenuesInfo.class);
            venues.add(venue);
        }
        return venues;
    }

}
