package service.implementations;

import model.Sex;

public class ValidatorService {

    public static boolean isStudentValid(String firstName, String secondName, String fathersName,
                                         Sex sex, String groupNumber, String course, String roomId){
      return true;
    }

    public static boolean isRoomValid(String roomNumber, String flore, String bedsCount){
        if(!numberValidation(roomNumber))
            return false;
        if(!numberValidation(flore))
            return false;
        if(!numberValidation(bedsCount))
            return false;
        return true;
    }

    public static boolean isRoomNumberFieldsValid(String roomNumber, String flore, String bedsCount){
        if(Integer.parseInt(roomNumber) > 2000)
            return false;
        if(Integer.parseInt(flore) > 10)
            return false;
        if(Integer.parseInt(bedsCount) > 5)
            return false;
        return true;
    }

    public static boolean numberValidation(String number){
        if(number.length() == 0)
            return false;
        return number.equals(number.replaceAll("[A-Za-zА-Яа-яЁё]", ""));
    }
}
