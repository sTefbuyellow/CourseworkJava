package service.implementations;

import model.Sex;

public class ValidatorService {

    public static boolean isStudentValid(String firstName, String secondName, String fathersName,
                                         String groupNumber, String course, String roomId) {
        if(!stringValidation(firstName))
            return false;
        if(!stringValidation(secondName))
            return false;
        if(!stringValidation(fathersName))
            return false;
        if (!numberValidation(groupNumber))
            return false;
        if (!numberValidation(course))
            return false;
        if (!numberValidation(roomId))
            return false;
        return true;
    }

    public static boolean isRoomValid(String roomNumber, String flore, String bedsCount) {
        if (!numberValidation(roomNumber))
            return false;
        if (!numberValidation(flore))
            return false;
        if (!numberValidation(bedsCount))
            return false;
        return true;
    }

    public static boolean isRoomNumberFieldsValid(String roomNumber, String flore, String bedsCount) {
        if (Integer.parseInt(roomNumber) > 2000)
            return false;
        if (Integer.parseInt(flore) > 10)
            return false;
        if (Integer.parseInt(bedsCount) > 5)
            return false;
        return true;
    }

    public static boolean isStudentNumberFieldsValid(String course) {
        if (Integer.parseInt(course) > 6)
            return false;
        return true;
    }

    public static boolean numberValidation(String number) {
        if (number.length() == 0)
            return false;
        return number.equals(number.replaceAll("[^\\d]", ""));
    }

    public static boolean stringValidation(String string) {
        if (string.length() == 0)
            return false;
        return string.equals(string.replaceAll("[^А-Яа-яЁё\\s]", ""));
    }
}
