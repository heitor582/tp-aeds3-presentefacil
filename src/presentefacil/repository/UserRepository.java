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

    public User readByEmail(String email){
        int id = 0; 
        User user = null;
        try{
            idEmailIndexPair pair = hash.read(email.hashCode());

            if(pair == null) return null;
            id = pair.getId();
            
            user = super.read(id);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return user;
    }
}
