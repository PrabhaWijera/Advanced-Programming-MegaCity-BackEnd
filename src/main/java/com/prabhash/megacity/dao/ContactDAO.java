package com.prabhash.megacity.dao;

import com.prabhash.megacity.dto.ContactDTO;

public interface ContactDAO {
    void saveContact(ContactDTO contactDTO) throws Exception;
}
