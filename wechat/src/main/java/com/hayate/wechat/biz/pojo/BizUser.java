package com.hayate.wechat.biz.pojo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Table(name = "biz_user")
public class BizUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private Byte sex;

    private Byte age;

    private String url;

    private BigDecimal account;

    private BigDecimal account2;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * @return sex
     */
    public Byte getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(Byte sex) {
        this.sex = sex;
    }

    /**
     * @return age
     */
    public Byte getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(Byte age) {
        this.age = age;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * @return account
     */
    public BigDecimal getAccount() {
        return account;
    }

    /**
     * @param account
     */
    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    /**
     * @return account2
     */
    public BigDecimal getAccount2() {
        return account2;
    }

    /**
     * @param account2
     */
    public void setAccount2(BigDecimal account2) {
        this.account2 = account2;
    }

    /**
     * @return create_date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return update_date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}