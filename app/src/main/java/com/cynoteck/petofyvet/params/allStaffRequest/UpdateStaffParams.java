package com.cynoteck.petofyvet.params.allStaffRequest;

public class UpdateStaffParams {
    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String encryptedId;

    private String email;

    public String getFirstName ()
    {
        return firstName;
    }

    public void setFirstName (String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName ()
    {
        return lastName;
    }

    public void setLastName (String lastName)
    {
        this.lastName = lastName;
    }

    public String getPhoneNumber ()
    {
        return phoneNumber;
    }

    public void setPhoneNumber (String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getEncryptedId ()
    {
        return encryptedId;
    }

    public void setEncryptedId (String encryptedId)
    {
        this.encryptedId = encryptedId;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [firstName = "+firstName+", lastName = "+lastName+", phoneNumber = "+phoneNumber+", encryptedId = "+encryptedId+", email = "+email+"]";
    }
}