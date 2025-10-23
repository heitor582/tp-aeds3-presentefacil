package repository.user;

import model.User;
import repository.DBFile;
import repository.ExtensibleHash;

public final class UserRepository extends DBFile<User> {
    ExtensibleHash<IdEmailIndexPair> indirectIndex;

    public UserRepository() throws Exception {
        super(User.class);
        this.indirectIndex = new ExtensibleHash<IdEmailIndexPair>(
            IdEmailIndexPair.class.getConstructor(), 
            5,
            "user/id.email",
            "user/id.email"
        );
    }

    public int create(final User user) throws Exception {
        int id = super.create(user);
        this.indirectIndex.create(IdEmailIndexPair.create(user.getId(), user.getEmail()));
        return id;
    }

    public User findByEmail(final String email){
        int id = -1; 
        User user = null;
        try{
            IdEmailIndexPair pair = this.indirectIndex.read(email.hashCode());
            
            if(pair == null) return null;
            id = pair.getId();
            
            user = super.read(id);
        }catch(final Exception e){
            System.out.println(e.getMessage());
        }

        return user;
    }
    
    @Override
    public boolean delete(int id) throws Exception {
        User user = this.read(id);
        if(user == null) return false;
        this.indirectIndex.delete(user.getEmail().hashCode());
        return super.delete(id);
    }

    public void updateIndirectIndex(final User user, final String oldEmail) throws Exception {
        this.indirectIndex.delete(oldEmail.hashCode());
        this.indirectIndex.create(IdEmailIndexPair.create(user.getId(), user.getEmail()));
    }

}
