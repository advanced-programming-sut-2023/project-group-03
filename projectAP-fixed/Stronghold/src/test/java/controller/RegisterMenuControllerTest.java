package controller;

import Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;
import static controller.ControllerFunctions.*;
import static view.Enums.SignUpMenuCommands.*;
import static controller.Enums.Response.*;

class RegisterMenuControllerTest {
    Matcher matcher;
    Scanner scanner = null;
    RegisterMenuController registerMenuController = new RegisterMenuController();

    @Test
    void invalidRegisterCommand() {
        matcher = getMatcher("user create -u username -p password password -e email -n nickname dard", NEW_USER.getRegex());
        assertEquals(INVALID_COMMAND.getOutput(), registerMenuController.registerNewUser(matcher, scanner), "with all options");
        matcher = getMatcher("user create -u username -p password password dard", NEW_USER.getRegex());
        assertEquals(INVALID_COMMAND.getOutput(), registerMenuController.registerNewUser(matcher, scanner), "with few options");
        matcher = getMatcher("user create -u username -p pAssw0r! -e email -n nickname", NEW_USER.getRegex());
        assertNotEquals(INVALID_COMMAND.getOutput(), registerMenuController.registerNewUser(matcher, scanner), "missing confirmation");
    }

    @Test
    void invalidUsernameNickname() {
        matcher = getMatcher("user create -u username! -p pAssw0r! pAssw0r! -e email@gmail.com -n nickname", NEW_USER.getRegex());
        assertEquals(INVALID_USERNAME_FORMAT.getOutput(), registerMenuController.registerNewUser(matcher, scanner),
                "weird character username");
        matcher = getMatcher("user create -u username -p pAssw0r! pAssw0r! -e email@gmail.com -n nickna!me", NEW_USER.getRegex());
        assertEquals(INVALID_NICKNAME_FORMAT.getOutput(), registerMenuController.registerNewUser(matcher, scanner),
                "weird character nickname");
    }

    @Test
    void invalidPassword() {
        matcher = getMatcher("user create -u username -p password1! pAssw0r! -e email@gmail.com -n nickname", NEW_USER.getRegex());
        assertEquals(WEAK_PASSWORD.getOutput(), registerMenuController.registerNewUser(matcher, scanner),
                "missing capital letters");
        matcher = getMatcher("user create -u username -p PASSWORD1! pAssw0r! -e email@gmail.com -n nickname", NEW_USER.getRegex());
        assertEquals(WEAK_PASSWORD.getOutput(), registerMenuController.registerNewUser(matcher, scanner),
                "missing small letters");
        matcher = getMatcher("user create -u username -p pAsswr!!! pAssw0r! -e email@gmail.com -n nickname", NEW_USER.getRegex());
        assertEquals(WEAK_PASSWORD.getOutput(), registerMenuController.registerNewUser(matcher, scanner),
                "missing numbers");
        matcher = getMatcher("user create -u username -p pAssw0r! pAssw0rdfdf -e email@gmail.com -n nickname", NEW_USER.getRegex());
        assertNotEquals(WEAK_PASSWORD.getOutput(), registerMenuController.registerNewUser(matcher, scanner),
                "just small letters");
    }

    @Test
    void passwordConfirmation() {
        matcher = getMatcher("user create -u username -p pAsswor!2 pAssw0r! -e email@gmail.com -n nickname", NEW_USER.getRegex());
        assertEquals(WRONG_PASSWORD_CONFIRMATION.getOutput(), registerMenuController.registerNewUser(matcher, scanner));
    }

    @Test
    void invalidEmail() {
        matcher = getMatcher("user create -u username -p pAssw0r! pAssw0r! -e email.com -n nickname", NEW_USER.getRegex());
        assertEquals(INVALID_EMAIL_FORMAT.getOutput(), registerMenuController.registerNewUser(matcher, scanner),
                "no @");
        matcher = getMatcher("user create -u username -p pAssw0r! pAssw0r! -e email@com -n nickname", NEW_USER.getRegex());
        assertEquals(INVALID_EMAIL_FORMAT.getOutput(), registerMenuController.registerNewUser(matcher, scanner),
                "no .");
        matcher = getMatcher("user create -u username -p pAssw0r! pAssw0r! -e email -n nickname", NEW_USER.getRegex());
        assertEquals(INVALID_EMAIL_FORMAT.getOutput(), registerMenuController.registerNewUser(matcher, scanner),
                "neither @ nor .");
    }

    @Test
    void usernameExist() {
        new User("pAssw0r!", "ali", "ali", "ali@gmail.com", null);
        matcher = getMatcher("user create -u ali -p pAssw0r! pAssw0r! -e email@dfd.com -n nickname", NEW_USER.getRegex());
        assertEquals(REPETITIVE_USERNAME.getOutput(), registerMenuController.registerNewUser(matcher, scanner));
    }

    @Test
    void emailExist() {
        new User("pAssw0r!", "ali", "ali", "ali@gmail.com", null);
        matcher = getMatcher("user create -u ali2 -p pAssw0r! pAssw0r! -e ali@gmail.com -n nickname", NEW_USER.getRegex());
        assertEquals(REPETITIVE_EMAIL.getOutput(), registerMenuController.registerNewUser(matcher, scanner));
    }

    @Test
    void checkQuestionPickNumber() {
        int questionAmount = UserBasedMenuController.securityQuestions.size();
    }

    @Test
    void properInput() {
        matcher = getMatcher("user create -u ali2 -p pAssw0r! pAssw0r! -e ali@gm34ail.com -n nickname", NEW_USER.getRegex());
        assertThrows(NullPointerException.class, () -> registerMenuController.registerNewUser(matcher, scanner));
    }

}