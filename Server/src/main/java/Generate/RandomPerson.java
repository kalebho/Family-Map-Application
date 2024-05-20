package Generate;

import JSonParser.FemNames;
import JSonParser.MaleNames;
import JSonParser.SirNames;
import Model.Person;
import Model.User;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Random;

public class RandomPerson {

    public Person generatePerson(User u, String gender) throws FileNotFoundException{
        RandomID randID = new RandomID();
        String personID = randID.generateRandomID();
        String firstName;
        if (gender.equals("m")) {
            firstName = getRandomMale();
        }
        else {
            firstName = getRandomFemale();
        }
        String lastName = getRandomSir();
        Person newPerson = new Person(personID, u.getUsername(), firstName, lastName, gender);
        return newPerson;
    }


    public String getRandomMale() throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader("json/mnames.json");
        MaleNames mNames = gson.fromJson(reader, MaleNames.class);
        String [] maleNames = mNames.getData();
        Random rand = new Random();
        int randomIndex = rand.nextInt(50);
        String randomMale = maleNames[randomIndex];
        return randomMale;
    }

    public String getRandomFemale() throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader("json/fnames.json");
        FemNames fNames = gson.fromJson(reader, FemNames.class);
        String [] femaleNames = fNames.getData();
        Random rand = new Random();
        int randomIndex = rand.nextInt(50);
        String randomFemale = femaleNames[randomIndex];
        return randomFemale;
    }

    public String getRandomSir() throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader("json/snames.json");
        SirNames sNames = gson.fromJson(reader, SirNames.class);
        String [] sirNames = sNames.getData();
        Random rand = new Random();
        int randomIndex = rand.nextInt(50);
        String randomSir = sirNames[randomIndex];
        return randomSir;
    }






}
