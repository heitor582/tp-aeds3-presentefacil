package repository;

import model.User;

public class UserRepository extends DBFile<User> {
    ExtensibleHash<IdEmailIndexPair> indrectIndex;

    public UserRepository() throws Exception {
        super(User.class);
        indrectIndex = new ExtensibleHash<IdEmailIndexPair>(
            IdEmailIndexPair.class.getConstructor(), 
            5,
            "user/id.email",
            "user/id.email"
        );
    }

    public int create(final User user) throws Exception{
        int id = super.create(user);
        indrectIndex.create(IdEmailIndexPair.create(user.getId(), user.getEmail()));
        return id;
    }

    public User readByEmail(String email){
        int id = 0; 
        User user = null;
        try{
            IdEmailIndexPair pair = indrectIndex.read(email.hashCode());
            
            if(pair == null) return null;
            id = pair.getId();
            
            user = super.read(id);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return user;
    }
}
