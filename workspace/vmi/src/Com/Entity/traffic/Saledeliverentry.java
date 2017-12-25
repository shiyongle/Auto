package Com.Entity.traffic;
// default package
// Generated 2013-11-21 16:31:28 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

/**
 * TTraSaledeliverentry generated by hbm2java
 */
public class Saledeliverentry implements java.io.Serializable {

	private String fid;
	private Integer fseq;
	private String fparentid;
	private String fsaleorderid;
	private String fdeliverorderid;
	private String fsupplierid;
	private String fproductid;
	private String fproductspec;
	private BigDecimal famount;
	private String freceiveaddress;
	private String freceiver;
	private String freceiverphone;
	private String fdeliveryaddress;
	private String fdelivery;
	private String fdeliveryphone;
	private String fremark;
	private String feasid;
	private String fassembleentryid;
	private BigDecimal frealamount;//实收数量
	private String freceiptorid;
	private Date freceiptdate;
	private Integer fisreceipts;
	private String fmaterialspec;
	private Integer faffirmed;

	public Saledeliverentry() {
	}

	public Saledeliverentry(String fid) {
		this.fid = fid;
	}

	public Saledeliverentry(String fid, Integer fseq, String fparentid,
			String fsaleorderid, String fdeliverorderid, String fsupplierid,
			String fproductid, String fproductspec, BigDecimal famount,
			String freceiveaddress, String freceiver, String freceiverphone,
			String fdeliveryaddress, String fdelivery, String fdeliveryphone,
			String fremark, String feasid, String fassembleentryid, BigDecimal frealamount,
 String freceiptorid,Date freceiptdate,Integer fisreceipts) {
		this.fid = fid;
		this.fseq = fseq;
		this.fparentid = fparentid;
		this.fsaleorderid = fsaleorderid;
		this.fdeliverorderid = fdeliverorderid;
		this.fsupplierid = fsupplierid;
		this.fproductid = fproductid;
		this.fproductspec = fproductspec;
		this.famount = famount;
		this.freceiveaddress = freceiveaddress;
		this.freceiver = freceiver;
		this.freceiverphone = freceiverphone;
		this.fdeliveryaddress = fdeliveryaddress;
		this.fdelivery = fdelivery;
		this.fdeliveryphone = fdeliveryphone;
		this.fremark = fremark;
		this.feasid = feasid;
		this.fassembleentryid = fassembleentryid;
		this.frealamount=frealamount;
		this.freceiptorid=freceiptorid;
		this.freceiptdate=freceiptdate;
		this.fisreceipts=fisreceipts;
	}

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public Integer getFseq() {
		return this.fseq;
	}

	public void setFseq(Integer fseq) {
		this.fseq = fseq;
	}

	public String getFparentid() {
		return this.fparentid;
	}

	public void setFparentid(String fparentid) {
		this.fparentid = fparentid;
	}

	public String getFsaleorderid() {
		return this.fsaleorderid;
	}

	public void setFsaleorderid(String fsaleorderid) {
		this.fsaleorderid = fsaleorderid;
	}

	public String getFdeliverorderid() {
		return this.fdeliverorderid;
	}

	public void setFdeliverorderid(String fdeliverorderid) {
		this.fdeliverorderid = fdeliverorderid;
	}

	public String getFsupplierid() {
		return this.fsupplierid;
	}

	public void setFsupplierid(String fsupplierid) {
		this.fsupplierid = fsupplierid;
	}

	public String getFproductid() {
		return this.fproductid;
	}

	public void setFproductid(String fproductid) {
		this.fproductid = fproductid;
	}

	public String getFproductspec() {
		return this.fproductspec;
	}

	public void setFproductspec(String fproductspec) {
		this.fproductspec = fproductspec;
	}

	public BigDecimal getFamount() {
		return this.famount;
	}

	public void setFamount(BigDecimal famount) {
		this.famount = famount;
	}

	public String getFreceiveaddress() {
		return this.freceiveaddress;
	}

	public void setFreceiveaddress(String freceiveaddress) {
		this.freceiveaddress = freceiveaddress;
	}

	public String getFreceiver() {
		return this.freceiver;
	}

	public void setFreceiver(String freceiver) {
		this.freceiver = freceiver;
	}

	public String getFreceiverphone() {
		return this.freceiverphone;
	}

	public void setFreceiverphone(String freceiverphone) {
		this.freceiverphone = freceiverphone;
	}

	public String getFdeliveryaddress() {
		return this.fdeliveryaddress;
	}

	public void setFdeliveryaddress(String fdeliveryaddress) {
		this.fdeliveryaddress = fdeliveryaddress;
	}

	public String getFdelivery() {
		return this.fdelivery;
	}

	public void setFdelivery(String fdelivery) {
		this.fdelivery = fdelivery;
	}

	public String getFdeliveryphone() {
		return this.fdeliveryphone;
	}

	public void setFdeliveryphone(String fdeliveryphone) {
		this.fdeliveryphone = fdeliveryphone;
	}

	public String getFremark() {
		return this.fremark;
	}

	public void setFremark(String fremark) {
		this.fremark = fremark;
	}

	public String getFeasid() {
		return feasid;
	}

	public void setFeasid(String feasid) {
		this.feasid = feasid;
	}

	public String getFassembleentryid() {
		return fassembleentryid;
	}

	public void setFassembleentryid(String fassembleentryid) {
		this.fassembleentryid = fassembleentryid;
	}

	public BigDecimal getFrealamount() {
		return frealamount;
	}

	public void setFrealamount(BigDecimal frealamount) {
		this.frealamount = frealamount;
	}



	public Date getFreceiptdate() {
		return freceiptdate;
	}

	public void setFreceiptdate(Date freceiptdate) {
		this.freceiptdate = freceiptdate;
	}

	public Integer getFisreceipts() {
		return fisreceipts;
	}

	public void setFisreceipts(Integer fisreceipts) {
		this.fisreceipts = fisreceipts;
	}

	public String getFreceiptorid() {
		return freceiptorid;
	}

	public void setFreceiptorid(String freceiptorid) {
		this.freceiptorid = freceiptorid;
	}

	public String getFmaterialspec() {
		return fmaterialspec;
	}

	public void setFmaterialspec(String fmaterialspec) {
		this.fmaterialspec = fmaterialspec;
	}

	public Integer getFaffirmed() {
		return faffirmed;
	}

	public void setFaffirmed(Integer faffirmed) {
		this.faffirmed = faffirmed;
	}

}
