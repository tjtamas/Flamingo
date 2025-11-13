package hu.flamingo.app.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "mt_identifier")
    private String mtIdentifier;

    @Column(name = "msisdn")
    private String msisdn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Segment segment;

    // --- Termék kapcsolatok ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_mobil_hang_id")
    private Product mobilHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_mobil_adat_id")
    private Product mobilAdat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_vezetekes_net_id")
    private Product vezetekesNet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_vezetekes_tv_id")
    private Product vezetekesTv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_vezetekes_hang_id")
    private Product vezetekesHang;

    // --- Egyéb mezők ---
    private Integer huseg;
    private Boolean telekomKosar;
    private Boolean hitelkartya;
    private Integer digitalis;
    private Integer nonCore;
    private Integer minoseg;

    @Column(name = "total_vbs")
    private Integer totalVbs;

    // ----- Constructors -----
    public Sale() {}

    public Sale(LocalDate date, User user) {
        this.date = date;
        this.user = user;
    }

    // ----- GETTERS + SETTERS -----
    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMtIdentifier() {
        return mtIdentifier;
    }

    public void setMtIdentifier(String mtIdentifier) {
        this.mtIdentifier = mtIdentifier;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public Product getMobilHang() {
        return mobilHang;
    }

    public void setMobilHang(Product mobilHang) {
        this.mobilHang = mobilHang;
    }

    public Product getMobilAdat() {
        return mobilAdat;
    }

    public void setMobilAdat(Product mobilAdat) {
        this.mobilAdat = mobilAdat;
    }

    public Product getVezetekesNet() {
        return vezetekesNet;
    }

    public void setVezetekesNet(Product vezetekesNet) {
        this.vezetekesNet = vezetekesNet;
    }

    public Product getVezetekesTv() {
        return vezetekesTv;
    }

    public void setVezetekesTv(Product vezetekesTv) {
        this.vezetekesTv = vezetekesTv;
    }

    public Product getVezetekesHang() {
        return vezetekesHang;
    }

    public void setVezetekesHang(Product vezetekesHang) {
        this.vezetekesHang = vezetekesHang;
    }

    public Integer getHuseg() {
        return huseg;
    }

    public void setHuseg(Integer huseg) {
        this.huseg = huseg;
    }

    public Boolean getTelekomKosar() {
        return telekomKosar;
    }

    public void setTelekomKosar(Boolean telekomKosar) {
        this.telekomKosar = telekomKosar;
    }

    public Boolean getHitelkartya() {
        return hitelkartya;
    }

    public void setHitelkartya(Boolean hitelkartya) {
        this.hitelkartya = hitelkartya;
    }

    public Integer getDigitalis() {
        return digitalis;
    }

    public void setDigitalis(Integer digitalis) {
        this.digitalis = digitalis;
    }

    public Integer getNonCore() {
        return nonCore;
    }

    public void setNonCore(Integer nonCore) {
        this.nonCore = nonCore;
    }

    public Integer getMinoseg() {
        return minoseg;
    }

    public void setMinoseg(Integer minoseg) {
        this.minoseg = minoseg;
    }

    public Integer getTotalVbs() {
        return totalVbs;
    }

    public void setTotalVbs(Integer totalVbs) {
        this.totalVbs = totalVbs;
    }
}
