package roizot.com.outerspacemanager.outerspacemanager.netWork;

import java.util.ArrayList;

import roizot.com.outerspacemanager.outerspacemanager.models.UserInfos;

/**
 * Created by mac4 on 14/03/2017.
 */

public class UsersResponse {

    private ArrayList<UserInfos> users;

    public ArrayList<UserInfos> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        String str = "Yolo : ";
        for (UserInfos user : users) {
            str += user.getUsername() + ", ";
        }
        return str;
    }
}
