/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.app.rest.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.dspace.app.rest.RestResourceController;

/**
 * The REST object for the Contactus objects
 */
public class ContactusRest extends BaseObjectRest<Integer> {

    private static final long serialVersionUID = 1L;

    public static final String NAME = "contactus";
    public static final String CATEGORY = RestAddressableModel.TOOLS;

    private String page;
    private String email;
    private String senderName;
    private String relationship;
    private String related;
    private String message;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public void setRelated(String related) {
        this.related = related;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getRelationship() {
        return relationship;
    }

    public String getRelated() {
        return related;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Override
    @JsonIgnore
    public Integer getId() {
        return id;
    }

    @Override
    @JsonIgnore
    public String getType() {
        return NAME;
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class getController() {
        return RestResourceController.class;
    }

}