package controller;

public class UserBasedMenuController extends Controller {
    public static boolean checkUsernameNicknameFormat(String username) {
        return true;
    }
    public static String checkUsername(String username) {
        //call checkUsernameFormat
        //check for repetitive username
        return "";
    }
    public static boolean checkPasswordWeakness(String password) {
        return true;
    }

    public static String checkPassword(String password, String passwordConfirmation) {
        //call checkPasswordWeakness
        //check equality of password and its confirmation
        return "";
    }

    public static boolean checkEmailFormat(String email) {
        return true;
    }

    public String randomPasswordGenerator() {
        return "";
    }

    public String randomSloganGenerator() {
        return "";
    }

    public String captchaGenerator() {
        return "";
    }
}
