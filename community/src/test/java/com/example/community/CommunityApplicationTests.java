package com.example.community;

import com.example.community.utils.MD5Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

class CommunityApplicationTests {
    public static void main(String[] args) {
        SimpleDateFormat s=new SimpleDateFormat();
        s.applyPattern("yyyy-MM-dd");
        Date date=new Date();
        System.out.println(s.format(date));

    }
}
