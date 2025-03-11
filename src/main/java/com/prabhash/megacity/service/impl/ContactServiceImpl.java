package com.prabhash.megacity.service.impl;

import com.prabhash.megacity.dao.ContactDAO;
import com.prabhash.megacity.dao.impl.ContactDAOImpl;
import com.prabhash.megacity.dto.ContactDTO;
import com.prabhash.megacity.service.ContactService;

public class ContactServiceImpl implements ContactService {

    private final ContactDAO contactDAO;

    public ContactServiceImpl() {
        this.contactDAO = new ContactDAOImpl();
    }

    @Override
    public void saveContact(ContactDTO contactDTO) throws Exception {
        contactDAO.saveContact(contactDTO);
    }
}
