package com.rheal.security.idm.rest.handler;

import com.rheal.security.idm.ws.exception.IDMException;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
public interface IdentityServiceHandler<Req, Res, Auth> {

	String getOperation();
    Res execute(Req request, Auth authHeader)throws IDMException;
    boolean validate(Req request, Auth authHeader) throws IDMException;
}