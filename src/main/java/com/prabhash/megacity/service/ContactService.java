package com.prabhash.megacity.service;

import com.prabhash.megacity.dto.ContactDTO;

public interface ContactService {
    void saveContact(ContactDTO contactDTO) throws Exception;
}
