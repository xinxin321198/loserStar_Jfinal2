/**
 * WebServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.kaen.sms.webServiceClientEP;

public interface WebServiceSoap extends java.rmi.Remote {
    public com.kaen.sms.webServiceClientEP.MOMsg[] getMOMessage(java.lang.String account, java.lang.String password, int pagesize) throws java.rmi.RemoteException;
    public com.kaen.sms.webServiceClientEP.BusinessType[] getBusinessType(java.lang.String account, java.lang.String password) throws java.rmi.RemoteException;
    public com.kaen.sms.webServiceClientEP.AccountInfo getAccountInfo(java.lang.String account, java.lang.String password) throws java.rmi.RemoteException;
    public int modifyPassword(java.lang.String account, java.lang.String old_password, java.lang.String new_password) throws java.rmi.RemoteException;
    public com.kaen.sms.webServiceClientEP.GsmsResponse post(java.lang.String account, java.lang.String password, com.kaen.sms.webServiceClientEP.MTPacks mtpack) throws java.rmi.RemoteException;
    public com.kaen.sms.webServiceClientEP.MTResponse[] getResponse(java.lang.String account, java.lang.String password, int pageSize) throws java.rmi.RemoteException;
    public com.kaen.sms.webServiceClientEP.MTReport[] getReport(java.lang.String account, java.lang.String password, int pageSize) throws java.rmi.RemoteException;
    public com.kaen.sms.webServiceClientEP.MTResponse[] findResponse(java.lang.String account, java.lang.String password, java.lang.String batchid, java.lang.String mobile, int pageindex, int flag) throws java.rmi.RemoteException;
    public com.kaen.sms.webServiceClientEP.MTReport[] findReport(java.lang.String account, java.lang.String password, java.lang.String batchid, java.lang.String mobile, int pageindex, int flag) throws java.rmi.RemoteException;
    public com.kaen.sms.webServiceClientEP.MediaItems[] setMedias(java.lang.String fullPath) throws java.rmi.RemoteException;
}
