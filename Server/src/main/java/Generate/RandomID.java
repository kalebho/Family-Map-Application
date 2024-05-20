package Generate;

import java.util.UUID;

public class RandomID {

    public String generateRandomID() {
        UUID uuid = UUID.randomUUID();
        String myRandomID = uuid.toString();
        return myRandomID;
    }
}
