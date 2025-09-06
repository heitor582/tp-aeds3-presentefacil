package model;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class User extends Entity{
    private String name = "";
    private String email = "";
    private String hashPassword = "";
    private String secretQuestion = "";
    private String secretAnswer = "";

    public User() {}
    private User(
        final int id,
        final String name,
        final String email,
        final String hashPassword,
        final String secretQuestion,
        final String secretAnswer
    ) {
        super(id);
        this.name = name;
        this.email = email;
        this.hashPassword = hashPassword;
        this.secretQuestion = secretQuestion;
        this.secretAnswer = secretAnswer;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setHashPassword(String hashPassword){
        this.hashPassword = hashPassword;
    }
    public String getHashPassword(){
        return this.hashPassword;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {this.email = email;}
    public String getSecretQuestion(){
        return this.secretQuestion;
    }
    public void setSecretQuestion(String secretQuestion) { this.secretQuestion = secretQuestion;}
    public String getSecretAnswer() {return this.secretAnswer;}
    public void setSecretAnswer(String secretAnswer) {this.secretAnswer = secretAnswer;}
    public static User from(
        final String name,
        final String email,
        final String hashPassword,
        final String secretQuestion,
        final String secretAnswer
    ) {
        return new User(-1, name, email, hashPassword, secretQuestion, secretAnswer);
    }

    @Override
    public void fromByteArray(byte[] array) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(array);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.name = dis.readUTF();
        this.email = dis.readUTF();
        this.hashPassword = dis.readUTF();
        this.secretQuestion = dis.readUTF();
        this.secretAnswer = dis.readUTF();
    }
    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.name);
        dos.writeUTF(this.email);
        dos.writeUTF(this.hashPassword);
        dos.writeUTF(this.secretQuestion);
        dos.writeUTF(this.secretAnswer);
        return baos.toByteArray();
    }
}
