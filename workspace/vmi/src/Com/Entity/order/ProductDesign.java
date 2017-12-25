package Com.Entity.order;
// default package

import java.util.Date;


/**
 * ProductDesign entity. @author MyEclipse Persistence Tools
 */

public class ProductDesign  implements java.io.Serializable {


    // Fields    

     private String fid;
     private String fproductname;
     private String fboxpileid;
     private String fnumber;
     private Double fboxlength;
     private Double fboxwidth;
     private Double fboxheight;
     private Double fboardlength;
     private Double fboardwidth;
     private String fmaterial="";
     private Integer fcorrugated;
     private Double ftissueweight;
     private Double fwar1weight;
     private Double fwar2weight;
     private Double fwar3weight;
     private Double fxin1weight;
     private Double fxin2weight;
     private Double fpaperweight;
     private String ftissuefactory="";
     private String fwar1factory="";
     private String fwar2factory="";
     private String fwar3factory="";
     private String fxin1factory="";
     private String fxin2factory="";
     private String fpaperfactory="";
     private String fprintcolor="";
     private Integer famount;
     private Date foverdate;
     private Integer funitestyle;
     private Integer fprintstyle;
     private Integer fsurfacetreatment;
     private String fpackstyle="";
     private String fpackdescription="";
     private Boolean fisclean;
     private Boolean fispackage;
     private String fpackagedescription="";
     private Boolean fislettering;
     private String fletteringescription="";
     private String fdescription="";
     private String ffirstproductid;
     private String fschemedesignid;


    // Constructors

    /** default constructor */
    public ProductDesign() {
    }

	/** minimal constructor */
    public ProductDesign(String fid, String fnumber) {
        this.fid = fid;
        this.fnumber = fnumber;
    }
    

   
    public ProductDesign(String fid, String fproductname, String fboxpileid,
			String fnumber, Double fboxlength, Double fboxwidth,
			Double fboxheight, Double fboardlength, Double fboardwidth,
			String fmaterial, Integer fcorrugated, Double ftissueweight,
			Double fwar1weight, Double fwar2weight, Double fwar3weight,
			Double fxin1weight, Double fxin2weight, Double fpaperweight,
			String ftissuefactory, String fwar1factory, String fwar2factory,
			String fwar3factory, String fxin1factory, String fxin2factory,
			String fpaperfactory, String fprintcolor, Integer famount,
			Date foverdate, Integer funitestyle, Integer fprintstyle,
			Integer fsurfacetreatment, String fpackstyle,
			String fpackdescription, Boolean fisclean, Boolean fispackage,
			String fpackagedescription, Boolean fislettering,
			String fletteringescription, String fdescription,
			String ffirstproductid, String fschemedesignid) {
		super();
		this.fid = fid;
		this.fproductname = fproductname;
		this.fboxpileid = fboxpileid;
		this.fnumber = fnumber;
		this.fboxlength = fboxlength;
		this.fboxwidth = fboxwidth;
		this.fboxheight = fboxheight;
		this.fboardlength = fboardlength;
		this.fboardwidth = fboardwidth;
		this.fmaterial = fmaterial;
		this.fcorrugated = fcorrugated;
		this.ftissueweight = ftissueweight;
		this.fwar1weight = fwar1weight;
		this.fwar2weight = fwar2weight;
		this.fwar3weight = fwar3weight;
		this.fxin1weight = fxin1weight;
		this.fxin2weight = fxin2weight;
		this.fpaperweight = fpaperweight;
		this.ftissuefactory = ftissuefactory;
		this.fwar1factory = fwar1factory;
		this.fwar2factory = fwar2factory;
		this.fwar3factory = fwar3factory;
		this.fxin1factory = fxin1factory;
		this.fxin2factory = fxin2factory;
		this.fpaperfactory = fpaperfactory;
		this.fprintcolor = fprintcolor;
		this.famount = famount;
		this.foverdate = foverdate;
		this.funitestyle = funitestyle;
		this.fprintstyle = fprintstyle;
		this.fsurfacetreatment = fsurfacetreatment;
		this.fpackstyle = fpackstyle;
		this.fpackdescription = fpackdescription;
		this.fisclean = fisclean;
		this.fispackage = fispackage;
		this.fpackagedescription = fpackagedescription;
		this.fislettering = fislettering;
		this.fletteringescription = fletteringescription;
		this.fdescription = fdescription;
		this.ffirstproductid = ffirstproductid;
		this.fschemedesignid = fschemedesignid;
	}

