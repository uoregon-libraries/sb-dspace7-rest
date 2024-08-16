/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.app.rest.repository;
import java.io.IOException;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.dspace.app.rest.exception.DSpaceBadRequestException;
import org.dspace.app.rest.exception.DSpaceContactusNotFoundException;
import org.dspace.app.rest.exception.RepositoryMethodNotImplementedException;
import org.dspace.app.rest.exception.UnprocessableEntityException;
import org.dspace.app.rest.model.ContactusRest;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.service.ContactusService;
import org.dspace.core.Context;
import org.dspace.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

/**
 * This is the Repository that takes care of the operations on the {@link ContactusRest} objects
 * 
 * @author Mykhaylo Boychuk (mykhaylo.boychuk@4science.com)
 */
@Component(ContactusRest.CATEGORY + "." + ContactusRest.NAME)
public class ContactusRestRepository extends DSpaceRestRepository<ContactusRest, Integer> {

    @Autowired
    private ContactusService contactusService;
    @Autowired
    private ConfigurationService configurationService;

    @Override
    @PreAuthorize("hasAuthority('AUTHENTICATED')")
    public Page<ContactusRest> findAll(Context context, Pageable pageable) {
        throw new RepositoryMethodNotImplementedException(ContactusRest.NAME, "findAll");
    }

    @Override
    @PreAuthorize("hasAuthority('AUTHENTICATED')")
    public ContactusRest findOne(Context context, Integer id) {
        throw new RepositoryMethodNotImplementedException(ContactusRest.NAME, "findOne");
    }

    @Override
    @PreAuthorize("permitAll()")
    protected ContactusRest createAndReturn(Context context) throws AuthorizeException, SQLException {
        HttpServletRequest req = getRequestService().getCurrentRequest().getHttpServletRequest();
        ObjectMapper mapper = new ObjectMapper();
        ContactusRest contactusRest = null;

        String recipientEmail = configurationService.getProperty("contactus.recipient");
        if (StringUtils.isBlank(recipientEmail)) {
            throw new DSpaceContactusNotFoundException("Contactus cannot be sent at this time, Contactus recipient " +
                "is disabled");
        }

        try {
            contactusRest = mapper.readValue(req.getInputStream(), ContactusRest.class);
        } catch (IOException exIO) {
            throw new UnprocessableEntityException("error parsing the body " + exIO.getMessage(), exIO);
        }

        String senderEmail = contactusRest.getEmail();
        String senderName = contactusRest.getSenderName();
        String relationship = contactusRest.getRelationship();
        String related = contactusRest.getRelated();
        String message = contactusRest.getMessage();

        if (StringUtils.isBlank(senderEmail) || StringUtils.isBlank(senderName) || StringUtils.isBlank(relationship) || StringUtils.isBlank(related) || StringUtils.isBlank(message)) {
            throw new DSpaceBadRequestException("e-mail and message fields are mandatory!");
        }

        try {
            contactusService.sendEmail(context, req, recipientEmail, senderEmail, senderName, relationship, related, message, contactusRest.getPage());
        } catch (IOException | MessagingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Class<ContactusRest> getDomainClass() {
        return ContactusRest.class;
    }

    public ContactusService getContactusService() {
        return contactusService;
    }

    public void setContactusService(ContactusService contactusService) {
        this.contactusService = contactusService;
    }

}