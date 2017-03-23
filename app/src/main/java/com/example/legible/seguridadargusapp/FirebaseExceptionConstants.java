package com.example.legible.seguridadargusapp;

/**
 * Created by sergiosilva on 3/15/17.
 */

public class FirebaseExceptionConstants {
    /**
    ("ERROR_INVALID_CUSTOM_TOKEN", "The custom token format is incorrect. Please check the documentation."));
    ("ERROR_CUSTOM_TOKEN_MISMATCH", "The custom token corresponds to a different audience."));
    ("ERROR_INVALID_CREDENTIAL", "The supplied auth credential is malformed or has expired."));
    +("ERROR_INVALID_EMAIL", "The email address is badly formatted."));
    +("ERROR_WRONG_PASSWORD", "The password is invalid or the user does not have a password."));
    ("ERROR_USER_MISMATCH", "The supplied credentials do not correspond to the previously signed in user."));
    ("ERROR_REQUIRES_RECENT_LOGIN", "This operation is sensitive and requires recent authentication. Log in again before retrying this request."));
    ("ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL", "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address."));
    +("ERROR_EMAIL_ALREADY_IN_USE", "The email address is already in use by another account."));
    ("ERROR_CREDENTIAL_ALREADY_IN_USE", "This credential is already associated with a different user account."));
    +("ERROR_USER_DISABLED", "The user account has been disabled by an administrator."));
    ("ERROR_USER_TOKEN_EXPIRED", "The user\'s credential is no longer valid. The user must sign in again."));
    +("ERROR_USER_NOT_FOUND", "There is no user record corresponding to this identifier. The user may have been deleted."));
    ("ERROR_INVALID_USER_TOKEN", "The user\'s credential is no longer valid. The user must sign in again."));
    ("ERROR_OPERATION_NOT_ALLOWED", "This operation is not allowed. You must enable this service in the console."));
    ("ERROR_WEAK_PASSWORD", "The given password is invalid."));**/

    //Translates to Different Language
    public static String getFirebaseExceptionConstants(String Error){

        switch (Error){
            case "ERROR_WRONG_PASSWORD":
                Error = "Contraseña Incorrecta";
                break;
            case "ERROR_USER_NOT_FOUND":
                Error = "Usuario no Encontrado";
                break;
            case "ERROR_USER_DISABLED":
                Error = "Usuario Baneado";

            case "ERROR_INVALID_EMAIL":
                Error = "Correo Invalido";

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Error = "Error Correo se encuentra activo en otra cuenta";

        }

        return Error;
    }

    public static String getFirebaseExceptionConstantsErrorInputField(String Error){

        String inputField = "";

        switch (Error){
            case "ERROR_WRONG_PASSWORD":
                inputField = "PasswordField";
                break;

            case "ERROR_USER_NOT_FOUND":
            case "ERROR_USER_DISABLED":
            case "ERROR_INVALID_EMAIL":
            case "ERROR_EMAIL_ALREADY_IN_USE":
                inputField = "EmailField";
                break;

        }

        return inputField;

    }



}
