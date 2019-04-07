package com.jfinkelstudio.practice1;

import java.util.Random;

public class SolaceWords {

    public String accessDenied(){
        String[] accessDeniedWords = {" Application Privilege is Locked, Please Confirm User Authentication to unlock this feature ", "Please Access User Authentication",
                "Application is Locked, Please Verify Yourself User", "User Authentication Required", "Application feature not accessible, error code 4 3 7 9 8 4 5 6",
                "User Authentication Not Found error code 9 2  7 1 3 4  ", "Error Code 4 5 7 1 5 6   User Authentication not found ", "User Authentication is currently not verified.",
                "User Authentication needs to be verified, error 4 6 2 9 3 4","Please Verify User Authentication registration","Missing User Authentication, Error Code 9 1 4 8 3 6",
                "Application is not available, I am missing user verification "
        };

        Random rand = new Random();
        int maxInt = accessDeniedWords.length;
        int minInt = 0;
        int randomizeContent = rand.nextInt(maxInt) + minInt;
        return(accessDeniedWords[randomizeContent]);
    }
}
