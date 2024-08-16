/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.content.service;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.dspace.core.Context;

/**
 * Service interface class for the Contactus object.
 */
public interface ContactusService {

    /**
     * This method sends the feeback email to the recipient passed as parameter
     * @param context current DSpace application context
     * @param request current servlet request
     * @param recipientEmail recipient to which mail is sent
     * @param senderEmail email address of the sender
     * @param senderName name address of the sender
     * @param relationship relationship address of the sender
     * @param related related address of the sender
     * @param message message body
     * @param page page from which user accessed and filled contactus form
     * @throws IOException
     * @throws MessagingException
     */
    public void sendEmail(Context context, HttpServletRequest request, String recipientEmail, String senderEmail, String senderName, 
            String relationship, String related, String message, String page) throws IOException, MessagingException;

}