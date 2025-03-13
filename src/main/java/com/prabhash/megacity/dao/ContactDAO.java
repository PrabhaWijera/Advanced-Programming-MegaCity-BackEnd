package com.prabhash.megacity.dao;

import com.prabhash.megacity.entity.ContactEntity;

public interface ContactDAO {
    boolean saveContact(ContactEntity contactEntity);
}
