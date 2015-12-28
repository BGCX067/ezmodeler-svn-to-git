package org.i4change.paypal.payment;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.paypal.soap.api.CountryCodeType;

public class CountryCodeManager {

	private static final Log log = LogFactory.getLog(CountryCodeManager.class);

//	private static CountryCodeManager instance = null;
//
//	private CountryCodeManager() {
//	}
//
//	public static synchronized CountryCodeManager getInstance() {
//		if (instance == null) {
//			instance = new CountryCodeManager();
//		}
//		return instance;
//	}
	
	public static CountryCodeType getCountryCodeTypeByCountryCode(String countryCode) throws Exception {
		
		CountryCodeType countryToken = null;
		
		if (countryCode.equals("AL")) {
			return CountryCodeType.AL;
		} else if (countryCode.equals("DZ")) {
			return CountryCodeType.DZ;
		} else if (countryCode.equals("AS")) {
			return CountryCodeType.AS;
		} else if (countryCode.equals("AD")) {
			return CountryCodeType.AD;
		} else if (countryCode.equals("AI")) {
			return CountryCodeType.AI;
		} else if (countryCode.equals("AG")) {
			return CountryCodeType.AG;
		} else if (countryCode.equals("AR")) {
			return CountryCodeType.AR;
		} else if (countryCode.equals("AM")) {
			return CountryCodeType.AM;
		} else if (countryCode.equals("AU")) {
			return CountryCodeType.AU;
		} else if (countryCode.equals("AT")) {
			return CountryCodeType.AT;
		} else if (countryCode.equals("AZ")) {
			return CountryCodeType.AZ;
		} else if (countryCode.equals("BS")) {
			return CountryCodeType.BS;
		} else if (countryCode.equals("BH")) {
			return CountryCodeType.BH;
		} else if (countryCode.equals("BD")) {
			return CountryCodeType.BD;
		} else if (countryCode.equals("BB")) {
			return CountryCodeType.BB;
		} else if (countryCode.equals("BY")) {
			return CountryCodeType.BY;
		} else if (countryCode.equals("BE")) {
			return CountryCodeType.BE;
		} else if (countryCode.equals("BZ")) {
			return CountryCodeType.BZ;
		} else if (countryCode.equals("BJ")) {
			return CountryCodeType.BJ;
		} else if (countryCode.equals("BM")) {
			return CountryCodeType.BM;
		} else if (countryCode.equals("BO")) {
			return CountryCodeType.BO;
		} else if (countryCode.equals("BA")) {
			return CountryCodeType.BA;
		} else if (countryCode.equals("BW")) {
			return CountryCodeType.BW;
		} else if (countryCode.equals("BR")) {
			return CountryCodeType.BR;
		} else if (countryCode.equals("VG")) {
			return CountryCodeType.VG;
		} else if (countryCode.equals("BN")) {
			return CountryCodeType.BN;
		} else if (countryCode.equals("BG")) {
			return CountryCodeType.BG;
		} else if (countryCode.equals("BF")) {
			return CountryCodeType.BF;
		} else if (countryCode.equals("KH")) {
			return CountryCodeType.KH;
		} else if (countryCode.equals("CM")) {
			return CountryCodeType.CM;
		} else if (countryCode.equals("CA")) {
			return CountryCodeType.CA;
		} else if (countryCode.equals("CV")) {
			return CountryCodeType.CV;
		} else if (countryCode.equals("KY")) {
			return CountryCodeType.KY;
		} else if (countryCode.equals("CL")) {
			return CountryCodeType.CL;
		} else if (countryCode.equals("CN")) {
			return CountryCodeType.CN;
		} else if (countryCode.equals("CO")) {
			return CountryCodeType.CO;
		} else if (countryCode.equals("CK")) {
			return CountryCodeType.CK;
		} else if (countryCode.equals("HR")) {
			return CountryCodeType.HR;
		} else if (countryCode.equals("CY")) {
			return CountryCodeType.CY;
		} else if (countryCode.equals("CZ")) {
			return CountryCodeType.CZ;
		} else if (countryCode.equals("DK")) {
			return CountryCodeType.DK;
		} else if (countryCode.equals("DJ")) {
			return CountryCodeType.DJ;
		} else if (countryCode.equals("DM")) {
			return CountryCodeType.DM;
		} else if (countryCode.equals("DO")) {
			return CountryCodeType.DO;
		} else if (countryCode.equals("EG")) {
			return CountryCodeType.EG;
		} else if (countryCode.equals("SV")) {
			return CountryCodeType.SV;
		} else if (countryCode.equals("EE")) {
			return CountryCodeType.EE;
		} else if (countryCode.equals("FJ")) {
			return CountryCodeType.FJ;
		} else if (countryCode.equals("FI")) {
			return CountryCodeType.FI;
		} else if (countryCode.equals("FR")) {
			return CountryCodeType.FR;
		} else if (countryCode.equals("GF")) {
			return CountryCodeType.GF;
		} else if (countryCode.equals("PF")) {
			return CountryCodeType.PF;
		} else if (countryCode.equals("GA")) {
			return CountryCodeType.GA;
		} else if (countryCode.equals("GE")) {
			return CountryCodeType.GE;
		} else if (countryCode.equals("DE")) {
			return CountryCodeType.DE;
		} else if (countryCode.equals("GH")) {
			return CountryCodeType.GH;
		} else if (countryCode.equals("GI")) {
			return CountryCodeType.GI;
		} else if (countryCode.equals("GR")) {
			return CountryCodeType.GR;
		} else if (countryCode.equals("GD")) {
			return CountryCodeType.GD;
		} else if (countryCode.equals("GP")) {
			return CountryCodeType.GP;
		} else if (countryCode.equals("GU")) {
			return CountryCodeType.GU;
		} else if (countryCode.equals("GT")) {
			return CountryCodeType.GT;
		} else if (countryCode.equals("GN")) {
			return CountryCodeType.GN;
		} else if (countryCode.equals("GY")) {
			return CountryCodeType.GY;
		} else if (countryCode.equals("HT")) {
			return CountryCodeType.HT;
		} else if (countryCode.equals("HN")) {
			return CountryCodeType.HN;
		} else if (countryCode.equals("HU")) {
			return CountryCodeType.HU;
		} else if (countryCode.equals("IS")) {
			return CountryCodeType.IS;
		} else if (countryCode.equals("IN")) {
			return CountryCodeType.IN;
		} else if (countryCode.equals("ID")) {
			return CountryCodeType.ID;
		} else if (countryCode.equals("IE")) {
			return CountryCodeType.IE;
		} else if (countryCode.equals("IL")) {
			return CountryCodeType.IL;
		} else if (countryCode.equals("IT")) {
			return CountryCodeType.TT;
		} else if (countryCode.equals("JM")) {
			return CountryCodeType.JM;
		} else if (countryCode.equals("JP")) {
			return CountryCodeType.JP;
		} else if (countryCode.equals("JO")) {
			return CountryCodeType.JO;
		} else if (countryCode.equals("KZ")) {
			return CountryCodeType.KZ;
		} else if (countryCode.equals("KE")) {
			return CountryCodeType.KE;
		} else if (countryCode.equals("KW")) {
			return CountryCodeType.KW;
		} else if (countryCode.equals("LA")) {
			return CountryCodeType.LA;
		} else if (countryCode.equals("LV")) {
			return CountryCodeType.LV;
		} else if (countryCode.equals("LB")) {
			return CountryCodeType.LB;
		} else if (countryCode.equals("LS")) {
			return CountryCodeType.LS;
		} else if (countryCode.equals("LT")) {
			return CountryCodeType.LT;
		} else if (countryCode.equals("LU")) {
			return CountryCodeType.LU;
		} else if (countryCode.equals("MG")) {
			return CountryCodeType.MG;
		} else if (countryCode.equals("MY")) {
			return CountryCodeType.MY;
		} else if (countryCode.equals("MV")) {
			return CountryCodeType.MV;
		} else if (countryCode.equals("ML")) {
			return CountryCodeType.ML;
		} else if (countryCode.equals("MT")) {
			return CountryCodeType.MT;
		} else if (countryCode.equals("MH")) {
			return CountryCodeType.MH;
		} else if (countryCode.equals("MQ")) {
			return CountryCodeType.MQ;
		} else if (countryCode.equals("MU")) {
			return CountryCodeType.MU;
		} else if (countryCode.equals("MX")) {
			return CountryCodeType.MX;
		} else if (countryCode.equals("FM")) {
			return CountryCodeType.FM;
		} else if (countryCode.equals("MN")) {
			return CountryCodeType.MN;
		} else if (countryCode.equals("MS")) {
			return CountryCodeType.MS;
		} else if (countryCode.equals("MA")) {
			return CountryCodeType.MA;
		} else if (countryCode.equals("MZ")) {
			return CountryCodeType.MZ;
		} else if (countryCode.equals("NA")) {
			return CountryCodeType.NA;
		} else if (countryCode.equals("NP")) {
			return CountryCodeType.NP;
		} else if (countryCode.equals("NL")) {
			return CountryCodeType.NL;
		} else if (countryCode.equals("AN")) {
			return CountryCodeType.AN;
		} else if (countryCode.equals("NZ")) {
			return CountryCodeType.AZ;
		} else if (countryCode.equals("NI")) {
			return CountryCodeType.NI;
		} else if (countryCode.equals("MP")) {
			return CountryCodeType.MP;
		} else if (countryCode.equals("NO")) {
			return CountryCodeType.NO;
		} else if (countryCode.equals("OM")) {
			return CountryCodeType.OM;
		} else if (countryCode.equals("PK")) {
			return CountryCodeType.PK;
		} else if (countryCode.equals("PW")) {
			return CountryCodeType.PW;
		} else if (countryCode.equals("PA")) {
			return CountryCodeType.PA;
		} else if (countryCode.equals("PG")) {
			return CountryCodeType.PG;
		} else if (countryCode.equals("PY")) {
			return CountryCodeType.PY;
		} else if (countryCode.equals("PE")) {
			return CountryCodeType.PE;
		} else if (countryCode.equals("PH")) {
			return CountryCodeType.PH;
		} else if (countryCode.equals("PL")) {
			return CountryCodeType.PL;
		} else if (countryCode.equals("PT")) {
			return CountryCodeType.PT;
		} else if (countryCode.equals("PR")) {
			return CountryCodeType.PR;
		} else if (countryCode.equals("QA")) {
			return CountryCodeType.QA;
		} else if (countryCode.equals("RO")) {
			return CountryCodeType.RO;
		} else if (countryCode.equals("RU")) {
			return CountryCodeType.RU;
		} else if (countryCode.equals("RW")) {
			return CountryCodeType.RW;
		} else if (countryCode.equals("KN")) {
			return CountryCodeType.KN;
		} else if (countryCode.equals("LC")) {
			return CountryCodeType.LC;
		} else if (countryCode.equals("VC")) {
			return CountryCodeType.VC;
		} else if (countryCode.equals("WS")) {
			return CountryCodeType.WS;
		} else if (countryCode.equals("SA")) {
			return CountryCodeType.SA;
		} else if (countryCode.equals("CS")) {
			return CountryCodeType.CS;
		} else if (countryCode.equals("SC")) {
			return CountryCodeType.SC;
		} else if (countryCode.equals("SG")) {
			return CountryCodeType.SG;
		} else if (countryCode.equals("SK")) {
			return CountryCodeType.SK;
		} else if (countryCode.equals("SI")) {
			return CountryCodeType.SI;
		} else if (countryCode.equals("SB")) {
			return CountryCodeType.SB;
		} else if (countryCode.equals("ZA")) {
			return CountryCodeType.ZA;
		} else if (countryCode.equals("ES")) {
			return CountryCodeType.ES;
		} else if (countryCode.equals("LK")) {
			return CountryCodeType.LK;
		} else if (countryCode.equals("SZ")) {
			return CountryCodeType.SZ;
		} else if (countryCode.equals("SE")) {
			return CountryCodeType.SE;
		} else if (countryCode.equals("CH")) {
			return CountryCodeType.CH;
		} else if (countryCode.equals("TW")) {
			return CountryCodeType.TW;
		} else if (countryCode.equals("TH")) {
			return CountryCodeType.TH;
		} else if (countryCode.equals("TG")) {
			return CountryCodeType.TG;
		} else if (countryCode.equals("TO")) {
			return CountryCodeType.TO;
		} else if (countryCode.equals("TT")) {
			return CountryCodeType.TT;
		} else if (countryCode.equals("TN")) {
			return CountryCodeType.TN;
		} else if (countryCode.equals("TR")) {
			return CountryCodeType.TR;
		} else if (countryCode.equals("TM")) {
			return CountryCodeType.TM;
		} else if (countryCode.equals("TC")) {
			return CountryCodeType.TC;
		} else if (countryCode.equals("UG")) {
			return CountryCodeType.UG;
		} else if (countryCode.equals("UA")) {
			return CountryCodeType.UA;
		} else if (countryCode.equals("AE")) {
			return CountryCodeType.AE;
		} else if (countryCode.equals("US")) {
			return CountryCodeType.US;
		} else if (countryCode.equals("UY")) {
			return CountryCodeType.UY;
		} else if (countryCode.equals("UZ")) {
			return CountryCodeType.UZ;
		} else if (countryCode.equals("VU")) {
			return CountryCodeType.VU;
		} else if (countryCode.equals("VE")) {
			return CountryCodeType.VE;
		} else if (countryCode.equals("VN")) {
			return CountryCodeType.VN;
		} else if (countryCode.equals("YE")) {
			return CountryCodeType.YE;
		} else if (countryCode.equals("ZM")) {
			return CountryCodeType.ZM;
		}

		
		return countryToken;
		
	}
	
}