	// Property accessors

    public String getFfirstproductid() {
		return ffirstproductid;
	}

	public void setFfirstproductid(String ffirstproductid) {
		this.ffirstproductid = ffirstproductid;
	}

	public String getFschemedesignid() {
		return fschemedesignid;
	}

	public void setFschemedesignid(String fschemedesignid) {
		this.fschemedesignid = fschemedesignid;
	}

	public String getFid() {
        return this.fid;
    }
    
    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFproductname() {
        return this.fproductname;
    }
    
    public void setFproductname(String fproductname) {
        this.fproductname = fproductname;
    }

    


	public String getFboxpileid() {
		return fboxpileid;
	}

	public void setFboxpileid(String fboxpileid) {
		this.fboxpileid = fboxpileid;
	}

	public String getFnumber() {
        return this.fnumber;
    }
    
    public void setFnumber(String fnumber) {
        this.fnumber = fnumber;
    }

    public Double getFboxlength() {
        return this.fboxlength;
    }
    
    public void setFboxlength(Double fboxlength) {
        this.fboxlength = fboxlength;
    }

    public Double getFboxwidth() {
        return this.fboxwidth;
    }
    
    public void setFboxwidth(Double fboxwidth) {
        this.fboxwidth = fboxwidth;
    }

    public Double getFboxheight() {
        return this.fboxheight;
    }
    
    public void setFboxheight(Double fboxheight) {
        this.fboxheight = fboxheight;
    }

    public Double getFboardlength() {
        return this.fboardlength;
    }
    
    public void setFboardlength(Double fboardlength) {
        this.fboardlength = fboardlength;
    }

    public Double getFboardwidth() {
        return this.fboardwidth;
    }
    
    public void setFboardwidth(Double fboardwidth) {
        this.fboardwidth = fboardwidth;
    }

    public String getFmaterial() {
        return this.fmaterial;
    }
    
    public void setFmaterial(String fmaterial) {
        this.fmaterial = fmaterial;
    }

    public Integer getFcorrugated() {
        return this.fcorrugated;
    }
    
    public void setFcorrugated(Integer fcorrugated) {
        this.fcorrugated = fcorrugated;
    }

    public Double getFtissueweight() {
        return this.ftissueweight;
    }
    
    public void setFtissueweight(Double ftissueweight) {
        this.ftissueweight = ftissueweight;
    }

    public Double getFwar1weight() {
        return this.fwar1weight;
    }
    
    public void setFwar1weight(Double fwar1weight) {
        this.fwar1weight = fwar1weight;
    }

    public Double getFwar2weight() {
        return this.fwar2weight;
    }
    
    public void setFwar2weight(Double fwar2weight) {
        this.fwar2weight = fwar2weight;
    }

    public Double getFwar3weight() {
        return this.fwar3weight;
    }
    
    public void setFwar3weight(Double fwar3weight) {
        this.fwar3weight = fwar3weight;
    }

    public Double getFxin1weight() {
        return this.fxin1weight;
    }
    
    public void setFxin1weight(Double fxin1weight) {
        this.fxin1weight = fxin1weight;
    }

    public Double getFxin2weight() {
        return this.fxin2weight;
    }
    
    public void setFxin2weight(Double fxin2weight) {
        this.fxin2weight = fxin2weight;
    }

    public Double getFpaperweight() {
        return this.fpaperweight;
    }
    
