package com.hebj.forecast.util;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;

public class WebsUtil {

	/*
	 * TODO:wsdl��ַ�������廷������
	 */
	// private final String wsdl =
	// "http://10.20.76.32:8008/cimiss-web/services/ws?wsdl" ;
	private final String wsdl = "http://10.62.89.55/cimiss-web/services/ws?wsdl";
	private final String targetNamespace = "http://ws";
	private final long timeoutInMilliSeconds = 1000 * 60 * 2; // 2 MINUTE

	/*
	 * web service������񣬻�ȡ���ݣ��������л��ַ�����
	 */
	public String getWsString(String method, String params) {
		Class[] returnTypes = new Class[] { String.class };
		return (String) this.getWsData(method, params, returnTypes);
	}

	public String[][] getWsArray(String method, String params) {
		Class[] returnTypes = new Class[] { String[][].class };
		return (String[][]) this.getWsData(method, params, returnTypes);
	}

	public int storeWsString(String method, String params, String inString) {
		Class[] returnTypes = new Class[] { String.class };
		return Integer.parseInt(this.setWsData(method, params, inString, returnTypes).toString());
	}

	public void outputRst(String[][] data) {
		if (data.length < 1) {
			return;
		}
		// ��1��Ϊ���ֶ���
		for (int iCol = 0; iCol < data[0].length; iCol++) {
			System.out.print(data[0][iCol] + "\t");
		}
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		// ��2�п�ʼΪҪ��ֵ
		for (int iRow = 1; iRow < data.length; iRow++) {
			for (int iCol = 0; iCol < data[iRow].length; iCol++) {
				System.out.print(data[iRow][iCol] + "\t");
			}
			System.out.println();
			// DEMO�У����ֻ���10��
			if (iRow > 10) {
				System.out.println("......");
				break;
			}
		}
	}

	private Object getWsData(String method, String params, Class returnTypes[]) {
		Object response = null;
		try {
			RPCServiceClient wsClient = new RPCServiceClient();
			Options options = wsClient.getOptions();
			// added 2015.11.03
			options.setProperty(HTTPConstants.CHUNKED, false);
			//
			options.setTimeOutInMilliSeconds(this.timeoutInMilliSeconds);
			EndpointReference end = new EndpointReference(wsdl);
			options.setTo(end);
			QName qN = new QName(this.targetNamespace, method);
			String[] param = { params };
			response = wsClient.invokeBlocking(qN, param, returnTypes)[0];
			wsClient.cleanupTransport();
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		return response;
	}

	private Object setWsData(String method, String params, String inString, Class returnTypes[]) {
		Object response = null;
		try {
			RPCServiceClient wsClient = new RPCServiceClient();
			Options options = wsClient.getOptions();
			options.setTimeOutInMilliSeconds(this.timeoutInMilliSeconds);
			EndpointReference end = new EndpointReference(wsdl);
			options.setTo(end);
			QName qN = new QName(this.targetNamespace, method);
			String[] param = { params, inString };
			response = wsClient.invokeBlocking(qN, param, returnTypes)[0];
			wsClient.cleanupTransport();
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		return response;
	}
}
