package com.ricston.proxyvalidation;


import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;

public class FaultInterceptor extends AbstractSoapInterceptor {

    public FaultInterceptor() {
        super(Phase.MARSHAL);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        Fault fault = (Fault) message.getContent(Exception.class);

        if (fault.getCode() != null && fault.getCode().equals("READ_VALIDATION_ERROR")) {
            Element detail = fault.getOrCreateDetail();
            Element errorDetail = detail.getOwnerDocument().createElement("error-reply");
            Element error = errorDetail.getOwnerDocument().createElement("error");
            Element extRefNumber = errorDetail.getOwnerDocument().createElement("ExternalReferenceNumber");
            Element partnerId = errorDetail.getOwnerDocument().createElement("PartnerID");

            partnerId.setTextContent("123");
            extRefNumber.setTextContent("321");
            error.setTextContent("Contact the administrator");

            errorDetail.appendChild(error);
            errorDetail.appendChild(extRefNumber);
            errorDetail.appendChild(partnerId);
            detail.appendChild(errorDetail);
        }
    }
}