    public void setFpaperweight(Double fpaperweight) {
        this.fpaperweight = fpaperweight;
    }

    public String getFtissuefactory() {
        return this.ftissuefactory;
    }
    
    public void setFtissuefactory(String ftissuefactory) {
        this.ftissuefactory = ftissuefactory;
    }

    public String getFwar1factory() {
        return this.fwar1factory;
    }
    
    public void setFwar1factory(String fwar1factory) {
        this.fwar1factory = fwar1factory;
    }

    public String getFwar2factory() {
        return this.fwar2factory;
    }
    
    public void setFwar2factory(String fwar2factory) {
        this.fwar2factory = fwar2factory;
    }

    public String getFwar3factory() {
        return this.fwar3factory;
    }
    
    public void setFwar3factory(String fwar3factory) {
        this.fwar3factory = fwar3factory;
    }

    public String getFxin1factory() {
        return this.fxin1factory;
    }
    
    public void setFxin1factory(String fxin1factory) {
        this.fxin1factory = fxin1factory;
    }

    public String getFxin2factory() {
        return this.fxin2factory;
    }
    
    public void setFxin2factory(String fxin2factory) {
        this.fxin2factory = fxin2factory;
    }

    public String getFpaperfactory() {
        return this.fpaperfactory;
    }
    
    public void setFpaperfactory(String fpaperfactory) {
        this.fpaperfactory = fpaperfactory;
    }

    public String getFprintcolor() {
        return this.fprintcolor;
    }
    
    public void setFprintcolor(String fprintcolor) {
        this.fprintcolor = fprintcolor;
    }

    public Integer getFamount() {
        return this.famount;
    }
    
    public void setFamount(Integer famount) {
        this.famount = famount;
    }

    public Date getFoverdate() {
        return this.foverdate;
    }
    
    public void setFoverdate(Date foverdate) {
        this.foverdate = foverdate;
    }

    public Integer getFunitestyle() {
        return this.funitestyle;
    }
    
    public void setFunitestyle(Integer funitestyle) {
        this.funitestyle = funitestyle;
    }

    public Integer getFprintstyle() {
        return this.fprintstyle;
    }
    
    public void setFprintstyle(Integer fprintstyle) {
        this.fprintstyle = fprintstyle;
    }

    public Integer getFsurfacetreatment() {
        return this.fsurfacetreatment;
    }
    
    public void setFsurfacetreatment(Integer fsurfacetreatment) {
        this.fsurfacetreatment = fsurfacetreatment;
    }

    public String getFpackstyle() {
        return this.fpackstyle;
    }
    
    public void setFpackstyle(String fpackstyle) {
        this.fpackstyle = fpackstyle;
    }

    public String getFpackdescription() {
        return this.fpackdescription;
    }
    
    public void setFpackdescription(String fpackdescription) {
        this.fpackdescription = fpackdescription;
    }

    public Boolean getFisclean() {
        return this.fisclean;
    }
    
    public void setFisclean(Boolean fisclean) {
        this.fisclean = fisclean;
    }

    public Boolean getFispackage() {
        return this.fispackage;
    }
    
    public void setFispackage(Boolean fispackage) {
        this.fispackage = fispackage;
    }

    public String getFpackagedescription() {
        return this.fpackagedescription;
    }
    
    public void setFpackagedescription(String fpackagedescription) {
        this.fpackagedescription = fpackagedescription;
    }

    public Boolean getFislettering() {
        return this.fislettering;
    }
    
    public void setFislettering(Boolean fislettering) {
        this.fislettering = fislettering;
    }

    public String getFletteringescription() {
        return this.fletteringescription;
    }
    
    public void setFletteringescription(String fletteringescription) {
        this.fletteringescription = fletteringescription;
    }

    public String getFdescription() {
        return this.fdescription;
    }
    
    public void setFdescription(String fdescription) {
        this.fdescription = fdescription;
    }




}