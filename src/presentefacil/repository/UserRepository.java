package repository;

import model.User;

public class UserRepository extends DBFile<User> {
    //Para pegar o email do id;
    ExtensibleHash<idEmailIndexPair> indrectIndex;

    public UserRepository() throws Exception {
        super(User.class);
        //FALAR COM O HEITOR DPS PARA SABER O NOME QUE ELE COLOCOU; :)
        indrectIndex = new ExtensibleHash<idEmailIndexPair>(idEmailIndexPair.class.getConstructor(), 5,"data/user/user.id.email.d.db", "data/user/user.id.email.c.db");
    }

    public int create(User user) throws Exception{
        int id = super.create(user);
        indrectIndex.create(idEmailIndexPair.create(user.getId(), user.getEmail()));
        return id;
    }

    public User readByEmail(String email){
        int id = 0; 
        User user = null;
        try{
            idEmailIndexPair pair = indrectIndex.read(email.hashCode());
            
            if(pair == null) return null;
            id = pair.getId();
            
            user = super.read(id);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return user;
    }
}
