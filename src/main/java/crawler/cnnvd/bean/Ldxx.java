package crawler.cnnvd.bean;

/**
 * 漏洞信息
 *
 * @author 李斌
 */
public class Ldxx {
    /**
     * 主键
     */
    private String ID;
    /**
     * 名称
     */
    private String NAME;
    /**
     * CNNVD编号
     */
    private String CNNVDBH;
    /**
     * 危害等级
     */
    private String WHDJ;
    /**
     * CVE编号
     */
    private String CVEBH;
    /**
     * 漏洞类型
     */
    private String LDLX;
    /**
     * 发布时间
     */
    private String FBSJ;
    /**
     * 威胁类型
     */
    private String WXLX;
    /**
     * 更新时间
     */
    private String GXSJ;
    /**
     * 厂商
     */
    private String CS;
    /**
     * 漏洞来源
     */
    private String LDLY;
    /**
     * 漏洞简介
     */
    private String LDJJ;
    /**
     * 漏洞公告
     */
    private String LDGG;
    /**
     * 参考网址
     */
    private String CKWZ;
    /**
     * 受影响实体
     */
    private String SYXST;
    /**
     * 补丁
     */
    private String BD;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getCNNVDBH() {
        return CNNVDBH;
    }

    public void setCNNVDBH(String CNNVDBH) {
        this.CNNVDBH = CNNVDBH;
    }

    public String getWHDJ() {
        return WHDJ;
    }

    public void setWHDJ(String WHDJ) {
        this.WHDJ = WHDJ;
    }

    public String getCVEBH() {
        return CVEBH;
    }

    public void setCVEBH(String CVEBH) {
        this.CVEBH = CVEBH;
    }

    public String getLDLX() {
        return LDLX;
    }

    public void setLDLX(String LDLX) {
        this.LDLX = LDLX;
    }

    public String getFBSJ() {
        return FBSJ;
    }

    public void setFBSJ(String FBSJ) {
        this.FBSJ = FBSJ;
    }

    public String getWXLX() {
        return WXLX;
    }

    public void setWXLX(String WXLX) {
        this.WXLX = WXLX;
    }

    public String getGXSJ() {
        return GXSJ;
    }

    public void setGXSJ(String GXSJ) {
        this.GXSJ = GXSJ;
    }

    public String getCS() {
        return CS;
    }

    public void setCS(String CS) {
        this.CS = CS;
    }

    public String getLDLY() {
        return LDLY;
    }

    public void setLDLY(String LDLY) {
        this.LDLY = LDLY;
    }

    public String getLDJJ() {
        return LDJJ;
    }

    public void setLDJJ(String LDJJ) {
        this.LDJJ = LDJJ;
    }

    public String getLDGG() {
        return LDGG;
    }

    public void setLDGG(String LDGG) {
        this.LDGG = LDGG;
    }

    public String getCKWZ() {
        return CKWZ;
    }

    public void setCKWZ(String CKWZ) {
        this.CKWZ = CKWZ;
    }

    public String getSYXST() {
        return SYXST;
    }

    public void setSYXST(String SYXST) {
        this.SYXST = SYXST;
    }

    public String getBD() {
        return BD;
    }

    public void setBD(String BD) {
        this.BD = BD;
    }
}
