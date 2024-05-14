package org.example.gymbackend.projection;

import java.util.Date;

public interface SubscriptionProjection {
    String getId();
    String getUserId();
    String getPhoneNumber();
    String getImage();
    String getFullName();
    String getPrice();
    Date getStartTime();
    Boolean getStatus();
    Boolean getLimited();
    String getName();
    Date getEndTime();
    Integer getDayCount();
}
