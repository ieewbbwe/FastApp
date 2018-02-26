package cn.picher.sdk_mvvm.event;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static cn.picher.sdk_mvvm.event.RxEvent.RxEventId.RX_LOGIN_SUCCEED;

/**
 * Created by mxh on 2017/6/7.
 * Describe：
 */

public class RxEvent {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(RX_LOGIN_SUCCEED)
    public @interface RxEventId {
        int RX_LOGIN_SUCCEED = 0;
    }


    @RxEvent.RxEventId
    private int eventId;
    private Object obj;

    private RxEvent() {
    }

    private RxEvent(int eventId) {
        this.eventId = eventId;
    }

    private RxEvent(int eventId, Object obj) {
        this.eventId = eventId;
        this.obj = obj;
    }

    public int getEventId() {
        return eventId;
    }

    public Object getData() {
        return obj;
    }

    public static RxEvent create(@RxEventId int eventId) {
        return new RxEvent(eventId);
    }

    public static RxEvent create(@RxEventId int eventId, Object data) {
        return new RxEvent(eventId, data);
    }
}
