package com.prabhash.megacity.dao;

import com.prabhash.megacity.entity.ContactEntity;

import java.util.List;

public interface ContactDAO {
    boolean saveContact(ContactEntity contactEntity);
    List<ContactEntity> getAllContacts();
}
