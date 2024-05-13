package org.example.gymbackend.projection;

import java.util.Date;

public interface SubscriptionProjection {
    String getId();
    String getPhoneNumber();
    String getImage();
    String getFullName();
    String getName();
    Date getEndTime();
    Integer getDayCount();
}
