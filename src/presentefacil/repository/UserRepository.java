package repository;

import model.User;

public class UserRepository extends DBFile<User> {
    //Para pegar o email do id;
    ExtensibleHash<idEmailIndexPair> hash;

    public UserRepository() throws Exception {
        super(User.class);
        //FALAR COM O HEITOR DPS PARA SABER O NOME QUE ELE COLOCOU; :)
        hash = new ExtensibleHash<idEmailIndexPair>(idEmailIndexPair.class.getConstructor(), 5,"DEFINIR.d.db", "DEFINIR.c.bd");
    }

    public int create(User user) throws Exception{
        return super.create(user);
    }

    public int readBYEmail(String email){
        int id = 0; 

        return id;
    }
}
