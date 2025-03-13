package com.prabhash.megacity.service.impl;

import com.prabhash.megacity.dao.ContactDAO;
import com.prabhash.megacity.dao.impl.ContactDAOImpl;
import com.prabhash.megacity.dto.ContactDTO;
import com.prabhash.megacity.entity.ContactEntity;
import com.prabhash.megacity.service.ContactService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactServiceImpl implements ContactService {

    private ContactDAO contactDAO = new ContactDAOImpl();

    @Override
    public boolean saveContact(ContactDTO contactDTO) {
        // Validate the contact data (this can be more detailed depending on your requirements)
        if (contactDTO.getEmail().isEmpty() || contactDTO.getName().isEmpty() || contactDTO.getMessage().isEmpty()) {
            return false;
        }

        // Map DTO to Entity
        ContactEntity contactEntity = new ContactEntity(
                null, // ID will be generated automatically in DB
                contactDTO.getUser_id(),
                contactDTO.getEmail(),
                contactDTO.getName(),
                contactDTO.getMessage()
        );

        // Save contact to database
        return contactDAO.saveContact(contactEntity);
    }

    @Override
    public List<ContactDTO> getAllContacts() {
        List<ContactEntity> contactEntities = contactDAO.getAllContacts();
        List<ContactDTO> contactDTOs = new ArrayList<>();

        for (ContactEntity contactEntity : contactEntities) {
            ContactDTO contactDTO = new ContactDTO(
                    contactEntity.getId(),
                    contactEntity.getUserId(),
                    contactEntity.getEmail(),
                    contactEntity.getName(),
                    contactEntity.getMessage()
            );
            contactDTOs.add(contactDTO);
        }

        return contactDTOs;
    }
}
