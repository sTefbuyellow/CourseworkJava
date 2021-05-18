package service;

import java.util.ArrayList;

/**
 * A <code>ValidationService</code> class contains methods
 * to validate users input.
 *
 * @author Kirichuk K.N.
 * @version 0.01 07.03.2021
 */
public class ValidatorService {


    /**
     * Validates a student object.
     *
     * @param firstName <code>String</code> object of students first name
     * @param secondName <code>String</code> object of students second name
     * @param fathersName <code>String</code> object of students fathers name
     * @param groupNumber <code>long</code> value of students group number
     * @param course <code>int</code> value of students course
     * @param roomId <code>int</code> values of students room
     * @return array of <code>boolean</code> witch contains fields validation results
     */
    public static boolean[] isStudentNotValid(String firstName, String secondName, String fathersName,
                                         String groupNumber, String course, String roomId) {
        boolean[] fields = new boolean[]{false, false, false, false, false, false};
        if(stringValidation(secondName))
            fields[0] = true;
        if(stringValidation(firstName))
            fields[1] = true;
        if(stringValidation(fathersName))
            fields[2] = true;
        if (numberValidation(groupNumber))
            fields[3] = true;
        if (numberValidation(course))
            fields[4] = true;
        if (numberValidation(roomId))
            fields[5] = true;
        return fields;
    }

    /**
     * Validates a room object.
     *
     * @param roomNumber <code>int</code> value of room number
     * @param flore <code>int</code> value of room flore
     * @param bedsCount <code>int</code> value of room beds count
     * @return array of <code>boolean</code> witch contains fields validation results
     */
    public static boolean[] isRoomNotValid(String roomNumber, String flore, String bedsCount) {
        boolean[] fields = new boolean[]{false, false, false};
        if (numberValidation(roomNumber))
            fields[0] = true;
        if (numberValidation(flore))
            fields[1] = true;
        if (numberValidation(bedsCount))
            fields[2] = true;
        return fields;
    }

    /**
     * Checks if fields of room object are realistic.
     *
     * @param roomNumber <code>int</code> value of room number
     * @param flore <code>int</code> value of room flore
     * @param bedsCount <code>int</code> value of room beds count
     * @return array of <code>boolean</code> witch contains fields validation results
     */
    public static boolean[] isRoomNumberFieldsNotValid(String roomNumber, String flore, String bedsCount) {
        boolean[] fields = new boolean[]{false, false, false};
        if (Integer.parseInt(roomNumber) > 10000)
            fields[0] = true;
        if (Integer.parseInt(flore) > 20)
            fields[1] = true;
        if (Integer.parseInt(bedsCount) > 6)
            fields[2] = true;
        return fields;
    }

    /**
     * Checks if value of field "course" is realistic.
     *
     * @param course <code>int</code> value of students course
     * @return <code>true</code> if field is valid and <code>false</code> otherwise
     */
    public static boolean isStudentNumberFieldsNotValid(String course) {
        return Integer.parseInt(course) > 6;
    }

    /**
     * Validates numeric value
     *
     * @param number <code>String</code> value of numeric input
     * @return <code>true</code> if number do not contains any
     * non numeric values and <code>false</code> otherwise
     */
    public static boolean numberValidation(String number) {
        if (number.length() == 0)
            return true;
        return !number.equals(number.replaceAll("[^\\d]", ""));
    }

    /**
     * Validates literal value.
     *
     * @param string <code>String</code> value of literal input
     * @return <code>true</code> if string do not contains any
     * non literal values and <code>false</code> otherwise
     */
    public static boolean stringValidation(String string) {
        if (string.length() == 0)
            return true;
        return !string.equals(string.replaceAll("[^А-Яа-яЁё\\s]", ""));
    }


    /**
     * Creates array of error text, witch will be displayed on error message.
     *
     * @param errors array of <code>boolean</code> witch contains fields validation results
     * @return array of <code>String</code> object witch contains error text
     */
    public static String[] generateStudentErrorString(boolean[] errors) {
        ArrayList<String> errorMassages = new ArrayList<>();
        errorMassages.add("Ошибка: ");
        if (errors[0]) {
            errorMassages.add("Поле фамилия быть заполнено");
            errorMassages.add("Фомат: только кириллические символы");
        }
        if (errors[1]) {
            errorMassages.add("Поле имя должно быть заполнено");
            errorMassages.add("Фомат: только кириллические символы");
        }
        if (errors[2]) {
            errorMassages.add("Поле отчество должно быть заполнено");
            errorMassages.add("Фомат: только кириллические символы");
        }
        if (errors[4]) {
            errorMassages.add("Поле курс должно быть заполнено");
            errorMassages.add("Фомат: только цифры");
        }
        if (errors[5]) {
            errorMassages.add("Поле группа должно быть заполнено");
            errorMassages.add("Фомат: только цифры");
        }
        return errorMassages.toArray(String[]::new);
    }

    /**
     * Creates array of error text, witch will be displayed on error message.
     *
     * @param errors array of <code>boolean</code> witch contains fields validation results
     * @return array of <code>String</code> object witch contains error text
     */
    public static String[] generateRoomErrorString(boolean[] errors){
        ArrayList<String> errorMassages = new ArrayList<>();
        errorMassages.add("Ошибка: ");
        if(errors[0]) {
            errorMassages.add("Поле номер комнаты должно быть заполнено");
            errorMassages.add("Фомат: только числа до 10000");
        }
        if(errors[1]) {
            errorMassages.add("Поле этаж должно быть заполнено");
            errorMassages.add("Фомат: только числа до 20");
        }
        if(errors[2]) {
            errorMassages.add("Поле количество мест должно быть заполнено");
            errorMassages.add("Фомат: только числа до 6");
        }
        return errorMassages.toArray(String[]::new);
    }
}
