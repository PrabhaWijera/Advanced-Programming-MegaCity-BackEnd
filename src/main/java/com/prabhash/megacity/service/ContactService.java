package com.prabhash.megacity.service;

import com.prabhash.megacity.dto.ContactDTO;

import java.util.List;

public interface ContactService {
    boolean saveContact(ContactDTO contactDTO);
    List<ContactDTO> getAllContacts();
}
