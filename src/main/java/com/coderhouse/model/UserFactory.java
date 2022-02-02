package com.coderhouse.model;

import com.coderhouse.builder.UserBuilder;
import com.coderhouse.model.database.document.UserDocument;
import com.coderhouse.model.request.UserRequest;
import lombok.Data;

@Data
public class UserFactory {
    public UserDocument createUser(UserRequest request) {
        switch (request.getType()) {
            case "Admin":
                return UserBuilder.requestToEntityAdmin(request);
            case "Editor":
                return UserBuilder.requestToEntityEditor(request);
            case "Client":
                return UserBuilder.requestToEntityClient(request);
            default:
                return null;
        }
    }

}