package Services;

import Model.Person;
import Model.User;
import RandomData.RandomDataGenerator;
import RandomData.RandomGenerationData;
import Requests.FillRequest;
import Result.FillResult;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.Database;
import dataAccess.PersonDao;
import dataAccess.UserDao;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This service executes all Daos required to do Event
 */
public class FillService {
    private Database db;
    private RandomGenerationData myData;
    private Map<Person,Boolean> personMap;
    public FillService(Database db) {
        personMap = new HashMap<>();
        this.db = db;
        myData = null;
    }

    /**
     * This generates random people with 4 generations
     * @param userName the request body
     * @return response body
     */
    public FillResult fill(String userName) {
        User userResult = null;
        ArrayList <Integer> indices = new ArrayList<>();
        try {
            Connection conn = db.openConnection();
            UserDao uDao = new UserDao(conn);
            PersonDao pDao = new PersonDao(conn);
            userResult = uDao.QueryUser(userName);
            myData = RandomDataGenerator.getInstance().getData();

            if (userResult == null) {
                throw new DataAccessException("Invalid Name");
            }
            for (int i = 0; i < 3; i++) {
                indices = randomNums();  //Get Random Indices
                ArrayList <String> names = GetRandomFullName(indices); // Gets Random names based on Indices
                MakeRandomPersonObj(names, userName);
                for (Map.Entry<Person, Boolean> entry : personMap.entrySet()) {
                    if (entry.getValue() == false) {
                        MakeRandomPersonObj(names, userName);
                        entry.setValue(true);
                    }
                }

            }

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        //query userName and check that it is in the database
        //call the clear request on the userName
        //for loop running personDao.addperson 4 times which is the default
        return null;
    }
    /**
     * This generates random people with numGenerations
     * @param userName the user to which the new generations will be applied
     * @param numGenerations the number of generations to be made
     * @return response body
     */
    public FillResult fill(String userName, Integer numGenerations) {
        //query userName and check that it is in the database
        //call the clear request on the userName
        //check that numGenerations is a non negative integer
        //for loop running personDao.addperson numGenerations times
        return null;
    }

    private ArrayList<Integer> randomNums() {
        ArrayList <Integer> indices = new ArrayList<>();
        Random genderNumber = new Random();
        int genderIndex = genderNumber.nextInt();
        int gnameNumber = 0;
        int genderAssignment = 0;
        int spouse = 0;
        if (genderIndex % 2 == 0) { //male = 1, female = 0;
            Random fnameNumber = new Random();
            gnameNumber = fnameNumber.nextInt(147);
            genderAssignment = 0;
            spouse = 1;
        }
        else {
            Random mNameNumber = new Random();
            gnameNumber = mNameNumber.nextInt(144);
            genderAssignment = 1;
            spouse = 0;
        }

        Random fnameNumber = new Random();
        int femaleNameIndex = fnameNumber.nextInt(147);

        Random mNameNumber = new Random();
        int maleNameIndex = mNameNumber.nextInt(144);

        Random lNameNumber = new Random();
        int lastNameIndex = lNameNumber.nextInt(152);

        Random locationNumber = new Random();
        int locationIndex = locationNumber.nextInt(978);

        Random spouseGenerator = new Random();
        if (spouse == 0) {
            fnameNumber = new Random();
            spouse = fnameNumber.nextInt(147);
        }
        else {
            mNameNumber = new Random();
            spouse = mNameNumber.nextInt(144);
        }
        indices.add(genderAssignment);
        indices.add(gnameNumber);
        indices.add(femaleNameIndex);
        indices.add(maleNameIndex);
        indices.add(lastNameIndex);
        indices.add(spouse);
        indices.add(locationIndex);
        return indices;
    }

    private ArrayList <String> GetRandomFullName(ArrayList<Integer> indices) {
        int gender = indices.get(0);
        int gnameNumber = indices.get(1);
        int spouseNameIndex = indices.get(5);
        int motherNumber = indices.get(2);
        int fatherNumber = indices.get(3);
        int lastNameIndex = indices.get(4);

        ArrayList <String> names = new ArrayList<>();
        if (gender == 0) {
            names.add("f");
            names.add(myData.getFemaleNames()[gnameNumber]);
            names.add(myData.getMaleNames()[spouseNameIndex]);
        }
        else {
            names.add("m");
            names.add(myData.getMaleNames()[gnameNumber]);
            names.add(myData.getFemaleNames()[spouseNameIndex]);
        }
        names.add(myData.getFemaleNames()[motherNumber]);
        names.add(myData.getMaleNames()[fatherNumber]);
        names.add(myData.getLastNames()[lastNameIndex]);

        return names;
    }

    private void MakeRandomPersonObj(ArrayList <String> names, String userName) {
        Random randomNumber = new Random();
        int uniqueNumber = randomNumber.nextInt(15000);
        String personID = names.get(1) + uniqueNumber;
        String descendant = userName;
        String firstName = names.get(1);
        String spouse = names.get(2);
        String gender = names.get(0);
        String mother = names.get(3);
        String father = names.get(4);
        String lastName = names.get(5);


        Person newPerson = new Person(personID, descendant, firstName, lastName, gender, father, mother, spouse);
        Person newSpouse = MakeRandomSpouse(names, userName);
        personMap.put(newPerson, false);
        personMap.put(newSpouse, false);
    }

    private Person MakeRandomSpouse(ArrayList <String> names, String user) {
        Random randomNumber = new Random();
        int uniqueNumber = randomNumber.nextInt(15000);

        Random fatherRandom = new Random();
        int fatherIndex = fatherRandom.nextInt(144);

        Random motherRandom = new Random();
        int motherIndex = motherRandom.nextInt(147);

        String personID = names.get(2) + uniqueNumber;
        String descendant = user;
        String firstName = names.get(2);
        String lastName = names.get(5);
        String gender = null;
        if (names.get(0) == "m") {
            gender = "f";
        }
        else {
            gender = "m";
        }
        String spouse = names.get(1);
        String father = myData.getMaleNames()[fatherIndex];
        String mother = myData.getFemaleNames()[motherIndex];

        Person newSpouse = new Person(personID, descendant, firstName, lastName, gender, father, mother, spouse);
        return newSpouse;
    }

    private void RecursiveFunctionMakeSpouse() {

    }

}
